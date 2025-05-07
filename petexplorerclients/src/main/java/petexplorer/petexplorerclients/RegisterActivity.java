package petexplorer.petexplorerclients;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

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
        // TODO
    }
}
