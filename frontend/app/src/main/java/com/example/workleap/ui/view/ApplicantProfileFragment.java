package com.example.workleap.ui.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.workleap.R;
import com.example.workleap.data.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApplicantProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplicantProfileFragment extends Fragment {

    View view;
    TextView tvUserName, tvUserNameInfo, tvMailInfo, tvPhoneInfo;
    User user;

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

        user = (User) getArguments().getSerializable("user");

        tvUserName = (TextView) view.findViewById(R.id.textView2);
        tvUserNameInfo = (TextView) view.findViewById(R.id.companynameInfo);
        tvMailInfo = (TextView) view.findViewById(R.id.emailInfo);
        tvPhoneInfo= (TextView) view.findViewById(R.id.phoneInfo);

        tvUserName.setText(user.getUsername());
        tvUserNameInfo.setText(user.getUsername());
        tvMailInfo.setText(user.getEmail());
        tvPhoneInfo.setText(user.getPhoneNumber());

        return view;
    }
}