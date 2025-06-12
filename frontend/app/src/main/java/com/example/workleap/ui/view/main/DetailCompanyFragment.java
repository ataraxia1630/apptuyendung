package com.example.workleap.ui.view.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.workleap.R;
import com.example.workleap.data.model.entity.Company;
import com.example.workleap.data.model.entity.JobPost;
import com.example.workleap.ui.viewmodel.JobPostViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailCompanyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailCompanyFragment extends Fragment {
    private JobPostViewModel jobPostViewModel;

    private TextView txtJobName, txtCompanyName, txtSalary, txtLocation, txtAboutUs, txtContactInfor;
    private Button btnApply, btnDescription;
    private ImageButton btnOption, btnBack;
    private NavController nav;
    private Bundle bundle;
    private boolean isJobPostSubmitted = false; // Biến trạng thái đảm bảo chỉ trở về khi đã tạo thành công

    public DetailCompanyFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DetailCompanyFragment newInstance(String param1, String param2) {
        DetailCompanyFragment fragment = new DetailCompanyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        nav = NavHostFragment.findNavController(this);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_company, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        jobPostViewModel = new ViewModelProvider(requireActivity()).get(JobPostViewModel.class);
        jobPostViewModel.InitiateRepository(getContext());

        //Lay company hien tai
        Company currentCompany = (Company) getArguments().getSerializable("company");

        //find component
        txtJobName = view.findViewById(R.id.txtJobName);
        txtCompanyName = view.findViewById(R.id.txtCompanyName);
        txtSalary = view.findViewById(R.id.txtSalary);
        txtLocation = view.findViewById(R.id.txtLocation);
        txtAboutUs = view.findViewById(R.id.txtAboutUs);
        txtContactInfor = view.findViewById(R.id.txtContactInformation);

        btnApply = view.findViewById(R.id.btnApply);
        btnOption = view.findViewById(R.id.btnOption);
        btnBack = view.findViewById(R.id.btnBack);
        btnDescription = view.findViewById(R.id.btnDescription);

        //Get current jobpost from jobpost fragment
        JobPost jobPost = (JobPost) getArguments().getSerializable("jobPost");
        txtJobName.setText(jobPost.getTitle());
        txtCompanyName.setText(jobPost.getCompany().getName());
        txtSalary.setText(jobPost.getSalaryStart() + " - " + jobPost.getSalaryEnd() + " " + jobPost.getCurrency());
        txtLocation.setText(jobPost.getLocation());
        txtAboutUs.setText(currentCompany.getDescription());
        txtContactInfor.setText(currentCompany.getTaxcode());
        txtLocation.setText(jobPost.getLocation());
        // TODO: Add listeners or bind ViewModel here

        btnOption.setOnClickListener(v -> {
            // TODO: Handle option button click
            PopupMenu popupMenu = new PopupMenu(v.getContext(), btnOption);
            popupMenu.inflate(R.menu.menu_details_myjobpost); // Load menu từ file XML
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_edit) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("jobPost", jobPost);
                    //Chuyen sang fragment edit update
                    // Ẩn bottom navigation
                    ((NavigationActivity) getActivity()).showBottomNav(false);
                    nav.navigate(R.id.updateJobPostFragment, bundle);
                    return true;
                } else if (item.getItemId() == R.id.menu_cancle) {
                    //Doi status
                    return true;
                } else
                    return false;
            });
            popupMenu.show();
        });

        //Quay lai
        btnBack.setOnClickListener(v -> {
            ((NavigationActivity) getActivity()).showBottomNav(true);
            NavHostFragment.findNavController(this).navigateUp();
        });

        //Chuyen qua detail job
        btnDescription.setOnClickListener(x ->
                {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("jobPost", jobPost);
                    bundle.putSerializable("company", jobPost.getCompany());
                    // Ẩn bottom navigation
                    ((NavigationActivity) getActivity()).showBottomNav(false);
                    NavHostFragment.findNavController(this).navigateUp();
                }
        );
    }
}