package petexplorer.petexplorerclients;

import static android.content.ContentValues.TAG;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.Manifest;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import domain.CabinetVeterinar;
import petexplorer.petexplorerclients.databinding.ActivityMapsBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.ApiService;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Button filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Adăugăm un buton pentru a arăta BottomSheet-ul
        filterButton = findViewById(R.id.filterButton);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ridicăm BottomSheet-ul (puteți personaliza logica de filtrare)
                showBottomSheet();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void showBottomSheet() {
        FiltrareBottomSheetFragment bottomSheet = new FiltrareBottomSheetFragment();
        bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Verificăm permisiunile pentru locație
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        // Permitem utilizarea locației pe hartă
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        // Activăm controalele de zoom
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Creăm un FusedLocationProviderClient
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Solicităm locația curentă
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

                    // Adăugăm un marker pe hartă și mutăm camera
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(userLocation).title("Locația curentă"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));

                    // Încărcăm cabinetele veterinare pe hartă
                    loadVeterinaryOffices();
                } else {
                    Toast.makeText(MapsActivity.this, "Locația curentă nu poate fi obținută", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                Toast.makeText(this, "Permisiunea de locație este necesară", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadVeterinaryOffices() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<CabinetVeterinar>> call = apiService.getCabineteVeterinare();
        call.enqueue(new Callback<List<CabinetVeterinar>>() {
            @Override
            public void onResponse(Call<List<CabinetVeterinar>> call, Response<List<CabinetVeterinar>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Răspunsul serverului: " + response.body().toString()); // Log la răspunsul primit
                    List<CabinetVeterinar> cabinetVeterinarList = response.body();
                    for (CabinetVeterinar cabinet : cabinetVeterinarList) {
                        LatLng cabinetLocation = new LatLng(cabinet.getLatitudine(), cabinet.getLongitudine());
                        mMap.addMarker(new MarkerOptions()
                                .position(cabinetLocation)
                                .title(cabinet.getNume_cabinet()));
                    }
                } else {
                    Log.e(TAG, "Eroare răspuns server: " + response.code()); // Log pentru codul de răspuns al serverului
                    Toast.makeText(MapsActivity.this, "Eroare la obținerea cabinetelor veterinare", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CabinetVeterinar>> call, Throwable t) {
                Log.e(TAG, "Eroare la conectarea la server: ", t);
                Toast.makeText(MapsActivity.this, "Eroare la conectarea la server", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
