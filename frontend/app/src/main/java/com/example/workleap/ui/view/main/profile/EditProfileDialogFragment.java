package com.example.workleap.ui.view.main.profile;

import android.text.TextWatcher;
import android.text.Editable;
import android.text.InputFilter;
import android.view.inputmethod.EditorInfo;
import android.view.Gravity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.workleap.R;
import com.example.workleap.data.model.entity.ApplicantEducation;
import com.example.workleap.data.model.entity.Education;
import com.example.workleap.data.model.entity.Experience;
import com.example.workleap.data.model.entity.Field;
import com.example.workleap.ui.viewmodel.ApplicantViewModel;
import com.example.workleap.utils.ToastUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditProfileDialogFragment extends DialogFragment {
    private String cardType;
    private Spinner spinnerSchool;
    ArrayList<Field> listField;
    private Spinner spinnerInterestedField;
    private Spinner spinnerEduLevel;
    private ArrayList<Education> listEducation;
    private final List<EditText> editTexts = new ArrayList<>();
    ApplicantViewModel applicantViewModel;

    String[] eduLevels = {"BACHELOR", "MASTER", "DOCTOR"};
    public static EditProfileDialogFragment newInstance(String cardType) {
        EditProfileDialogFragment fragment = new EditProfileDialogFragment();
        Bundle args = new Bundle();
        args.putString("cardType", cardType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        cardType = getArguments().getString("cardType");
        Log.e("dialog", String.valueOf(cardType));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_profile, null);

        LinearLayout container = view.findViewById(R.id.fieldsContainer);

        applicantViewModel = new ViewModelProvider(requireActivity()).get(ApplicantViewModel.class);
        applicantViewModel.InitiateRepository(getContext());

        //applicant
        if ("ApplicantName".equals(cardType)) {
            addField(container, "User Name");
            addField(container, "Status");
        } else if ("AboutMe".equals(cardType)) {
            addField(container, "About me");
            editTexts.get(0).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            editTexts.get(0).setGravity(Gravity.TOP | Gravity.START);
            editTexts.get(0).setLines(5);
            editTexts.get(0).setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
            editTexts.get(0).setFilters(new InputFilter[] { new InputFilter.LengthFilter(2000)});
            editTexts.get(0).setText(getArguments().getString("aboutMe"));

        } else if ("ApplicantInfo".equals(cardType)) {
            addField(container, "First Name");
            editTexts.get(0).setFilters(new InputFilter[] { new InputFilter.LengthFilter(15)});
            editTexts.get(0).setText(getArguments().getString("firstName"));
            addField(container, "Last Name");
            editTexts.get(1).setFilters(new InputFilter[] { new InputFilter.LengthFilter(15)});
            editTexts.get(1).setText(getArguments().getString("lastName"));
            //addField(container, "Gender");
            //addField(container, "Age");
            //addField(container, "Date of birth");
            //addField(container, "Mobile");
            //addField(container, "Email");
            addField(container, "Address");
            editTexts.get(2).setFilters(new InputFilter[] { new InputFilter.LengthFilter(30)});
            editTexts.get(2).setText(getArguments().getString("address"));
        }
        else if("ApplicantSkill".equalsIgnoreCase(cardType))
        {
            addField(container, "Skill");
            editTexts.get(0).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() < 3) {
                        editTexts.get(0).setError("Nhập ít nhất 3 ký tự");
                    } else {
                        editTexts.get(0).setError(null); // Xoá lỗi nếu đã đủ
                    }
                }
            });
        }
        else if("ApplicantEdu".equalsIgnoreCase(cardType))
        {
            //list school name
            listEducation = (ArrayList<Education>) getArguments().getSerializable("listEducation");
            spinnerSchool = addSchoolField(container, listEducation, "School");
            /*ArrayList<String> schoolNames = new ArrayList<>();
            if(listEducation != null)
            {
                for (Education edu : listEducation) {
                    schoolNames.add(edu.getUniName());
                }
            }
            else
            {
                Log.e("EditProfileDialog", "list Education null");
            }*/

            /*//add autocompletetextview to dialog
            autoCompleteSchool = new AutoCompleteTextView(getContext());
            autoCompleteSchool.setHint("School name");
            autoCompleteSchool.setThreshold(1); // Số ký tự gõ tối thiểu trước khi hiện dropdown
            autoCompleteSchool.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getContext(), android.R.layout.simple_dropdown_item_1line, schoolNames
            );
            autoCompleteSchool.setAdapter(adapter);
            //editTexts.add(autoCompleteSchool);
            container.addView(autoCompleteSchool);*/


            //eduStart, eduEnd, major, eduLevel, achievement
            addDateField(container, "Year Start");
            addDateField(container, "Year End");
            //addField(container, "Major");
            spinnerEduLevel = addSpinnerField(container, eduLevels, "Edu level");
            addField(container, "Major");
            //addField(container, "Achievement");
        }
        else if ("UpdateApplicantEducation".equalsIgnoreCase(cardType))
        {
            //list school name
            listEducation = (ArrayList<Education>) getArguments().getSerializable("listEducation");
            if(listEducation==null) Log.e("EditProfileDialog", "list Education null");
            spinnerSchool = addSchoolField(container, listEducation, "School");
            int selectedPosition = 0;
            String oldSchoolName = getArguments().getString("schoolName");
            for (int i = 0; i < listEducation.size(); i++) {
                Education edu = listEducation.get(i);
                if (edu.getUniName().equalsIgnoreCase(oldSchoolName)) {
                    selectedPosition = i;
                    break;
                }
            }
            spinnerSchool.setSelection(selectedPosition);

            //eduStart, eduEnd, major, eduLevel, achievement

            addDateField(container, "Year Start");
            editTexts.get(0).setText(getArguments().getString("yearStart"));
            addDateField(container, "Year End");
            editTexts.get(1).setText(getArguments().getString("yearEnd"));

            spinnerEduLevel = addSpinnerField(container, eduLevels, "Edu level");
            int index = 0;
            String oldEduLevel = getArguments().getString("eduLevel");
            for (int i = 0; i < eduLevels.length; i++) {
                if (eduLevels[i].equals(oldEduLevel)) {
                    index = i;
                    break;
                }
            }
            spinnerEduLevel.setSelection(index);

            addField(container, "Major");
            editTexts.get(2).setText(getArguments().getString("major"));
        }
        else if("ApplicantInterestedField".equalsIgnoreCase(cardType))
        {
            listField = (ArrayList<Field>) getArguments().getSerializable("listField");
            //list field name
            ArrayList<String> fieldNames = new ArrayList<>();
            if(listField != null)
            {
                for (Field field : listField) {
                    fieldNames.add(field.getName());
                }
            }
            else
            {
                Log.e("EditProfileDialog", "list Interested field null");
            }

            //spinner
            spinnerInterestedField = new Spinner(getContext());
            spinnerInterestedField.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getContext(), android.R.layout.simple_spinner_item, fieldNames
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerInterestedField.setAdapter(adapter);
            container.addView(spinnerInterestedField);
        }
        else if("ApplicantExperience".equalsIgnoreCase(cardType))
        {
            addField(container, "Company name");
            addField(container, "Company link");
            editTexts.get(1).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    String input = s.toString().trim();
                    String urlPattern = "^https?://[\\w.-]+(?:\\.[\\w\\.-]+)+[/\\w\\.-]*$";

                    if (!input.matches(urlPattern)) {
                        editTexts.get(1).setError("Invalid URL format");
                    } else {
                        editTexts.get(1).setError(null); // Hợp lệ, xoá lỗi
                    }
                }
            });
            addField(container, "Position");
            addDateField(container, "Work start");
            addDateField(container, "Work end");
            addField(container, "Job Responsibility");
        }
        else if("UpdateApplicantExperience".equalsIgnoreCase(cardType))
        {
            addField(container, "Company name");
            editTexts.get(0).setText(getArguments().getString("companyName"));
            addField(container, "Company link");
            editTexts.get(1).setText(getArguments().getString("companyLink"));
            editTexts.get(1).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    String input = s.toString().trim();
                    String urlPattern = "^https?://[\\w.-]+(?:\\.[\\w\\.-]+)+[/\\w\\.-]*$";

                    if (!input.matches(urlPattern)) {
                        editTexts.get(1).setError("Invalid URL format");
                    } else {
                        editTexts.get(1).setError(null); // Hợp lệ, xoá lỗi
                    }
                }
            });
            addField(container, "Position");
            editTexts.get(2).setText(getArguments().getString("position"));
            addDateField(container, "Work start");
            editTexts.get(3).setText(getArguments().getString("yearStart"));
            addDateField(container, "Work end");
            editTexts.get(4).setText(getArguments().getString("yearEnd"));
            addField(container, "Job Responsibility");
            editTexts.get(5).setText(getArguments().getString("jobResponsibility"));
        }
        //company
        else if ("AboutCompany".equalsIgnoreCase(cardType))
        {
            addField(container, "AboutCompany");
            editTexts.get(0).setText(getArguments().getString("aboutCompany"));
        }
        else if ("CompanyInfo".equalsIgnoreCase(cardType))
        {
            addField(container, "Name");
            editTexts.get(0).setText(getArguments().getString("companyName"));
            addField(container, "EstablishedYear");
            editTexts.get(1).setText(getArguments().getString("establishedYear"));
            //addField(container, "Phone");
            //addField(container, "Email");
            addField(container, "Tax code");
            editTexts.get(2).setFilters(new InputFilter[] { new InputFilter.LengthFilter(30)});
            editTexts.get(2).setText(getArguments().getString("taxCode"));
        }


        if("UpdateApplicantEducation".equalsIgnoreCase(cardType))
        {
            AlertDialog dialog = builder
                    .setView(view)
                    .setTitle("Update Applicant Education")
                    .setPositiveButton("Save", null) // tam de null
                    .setNeutralButton("Delete", (dialog1, which) -> {
                        ApplicantEducation deleteEducation = (ApplicantEducation) getArguments().getSerializable("deleteApplicantEducation");
                        applicantViewModel.deleteApplicantEducation(deleteEducation.getId());
                    })
                    .setNegativeButton("Cancel", null)
                    .create();

            dialog.setOnShowListener(dlg -> {
                Button saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                saveButton.setOnClickListener(v -> {
                    ArrayList<String> updated = new ArrayList<>();
                    boolean isValid = true;

                    for (EditText et : editTexts) {
                        String input = et.getText().toString().trim();
                        if (input.isEmpty()) {
                            et.setError("This field is required");
                            et.requestFocus();
                            isValid = false;
                            break;
                        }
                        updated.add(input);
                    }

                    if (!isValid) return;

                    // spinnerEduLevel
                    if (spinnerEduLevel != null && spinnerEduLevel.getSelectedItem() != null) {
                        int position = spinnerEduLevel.getSelectedItemPosition();
                        updated.add(eduLevels[position]);
                    }

                    // spinnerSchool
                    if (spinnerSchool != null && spinnerSchool.getSelectedItem() != null) {
                        int position = spinnerSchool.getSelectedItemPosition();
                        updated.add(listEducation.get(position).getId());
                    }

                    Bundle result = new Bundle();
                    result.putString("cardType", cardType);
                    result.putStringArrayList("values", updated);
                    ApplicantEducation updateApplicantEducation = (ApplicantEducation) getArguments().getSerializable("deleteApplicantEducation");
                    result.putString("deleteApplicantEducationId", updateApplicantEducation.getId());

                    getParentFragmentManager().setFragmentResult("editProfile", result);
                    dialog.dismiss(); // chi dong neu hop le
                });
            });

            return dialog;
        }
        else if("UpdateApplicantExperience".equalsIgnoreCase(cardType))
        {
            builder.setView(view)
                    .setTitle("Update Applicant Experience")
                    .setPositiveButton("Save", null) // để xử lý riêng
                    .setNeutralButton("Delete", (dialog, which) -> {
                        Experience deleteExperience = (Experience) getArguments().getSerializable("deleteApplicantExperience");
                        applicantViewModel.deleteApplicantExperience(deleteExperience.getId());
                        Log.e("dialog", "delete applicant exp");
                    })
                    .setNegativeButton("Cancel", null);

            AlertDialog dialog = builder.create();

            dialog.setOnShowListener(d -> {
                Button saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                saveButton.setOnClickListener(v -> {
                    ArrayList<String> updated = new ArrayList<>();
                    boolean hasEmptyField = false;

                    for (EditText et : editTexts) {
                        String input = et.getText().toString().trim();
                        if (input.isEmpty()) {
                            et.setError("This field is required");
                            et.requestFocus();
                            hasEmptyField = true;
                            break;
                        }
                        updated.add(input);
                    }

                    if (hasEmptyField) return; // khong dong neu co loi

                    // Tạo Bundle và đẩy kết quả
                    Bundle result = new Bundle();
                    result.putString("cardType", cardType);
                    result.putStringArrayList("values", updated);

                    Experience deleteApplicantExperience = (Experience) getArguments().getSerializable("deleteApplicantExperience");
                    result.putString("deleteApplicantExperience", deleteApplicantExperience.getId());

                    getParentFragmentManager()
                            .setFragmentResult("editProfile", result);

                    dialog.dismiss(); // chi dong khi hop le
                });
            });

            return dialog;
        }
        else {
            builder.setView(view)
                    .setTitle("Edit")
                    .setPositiveButton("Save", null) // ngăn auto-dismiss
                    .setNegativeButton("Cancel", null);

            AlertDialog dialog = builder.create();

            dialog.setOnShowListener(d -> {
                Button saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                saveButton.setOnClickListener(v -> {
                    ArrayList<String> updated = new ArrayList<>();
                    boolean hasEmptyField = false;

                    for (EditText et : editTexts) {
                        String input = et.getText().toString().trim();
                        if (input.isEmpty()) {
                            et.setError("This field is required");
                            et.requestFocus();
                            hasEmptyField = true;
                            break;
                        }
                        updated.add(input);
                    }

                    if (hasEmptyField) return; // khong gui va khong dong dialog

                    //check years
                    if("ApplicantExperience".equalsIgnoreCase(cardType)){
                        String yearStartStr = editTexts.get(3).getText().toString().trim();
                        String yearEndStr = editTexts.get(4).getText().toString().trim();

                        if (!yearStartStr.matches("\\d{4}") || !yearEndStr.matches("\\d{4}")) {
                            Toast.makeText(getContext(), "Years must be 4-digit numbers with no spaces", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        int yearStart = Integer.parseInt(yearStartStr);
                        int yearEnd = Integer.parseInt(yearEndStr);
                        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);

                        if (yearStart >= yearEnd) {
                            Toast.makeText(getContext(), "End year must be after start year", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (yearStart > currentYear || yearEnd > currentYear) {
                            Toast.makeText(getContext(), "Years must not exceed current year", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    // ApplicantEdu
                    if ("ApplicantEdu".equalsIgnoreCase(cardType)) {
                        if (spinnerEduLevel != null && spinnerEduLevel.getSelectedItem() != null && !spinnerEduLevel.getSelectedItem().toString().trim().isEmpty()) {
                            int position = spinnerEduLevel.getSelectedItemPosition();
                            String major = eduLevels[position];
                            updated.add(major);
                        }

                        if (spinnerSchool != null && spinnerSchool.getSelectedItem() != null && !spinnerSchool.getSelectedItem().toString().trim().isEmpty()) {
                            int position = spinnerSchool.getSelectedItemPosition();
                            String eduId = listEducation.get(position).getId();
                            updated.add(eduId);
                        }
                    }

                    // ApplicantInterestedField
                    if ("ApplicantInterestedField".equalsIgnoreCase(cardType)) {
                        if (spinnerInterestedField != null && spinnerInterestedField.getSelectedItem() != null && !spinnerInterestedField.getSelectedItem().toString().trim().isEmpty()) {
                            int position = spinnerInterestedField.getSelectedItemPosition();
                            Field field = listField.get(position);
                            updated.add(field.getId());
                        }
                    }

                    Bundle result = new Bundle();
                    result.putString("cardType", cardType);
                    result.putStringArrayList("values", updated);

                    getParentFragmentManager()
                            .setFragmentResult("editProfile", result);

                    dialog.dismiss();
                });
            });

            return dialog;

        }
    }

    private void addField(LinearLayout container, String hint) {
        EditText editText = new EditText(getContext());
        //editText.setHint(hint);
        editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
        editText.setTextSize(16);

        TextView textView = new TextView(getContext());
        textView.setText(hint);
        textView.setTextSize(18);
        textView.setPadding(8, 32, 0,0);
        textView.setTypeface(null, Typeface.BOLD);


        container.addView(textView);
        container.addView(editText);

        editTexts.add(editText);
    }
    private void addDateField(LinearLayout container, String hint) {
        EditText editText = new EditText(getContext());
        editText.setHint(hint);
        editText.setInputType(InputType.TYPE_CLASS_DATETIME);

        TextView textView = new TextView(getContext());
        textView.setText(hint);
        textView.setTextSize(18);
        textView.setPadding(8, 32, 0,8);
        textView.setTypeface(null, Typeface.BOLD);

        container.addView(textView);
        container.addView(editText);

        editTexts.add(editText);
    }
    private Spinner addSpinnerField(LinearLayout container, String[] array, String hint)
    {
        Spinner spinner = new Spinner(getContext());
        spinner.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_item, array
        );
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setPadding(0, 8, 0, 16);

        TextView textView = new TextView(getContext());
        textView.setText(hint);
        textView.setTextSize(18);
        textView.setPadding(8, 32, 0,8);
        textView.setTypeface(null, Typeface.BOLD);

        container.addView(textView);
        container.addView(spinner);
        return spinner;
    }
    private Spinner addSchoolField(LinearLayout container, ArrayList<Education> array, String hint)
    {
        if(array==null) return null;

        Spinner spinner = new Spinner(getContext());
        spinner.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        ArrayList<String> names = new ArrayList<>();
        for (Education education: array)
        {
            names.add(education.getUniName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_item, names
        );
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setPadding(0, 8, 0, 16);

        TextView textView = new TextView(getContext());
        textView.setText(hint);
        textView.setTextSize(18);
        textView.setPadding(8, 32, 0,8);
        textView.setTypeface(null, Typeface.BOLD);

        container.addView(textView);
        container.addView(spinner);
        return spinner;
    }
}
