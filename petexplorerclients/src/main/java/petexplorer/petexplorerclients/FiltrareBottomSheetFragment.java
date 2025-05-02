package petexplorer.petexplorerclients;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FiltrareBottomSheetFragment extends BottomSheetDialogFragment {

    public FiltrareBottomSheetFragment() {
        // Constructorul implicit
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflatează layout-ul pentru BottomSheet
        View rootView = inflater.inflate(R.layout.filtrare_bottom_sheet_fragment, container, false);

        // Butoane pentru filtrare
        ImageButton filterCabineteButton = rootView.findViewById(R.id.filterCabineteButton);
        ImageButton filterFarmaciiButton = rootView.findViewById(R.id.filterFarmaciiButton);
        ImageButton filterSaloaneButton = rootView.findViewById(R.id.filterSaloaneButton);
        ImageButton filterPensiuniButton = rootView.findViewById(R.id.filterPensiuniButton);
        ImageButton filterMagazineButton = rootView.findViewById(R.id.filterMagazineButton);

        LinearLayout buttonsLayout = rootView.findViewById(R.id.buttonsLayout);

        SearchView searchView = rootView.findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.trim().isEmpty()) {
                    buttonsLayout.setVisibility(View.GONE);
                } else {
                    buttonsLayout.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });


        filterMagazineButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Filtrare 1 activata: Magazine Veterinare", Toast.LENGTH_SHORT).show();
            MapsActivity mapsActivity = (MapsActivity) getActivity();
            if (mapsActivity != null) {
                mapsActivity.loadMagazine();
            }
        });


        filterFarmaciiButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Filtrare 2 activată: Farmacii veterinare", Toast.LENGTH_SHORT).show();
             MapsActivity mapsActivity = (MapsActivity) getActivity();
            if (mapsActivity != null) {
                mapsActivity.loadFarmaciiVeterinare();
            }
        });


        filterSaloaneButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Filtrare 3 activată: Saloane", Toast.LENGTH_SHORT).show();
            // Aici adaugi logica pentru filtrare 3
        });

        filterPensiuniButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Filtrare 4 activată: Pensiuni", Toast.LENGTH_SHORT).show();
            // Aici adaugi logica pentru filtrare 4
        });

        Button closeButton = rootView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> dismiss());

        //setTouchListenerForButton(filterCabineteButton);
        //setTouchListenerForButton(filterFarmaciiButton);
        //setTouchListenerForButton(filterSaloaneButton);
        //setTouchListenerForButton(filterPensiuniButton);

        return rootView;
    }

    // Funcție pentru a permite mișcarea butoanelor
    @SuppressLint("ClickableViewAccessibility")
    private void setTouchListenerForButton(Button button) {
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Nu se face nimic special când apăsăm pe buton
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        // Mișcăm butonul conform mișcării degetului
                        float x = event.getRawX();
                        float y = event.getRawY();

                        // Calculăm poziția butonului
                        // Setează noua poziție a butonului, doar pe orizontală
                        float minX = 0;
                        float maxX = getResources().getDisplayMetrics().widthPixels - v.getWidth();
                        float minY = 0;
                        float maxY = getResources().getDisplayMetrics().heightPixels - v.getHeight();

                        // Permitem mișcarea doar pe orizontală (adică păstrăm y constant)
                        v.setX(Math.max(minX, Math.min(x - v.getWidth() / 2, maxX)));
                        v.setY(Math.max(minY, Math.min(y - v.getHeight() / 2, maxY)));

                        return true;

                    case MotionEvent.ACTION_UP:
                        // La eliberarea butonului, mișcarea se oprește
                        return true;

                    default:
                        return false;
                }
            }
        });
    }
}
