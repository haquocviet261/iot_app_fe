package com.project.smartfrigde.view.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.project.smartfrigde.R;

public class AddUserDialogFragment extends DialogFragment {

    private EditText inputEmail;

    public interface AddUserDialogListener {
        void onDialogPositiveClick(String email);
    }

    private AddUserDialogListener listener;

    public AddUserDialogFragment(AddUserDialogListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_user, null);

        inputEmail = view.findViewById(R.id.input_email);

        builder.setView(view)
                .setTitle("Add User")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String email = inputEmail.getText().toString();
                        listener.onDialogPositiveClick(email);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        AddUserDialogFragment.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
