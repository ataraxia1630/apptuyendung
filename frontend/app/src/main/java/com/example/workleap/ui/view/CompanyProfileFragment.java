package com.example.workleap.ui.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompanyProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompanyProfileFragment extends Fragment {
    View view;
    TextView tvUserName;
    TextView tvAboutCompany;
    TextView tvUserNameInfo, tvEstablishedYear, tvMailInfo, tvPhoneInfo;
    User user;

    ImageButton btnEditCompanyName, btnEditAboutCompany, btnEditCompanyInfo;

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CompanyProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
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

        user = (User) getArguments().getSerializable("user");

        tvUserName = (TextView) view.findViewById(R.id.textView2);

        tvAboutCompany = (TextView) view.findViewById(R.id.textViewAboutCompany);

        tvUserNameInfo = (TextView) view.findViewById(R.id.companynameInfo);
        tvEstablishedYear = (TextView) view.findViewById(R.id.textViewEstablishedYear);
        tvMailInfo = (TextView) view.findViewById(R.id.emailInfo);
        tvPhoneInfo= (TextView) view.findViewById(R.id.phoneInfo);

        tvUserName.setText(user.getUsername());

        tvUserNameInfo.setText(user.getUsername());
        //tvEstablishedYear.setText(user.get);
        tvMailInfo.setText(user.getEmail());
        tvPhoneInfo.setText(user.getPhoneNumber());

        btnEditCompanyName = view.findViewById(R.id.btnEditCompanyName);
        btnEditAboutCompany = view.findViewById(R.id.btnEditAboutCompany);
        btnEditCompanyInfo = view.findViewById(R.id.btnEditCompanyInfo);

        getParentFragmentManager().setFragmentResultListener(
                "editProfile",
                getViewLifecycleOwner(),
                (requestKey, bundle) -> {
                    String cardType = bundle.getString("cardType");
                    ArrayList<String> values = bundle.getStringArrayList("values");
                    // TODO: xử lý cập nhật UI hoặc gọi ViewModel
                    if ("CompanyInfo".equals(cardType) && values != null) {
                        tvUserNameInfo.setText(values.get(0));
                        // … tương ứng với thứ tự addField
                    }
                }
        );

        btnEditCompanyName.setOnClickListener(v -> {
            EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("ApplicantName");
            dialog.show(getChildFragmentManager(), "EditApplicantNameDialog");
        });
        btnEditAboutCompany.setOnClickListener(v -> {
            EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("AboutMe");
            dialog.show(getChildFragmentManager(), "EditApplicantNameDialog");
        });
        btnEditCompanyInfo.setOnClickListener(v -> {
            EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("ApplicantInfo");
            dialog.show(getChildFragmentManager(), "EditApplicantNameDialog");
        });

        return view;
    }
}