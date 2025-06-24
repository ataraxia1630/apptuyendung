package com.example.workleap.ui.view.main.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.User;
import com.example.workleap.ui.view.auth.MainActivity;
import com.example.workleap.ui.viewmodel.AuthViewModel;
import com.example.workleap.ui.viewmodel.CompanyViewModel;
import com.example.workleap.ui.viewmodel.UserViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WatchCompanyProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WatchCompanyProfileFragment extends Fragment {
    View view;
    TextView tvCompanyName;
    TextView tvAboutCompany;
    TextView tvCompanyNameInfo, tvEstablishedYear, tvMailInfo, tvPhoneInfo, tvTaxCode;
    User user;

    AuthViewModel authViewModel;
    UserViewModel userViewModel;
    CompanyViewModel companyViewModel;
    ImageButton btnOptions, btnFollow, btnChat, btnBack;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String userId, companyId;

    public WatchCompanyProfileFragment() {
        // Required empty public constructor
    }

    public static WatchCompanyProfileFragment newInstance(String param1, String param2) {
        WatchCompanyProfileFragment fragment = new WatchCompanyProfileFragment();
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
            userId = getArguments().getString("userId");
            Log.d("WatchCpnProfileFragment", "onCreate: " + userId + " " + companyId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_watch_company_profile, container, false);

        //Viewmodel
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        authViewModel.InitiateRepository(getContext());
        companyViewModel = new ViewModelProvider(requireActivity()).get(CompanyViewModel.class);
        companyViewModel.InitiateRepository(getContext());
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.InitiateRepository(getContext());

        //Component
        tvCompanyName = (TextView) view.findViewById(R.id.textView2);
        tvAboutCompany = (TextView) view.findViewById(R.id.textViewAboutCompany);
        tvCompanyNameInfo = (TextView) view.findViewById(R.id.companynameInfo);
        tvEstablishedYear = (TextView) view.findViewById(R.id.textViewEstablishedYear);
        tvMailInfo = (TextView) view.findViewById(R.id.emailInfo);
        tvPhoneInfo= (TextView) view.findViewById(R.id.phoneInfo);
        tvTaxCode = (TextView) view.findViewById(R.id.taxCodeInfo);
        btnOptions = view.findViewById(R.id.btnOptions);
        btnChat = view.findViewById(R.id.btnChat);
        btnFollow = view.findViewById(R.id.btnFollow);
        btnBack = view.findViewById(R.id.btnBack);

        //Set value from user
        userViewModel.getGetUserData().observe(getViewLifecycleOwner(), data -> {
            if(data != null)
            {
                tvMailInfo.setText(user.getEmail());
                tvPhoneInfo.setText(user.getPhoneNumber());
                companyId = data.getCompanyId();
            }
            else
                Log.d("WatchCpnProfileFragment", "user null");
        });
        //Set value from company
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
        companyViewModel.getCompany(companyId);
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

        //Option button
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

        //Follow observe and click handle
        userViewModel.getToggleFollowResult().observe(getViewLifecycleOwner(), result -> {
            if(result!=null)
                Log.e("follow", result);
            else
                Log.e("follow", "follow result null" );
        });
        btnFollow.setOnClickListener(v -> {
            userViewModel.toggleFollow(userId);
        });

        //Back
        btnBack.setOnClickListener(v ->
        {
            NavController nav = NavHostFragment.findNavController(this);
            nav.navigateUp();
        });
        return view;
    }
}