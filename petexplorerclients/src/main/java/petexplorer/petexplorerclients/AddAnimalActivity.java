package petexplorer.petexplorerclients;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import domain.AnimalPierdut;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.ApiService;

public class AddAnimalActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private LatLng selectedLatLng;
    private Uri imageUri;
    private static final int REQUEST_IMAGE_PICK = 101;
    private static final int REQUEST_IMAGE_CAPTURE = 102;
    private Uri selectedImageUri;
    private String tipCaz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);

        tipCaz = getIntent().getStringExtra("tipCaz");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        findViewById(R.id.btnSubmit).setOnClickListener(v -> submitAnimal());
        findViewById(R.id.btnUploadPhoto).setOnClickListener(v -> showImagePickerOptions());
    }

    private void showImagePickerOptions() {
        String[] options = {"Fă o poză", "Alege din galerie"};

        new android.app.AlertDialog.Builder(this)
                .setTitle("Încarcă o imagine")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        openCamera();
                    } else {
                        openGallery();
                    }
                })
                .show();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng cluj = new LatLng(46.770439, 23.591423);
        map.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(cluj, 13));
        map.setOnMapClickListener(latLng -> {
            selectedLatLng = latLng;
            map.clear();
            map.addMarker(new MarkerOptions().position(latLng).title("Locație selectată"));
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView imagePreview = findViewById(R.id.imagePreview);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                selectedImageUri = data.getData();
            } else if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                selectedImageUri = data.getData(); // Unele dispozitive returnează thumbnail
                if (selectedImageUri == null && data.getExtras() != null) {
                    // alternativ, creezi un Bitmap din extras și îl salvezi
                    Toast.makeText(this, "Imagine capturată (thumbnail)", Toast.LENGTH_SHORT).show();
                }
            }

            if (selectedImageUri != null) {
                imagePreview.setImageURI(selectedImageUri);
                imagePreview.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Imagine selectată!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Nu s-a putut obține imaginea", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void submitAnimal() {
        if (selectedLatLng == null || selectedImageUri == null) {
            Toast.makeText(this, "Completează toate câmpurile", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            File imageFile = createTempFileFromUri(selectedImageUri);
            RequestBody requestFile = RequestBody.create(imageFile, MediaType.parse("image/*"));
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("imagine", imageFile.getName(), requestFile);

            RequestBody nume = RequestBody.create(getTextFromField(R.id.editNume), MediaType.parse("text/plain"));
            RequestBody descriere = RequestBody.create(getTextFromField(R.id.editDescriere), MediaType.parse("text/plain"));
            RequestBody lat = RequestBody.create(String.valueOf(selectedLatLng.latitude), MediaType.parse("text/plain"));
            RequestBody lng = RequestBody.create(String.valueOf(selectedLatLng.longitude), MediaType.parse("text/plain"));
            RequestBody caz = RequestBody.create(tipCaz, MediaType.parse("text/plain"));
            RequestBody telefon = RequestBody.create("0744000000", MediaType.parse("text/plain")); // Poți adăuga câmp input pentru el

            ApiService apiService = RetrofitClient.getApiService();
            Call<AnimalPierdut> call = apiService.uploadAnimal(imagePart, nume, descriere, lat, lng, caz, telefon);

            call.enqueue(new Callback<AnimalPierdut>() {
                @Override
                public void onResponse(Call<AnimalPierdut> call, Response<AnimalPierdut> response) {
                    if (response.isSuccessful()) {
                        AnimalPierdut animal = response.body();
                        Toast.makeText(AddAnimalActivity.this, "Animal adăugat: " + animal.getNumeAnimal(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddAnimalActivity.this, "Eroare la trimitere", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AnimalPierdut> call, Throwable t) {
                    Toast.makeText(AddAnimalActivity.this, "Eroare rețea: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Eroare la procesarea fișierului", Toast.LENGTH_SHORT).show();
        }
    }

    private File createTempFileFromUri(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        String fileName = "upload_" + System.currentTimeMillis() + ".jpg";
        File tempFile = new File(getCacheDir(), fileName);

        if (inputStream != null) {
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.close();
            inputStream.close();
        }

        return tempFile;
    }


    private String getTextFromField(int fieldId) {
        EditText field = findViewById(fieldId);
        return field.getText().toString().trim();
    }

}