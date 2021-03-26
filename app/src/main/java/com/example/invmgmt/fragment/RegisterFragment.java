package com.example.invmgmt.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.invmgmt.CommonAsync;
import com.example.invmgmt.R;
import com.example.invmgmt.db.Data;
import com.example.invmgmt.exception.BaseException;

public class RegisterFragment extends Fragment {

    IRegister am;
    Data.AuthResponse acc = null;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IRegister) {
            am = (IRegister) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        getActivity().setTitle(R.string.create_new_account);
        EditText txt_name = view.findViewById(R.id.reg_name);
        EditText txt_email = view.findViewById(R.id.reg_email);
        EditText txt_pass = view.findViewById(R.id.reg_password);
        view.findViewById(R.id.button_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txt_name.getText().toString().isEmpty() || txt_email.getText().toString().isEmpty() || txt_pass.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), R.string.empty_fields, Toast.LENGTH_LONG).show();
                    return;
                }
                new CommonAsync.Task(new CommonAsync.ViewObject(view, new CommonAsync.CommonAsyncInterface() {
                    @Override
                    public void onWork() throws BaseException {
                        acc = Data.register(txt_name.getText().toString(), txt_email.getText().toString(), txt_pass.getText().toString());
                    }

                    @Override
                    public void afterWork() {
                        if (acc != null) {
                            Toast.makeText(getActivity(), R.string.success, Toast.LENGTH_LONG).show();
                            am.setAuthResponse(acc);
                            am.sendHomeFragment();
                        }
                    }
                })).execute();
            }
        });
        view.findViewById(R.id.reg_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                am.sendLoginFragment();
            }
        });
        return view;
    }

    public interface IRegister {

        void setAuthResponse(@Nullable Data.AuthResponse acc);

        void sendHomeFragment();

        void sendLoginFragment();

    }
}