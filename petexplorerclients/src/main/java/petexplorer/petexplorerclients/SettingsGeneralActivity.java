package petexplorer.petexplorerclients;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsGeneralActivity extends AppCompatActivity {

    private Button languageButton, themeButton, notificationsButton,backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_general);

        languageButton = findViewById(R.id.languageButton);
        themeButton = findViewById(R.id.themeButton);
        notificationsButton = findViewById(R.id.notificationsButton);
        setContentView(R.layout.activity_settings_general);


        languageButton.setOnClickListener(v ->
                Toast.makeText(this, "Funcționalitatea schimbării limbii va fi adăugată.", Toast.LENGTH_SHORT).show());

        themeButton.setOnClickListener(v ->
                Toast.makeText(this, "Modul întunecat nu este implementat încă.", Toast.LENGTH_SHORT).show());

        notificationsButton.setOnClickListener(v ->
                Toast.makeText(this, "Notificările vor fi disponibile în curând.", Toast.LENGTH_SHORT).show());

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
    }
}

