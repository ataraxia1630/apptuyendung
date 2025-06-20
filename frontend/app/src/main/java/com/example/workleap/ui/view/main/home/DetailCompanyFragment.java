package com.example.workleap.ui.view.main.home;

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
import com.example.workleap.ui.view.main.NavigationActivity;
import com.example.workleap.ui.viewmodel.JobPostViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailCompanyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailCompanyFragment extends Fragment {
    private JobPostViewModel jobPostViewModel;
    private Company currentCompany;
    private JobPost currentJobPost;
    private TextView txtAboutUs, txtContactInfor;
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

        //Lay jobpost, company hien tai
        currentJobPost = (JobPost) getArguments().getSerializable("currentJobPost");
        currentCompany = currentJobPost.getCompany();

        //find component
        txtAboutUs = view.findViewById(R.id.txtAboutUs);
        txtContactInfor = view.findViewById(R.id.txtContactInformation);

        if (currentJobPost != null) {
            //JobPost jobPost = (JobPost) getArguments().getSerializable("jobPost");
            txtAboutUs.setText(currentCompany.getDescription());
            txtContactInfor.setText(currentCompany.getTaxcode());
            // TODO: Add listeners or bind ViewModel here
        }
    }
}