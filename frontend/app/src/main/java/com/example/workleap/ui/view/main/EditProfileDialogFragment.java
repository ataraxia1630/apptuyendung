package com.example.workleap.ui.view.main;

import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.workleap.R;
import com.example.workleap.data.model.entity.ApplicantEducation;
import com.example.workleap.data.model.entity.Education;
import com.example.workleap.data.model.entity.Experience;
import com.example.workleap.data.model.entity.Field;
import com.example.workleap.ui.viewmodel.ApplicantViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditProfileDialogFragment extends DialogFragment {
    private String cardType;
    private Spinner spinnerSchool;
    ArrayList<Field> listField;
    private Spinner spinnerInterestedField;
    private Spinner spinnerEduLevel;
    private ArrayList<Education> listEducation;
    private final List<EditText> editTexts = new ArrayList<>();
    ApplicantViewModel applicantViewModel;

    String[] eduLevels = {"BACHELOR", "MASTER", "DOCTOR"};
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
            /*ArrayList<String> schoolNames = new ArrayList<>();
            if(listEducation != null)
            {
                for (Education edu : listEducation) {
                    schoolNames.add(edu.getUniName());
                }
            }
            else
            {
                Log.e("EditProfileDialog", "list Education null");
            }*/

            /*//add autocompletetextview to dialog
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
            container.addView(autoCompleteSchool);*/

            //eduStart, eduEnd, major, eduLevel, achievement
            spinnerSchool = addSchoolField(container, listEducation, "School");
            addDateField(container, "Year Start");
            addDateField(container, "Year End");
            //addField(container, "Major");
            spinnerEduLevel = addSpinnerField(container, eduLevels, "Edu level");
            addField(container, "Major");
            //addField(container, "Achievement");
        }
        else if ("UpdateApplicantEducation".equalsIgnoreCase(cardType))
        {
            //list school name
            listEducation = (ArrayList<Education>) getArguments().getSerializable("listEducation");
            if(listEducation==null) Log.e("EditProfileDialog", "list Education null");

            //eduStart, eduEnd, major, eduLevel, achievement
            spinnerSchool = addSchoolField(container, listEducation, "School");
            addDateField(container, "Year Start");
            addDateField(container, "Year End");
            spinnerEduLevel = addSpinnerField(container, eduLevels, "Edu level");
            addField(container, "Major");
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
        else if("ApplicantExperience".equalsIgnoreCase(cardType))
        {
            addField(container, "Company name");
            addField(container, "Company link");
            addField(container, "Position");
            addDateField(container, "Work start");
            addDateField(container, "Work end");
            addField(container, "Job Responsibility");
        }
        else if("UpdateApplicantExperience".equalsIgnoreCase(cardType))
        {
            addField(container, "Company name");
            addField(container, "Company link");
            addField(container, "Position");
            addDateField(container, "Work start");
            addDateField(container, "Work end");
            addField(container, "Job Responsibility");
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


        if("UpdateApplicantEducation".equalsIgnoreCase(cardType))
        {
            builder.setView(view)
                    .setTitle("Update Applicant Education")

                    .setPositiveButton("Save", (dialog, which) -> {
                        ArrayList<String> updated = new ArrayList<>();
                        for (EditText et : editTexts) {
                            updated.add(et.getText().toString());
                        }
                        ArrayList<Date> dates= new ArrayList<>();

                        //applicant edu
                        if("UpdateApplicantEducation".equalsIgnoreCase(cardType))
                        {
                            //Edu level
                            if (spinnerEduLevel != null && spinnerEduLevel.getSelectedItem() != null && !spinnerEduLevel.getSelectedItem().toString().trim().isEmpty()) {
                                int position = spinnerEduLevel.getSelectedItemPosition();
                                String major = eduLevels[position];
                                updated.add(major);
                            }

                            //list education
                            if (spinnerSchool != null && spinnerSchool.getSelectedItem() != null && !spinnerSchool.getSelectedItem().toString().trim().isEmpty()) {
                                int position = spinnerSchool.getSelectedItemPosition();
                                String eduId = listEducation.get(position).getId();
                                updated.add(eduId);
                            }
                        }

                        // Tạo Bundle và đẩy kết quả
                        Bundle result = new Bundle();
                        result.putString("cardType", cardType);
                        result.putStringArrayList("values", updated);
                        ApplicantEducation updateApplicantEducation = (ApplicantEducation) getArguments().getSerializable("deleteApplicantEducation");
                        result.putString("deleteApplicantEducationId", updateApplicantEducation.getId());

                        getParentFragmentManager()
                                .setFragmentResult("editProfile", result);
                    })
                    .setNeutralButton("Delete", (dialog, which)->
                    {
                        ApplicantEducation deleteEducation = (ApplicantEducation) getArguments().getSerializable("deleteApplicantEducation");
                        applicantViewModel.deleteApplicantEducation(deleteEducation.getId());
                    })

                    .setNegativeButton("Cancel", null);

            return builder.create();
        }
        else if("UpdateApplicantExperience".equalsIgnoreCase(cardType))
        {
            builder.setView(view)
                    .setTitle("Update Applicant Experience")

                    .setPositiveButton("Save", (dialog, which) -> {
                        ArrayList<String> updated = new ArrayList<>();
                        for (EditText et : editTexts) {
                            updated.add(et.getText().toString());
                        }
                        ArrayList<Date> dates= new ArrayList<>();


                        // Tạo Bundle và đẩy kết quả
                        Bundle result = new Bundle();
                        result.putString("cardType", cardType);
                        result.putStringArrayList("values", updated);
                        Experience deleteApplicantExperience = (Experience) getArguments().getSerializable("deleteApplicantExperience");
                        result.putString("deleteApplicantExperience", deleteApplicantExperience.getId());

                        getParentFragmentManager()
                                .setFragmentResult("editProfile", result);
                    })
                    .setNeutralButton("Delete", (dialog, which)->
                    {
                        Experience deleteExperience = (Experience) getArguments().getSerializable("deleteApplicantExperience");
                        applicantViewModel.deleteApplicantExperience(deleteExperience.getId());
                    })

                    .setNegativeButton("Cancel", null);

            return builder.create();
        }
        else {
            builder.setView(view)
                    .setTitle("Edit")

                    .setPositiveButton("Save", (dialog, which) -> {
                        ArrayList<String> updated = new ArrayList<>();
                        for (EditText et : editTexts) {
                            updated.add(et.getText().toString());
                        }
                        ArrayList<Date> dates = new ArrayList<>();

                        //applicant edu
                        if ("ApplicantEdu".equalsIgnoreCase(cardType)) {
                            //Edu level
                            if (spinnerEduLevel != null && spinnerEduLevel.getSelectedItem() != null && !spinnerEduLevel.getSelectedItem().toString().trim().isEmpty()) {
                                int position = spinnerEduLevel.getSelectedItemPosition();
                                String major = eduLevels[position];
                                updated.add(major);
                            }

                            //list education
                            if (spinnerSchool != null && spinnerSchool.getSelectedItem() != null && !spinnerSchool.getSelectedItem().toString().trim().isEmpty()) {
                                int position = spinnerSchool.getSelectedItemPosition();
                                String eduId = listEducation.get(position).getId();
                                updated.add(eduId);
                            }
                        }
                        if ("ApplicantInterestedField".equalsIgnoreCase(cardType)) {
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
    }

    private void addField(LinearLayout container, String hint) {
        EditText editText = new EditText(getContext());
        editText.setHint(hint);
        editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
        container.addView(editText);

        editTexts.add(editText);
    }
    private void addDateField(LinearLayout container, String hint) {
        EditText editText = new EditText(getContext());
        editText.setHint(hint);
        editText.setInputType(InputType.TYPE_CLASS_DATETIME);
        container.addView(editText);

        editTexts.add(editText);
    }
    private Spinner addSpinnerField(LinearLayout container, String[] array, String hint)
    {
        Spinner spinner = new Spinner(getContext());
        spinner.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_item, array
        );
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        container.addView(spinner);
        return spinner;
    }
    private Spinner addSchoolField(LinearLayout container, ArrayList<Education> array, String hint)
    {
        if(array==null) return null;

        Spinner spinner = new Spinner(getContext());
        spinner.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        ArrayList<String> names = new ArrayList<>();
        for (Education education: array)
        {
            names.add(education.getUniName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_item, names
        );
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        container.addView(spinner);
        return spinner;
    }
}
