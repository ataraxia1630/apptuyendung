package com.example.workleap.ui.view;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;


import com.example.workleap.R;

import java.util.ArrayList;
import java.util.List;

public class EditProfileDialogFragment extends DialogFragment {
    private String cardType;
    private final List<EditText> editTexts = new ArrayList<>();
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

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_profile, null);

        LinearLayout container = view.findViewById(R.id.fieldsContainer);

        if ("ApplicantName".equals(cardType)) {
            addField(container, "User Name");
            addField(container, "Status");
        } else if ("AboutMe".equals(cardType)) {
            addField(container, "About me");
        } else if ("ApplicantInfo".equals(cardType)) {
            addField(container, "Name");
            //addField(container, "Gender");
            //addField(container, "Age");
            //addField(container, "Date of birth");
            addField(container, "Mobile");
            addField(container, "Email");
            addField(container, "Address");
        } else if ("AboutCompany".equalsIgnoreCase(cardType))
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
