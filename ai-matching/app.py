from flask import Flask, request, jsonify
from sentence_transformers import SentenceTransformer, util
import fitz  # PyMuPDF
import os
import re

app = Flask(__name__)
model = SentenceTransformer('all-MiniLM-L6-v2')

def extract_text_from_pdf(file_path):
    text = ""
    with fitz.open(file_path) as doc:
        for page in doc:
            text += page.get_text()
    return text

def extract_job_title(text):
    """
    Cố gắng lấy nghề nghiệp chính từ mô tả job
    Ví dụ: 'Cần tuyển backend developer...' → 'backend developer'
    """
    # Ưu tiên cụm sau từ "tuyển", "vị trí", "cần",...
    match = re.search(r"\b(tuyển|vị trí|cần|đang tuyển)\b\s+([^\n,.]+)", text.lower())

    if match:
        return match.group(2).strip()
    return ""  # fallback nếu không thấy

def extract_title_from_cv(text):
    """
    Tìm nghề nghiệp chính từ CV. Ưu tiên các dòng đầu tiên.
    """
    lines = text.strip().split('\n')[:8]  # Chỉ đọc 8 dòng đầu
    for line in lines:
        line = line.lower()
        if any(w in line for w in ['developer', 'engineer', 'tester', 'designer', 'manager', 'receptionist']):
            return line.strip()
    return ""

def normalize_job_title(title):
    """
    Chuyển nghề nghiệp về dạng chuẩn (backend, receptionist, etc.)
    """
    title = title.lower()
    if "backend" in title: return "backend"
    if "frontend" in title: return "frontend"
    if "receptionist" in title or "tiếp tân" in title: return "receptionist"
    if "data" in title: return "data"
    if "tester" in title or "kiểm thử" in title: return "tester"
    if "manager" in title or "quản lý" in title: return "manager"
    if "devops" in title: return "devops"
    return title.strip()

@app.route('/match', methods=['POST'])
def match_cv_job():
    if 'cv' not in request.files or 'job_description' not in request.form:
        return jsonify({"error": "Missing file or job description"}), 400

    cv_file = request.files['cv']
    job_desc = request.form['job_description']

    if not cv_file.filename.endswith('.pdf'):
        return jsonify({"error": "Only PDF files are supported"}), 400

    temp_path = os.path.join("temp_cv.pdf")
    cv_file.save(temp_path)

    try:
        cv_text = extract_text_from_pdf(temp_path)
        job_title = extract_job_title(job_desc)
        cv_title = extract_title_from_cv(cv_text)

        if not job_title:
            job_title = job_desc

        # AI similarity full
        cv_embedding = model.encode(cv_text, convert_to_tensor=True)
        job_desc_embedding = model.encode(job_desc, convert_to_tensor=True)
        sim_total = float(util.cos_sim(cv_embedding, job_desc_embedding)[0])

        # So sánh nghề bằng logic
        norm_cv = normalize_job_title(cv_title)
        norm_job = normalize_job_title(job_title)

        if norm_cv and norm_job:
            if norm_cv == norm_job:
                sim_title = 0.95  # Boost điểm mạnh
            else:
                sim_title = 0.1   # Phạt nếu lệch nghề
        else:
            # fallback cosine nếu không rõ nghề
            if cv_title:
                cv_title_embedding = model.encode(cv_title, convert_to_tensor=True)
                job_title_embedding = model.encode(job_title, convert_to_tensor=True)
                sim_title = float(util.cos_sim(cv_title_embedding, job_title_embedding)[0])
            else:
                sim_title = 0.0

        # Tổng điểm
        weighted_similarity = 0.6 * sim_title + 0.4 * sim_total

        return jsonify({
            "similarity": round(weighted_similarity, 4),
            "percentage": round(weighted_similarity * 100, 2),
            "debug": {
                "cv_title": cv_title,
                "job_title": job_title,
                "normalized_cv": norm_cv,
                "normalized_job": norm_job,
                "sim_title": round(sim_title, 4),
                "sim_total": round(sim_total, 4)
            }
        })

    except Exception as e:
        return jsonify({"error": str(e)}), 500
    finally:
        if os.path.exists(temp_path):
            os.remove(temp_path)


if __name__ == '__main__':
    app.run(debug=True, port=5000)
