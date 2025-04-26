package com.example.workleap.ui.view;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.workleap.R;

public class EditProfileDialogFragment extends DialogFragment {
    private String cardType;

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
            addField(container, "Gender");
            addField(container, "Age");
            addField(container, "Date of birth");
            addField(container, "Mobile");
            addField(container, "Email");
            addField(container, "Address");
        }

        builder.setView(view)
                .setTitle("Edit")
                .setPositiveButton("Save", (dialog, which) -> {
                    // Gửi các field đã nhập về Fragment
                })
                .setNegativeButton("Cancel", null);

        return builder.create();
    }

    private void addField(LinearLayout container, String hint) {
        EditText editText = new EditText(getContext());
        editText.setHint(hint);
        container.addView(editText);
    }
}
