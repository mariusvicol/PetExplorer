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
        // TODO - add register option button id

        loginOptionBtn.setOnClickListener(v -> onLoginOptionChosen());
        // TODO - add register option button listener
    }


    private void onLoginOptionChosen() {
        Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
