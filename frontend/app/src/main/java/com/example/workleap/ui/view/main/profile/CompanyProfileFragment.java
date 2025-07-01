package com.example.workleap.ui.view.main.profile;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.workleap.R;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.view.auth.MainActivity;
import com.example.workleap.ui.viewmodel.AuthViewModel;
import com.example.workleap.ui.viewmodel.CompanyViewModel;
import com.example.workleap.ui.viewmodel.UserViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompanyProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompanyProfileFragment extends Fragment {
    View view;
    TextView tvCompanyName;
    TextView tvAboutCompany;
    TextView tvCompanyNameInfo, tvEstablishedYear, tvMailInfo, tvPhoneInfo, tvTaxCode;
    User user;

    AuthViewModel authViewModel;

    CompanyViewModel companyViewModel;
    UserViewModel userViewModel;
    ImageButton btnOptions, btnEditCompanyName, btnEditAboutCompany, btnEditCompanyInfo;
    ImageView avatar;
    private static final int PICK_IMAGE_REQUEST = 1001;
    private Uri imageUri;
    private String currentAvatarUrl;
    private ImageView imagePreview;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CompanyProfileFragment() {
        // Required empty public constructor
    }

    public static CompanyProfileFragment newInstance(String param1, String param2) {
        CompanyProfileFragment fragment = new CompanyProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_company_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user = (User) getArguments().getSerializable("user");
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        authViewModel.InitiateRepository(getContext());
        companyViewModel = new ViewModelProvider(requireActivity()).get(CompanyViewModel.class);
        companyViewModel.InitiateRepository(getContext());
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.InitiateRepository(getContext());

        tvCompanyName = (TextView) view.findViewById(R.id.textView2);

        tvAboutCompany = (TextView) view.findViewById(R.id.textViewAboutCompany);

        tvCompanyNameInfo = (TextView) view.findViewById(R.id.companynameInfo);
        tvEstablishedYear = (TextView) view.findViewById(R.id.textViewEstablishedYear);
        tvMailInfo = (TextView) view.findViewById(R.id.emailInfo);
        tvPhoneInfo= (TextView) view.findViewById(R.id.phoneInfo);
        tvTaxCode = (TextView) view.findViewById(R.id.taxCodeInfo);

        avatar = view.findViewById(R.id.shapeableImageView);

        //tvEstablishedYear.setText();
        tvMailInfo.setText(user.getEmail());
        tvPhoneInfo.setText(user.getPhoneNumber());
        //tvTaxCode.setText();

        btnOptions = view.findViewById(R.id.btnOptions);
        btnEditCompanyName = view.findViewById(R.id.btnEditCompanyName);
        btnEditAboutCompany = view.findViewById(R.id.btnEditAboutCompany);
        btnEditCompanyInfo = view.findViewById(R.id.btnEditCompanyInfo);


        //Lay avatar
        //Observe
        userViewModel.getUrlAvatarResult().observe(getViewLifecycleOwner(), result -> {
            if(result != null)
                Log.d("ApplicantProfile avatar", result);
            else
                Log.d("ApplicantProfile avatar", "getUrlAvatarResult NULL");
        });
        userViewModel.getUrlAvatarData().observe(getViewLifecycleOwner(), data -> {
            if(data != null)
            {
                currentAvatarUrl = data;
                Log.d("get url", data);
                Glide.with(this.getContext()).load(data).into(avatar);
                Log.d("ApplicantProfile avatar", "Set avatar success");
            }
            else
                Log.d("ApplicantProfile avatar", "getUrlAvatarData NULL");
        });
        //Kiem tra va gan avatar khi moi tao view
        if(user.getAvatar() != null)
        {
            Log.d("get url ctvw", user.getAvatar());
            //Load avatar from database
            userViewModel.getAvatarUrl(user.getAvatar());
        }
        else
            Log.d("ApplicantProfile avatar", "user avatar null");

        //observe upload image, cap nhat neu can
        userViewModel.getUploadAvatarData().observe(getViewLifecycleOwner(), data ->
        {
            if(data != null)
            {
                userViewModel.getAvatarUrl(data.getAvatar());
            }
            else
                Log.e("Upload img user", "Upload image data null");
        });
        userViewModel.getUpLoadAvatarResult().observe(getViewLifecycleOwner(), result ->
        {
            if(result != null)
            {
                Log.e("Upload image result", result);
            }
            else
                Log.e("Upload image result", "Upload image result null");
        });


        companyViewModel.getGetCompanyData().observe(getViewLifecycleOwner(), company -> {
            if(!isAdded() || getView()==null) return;
            if (company == null) {
                Log.e("ApplicantProfile", "applicant null");
            } else {
                Log.e("ApplicantProfile", "applicant setText");

                tvCompanyName.setText(company.getName());
                tvAboutCompany.setText(company.getDescription());
                tvEstablishedYear.setText(String.valueOf(company.getEstablishedYear()));
                tvTaxCode.setText(company.getTaxcode());
                tvCompanyNameInfo.setText(company.getName());
            }
        });
        companyViewModel.getGetCompanyResult().observe(getViewLifecycleOwner(), result ->{
            if(!isAdded() || getView()==null) return;
            if(result!=null)
                Log.e("company profile", result);
            else
                Log.e("company profile", "update company result null" );
        });
        companyViewModel.getCompany(user.getCompanyId());
        getParentFragmentManager().setFragmentResultListener(
                "editProfile",
                getViewLifecycleOwner(),
                (requestKey, bundle) -> {
                    String cardType = bundle.getString("cardType");
                    ArrayList<String> values = bundle.getStringArrayList("values");
                    // TODO: xử lý cập nhật UI hoặc gọi ViewModel
                    if ("CompanyInfo".equalsIgnoreCase(cardType) && values != null) {
                        tvCompanyNameInfo.setText(values.get(0));
                        tvEstablishedYear.setText(values.get(1));
                        //tvPhoneInfo.setText(values.get(2));
                        //tvMailInfo.setText(values.get(3));
                        tvTaxCode.setText(values.get(2));
                        Log.e("khoa", "khoa");

                        companyViewModel.updateCompany(user.getCompanyId(), values.get(0), tvAboutCompany.getText().toString(), Integer.parseInt(values.get(1)), values.get(2) );

                        //companyViewModel.updateCompany(user.getCompanyId(), tvCompanyNameInfo.getText().toString(), values.get(0), Integer.parseInt(tvEstablishedYear.getText().toString()), tvTaxCode.getText().toString());
                    }
                    else if ("AboutCompany".equalsIgnoreCase(cardType) && values != null) {
                        Log.e("company profile", "about company update");
                        tvAboutCompany.setText(values.get(0));

                        companyViewModel.updateCompany(user.getCompanyId(), tvCompanyNameInfo.getText().toString(), values.get(0), Integer.parseInt(tvEstablishedYear.getText().toString()), tvTaxCode.getText().toString());
                    }
                }
        );

        btnOptions.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), btnOptions);
            popupMenu.getMenuInflater().inflate(R.menu.menu_options, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.menu_setting) {
                    return true;
                } else if (itemId == R.id.menu_logout) {
                    authViewModel.logout();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    requireActivity().finish();
                    return true;
                }
                return false;
            });

            popupMenu.show();
        });
        btnEditCompanyName.setOnClickListener(v -> {
            EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("CompanyName");
            dialog.show(getParentFragmentManager(), "EditCompanyNameDialog");
        });
        btnEditAboutCompany.setOnClickListener(v -> {
            EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("AboutCompany");

            Bundle args = dialog.getArguments();
            args.putString("aboutCompany", String.valueOf(tvAboutCompany.getText()));
            dialog.setArguments(args);
            dialog.show(getParentFragmentManager(), "EditCompanyAboutCompanyDialog");
        });
        btnEditCompanyInfo.setOnClickListener(v -> {
            EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("CompanyInfo");

            Bundle args = dialog.getArguments();
            args.putString("companyName", String.valueOf(tvCompanyName.getText()));
            args.putString("establishedYear", String.valueOf(tvEstablishedYear.getText()));
            args.putString("taxCode", String.valueOf(tvTaxCode.getText()));
            dialog.setArguments(args);
            dialog.show(getParentFragmentManager(), "EditCompanyInfoDialog");
        });

        //click handle
        //avatar
        avatar.setOnClickListener(v -> {
            showImagePopup(this.requireContext());
        });
    }


    //Handle load avatar
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();

            //Hien thi anh duoc chon len
            imagePreview.setImageURI(imageUri);
        }
    }
    private File uriToFile(Uri uri) {
        File file = new File(requireContext().getCacheDir(), "avatar.jpg");
        try (InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
             OutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 1);
            } else {
                openImagePicker();
            }
        } else {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                openImagePicker();
            }
        }
    }
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public void showImagePopup(Context context) {
        // Inflate layout
        LayoutInflater inflater = LayoutInflater.from(context); // hoặc getContext() nếu trong Fragment
        View view = inflater.inflate(R.layout.dialog_image_popup, null);

        // Tạo dialog
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(false)
                .create();

        // Ánh xạ View
        imagePreview = view.findViewById(R.id.imagePreview);
        ImageButton btnLoad = view.findViewById(R.id.btnLoadImage);
        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        //Set current image preview
        if(currentAvatarUrl != null)
            Glide.with(this).load(currentAvatarUrl).into(imagePreview);

        // Xử lý sự kiện
        btnLoad.setOnClickListener(v -> {
            Log.d("btnLoadImage", "btnLoadImage clicked");
            // Yêu cầu quyền (nếu chưa có)
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                Log.d("btnLoadImage", "xin quyen");
            } else {
                // Mở bộ chọn ảnh
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
                Log.d("btnLoadImage", "mo bo chon anh");
            }
        });

        //Upload and save image
        btnSave.setOnClickListener(v -> {
            //Upload anh dang hien thi len csdl
            //Ket qua
            //Tao file de gui
            if(imageUri != null)
            {
                File imageFile = uriToFile(imageUri);
                // Tạo RequestBody từ file
                RequestBody requestFile = RequestBody.create(
                        MediaType.parse("image/*"),
                        imageFile
                );

                // Tạo MultipartBody.Part
                MultipartBody.Part body = MultipartBody.Part.createFormData("file", imageFile.getName(), requestFile);
                userViewModel.loadAvatar(body);
            }

            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        // Hiển thị dialog
        dialog.show();
    }

}