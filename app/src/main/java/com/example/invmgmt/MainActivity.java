package com.example.invmgmt;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.invmgmt.db.Data;
import com.example.invmgmt.fragment.LoginFragment;
import com.example.invmgmt.fragment.RegisterFragment;

/**
 * SSDI 6112 Project Inventory Management
 */
public class MainActivity extends AppCompatActivity implements LoginFragment.ILogin, RegisterFragment.IRegister {

    Data.AuthResponse resp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendLoginFragment();
    }

    public void sendLoginFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, LoginFragment.newInstance())
                .commit();
    }

    public @Nullable
    Data.AuthResponse getAuthResponse() {
        return resp;
    }

    public void setAuthResponse(@Nullable Data.AuthResponse acc) {
        this.resp = acc;
    }

    public void sendHomeFragment() {

    }

    public void sendNewAccFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, RegisterFragment.newInstance())
                .commit();
    }

}