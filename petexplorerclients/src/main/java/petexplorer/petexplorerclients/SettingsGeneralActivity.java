package petexplorer.petexplorerclients;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsGeneralActivity extends AppCompatActivity {

    private Button languageButton, themeButton, notificationsButton, backButton;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_general);

        prefs = getSharedPreferences("app_settings", MODE_PRIVATE);

        languageButton = findViewById(R.id.languageButton);
        themeButton = findViewById(R.id.themeButton);
        notificationsButton = findViewById(R.id.notificationsButton);
        backButton = findViewById(R.id.backButton);

        languageButton.setOnClickListener(v -> showLanguageDialog());
        themeButton.setOnClickListener(v -> toggleTheme());
        notificationsButton.setOnClickListener(v ->
                Toast.makeText(this, "Notificările vor fi disponibile în curând.", Toast.LENGTH_SHORT).show());
        backButton.setOnClickListener(v -> finish());
    }

    private void showLanguageDialog() {
        final String[] languages = {"Română", "English"};
        new AlertDialog.Builder(this)
                .setTitle("Selectează limba")
                .setItems(languages, (dialog, which) -> {
                    String langCode = which == 0 ? "ro" : "en";
                    prefs.edit().putString("lang", langCode).apply();
                    Toast.makeText(this, "Limba selectată: " + languages[which], Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    private void toggleTheme() {
        boolean darkMode = prefs.getBoolean("dark_mode", false);
        boolean newMode = !darkMode;
        prefs.edit().putBoolean("dark_mode", newMode).apply();

        AppCompatDelegate.setDefaultNightMode(
                newMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );

        Toast.makeText(this, "Tema a fost schimbată la " + (newMode ? "Întunecat" : "Deschis"), Toast.LENGTH_SHORT).show();
        recreate();
    }

}
