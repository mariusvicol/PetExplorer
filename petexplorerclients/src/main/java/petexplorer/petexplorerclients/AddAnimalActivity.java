package petexplorer.petexplorerclients;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddAnimalActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private LatLng selectedLatLng;
    private Uri imageUri;
    private static final int REQUEST_IMAGE_PICK = 101;
    private static final int REQUEST_IMAGE_CAPTURE = 102;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);

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
        // Aici trimiți datele la server, inclusiv locația și imaginea
        if (selectedLatLng == null || imageUri == null) {
            Toast.makeText(this, "Completează toate câmpurile", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: Trimite datele la server
    }
}