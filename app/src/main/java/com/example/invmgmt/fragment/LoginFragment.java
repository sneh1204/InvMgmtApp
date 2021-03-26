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


public class LoginFragment extends Fragment {

    ILogin am;

    Data.AuthResponse acc = null;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ILogin) {
            am = (ILogin) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().setTitle(R.string.login);
        EditText txt_email = view.findViewById(R.id.enter_email);
        EditText txt_pass = view.findViewById(R.id.enter_password);
        txt_email.setText("a@a.com");
        txt_pass.setText("test");
        view.findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txt_email.getText().toString().isEmpty() || txt_pass.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), R.string.empty_fields, Toast.LENGTH_LONG).show();
                    return;
                }
                new CommonAsync.Task(new CommonAsync.ViewObject(view, new CommonAsync.CommonAsyncInterface() {
                    @Override
                    public void onWork() throws BaseException {
                        acc = Data.login(txt_email.getText().toString(), txt_pass.getText().toString());
                    }

                    @Override
                    public void afterWork() {
                        if (acc != null) {
                            am.setAuthResponse(acc);
                            am.sendHomeFragment();
                        }
                    }
                })).execute();
            }
        });
        view.findViewById(R.id.create_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                am.sendNewAccFragment();
            }
        });

        return view;
    }

    public interface ILogin {

        void setAuthResponse(@Nullable Data.AuthResponse acc);

        void sendHomeFragment();

        void sendNewAccFragment();

    }

}