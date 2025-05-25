package petexplorer.petexplorerclients;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import domain.AnimalPierdut;
import petexplorer.petexplorerclients.adapters.AnimalAdapter;
import petexplorer.petexplorerclients.notification.WebSocketStompClientManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.ApiService;

// import pentru STOMP
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.LifecycleEvent;
import ua.naiksoftware.stomp.dto.StompMessage;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class LostAnimalsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private RecyclerView recyclerView;
    private Button btnVeziPierdute, btnVeziGasite;
    private String cazCurent = "pierdut";
    private AnimalAdapter adapter;
    private static final int REQUEST_ADD_ANIMAL = 1001;



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
        Button btnAddAnimal = findViewById(R.id.btnAddAnimal);

        btnVeziPierdute.setOnClickListener(v -> {
            cazCurent = "pierdut";
            loadAnimalePierdute();
            btnAddAnimal.setText("+ Animal Pierdut");
            btnVeziPierdute.setBackground(ContextCompat.getDrawable(this, R.drawable.custom_button));
            btnVeziGasite.setBackground(ContextCompat.getDrawable(this, R.drawable.not_focused_button));
        });

        btnVeziGasite.setOnClickListener(v -> {
            cazCurent = "vazut";
            loadAnimaleGasite();
            btnAddAnimal.setText("+ Am găsit un animal");
            btnVeziGasite.setBackground(ContextCompat.getDrawable(this, R.drawable.custom_button));
            btnVeziPierdute.setBackground(ContextCompat.getDrawable(this, R.drawable.not_focused_button));
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.lost_animals_map);
        mapFragment.getMapAsync(this);

        loadAnimalePierdute();
        btnVeziPierdute.setBackground(ContextCompat.getDrawable(this, R.drawable.custom_button));
        btnVeziGasite.setBackground(ContextCompat.getDrawable(this, R.drawable.not_focused_button));

        btnAddAnimal.setOnClickListener(v -> {
            Intent intent = new Intent(LostAnimalsActivity.this, AddAnimalActivity.class);
            intent.putExtra("tipCaz", cazCurent);
            startActivityForResult(intent, REQUEST_ADD_ANIMAL);
        });

        ImageButton btnBackToMap = findViewById(R.id.btnBackToMap);
        btnBackToMap.setOnClickListener(v -> {
            Intent intent = new Intent(LostAnimalsActivity.this, MapsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_design));
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng initialLoc = new LatLng(46.770519, 23.590103);
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
                                    .icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromDrawable(R.drawable.location))));
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
                                    .icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromDrawable(R.drawable.location))));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD_ANIMAL && resultCode == RESULT_OK) {
            if ("pierdut".equals(cazCurent)) {
                loadAnimalePierdute();
            } else {
                loadAnimaleGasite();
            }
        }
    }

    private Bitmap getBitmapFromDrawable(@DrawableRes int resId) {
        Bitmap bitmap = null;
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), resId, null);

        if (drawable != null) {
            bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }

        return bitmap;
    }
}
