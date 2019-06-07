package com.example.demo_rahuldshetty;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button regBtn;
    private EditText email,pass,cpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        regBtn = findViewById(R.id.reg_btn);
        email = findViewById(R.id.reg_email);
        pass = findViewById(R.id.reg_pass);
        cpass = findViewById(R.id.reg_pass2);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordText = pass.getText().toString();
                String password2Text = cpass.getText().toString();
                String emailText = email.getText().toString();

                if(!TextUtils.isEmpty(password2Text) && !TextUtils.isEmpty(password2Text) && !TextUtils.isEmpty(emailText)){
                    if(validate(emailText,passwordText,password2Text)){
                            mAuth.createUserWithEmailAndPassword(emailText,password2Text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Intent activity = new Intent(Register.this,Profile.class);
                                        startActivity(activity);
                                        finish();
                                    }
                                    else{
                                        makeToast("Error registering"+task);
                                    }
                                }
                            });
                    }
                }
                else{
                    makeToast("Enter all fields");
                }
            }
        });

    }

    void makeToast(String msg){
        Toast.makeText(Register.this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent activity = new Intent(Register.this,MainActivity.class);
        startActivity(activity);
        finish();
    }

    private boolean validate(String email,String password1,String password2){

        if(!password1.equals(password2))
        {
            makeToast("Password mismatch");
            return false;
        }
        else if(!email.matches("[a-zA-Z0-9_.%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9_.]{2,}"))
        {
            makeToast("Invalid Email");
            return false;
        }
        else if(password1.length()<8){
            makeToast("Password must be atleast 8 characters long.");
            return false;
        }
        return true;
    }

}
