package petexplorer.petexplorerclients;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

    protected Button loginOptionBtn, registerOptionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);

        loginOptionBtn = findViewById(R.id.loginOptionBtn);
        registerOptionBtn = findViewById(R.id.registerOptionBtn);

        loginOptionBtn.setOnClickListener(v -> onLoginOptionChosen());
        registerOptionBtn.setOnClickListener(v -> onRegisterOptionChosen());
    }


    private void onLoginOptionChosen() {
        Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void onRegisterOptionChosen() {
        Intent intent = new Intent(IntroActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

}
