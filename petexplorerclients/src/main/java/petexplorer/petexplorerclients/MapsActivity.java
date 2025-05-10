package petexplorer.petexplorerclients;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;
import android.Manifest;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import domain.CabinetVeterinar;
import domain.Farmacie;
import domain.Magazin;
import domain.Parc;
import domain.PensiuneCanina;
import domain.Salon;
import petexplorer.petexplorerclients.databinding.ActivityMapsBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.ApiService;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int currentUserId;

    private ActivityMapsBinding binding;
    private Button filterButton;
    private final int FINE_PERMISSION_CODE = 1;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences("user_data", MODE_PRIVATE);
        this.currentUserId = prefs.getInt("user_id", -1);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageButton menuButton = findViewById(R.id.menuButton);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        menuButton.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        ImageButton animalePierduteButton = findViewById(R.id.animalePierduteButton);

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_account) {
                Intent intent = new Intent(this, MyAccountActivity.class);
                intent.putExtra("USER_ID", currentUserId);
                startActivity(intent);
            } else if (id == R.id.nav_favorites) {
                Toast.makeText(this, "Favorite", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_lost_pets) {
                animalePierduteButton.performClick();
                Toast.makeText(this, "Animale pierdute", Toast.LENGTH_SHORT).show();
            }else if (id == R.id.nav_settings) {
                    Intent intent = new Intent(this, SettingsGeneralActivity.class);
                    startActivity(intent);
                }
            else if (id == R.id.nav_logout) {
                Toast.makeText(this, "Delogare", Toast.LENGTH_SHORT).show();
                finish();
            }
            drawerLayout.closeDrawers();
            return true;
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        filterButton = findViewById(R.id.filterButton);
        filterButton.setOnClickListener(view -> showBottomSheet());


        animalePierduteButton.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, LostAnimalsActivity.class);
            startActivity(intent);
        });
    }


    private void showBottomSheet() {
        FiltrareBottomSheetFragment bottomSheet = new FiltrareBottomSheetFragment();
        bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_design));
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

    public void loadVeterinaryOffices() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<CabinetVeterinar>> call = apiService.getCabineteVeterinare();
        call.enqueue(new Callback<List<CabinetVeterinar>>() {
            @Override
            public void onResponse(Call<List<CabinetVeterinar>> call, Response<List<CabinetVeterinar>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Răspunsul serverului: " + response.body().toString()); // Log la răspunsul primit
                    List<CabinetVeterinar> cabinetVeterinarList = response.body();
                    mMap.clear();
                    for (CabinetVeterinar cabinet : cabinetVeterinarList) {
                        LatLng cabinetLocation = new LatLng(cabinet.getLatitudine(), cabinet.getLongitudine());
                        Log.d("DEBUG", "Cabinet: " + cabinet.getNume_cabinet() + " Lat: " + cabinet.getLatitudine() + " Long: " + cabinet.getLongitudine());
                        mMap.addMarker(new MarkerOptions()
                                .position(cabinetLocation)
                                .title(cabinet.getNume_cabinet()));
                    }
                    if (!cabinetVeterinarList.isEmpty()) {
                        LatLng firstLocation = new LatLng(cabinetVeterinarList.get(0).getLatitudine(), cabinetVeterinarList.get(0).getLongitudine());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 12));
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

    public void loadPensiuni() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<PensiuneCanina>> call = apiService.getPensiuniCanine();

        call.enqueue(new Callback<List<PensiuneCanina>>() {
            @Override
            public void onResponse(Call<List<PensiuneCanina>> call, Response<List<PensiuneCanina>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Răspunsul serverului: " + response.body());
                    List<PensiuneCanina> pensiuniList = response.body();
                    mMap.clear();
                    for (PensiuneCanina p : pensiuniList) {
                        LatLng pLoc = new LatLng(p.getLatitude(), p.getLongitude());
                        Log.d("DEBUG", "Pensiune: " + p.getName()+ " Lat: " + p.getLatitude() + " Long: " + p.getLongitude());
                        mMap.addMarker(new MarkerOptions()
                                .position(pLoc)
                                .title(p.getName()));
                    }
                    if (!pensiuniList.isEmpty()) {
                        LatLng firstLocation = new LatLng(pensiuniList.get(0).getLatitude(), pensiuniList.get(0).getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 12));
                    }
                } else {
                    Log.e(TAG, "Eroare răspuns server: " + response.code());
                    Toast.makeText(MapsActivity.this, "Eroare la obținerea pensiunilor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PensiuneCanina>> call, Throwable t) {
                Log.e(TAG, "Eroare la conectarea la server: ", t);
                Toast.makeText(MapsActivity.this, "Eroare la conectarea la server", Toast.LENGTH_SHORT).show();
            }
        });
     }

     public void loadSaloane() {
         ApiService apiService = RetrofitClient.getApiService();
         Call<List<Salon>> call = apiService.getSaloane();

         call.enqueue(new Callback<List<Salon>>() {
             @Override
             public void onResponse(Call<List<Salon>> call, Response<List<Salon>> response) {
                 if (response.isSuccessful() && response.body() != null) {
                     Log.d(TAG, "Răspunsul serverului: " + response.body());
                     List<Salon> saloaneList = response.body();
                     mMap.clear();
                     for (Salon s : saloaneList) {
                         LatLng pLoc = new LatLng(s.getLatitude(), s.getLongitude());
                         Log.d("DEBUG", "Salon: " + s.getName()+ " Lat: " + s.getLatitude() + " Long: " + s.getLongitude());
                         mMap.addMarker(new MarkerOptions()
                                 .position(pLoc)
                                 .title(s.getName()));
                     }
                     if (!saloaneList.isEmpty()) {
                         LatLng firstLocation = new LatLng(saloaneList.get(0).getLatitude(), saloaneList.get(0).getLongitude());
                         mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 12));
                     }
                 } else {
                     Log.e(TAG, "Eroare răspuns server: " + response.code());
                     Toast.makeText(MapsActivity.this, "Eroare la obținerea saloanelor", Toast.LENGTH_SHORT).show();
                 }
             }

             @Override
             public void onFailure(Call<List<Salon>> call, Throwable t) {
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
                    mMap.clear();
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

    public void loadParcuri() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<Parc>> call = apiService.getParcuri();

        call.enqueue(new Callback<List<Parc>>() {
            @Override
            public void onResponse(Call<List<Parc>> call, Response<List<Parc>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Răspunsul serverului: " + response.body().toString());
                    List<Parc> parcList = response.body();
                    mMap.clear();
                    for (Parc parc : parcList) {
                        LatLng parcLocation = new LatLng(parc.getLatitudine(), parc.getLongitudine());

                        mMap.addMarker(new MarkerOptions()
                                .position(parcLocation)
                                .title(parc.getNume())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))); // culoare verde
                    }
                    if (!parcList.isEmpty()) {
                        LatLng firstLocation = new LatLng(parcList.get(0).getLatitudine(), parcList.get(0).getLongitudine());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 12));
                    }
                } else {
                    Log.e(TAG, "Eroare raspuns server: " + response.code());
                    Toast.makeText(MapsActivity.this, "Eroare la obținerea parcurilor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Parc>> call, Throwable t) {
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
