package petexplorer.petexplorerclients;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import domain.AnimalPierdut;
import domain.Parc;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.ApiService;

public class LostAnimalsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private RecyclerView recyclerView;
    private Button btnVeziPierdute, btnVeziGasite;

    private AnimalAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_animals);

        recyclerView = findViewById(R.id.animalsListRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AnimalAdapter(new ArrayList<>(), animal -> {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(animal.getLatitudine(), animal.getLongitudine()), 16));
        });
        recyclerView.setAdapter(adapter);


        btnVeziPierdute = findViewById(R.id.btnVeziPierdute);
        btnVeziGasite = findViewById(R.id.btnVeziGasite);

        btnVeziPierdute.setOnClickListener(v -> loadAnimalePierdute());
        btnVeziGasite.setOnClickListener(v -> loadAnimaleGasite());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.lost_animals_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_design));
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng initialLoc = new LatLng(45.7489, 21.2087); // de ex. Timișoara
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLoc, 12));
    }

    public void loadAnimalePierdute() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<AnimalPierdut>> call = apiService.getAnimalePierdute();

        call.enqueue(new Callback<List<AnimalPierdut>>() {
            @Override
            public void onResponse(Call<List<AnimalPierdut>> call, Response<List<AnimalPierdut>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Raspunsul serverului: " + response.body());
                    List<AnimalPierdut> animaleList = response.body();
                    mMap.clear();
                    for (AnimalPierdut animal : animaleList) {
                        if(Objects.equals(animal.getTipCaz(), "pierdut")) {
                            LatLng animalLocation = new LatLng(animal.getLatitudine(), animal.getLongitudine());
                            mMap.addMarker(new MarkerOptions()
                                    .position(animalLocation)
                                    .title(animal.getNumeAnimal())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }
                    if (!animaleList.isEmpty()) {
                        LatLng firstLocation = new LatLng(animaleList.get(0).getLatitudine(), animaleList.get(0).getLongitudine());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 12));
                    }
                    adapter.updateData(animaleList.stream()
                            .filter(a -> "pierdut".equals(a.getTipCaz()))
                            .collect(Collectors.toList()));

                } else {
                    Toast.makeText(LostAnimalsActivity.this, "Eroare la obținerea animalelor pierdute", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AnimalPierdut>> call, Throwable t) {
                Log.e(TAG, "Eroare la conectarea la server: ", t);
                Toast.makeText(LostAnimalsActivity.this, "Eroare la conectarea la server", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void loadAnimaleGasite() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<AnimalPierdut>> call = apiService.getAnimalePierdute();

        call.enqueue(new Callback<List<AnimalPierdut>>() {
            @Override
            public void onResponse(Call<List<AnimalPierdut>> call, Response<List<AnimalPierdut>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Raspunsul serverului: " + response.body());
                    List<AnimalPierdut> animaleList = response.body();
                    mMap.clear();
                    for (AnimalPierdut animal : animaleList) {
                        if(Objects.equals(animal.getTipCaz(), "vazut")) {
                            LatLng loc = new LatLng(animal.getLatitudine(), animal.getLongitudine());
                            mMap.addMarker(new MarkerOptions()
                                    .position(loc)
                                    .title(animal.getNumeAnimal())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }
                    if (!animaleList.isEmpty()) {
                        LatLng firstLocation = new LatLng(animaleList.get(0).getLatitudine(), animaleList.get(0).getLongitudine());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 12));
                    }
                    adapter.updateData(animaleList.stream()
                            .filter(a -> "vazut".equals(a.getTipCaz()))
                            .collect(Collectors.toList()));

                } else {
                    Toast.makeText(LostAnimalsActivity.this, "Eroare la obținerea animalelor vazute", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AnimalPierdut>> call, Throwable t) {
                Log.e(TAG, "Eroare la conectarea la server: ", t);
                Toast.makeText(LostAnimalsActivity.this, "Eroare la conectarea la server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
