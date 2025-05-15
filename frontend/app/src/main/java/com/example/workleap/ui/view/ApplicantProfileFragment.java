package com.example.workleap.ui.view;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.viewmodel.ApplicantViewModel;
import com.example.workleap.ui.viewmodel.AuthViewModel;
import com.example.workleap.ui.viewmodel.UserViewModel;

import org.w3c.dom.Text;

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

    ImageButton btnOptions, btnEditApplicantName, btnEditAboutMe, btnEditApplicantInfo;

    ApplicantViewModel applicantViewModel;
    UserViewModel userViewModel;

    AuthViewModel authViewModel;

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
                        tvApplicantNameInfo.setText(values.get(0));
                        tvPhoneInfo.setText(values.get(1));
                        tvMailInfo.setText(values.get(2));
                        tvAddressInfo.setText(values.get(3));
                        //applicantViewModel.updateApplicant(user.getApplicantId(), "tvAddressInfo.getText().toString()", "null", "null", values.get(0), null);
                        applicantViewModel.updateApplicant(user.getApplicantId(), values.get(3), "null", "null", tvAboutMe.getText().toString(), null);
                        userViewModel.updateUser(user.getId(), values.get(0), user.getPassword(), values.get(2), values.get(1),"null", "null");
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
    }
}