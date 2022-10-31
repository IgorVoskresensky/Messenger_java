
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmailLA;
    private EditText etPasswordLA;
    private Button btnLoginLA;
    private TextView tvForgotPassLA;
    private TextView tvRegisterLA;

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        observeViewModel();
        setUpClickListeners();
    }

    private void initViews() {
        etEmailLA = findViewById(R.id.etEmailLA);
        etPasswordLA = findViewById(R.id.etPasswordLA);
        btnLoginLA = findViewById(R.id.btnLoginLA);
        tvForgotPassLA = findViewById(R.id.tvForgotPassLA);
        tvRegisterLA = findViewById(R.id.tvRegisterLA);
    }

    private void setUpClickListeners() {
        btnLoginLA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmailLA.getText().toString().trim();
                String password = etPasswordLA.getText().toString().trim();

                viewModel.login(email, password);
            }
        });

        tvForgotPassLA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ResetPasswordActivity.newIntent(LoginActivity.this,
                        etEmailLA.getText().toString().trim());
                startActivity(intent);
            }
        });

        tvRegisterLA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = RegistrationActivity.newIntent(LoginActivity.this);
                startActivity(intent);
            }
        });
    }

    private void observeViewModel() {
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error != null) {
                    Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
                }
            }
        });

        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Intent intent = MainActivity.newIntent(LoginActivity.this, firebaseUser.getUid());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}