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

public class RegisterActivity extends AppCompatActivity {
    protected Button registerButton;
    protected EditText emailEditText, nameEditText, phoneEditText, passwordEditText, retryPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        emailEditText = findViewById(R.id.emailEditText);
        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        retryPasswordEditText = findViewById(R.id.retryPasswordEditText);

        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> register());
    }

    private void register() {
        if(emailEditText.getText().isEmpty() || passwordEditText.getText().isEmpty() || nameEditText.getText().isEmpty() || phoneEditText.getText().isEmpty()){
            Toast.makeText(RegisterActivity.this, "Eroare inregistrare! Fieldurile nu pot fi goale!", Toast.LENGTH_SHORT).show();
        }else{
            if(phoneEditText.getText().length()>9){
                ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
                User registerRequest=new User();
                registerRequest.setEmail(emailEditText.getText().toString());
                registerRequest.setPassword(passwordEditText.getText().toString());
                registerRequest.setNr_Telefon(phoneEditText.getText().toString());
                registerRequest.setNume(nameEditText.getText().toString());
                apiService.register(registerRequest).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                User user = response.body();
                                SharedPreferences prefs = getSharedPreferences("user_data", MODE_PRIVATE);
                                prefs.edit()
                                        .putInt("user_id", user.getId())
                                        .putString("email", user.getEmail())
                                        .apply();

                                Intent intent = new Intent(RegisterActivity.this, MapsActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "User deja existent!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, "Eroare de rețea!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this,"Eroare inregistrare! Numarul de telefon nu poate fi mai mic de 9 cifre",Toast.LENGTH_SHORT).show();
                }
            }
        }
}
