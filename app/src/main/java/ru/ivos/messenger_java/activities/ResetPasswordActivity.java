package ru.ivos.messenger_java.activities;

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

import ru.ivos.messenger_java.R;
import ru.ivos.messenger_java.viewmodels.ResetPasswordViewModel;

public class ResetPasswordActivity extends AppCompatActivity {

    private static final String EXTRA_EMAIL = "email";

    private EditText etEmailRPA;
    private Button btnLoginRPA;

    private ResetPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        etEmailRPA = findViewById(R.id.etEmailRPA);
        btnLoginRPA = findViewById(R.id.btnLoginRPA);

        viewModel = new ViewModelProvider(this).get(ResetPasswordViewModel.class);

        observeViewModel();

        String email = getIntent().getStringExtra(EXTRA_EMAIL);
        etEmailRPA.setText(email);

        btnLoginRPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmailRPA.getText().toString().trim();
                viewModel.resetPassword(email);
            }
        });
    }

    private void observeViewModel() {
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error != null) {
                    Toast.makeText(ResetPasswordActivity.this, error, Toast.LENGTH_LONG).show();
                }
            }
        });

        viewModel.getIsSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean success) {
                if(success){
                    Toast.makeText(ResetPasswordActivity.this, "Link was send successfully", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public static Intent newIntent (Context context, String email) {
        Intent intent =  new Intent(context, ResetPasswordActivity.class);
        intent.putExtra(EXTRA_EMAIL, email);
        return intent;
    }
}