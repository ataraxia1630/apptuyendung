package com.example.workleap.ui.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.ApplicantEducation;
import com.example.workleap.data.model.entity.Education;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.viewmodel.ApplicantViewModel;
import com.example.workleap.ui.viewmodel.AuthViewModel;
import com.example.workleap.ui.viewmodel.UserViewModel;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.chip.Chip;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

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
    TextView tvApplicantName, tvApplicantNameInfo, tvMailInfo, tvPhoneInfo, tvAddressInfo;
    User user;

    ImageButton btnAddEdu, btnAddSkill, btnOptions, btnEditApplicantName, btnEditAboutMe, btnEditApplicantInfo;

    ApplicantViewModel applicantViewModel;
    UserViewModel userViewModel;

    AuthViewModel authViewModel;
    FlexboxLayout skillContainer ;
    LinearLayout educationListContainer;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

        btnOptions = view.findViewById(R.id.btnOptions);
        btnEditApplicantName = view.findViewById(R.id.btnEditUserName);
        btnEditAboutMe = view.findViewById(R.id.btnEditAboutMe);
        btnEditApplicantInfo = view.findViewById(R.id.btnApplicantInfo);
        btnAddSkill = view.findViewById(R.id.btnEditSkill);
        btnAddEdu = view.findViewById(R.id.btnEditEducation);

        skillContainer = view.findViewById(R.id.skillContainer);


        tvMailInfo.setText(user.getEmail());
        tvPhoneInfo.setText(user.getPhoneNumber());

        applicantViewModel.getApplicant(user.getApplicantId());
        Log.e("applicant profile", user.getApplicantId());
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
        userViewModel.getUpdateUserResult().observe(getViewLifecycleOwner(), result -> {
            if(!isAdded() || getView()==null) return;
            if(result!=null)
                Log.e("applicantprofile", result);
            else
                Log.e("applicantprofile", "update user result null" );
        });

        getParentFragmentManager().setFragmentResultListener(
                "editProfile",
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
                            applicantViewModel.createApplicantSkill(user.getApplicantId(), values.get(0), "null", "null", "null",0,0);
                            applicantViewModel.getCreateApplicantSkillResult().observe(getViewLifecycleOwner(), result ->
                            {
                                if(result != null)
                                    Log.e("AProfile creSkil result", result);
                                else
                                    Log.e("AProfile creSkil result", "update AEdu result null");

                            });
                            addSkillChip(values.get(0));
                        }
                    }
                    else if ("ApplicantEdu".equalsIgnoreCase(cardType) && values != null)
                    {
                        //applicantViewModel.updateApplicantEducation(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(7));
                        applicantViewModel.getUpdateApplicantEducationResult().observe(getViewLifecycleOwner(), result ->
                        {
                            if(result != null)
                                Log.e("AProfile upAEdu result", result);
                            else
                                Log.e("AProfile upAEdu result", "update AEdu result null");
                        });
                        LoadEducation();
                    }
                }
        );

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
        btnEditApplicantName.setOnClickListener(v -> {
            EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("ApplicantName");
            dialog.show(getParentFragmentManager(), "EditApplicantNameDialog");
        });
        btnEditAboutMe.setOnClickListener(v -> {
            EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("AboutMe");
            dialog.show(getParentFragmentManager(), "EditApplicantAboutMeDialog");
        });
        btnEditApplicantInfo.setOnClickListener(v -> {
            EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("ApplicantInfo");
            dialog.show(getParentFragmentManager(), "EditApplicantInfoDialog");
        });

        btnAddSkill.setOnClickListener(v -> {
            EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("ApplicantSkill");
            dialog.show(getParentFragmentManager(), "EditApplicantSkillDialog");
        });

        btnAddEdu.setOnClickListener(v -> {
            EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("ApplicantEdu");

            applicantViewModel.getAllEducation();
            applicantViewModel.getAllEducationResult().observe(getViewLifecycleOwner(), result ->
            {
                if(result != null)
                    Log.e("ApplicantProfile result", result);
                else
                    Log.e("applicantprofileEdu", "getAllEdu result null");

            });
            applicantViewModel.getAllEducationData().observe(getViewLifecycleOwner(), listEducation -> {
                if(listEducation==null) Log.e("Appprofile", "listedu null");
                else Log.e("Appprofile", "listedu NOT null");

                Bundle args = dialog.getArguments();
                args.putSerializable("listEducation", (Serializable) listEducation);
                dialog.setArguments(args);
                dialog.show(getParentFragmentManager(), "AddApplicantEduDialog");
            });

        });

    }

    private void addSkillChip(String skillName) {
        Chip chip = new Chip(requireContext());
        chip.setText(skillName);
        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(v -> skillContainer.removeView(chip));
        skillContainer.addView(chip);
    }
    private void LoadEducation()
    {
        educationListContainer = view.findViewById(R.id.educationListContainer);
        educationListContainer.removeAllViews();

        applicantViewModel.getAllApplicantEducation(user.getApplicantId());
        applicantViewModel.getAllApplicantEducationResult().observe(getViewLifecycleOwner(), result ->
        {
            if(result != null)
                Log.e("AProfile LoadEdu result", result);
            else
                Log.e("AProfile LoadEdu", "getAllApplicantEdu result null");
        });

        applicantViewModel.getAllApplicantEducationData().observe(getViewLifecycleOwner(), listApplicantEdu ->
        {
            for (ApplicantEducation applicantEdu : listApplicantEdu) {
                View eduItem = LayoutInflater.from(getContext()).inflate(R.layout.item_education, educationListContainer, false);

                TextView tvSchoolName   = eduItem.findViewById(R.id.tvSchoolName);
                TextView tvSchoolAddress= eduItem.findViewById(R.id.tvSchoolAddress);
                TextView tvMajorLevel   = eduItem.findViewById(R.id.tvMajorLevel);
                TextView tvTimeRange    = eduItem.findViewById(R.id.tvTimeRange);
                TextView tvAchievements = eduItem.findViewById(R.id.tvAchievements);
                TextView tvSchoolLink   = eduItem.findViewById(R.id.tvSchoolLink);
                ImageButton btnEdit     = eduItem.findViewById(R.id.btnEditEducation);

               /* tvSchoolName.setText(edu.get());
                tvSchoolAddress.setText(edu.getAddress());
                tvMajorLevel.setText(edu.getMajor() + " – " + edu.getEduLevel());
                String start = formatDate(edu.getEduStart());
                String end   = formatDate(edu.getEduEnd());
                tvTimeRange.setText(start + " – " + end);*/

                btnEdit.setOnClickListener(v -> {
                    EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("ApplicantSkill");
                    dialog.show(getParentFragmentManager(), "EditApplicantSkillDialog");
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
}