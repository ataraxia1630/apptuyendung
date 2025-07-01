package com.example.workleap.ui.view.main.profile;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.workleap.R;
import com.example.workleap.data.model.entity.ApplicantEducation;
import com.example.workleap.data.model.entity.Education;
import com.example.workleap.data.model.entity.Experience;
import com.example.workleap.data.model.entity.Field;
import com.example.workleap.data.model.entity.Skill;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.view.auth.MainActivity;
import com.example.workleap.ui.viewmodel.ApplicantViewModel;
import com.example.workleap.ui.viewmodel.AuthViewModel;
import com.example.workleap.ui.viewmodel.UserViewModel;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.chip.Chip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApplicantProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplicantProfileFragment extends Fragment {

    View view;
    String applicantFirstName = "first name";
    String applicantLastName = "last name";

    TextView tvAboutMe;
    TextView tvGender, tvApplicantName, tvApplicantNameInfo, tvMailInfo, tvPhoneInfo, tvAddressInfo;
    User user;

    ImageButton btnAddExperience, btnAddInterestedField, btnAddEdu, btnAddSkill, btnOptions, btnEditAboutMe, btnEditApplicantInfo;

    ImageView avatar;

    ApplicantViewModel applicantViewModel;
    UserViewModel userViewModel;

    AuthViewModel authViewModel;
    FlexboxLayout skillContainer, fieldContainer ;
    LinearLayout educationListContainer;
    LinearLayout experienceListContainer;
    List<Field> listField;
    List<Field> applicantInterestedField;
    List<Education> listEducation;
    List<Experience> applicantExperience;
    private static final int PICK_IMAGE_REQUEST = 1001;
    private Uri imageUri;
    private String currentAvatarUrl;
    private ImageView imagePreview;

    public ApplicantProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApplicantProfileFragment newInstance(String param1, String param2) {
        ApplicantProfileFragment fragment = new ApplicantProfileFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_applicant_profile, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user = (User) getArguments().getSerializable("user");
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.InitiateRepository(getContext());
        applicantViewModel = new ViewModelProvider(requireActivity()).get(ApplicantViewModel.class);
        applicantViewModel.InitiateRepository(getContext());
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        authViewModel.InitiateRepository(getContext());

        tvApplicantName = (TextView) view.findViewById(R.id.tvApplicantName);

        tvAboutMe = (TextView) view.findViewById(R.id.textViewAboutMe);
        tvApplicantNameInfo = (TextView) view.findViewById(R.id.companynameInfo);
        tvMailInfo = (TextView) view.findViewById(R.id.emailInfo);
        tvPhoneInfo= (TextView) view.findViewById(R.id.phoneInfo);
        tvAddressInfo= (TextView) view.findViewById(R.id.addressInfo);
        //tvGender = (TextView) view.findViewById(R.id.gender);

        btnOptions = view.findViewById(R.id.btnOptions);
        btnEditAboutMe = view.findViewById(R.id.btnEditAboutMe);
        btnEditApplicantInfo = view.findViewById(R.id.btnApplicantInfo);
        btnAddSkill = view.findViewById(R.id.btnEditSkill);
        btnAddEdu = view.findViewById(R.id.btnEditEducation);
        btnAddInterestedField = view.findViewById(R.id.btnEditInterestedField);
        btnAddExperience = view.findViewById(R.id.btnEditExperience);

        skillContainer = view.findViewById(R.id.skillContainer);
        fieldContainer = view.findViewById(R.id.interestedFieldContainer);
        educationListContainer = view.findViewById(R.id.educationListContainer);
        experienceListContainer = view.findViewById(R.id.experienceListContainer);
        avatar = view.findViewById(R.id.shapeableImageView);

        tvMailInfo.setText(user.getEmail());
        tvPhoneInfo.setText(user.getPhoneNumber());

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
                Glide.with(this.getContext()).load(data).into(avatar);
                Log.d("ApplicantProfile avatar", "Set avatar success");
            }
            else
                Log.d("ApplicantProfile avatar", "getUrlAvatarData NULL");
        });
        if(user.getAvatar() != null)
        {
            //Load avatar from database
            userViewModel.getAvatarUrl(user.getAvatar());
        }
        else
            Log.d("ApplicantProfile avatar", "user avatar null");

        //load
        LoadSkill();
        LoadEducation();
        LoadInterestedField();
        LoadExperience();

        //get all education
        applicantViewModel.getAllEducation();
        applicantViewModel.getAllEducationResult().observe(getViewLifecycleOwner(), result ->
        {
            if(result != null)
                Log.e("ApplicantProfile result", result);
            else
                Log.e("applicantprofileEdu", "getAllEdu result null");

        });
        applicantViewModel.getAllEducationData().observe(getViewLifecycleOwner(), allEducation -> {
            if(allEducation==null) Log.e("ApplicantProfile", "getAllEducationData NULL");
            else Log.e("ApplicantProfile", "getAllEducationData NOT null");

            listEducation = allEducation;
        });

        //applicant education
        applicantViewModel.getDeleteApplicantEducationResult().observe(getViewLifecycleOwner(), result ->{
            ReloadEducation();
            if(result != null)
                Log.e("ApplicantProfile", "getDeleteApplicantEducationResult " + result);
            else
                Log.e("ApplicantProfile", "getDeleteApplicantEducationResult null");

        });

        //applicant experience
        applicantViewModel.getDeleteApplicantExperienceResult().observe(getViewLifecycleOwner(), result ->{
            ReloadExperience();
            if(result != null)
                Log.e("ApplicantProfile", "getDeleteApplicantExperienceResult " + result);
            else
                Log.e("ApplicantProfile", "getDeleteApplicantExperienceResult null");
        });

        //get all fields for interested fields
        applicantViewModel.getAllFields();
        applicantViewModel.getGetAllFieldResult().observe(getViewLifecycleOwner(), result ->
        {
            if(result != null)
                Log.e("ProfileDialog allfield", result);
            else
                Log.e("ProfileDialog", "allfield result null");

        });
        applicantViewModel.getGetAllFieldData().observe(getViewLifecycleOwner(), dataResult -> {
            if(dataResult==null) Log.e("Appprofile", "listfield null");
            else Log.e("Appprofile", "listfield NOT null");

            listField = dataResult;
        });


        //get applicant
        applicantViewModel.getApplicant(user.getApplicantId());
        Log.e("applicant profile", "USER ID: "+user.getApplicantId());
        applicantViewModel.getGetApplicantData().observe(getViewLifecycleOwner(), applicant -> {
            if(!isAdded() || getView()==null) return;
            if (applicant == null) {
                Log.e("ApplicantProfile", "applicant null");
            } else {
                Log.e("ApplicantProfile", "applicant setText");
                applicantFirstName = applicant.getFirstName();
                applicantLastName = applicant.getLastName();
                tvApplicantName.setText(applicant.getFirstName() + " " + applicant.getLastName());
                tvAboutMe.setText(applicant.getProfileSummary());
                tvAddressInfo.setText(applicant.getAddress());
                //tvGender.setText(applicant.getGender());
                tvApplicantNameInfo.setText(applicant.getFirstName() + " " + applicant.getLastName());
            }
        });
        applicantViewModel.getUpdateApplicantResult().observe(getViewLifecycleOwner(), result -> {
            if(!isAdded() || getView()==null) return;
            if(result != null)
                Log.e("applicantprofile", result);
            else
                Log.e("applicantprofile", "updateresult null");

        });

        //user
        userViewModel.getUpdateUserResult().observe(getViewLifecycleOwner(), result -> {
            if(!isAdded() || getView()==null) return;
            if(result!=null)
                Log.e("applicantprofile", result);
            else
                Log.e("applicantprofile", "update user result null" );
        });

        getParentFragmentManager().setFragmentResultListener("editProfile",
                getViewLifecycleOwner(),
                (requestKey, bundle) -> {
                    String cardType = bundle.getString("cardType");
                    ArrayList<String> values = bundle.getStringArrayList("values");
                    // TODO: xử lý cập nhật UI hoặc gọi ViewModel
                    if ("AboutMe".equalsIgnoreCase(cardType) && values != null) {
                        tvAboutMe.setText(values.get(0));
                        //first name last name khong dc null
                        applicantViewModel.updateApplicant(user.getApplicantId(), tvAddressInfo.getText().toString(), applicantFirstName, applicantLastName, values.get(0), null);
                    }
                    else if("ApplicantInfo".equalsIgnoreCase(cardType) && values != null)
                    {
                        applicantFirstName = values.get(0);
                        applicantLastName = values.get(1);
                        tvApplicantNameInfo.setText( applicantFirstName + " " + applicantLastName);
                        tvApplicantName.setText(applicantFirstName + " " + applicantLastName);
                        tvAddressInfo.setText(values.get(2));
                        //tvPhoneInfo.setText(values.get(1));
                        //tvMailInfo.setText(values.get(2));
                        //applicantViewModel.updateApplicant(user.getApplicantId(), "tvAddressInfo.getText().toString()", "null", "null", values.get(0), null);
                        applicantViewModel.updateApplicant(user.getApplicantId(), values.get(2), applicantFirstName, applicantLastName, tvAboutMe.getText().toString(), null);
                        //userViewModel.updateUser(user.getId(), values.get(0), user.getPassword(), values.get(2), values.get(1),"null", "null");
                    }
                    else if ("ApplicantSkill".equalsIgnoreCase(cardType) && values != null)
                    {
                        if (!values.get(0).isEmpty()) {
                            applicantViewModel.createApplicantSkill(user.getApplicantId(), values.get(0), "null", "null", "BEGINNER",10,10);
                            applicantViewModel.getCreateApplicantSkillResult().observe(getViewLifecycleOwner(), result ->
                            {
                                //reload
                                ReloadSkill();

                                if(result != null)
                                    Log.e("AProfile creSkil result", result);
                                else
                                    Log.e("AProfile creSkil result", "update AEdu result null");
                            });

                        }
                    }
                    else if ("ApplicantEdu".equalsIgnoreCase(cardType) && values != null)
                    {
                        Date yearStart = StringToDate(values.get(0));
                        Date yearEnd = StringToDate(values.get(1));
                        applicantViewModel.createApplicantEducation(values.get(4), user.getApplicantId(), yearStart, yearEnd, values.get(2), values.get(3), null, null );
                        applicantViewModel.getCreateApplicantEducationResult().observe(getViewLifecycleOwner(), result ->
                        {
                            ReloadEducation();

                            if(result != null)
                                Log.e("ApplicantProfile", "getCreateApplicantEduResult " + result);
                            else
                                Log.e("ApplicantProfile", "getCreateApplicantEduResult NULL");
                        });

                    }
                    else if("UpdateApplicantEducation".equalsIgnoreCase(cardType))
                    {
                        String updateApplicantEducationId = bundle.getString("deleteApplicantEducationId");
                        Date yearStart = StringToDate(values.get(0));
                        Date yearEnd = StringToDate(values.get(1));
                        applicantViewModel.updateApplicantEducation(updateApplicantEducationId, values.get(4), yearStart, yearEnd, values.get(2), values.get(3), null, null );
                        applicantViewModel.getUpdateApplicantEducationResult().observe(getViewLifecycleOwner(), result ->
                        {
                            ReloadEducation();

                            if(result != null)
                                Log.e("ApplicantProfile", "getUpdateApplicantEducationResult " + result);
                            else
                                Log.e("ApplicantProfile", "getUpdateApplicantEducationResult NULL");
                        });
                    }
                    else if("ApplicantInterestedField".equalsIgnoreCase(cardType) && values != null)
                    {
                        if (!values.get(0).isEmpty()) {
                            List<String> fieldIds = new ArrayList<>();

                            //old fieldIds
                            for(Field fieldId: applicantInterestedField)
                            {
                                fieldIds.add(fieldId.getId());
                            }
                            //new fieldIds
                            fieldIds.add(values.get(0));

                            applicantViewModel.createInterestedField(user.getApplicantId(), fieldIds);
                            applicantViewModel.getCreateInterestedFieldResult().observe(getViewLifecycleOwner(), result ->
                            {
                                applicantInterestedField.clear();
                                ReLoadInterestedField();
                                if(result != null)
                                {
                                    Log.e("ApplicantProfile", "getCreateInterestedFieldResult " + result);
                                }
                                else
                                    Log.e("ApplicantProfile", "getCreateInterestedFieldResult NULL");
                            });
                        }
                        else
                            Log.e("AppProfile", "interested field from dialog is empty");

                    }
                    else if("ApplicantExperience".equalsIgnoreCase(cardType) && values != null)
                    {
                        Date workStart = StringToDate(values.get(3));
                        Date workEnd = StringToDate(values.get(4));
                        applicantViewModel.createApplicantExperience(user.getApplicantId(), values.get(0), values.get(1), values.get(2), workStart, workEnd, values.get(5), null, null );
                        applicantViewModel.getCreateApplicantExperienceResult().observe(getViewLifecycleOwner(), result ->
                        {
                            ReloadExperience();
                            if(result != null)
                            {
                                Log.e("ApplicantProfile", "getCreateApplicantEduResult " + result);
                                Toast.makeText(getContext(), "Failed to save experience", Toast.LENGTH_SHORT).show();
                            }
                            else
                                Log.e("ApplicantProfile", "getCreateApplicantEduResult NULL");
                        });
                    }
                    else if("UpdateApplicantExperience".equalsIgnoreCase(cardType))
                    {
                        String updateApplicantExperienceId = bundle.getString("deleteApplicantExperience");
                        Date workStart = StringToDate(values.get(3));
                        Date workEnd = StringToDate(values.get(4));
                        applicantViewModel.updateApplicantExperience(updateApplicantExperienceId, values.get(0), values.get(1), values.get(2), workStart, workEnd, values.get(5), null, null );
                        applicantViewModel.getUpdateApplicantExperienceResult().observe(getViewLifecycleOwner(), result ->
                        {
                            ReloadExperience();
                            if(result != null)
                                Log.e("ApplicantProfile", "getUpdateApplicantExperienceResult " + result);
                            else
                                Log.e("ApplicantProfile", "getUpdateApplicantExperienceResult NULL");
                        });
                    }

                }
        );


        //click handle
        //avatar
        avatar.setOnClickListener(v -> {
            showImagePopup(this.requireContext());
        });

        //Edit
        btnOptions.setOnClickListener( v -> {
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

        btnEditAboutMe.setOnClickListener(v -> {
            EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("AboutMe");

            Bundle args = dialog.getArguments();
            args.putString("aboutMe", String.valueOf(tvAboutMe.getText()));
            dialog.setArguments(args);

            dialog.show(getParentFragmentManager(), "EditApplicantAboutMeDialog");
        });
        btnEditApplicantInfo.setOnClickListener(v -> {
            EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("ApplicantInfo");

            Bundle args = dialog.getArguments();
            args.putString("firstName", applicantFirstName);
            args.putString("lastName", applicantLastName);
            //args.putString("gender", );
            //args.putString("age", );
            //args.putString("dateOfBirth", );
            args.putString("address", String.valueOf(tvAddressInfo.getText()));
            dialog.setArguments(args);

            dialog.show(getParentFragmentManager(), "EditApplicantInfoDialog");
        });

        btnAddSkill.setOnClickListener(v -> {
            EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("ApplicantSkill");
            dialog.show(getParentFragmentManager(), "EditApplicantSkillDialog");
        });

        btnAddEdu.setOnClickListener(v -> {
            EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("ApplicantEdu");

            Bundle args = dialog.getArguments();
            args.putSerializable("listEducation", (Serializable) listEducation);
            dialog.setArguments(args);
            dialog.show(getParentFragmentManager(), "AddApplicantEduDialog");
        });
        btnAddInterestedField.setOnClickListener(v -> {
            EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("ApplicantInterestedField");
            Bundle args = dialog.getArguments();
            args.putSerializable("listField", (Serializable) listField);
            dialog.setArguments(args);
            if (!dialog.isAdded()) {
                dialog.show(getParentFragmentManager(), "EditApplicantInterestedFieldDialog");
            }
        });
        btnAddExperience.setOnClickListener(v->
        {
            EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("ApplicantExperience");
            if (!dialog.isAdded()) {
                dialog.show(getParentFragmentManager(), "EditApplicantExperienceDialog");
            }
        });

    }
    private void LoadEducation()
    {
        applicantViewModel.getAllApplicantEducation(user.getApplicantId());
        applicantViewModel.getAllApplicantEducationResult().observe(getViewLifecycleOwner(), result ->
        {
            if(result != null)
                Log.e("ApplicantProfile", "getAllApplicantEducationResult " + result);
            else
                Log.e("ApplicantProfile", "getAllApplicantEducationResult result NULL");
        });

        applicantViewModel.getAllApplicantEducationData().observe(getViewLifecycleOwner(), listApplicantEdu ->
        {
            if(listApplicantEdu==null)
            {
                Log.e("ApplicantProfile", "getAllApplicantEducation Data NULL");
                return;
            }

            educationListContainer.removeAllViews();
            for (ApplicantEducation applicantEdu : listApplicantEdu) {
                View eduItem = LayoutInflater.from(getContext()).inflate(R.layout.item_education, educationListContainer, false);

                TextView tvSchoolName   = eduItem.findViewById(R.id.tvSchoolName);
                TextView tvSchoolAddress= eduItem.findViewById(R.id.tvSchoolAddress);
                TextView tvEduLevel   = eduItem.findViewById(R.id.tvEduLevel);
                TextView tvMajor   = eduItem.findViewById(R.id.tvMajor);
                TextView tvTimeRange    = eduItem.findViewById(R.id.tvTimeRange);
                //TextView tvAchievements = eduItem.findViewById(R.id.tvAchievements);
                TextView tvSchoolLink   = eduItem.findViewById(R.id.tvSchoolLink);
                ImageButton btnEdit     = eduItem.findViewById(R.id.btnEditEducation);
                tvSchoolName.setText(applicantEdu.getEducation().getUniName());
                tvSchoolAddress.setText(applicantEdu.getEducation().getAddress());
                tvEduLevel.setText(applicantEdu.getEduLevel());
                tvMajor.setText(applicantEdu.getMajor());
                String start = formatYear(applicantEdu.getEduStart());
                String end   = formatYear(applicantEdu.getEduEnd());
                tvTimeRange.setText(start + " – " + end);
                //tvAchievements.setText(applicantEdu.getAchievement().get(0));
                tvSchoolLink.setText(applicantEdu.getEducation().getUniLink());

                btnEdit.setOnClickListener(v -> {
                    EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("UpdateApplicantEducation");

                    Bundle args = dialog.getArguments();
                    args.putSerializable("deleteApplicantEducation", (Serializable) applicantEdu);
                    args.putSerializable("listEducation", (Serializable) listEducation);
                    args.putString("schoolName", String.valueOf(tvSchoolName.getText()));
                    args.putString("eduLevel", String.valueOf(tvEduLevel.getText()));
                    args.putString("major", String.valueOf(tvMajor.getText()));
                    args.putString("yearStart", start);
                    args.putString("yearEnd", end);
                    dialog.setArguments(args);
                    dialog.show(getParentFragmentManager(), "UpdateApplicantEducation");
                });


                //line
                View divider = new View(getContext());
                divider.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                divider.setBackgroundColor(Color.LTGRAY);
                divider.setPadding(0, 8, 0, 8);

                educationListContainer.addView(eduItem);
            };
        });
    }
    private void ReloadEducation()
    {
        applicantViewModel.getAllApplicantEducation(user.getApplicantId());
    }
    private void LoadSkill()
    {
        applicantViewModel.getApplicantSkill(user.getApplicantId());
        applicantViewModel.getGetApplicantSkillData().observe(getViewLifecycleOwner(), skills ->
        {
            skillContainer.removeAllViews();
            for(Skill skill : skills)
            {
                addSkillChip(skill.getSkillName(), skill.getId());
            }
        });
    }
    private void ReloadSkill()
    {
        applicantViewModel.getApplicantSkill(user.getApplicantId());
    }
    private void LoadInterestedField()
    {
        Log.e("AppProfileFragment", "Load Interested Field");
        applicantViewModel.getInterestedFields(user.getApplicantId());
        applicantViewModel.getGetInterestedFieldData().observe(getViewLifecycleOwner(), interestedFieldList ->
        {
            if(interestedFieldList==null)
            {
                Log.e("ApplicantProfile", "interestedFieldList NULL");
            }
            else
            {
                //applicantInterestedField cu de them moi
                applicantInterestedField = interestedFieldList;

                fieldContainer.removeAllViews();
                for(Field interestedField : interestedFieldList)
                {
                    AddFieldChip(interestedField.getName(), interestedField.getId());
                }
            }
        });
        applicantViewModel.getGetInterestedFieldResult().observe(getViewLifecycleOwner(), result->
        {
            Log.e("ApplicantProfile", "getGetInterestedFieldResult " + result);
        });
    }
    private void ReLoadInterestedField()
    {
        applicantViewModel.getInterestedFields(user.getApplicantId());
    }
    private void LoadExperience()
    {
        applicantViewModel.getApplicantExperience(user.getApplicantId());
        applicantViewModel.getGetApplicantExperienceData().observe(getViewLifecycleOwner(), applicantExperiences ->
        {
            experienceListContainer.removeAllViews();
            for (Experience experience : applicantExperiences) {
                View expItem = LayoutInflater.from(getContext()).inflate(R.layout.item_experience, experienceListContainer, false);

                TextView tvCompanyName   = expItem.findViewById(R.id.tvCompanyName);
                TextView tvCompanyLink= expItem.findViewById(R.id.tvCompanyLink);
                TextView tvPosition   = expItem.findViewById(R.id.tvPosition);
                TextView tvTimeRange    = expItem.findViewById(R.id.tvTimeRange);
                TextView tvJobResponsibility    = expItem.findViewById(R.id.tvJobResponsibility);
                ImageButton btnEdit     = expItem.findViewById(R.id.btnEditExperience);

                tvCompanyName.setText(experience.getCompanyName());
                tvCompanyLink.setText(experience.getCompanyLink());
                tvPosition.setText(experience.getPosition());
                String start = formatYear(experience.getWorkStart());
                String end   = formatYear(experience.getWorkEnd());
                tvTimeRange.setText(start + " – " + end);
                tvJobResponsibility.setText(experience.getJobResponsibility());

                btnEdit.setOnClickListener(v -> {
                    EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("UpdateApplicantExperience");

                    Bundle args = dialog.getArguments();
                    args.putSerializable("deleteApplicantExperience", (Serializable) experience);
                    args.putSerializable("listField", (Serializable) listField);
                    args.putString("companyName", String.valueOf(tvCompanyName.getText()));
                    args.putString("companyLink", String.valueOf(tvCompanyLink.getText()));
                    args.putString("position", String.valueOf(tvPosition.getText()));
                    args.putString("yearStart", start);
                    args.putString("yearEnd", end);
                    args.putString("jobResponsibility", String.valueOf(tvJobResponsibility.getText()));
                    dialog.setArguments(args);
                    dialog.show(getParentFragmentManager(), "UpdateApplicantExperienceDialog");
                });
                //line
                View divider = new View(getContext());
                divider.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                divider.setBackgroundColor(Color.LTGRAY);
                divider.setPadding(0, 8, 0, 8);

                experienceListContainer.addView(expItem);
            };
        });
        applicantViewModel.getGetApplicantExperienceResult().observe(getViewLifecycleOwner(), result->
        {
            if(result != null)
                Log.e("ApplicantProfile", "getGetApplicantExperienceResult " + result);
            else
                Log.e("ApplicantProfile", "getGetApplicantExperienceResult result NULL");
        });

    }
    private void ReloadExperience()
    {
        applicantViewModel.getApplicantExperience(user.getApplicantId());
    }
    private void addSkillChip(String skillName, String skillId) {
        Chip chip = new Chip(requireContext());
        chip.setText(skillName);
        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(v ->
        {
            applicantViewModel.deleteApplicantSkill(skillId);
            skillContainer.removeView(chip);
        });
        skillContainer.addView(chip);
    }
    private void AddFieldChip(String fieldName, String fieldId) {
        Chip chip = new Chip(requireContext());
        chip.setText(fieldName);
        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(v ->
        {
            applicantViewModel.deleteInterestedField(user.getApplicantId(), fieldId);
            fieldContainer.removeView(chip);
        });
        fieldContainer.addView(chip);
        Log.e("appprofile", "add field chip");
    }
    private Date StringToDate(String string)
    {
        Date date = new Date();
        if (!string.isEmpty()) {
            int year = Integer.parseInt(string);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, Calendar.JANUARY); // hoặc MONTH nào đó
            calendar.set(Calendar.DAY_OF_MONTH, 1);         // ngày mặc định

            date = calendar.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formatted = sdf.format(date);
        }
        else {
            date = null;
            Log.e("ApplicantProfile", "StringToDate() return date NULL");
        }

        return date;
    }
    private String formatYear(Date date) {
        if (date == null) return "N/A";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.getDefault());
        return sdf.format(date);
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

        btnSave.setOnClickListener(v -> {
            //Upload anh dang hien thi len csdl
            //Ket qua
            userViewModel.getUploadAvatarData().observe(getViewLifecycleOwner(), data -> {
                if(data != null)
                {
                    //Update current avatar
                    userViewModel.getAvatarUrl(data.getAvatar());
                }
                else
                    Log.e("Upload image data", "Upload image data null");
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