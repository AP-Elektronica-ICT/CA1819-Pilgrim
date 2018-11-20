package com.example.midasvg.pilgrim;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassActivity extends AppCompatActivity {

    private Button SendEmail;
    private EditText eMailField;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        mAuth = FirebaseAuth.getInstance();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getSupportActionBar().hide();


        SendEmail = (Button) findViewById(R.id.bttnSend);
        eMailField = (EditText) findViewById(R.id.txtEmail);

        SendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = eMailField.getText().toString();

                if(TextUtils.isEmpty(userEmail)){
                    Toast.makeText(ResetPassActivity.this, "Please enter your e-mail first.",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ResetPassActivity.this, "E-mail sent.",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ResetPassActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }else{
                                String error = task.getException().getMessage();
                                Toast.makeText(ResetPassActivity.this, "An error occured."+error,
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });


    }
}
