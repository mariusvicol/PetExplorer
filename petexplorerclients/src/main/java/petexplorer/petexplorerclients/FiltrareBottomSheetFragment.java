package petexplorer.petexplorerclients;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
        Button filterButton1 = rootView.findViewById(R.id.filterButton1);
        Button filterButton2 = rootView.findViewById(R.id.filterButton2);
        Button filterButton3 = rootView.findViewById(R.id.filterButton3);
        Button filterButton4 = rootView.findViewById(R.id.filterButton4);

        // Setează un OnClickListener pentru fiecare buton
        filterButton1.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Filtrare 1 activată", Toast.LENGTH_SHORT).show();
            // Aici adaugi logica pentru filtrare 1
        });

        filterButton2.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Filtrare 2 activată", Toast.LENGTH_SHORT).show();
            // Aici adaugi logica pentru filtrare 2
        });

        filterButton3.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Filtrare 3 activată", Toast.LENGTH_SHORT).show();
            // Aici adaugi logica pentru filtrare 3
        });

        filterButton4.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Filtrare 4 activată", Toast.LENGTH_SHORT).show();
            // Aici adaugi logica pentru filtrare 4
        });

        // Permite mutarea butoanelor
        setTouchListenerForButton(filterButton1);
        setTouchListenerForButton(filterButton2);
        setTouchListenerForButton(filterButton3);
        setTouchListenerForButton(filterButton4);

        return rootView;
    }

    // Funcție pentru a permite mișcarea butoanelor
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
