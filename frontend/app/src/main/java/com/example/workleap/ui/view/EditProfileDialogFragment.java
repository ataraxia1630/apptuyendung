package com.example.workleap.ui.view;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;


import com.example.workleap.R;
import com.example.workleap.data.model.entity.Education;
import com.example.workleap.data.model.entity.Field;
import com.example.workleap.ui.viewmodel.ApplicantViewModel;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

public class EditProfileDialogFragment extends DialogFragment {
    private String cardType;
    private AutoCompleteTextView autoCompleteSchool;
    ArrayList<Field> listField;
    private Spinner spinnerInterestedField;
    private ArrayList<Education> listEducation;
    private final List<EditText> editTexts = new ArrayList<>();
    ApplicantViewModel applicantViewModel;
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
        } else if ("ApplicantInfo".equals(cardType)) {
            addField(container, "First Name");
            addField(container, "Last Name");
            //addField(container, "Gender");
            //addField(container, "Age");
            //addField(container, "Date of birth");
            //addField(container, "Mobile");
            //addField(container, "Email");
            addField(container, "Address");
        }
        else if("ApplicantSkill".equalsIgnoreCase(cardType))
        {
            addField(container, "Skill");
        }
        else if("ApplicantEdu".equalsIgnoreCase(cardType))
        {
            //list school name
            listEducation = (ArrayList<Education>) getArguments().getSerializable("listEducation");
            ArrayList<String> schoolNames = new ArrayList<>();
            if(listEducation != null)
            {
                for (Education edu : listEducation) {
                    schoolNames.add(edu.getUniName());
                }
            }
            else
            {
                Log.e("EditProfileDialog", "list Education null");
            }

            //add autocompletetextview to dialog
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
            container.addView(autoCompleteSchool);
            //eduStart, eduEnd, major, eduLevel, achievement

            addField(container, "Year Start");
            addField(container, "Year End");
            addField(container, "Edu level");
            addField(container, "Achievement");
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
        //company
        else if ("AboutCompany".equalsIgnoreCase(cardType))
        {
            addField(container, "AboutCompany");
        }
        else if ("CompanyInfo".equalsIgnoreCase(cardType))
        {
            addField(container, "Name");
            addField(container, "EstablishedYear");
            addField(container, "Phone");
            addField(container, "Email");
            addField(container, "Tax code");
        }


        builder.setView(view)
                .setTitle("Edit")

                .setPositiveButton("Save", (dialog, which) -> {
                    ArrayList<String> updated = new ArrayList<>();
                    for (EditText et : editTexts) {
                        updated.add(et.getText().toString());
                    }

                    //applicant edu
                    if("ApplicantEdu".equalsIgnoreCase(cardType))
                    {
                        if (autoCompleteSchool != null && autoCompleteSchool.getText() != null && !autoCompleteSchool.getText().toString().trim().isEmpty()) {
                            updated.add(autoCompleteSchool.getText().toString().trim());
                        }
                    }
                    if("ApplicantInterestedField".equalsIgnoreCase(cardType))
                    {
                        if (spinnerInterestedField != null && spinnerInterestedField.getSelectedItem() != null && !spinnerInterestedField.getSelectedItem().toString().trim().isEmpty()) {
                            int position = spinnerInterestedField.getSelectedItemPosition();
                            Field field = listField.get(position);
                            updated.add(field.getId());
                        }
                    }

                    // Tạo Bundle và đẩy kết quả
                    Bundle result = new Bundle();
                    result.putString("cardType", cardType);
                    result.putStringArrayList("values", updated);
                    getParentFragmentManager()
                            .setFragmentResult("editProfile", result);
                })

                .setNegativeButton("Cancel", null);

        return builder.create();
    }

    private void addField(LinearLayout container, String hint) {
        EditText editText = new EditText(getContext());
        editText.setHint(hint);
        editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
        container.addView(editText);

        editTexts.add(editText);
    }
}
