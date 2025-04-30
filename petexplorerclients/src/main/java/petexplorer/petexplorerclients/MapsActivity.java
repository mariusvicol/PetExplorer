package petexplorer.petexplorerclients;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import domain.CabinetVeterinar;
import domain.Farmacie;
import domain.Magazin;
import petexplorer.petexplorerclients.databinding.ActivityMapsBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.ApiService;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Button filterButton;
    private final int FINE_PERMISSION_CODE = 1;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_account) {
                Toast.makeText(this, "Contul tau", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_favorites) {
                Toast.makeText(this, "Favorite", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_lost_pets) {
                Toast.makeText(this, "Animale pierdute", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_settings) {
                Toast.makeText(this, "Setari", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_logout) {
                Toast.makeText(this, "Delogare", Toast.LENGTH_SHORT).show();
                finish();
            }
            drawerLayout.closeDrawers();
            return true;
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        filterButton = findViewById(R.id.filterButton);
        filterButton.setOnClickListener(view -> showBottomSheet());
    }


    private void showBottomSheet() {
        FiltrareBottomSheetFragment bottomSheet = new FiltrareBottomSheetFragment();
        bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (currentLocation != null) {
            LatLng currentCoordinates = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(currentCoordinates).title("My Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentCoordinates, 15));
        } else {
            Toast.makeText(this, "Locația curentă nu este disponibilă", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Permisiunea de locație este necesară", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void loadVeterinaryOffices() {
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
                        Log.d("DEBUG", "Cabinet: " + cabinet.getNume_cabinet() + " Lat: " + cabinet.getLatitudine() + " Long: " + cabinet.getLongitudine());
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

    public void loadMagazine(){
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<Magazin>> call = apiService.getMagazine();
        call.enqueue(new Callback<List<Magazin>>() {
            @Override
            public void onResponse(Call<List<Magazin>> call, Response<List<Magazin>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Raspunsul serverului: " + response.body());
                    List<Magazin> magazinList = response.body();
                    mMap.clear();
                    for (Magazin magazin : magazinList) {
                        LatLng magazinLocation = new LatLng(magazin.getLatitudine(), magazin.getLongitudine());
                        Log.d("DEBUG", "Magazin: " + magazin.getNume() + " Lat: " + magazin.getLatitudine() + " Long: " + magazin.getLongitudine());
                        mMap.addMarker(new MarkerOptions()
                                .position(magazinLocation)
                                .title(magazin.getNume()));
                    }
                    if (!magazinList.isEmpty()) {
                        LatLng firstLocation = new LatLng(magazinList.get(0).getLatitudine(), magazinList.get(0).getLongitudine());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 12));
                    }

                } else {
                    Log.e(TAG, "Eroare raspuns server: " + response.code());
                    Toast.makeText(MapsActivity.this, "Eroare la obtinerea magazinelor veterinare", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<List<Magazin>> call, Throwable t) {
                Log.e(TAG, "Eroare la conectarea la server: ", t);
                Toast.makeText(MapsActivity.this, "Eroare la conectarea la server", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void loadFarmaciiVeterinare(){
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<Farmacie>> call = apiService.getFarmacii();
        call.enqueue(new Callback<List<Farmacie>>() {
            @Override
            public void onResponse(Call<List<Farmacie>> call, Response<List<Farmacie>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Raspunsul serverului: " + response.body());
                    List<Farmacie> farmacieList = response.body();
                    for (Farmacie farmacie:farmacieList) {
                        LatLng farmacieLocation = new LatLng(farmacie.getLatitudine(), farmacie.getLongitudine());
                        mMap.addMarker(new MarkerOptions()
                                .position(farmacieLocation)
                                .title(farmacie.getNume()));
                    }
                    if (!farmacieList.isEmpty()) {
                        LatLng firstLocation = new LatLng(farmacieList.get(0).getLatitudine(), farmacieList.get(0).getLongitudine());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 12));
                    }
                } else {
                    Log.e(TAG, "Eroare raspuns server: " + response.code());
                    Toast.makeText(MapsActivity.this, "Eroare la obtinerea farmaciilor veterinare", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Farmacie>> call, Throwable t) {
                Log.e(TAG, "Eroare la conectarea la server: ", t);
                Toast.makeText(MapsActivity.this, "Eroare la conectarea la server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                currentLocation = location;

                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                if (mapFragment != null) {
                    mapFragment.getMapAsync(MapsActivity.this);
                }
            } else {
                Toast.makeText(MapsActivity.this, "Nu s-a putut obtine locatia curenta", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
