package com.example.smartcoala1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

public class LoginActivity2 extends AppCompatActivity {

    private EditText email, password;
    private TextView forgotPass;
    private Button btnLogin, create_acc;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mp = MediaPlayer.create(LoginActivity2.this, R.raw.buttonsound);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.loginbtn);
        forgotPass = findViewById(R.id.fp);
        create_acc = findViewById(R.id.create_acc);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

      //  if (user != null) {
        //    finish();
          //  startActivity(new Intent(LoginActivity2.this, DashboardFragment.class));
        //}

        password.setHint("Password");

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    password.setHint("");
                else
                    password.setHint("Password");
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mp.start();
                String inEmail = email.getText().toString().trim();
                String inPassword = password.getText().toString().trim();

                if (validateInput(inEmail, inPassword)) {
                    signUser(inEmail, inPassword);
                }

            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                startActivity(new Intent(LoginActivity2.this, PasswordResetActivity.class));
            }
        });

        create_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                startActivity(new Intent(LoginActivity2.this, RegistrationActivity2.class));
            }
        });
    }

    public void signUser(final String email, final String password) {
        showProgressDialog();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user != null) {
                        DynamicToast.makeSuccess(LoginActivity2.this, "Login Successful!", 10).show();
                        startActivity(new Intent(LoginActivity2.this, LayoutVoiceControlActivity.class));
                        finish();
                    }
                } else {
                    DynamicToast.makeError(LoginActivity2.this, "Invalid Email or Password", 10).show();
                }
            }
        });
    }

    public boolean validateInput(String inemail, String inpassword) {
        if (inemail.isEmpty()) {
            email.setError("Email field is empty.");
            return false;
        }
        if (inpassword.isEmpty()) {
            password.setError("Password is empty.");
            return false;
        }
        return true;
    }

    private void showProgressDialog() {
        new LottieDialogFragment().show(getSupportFragmentManager(), "pd");
    }
}
