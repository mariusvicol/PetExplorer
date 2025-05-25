package petexplorer.petexplorerclients;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import domain.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.ApiService;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> login());
    }

    private void login() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Completeaza toate campurile!", Toast.LENGTH_SHORT).show();
            return;
        }

        User loginRequest = new User();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        apiService.login(loginRequest).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    SharedPreferences prefs = getSharedPreferences("user_data", MODE_PRIVATE);
                    prefs.edit()
                            .putInt("user_id", user.getId())
                            .putString("email", user.getEmail())
                            .putString("full_name", user.getNume())
                            .apply();

                    Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                    startActivity(intent);

                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Email sau parola greșita!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Eroare de rețea!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

