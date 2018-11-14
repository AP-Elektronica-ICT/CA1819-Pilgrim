package com.example.midasvg.pilgrim;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.internal.Objects;

import java.io.Console;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    boolean doubleBackToExit = false;
    CallbackManager callbackManager;
    LoginButton loginFacebook;
    private static final String EMAIL = "email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //De Action Bar van de app & status bar van de GSM verbergen.
        setContentView(R.layout.activity_login);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getSupportActionBar().hide();

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();


        loginFacebook = (LoginButton) findViewById(R.id.login_button);
        loginFacebook.setReadPermissions(Arrays.asList(EMAIL));

        facebookLogin();






        //Login knop, voorlopig enkel hardcoded, brengt de gebruiker naar het hoofdscherm.
        final Button loginButton = (Button) this.findViewById(R.id.loginBttn);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        final TextView registerText = (TextView) this.findViewById(R.id.txtRegister);
        registerText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //checkCameraPermission();
        checkPositioningPermission();
    }

    @Override
    public  void onBackPressed(){
        if (doubleBackToExit){
            super.onBackPressed();
            return;
        }

        this.doubleBackToExit = true;
        Toast.makeText(this, "Press again to leave", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExit = false;
            }
        },2000);
    }

    //Deze methode gaat een pop-up geven wanneer de app voor de eerste keer wordt
    //opgestart om te vragen of de Camera mag gebruikt worden.
    private void checkCameraPermission(){
        if(ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            //Permission is not granted
            if(ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                    Manifest.permission.CAMERA)){
                Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();


            }else{
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
    }

    private void checkPositioningPermission(){
        if(ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            //Permission is not granted
            if(ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();

            }else{
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }


    private void facebookLogin(){
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Login succesful", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Login with Facebook cancelled", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Log.w("Error", error);
                Toast.makeText(getApplicationContext(), "An error occurred, please try again", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
