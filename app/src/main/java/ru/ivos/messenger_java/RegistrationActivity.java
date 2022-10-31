package ru.ivos.messenger_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    private EditText etEmailRA;
    private EditText etPasswordRA;
    private EditText etNameRA;
    private EditText etSecondNameRA;
    private EditText etAgeRA;
    private Button btnRegisterRA;

    private RegistrationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initViews();
        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        observeViewModel();

        btnRegisterRA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmailRA.getText().toString().trim();
                String password = etPasswordRA.getText().toString().trim();
                String name = etNameRA.getText().toString().trim();
                String secondName = etSecondNameRA.getText().toString().trim();
                int age = Integer.parseInt(etAgeRA.getText().toString().trim());
                viewModel.signUp(email, password, name, secondName, age);
            }
        });
    }

    private void initViews() {
        etEmailRA = findViewById(R.id.etEmailRA);
        etPasswordRA = findViewById(R.id.etPasswordRA);
        etNameRA = findViewById(R.id.etNameRA);
        etSecondNameRA = findViewById(R.id.etSecondNameRA);
        etAgeRA = findViewById(R.id.etAgeRA);
        btnRegisterRA = findViewById(R.id.btnRegisterRA);
    }

    private void observeViewModel() {
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error != null) {
                    Toast.makeText(RegistrationActivity.this, error, Toast.LENGTH_LONG).show();
                }
            }
        });

        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Intent intent = MainActivity.newIntent(RegistrationActivity.this, firebaseUser.getUid());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }
}