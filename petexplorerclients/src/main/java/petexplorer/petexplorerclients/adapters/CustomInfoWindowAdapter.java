package petexplorer.petexplorerclients.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import domain.utils.CustomInfoWindowData;
import petexplorer.petexplorerclients.R;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowAdapter(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        return null;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        View view = LayoutInflater.from(context).inflate(R.layout.pin_info_window, null);

        TextView title = view.findViewById(R.id.infoWindowTitle);
        TextView nrTel = view.findViewById(R.id.infoWindowNrTel);
        TextView program = view.findViewById(R.id.infoWindowProgram);
        ImageView image = view.findViewById(R.id.infoWindowIv);

        var tag = marker.getTag();
        if (tag instanceof CustomInfoWindowData) {
            CustomInfoWindowData data = (CustomInfoWindowData) tag;
            title.setText(data.getTitle());
            nrTel.setText(data.getNrTel());
            program.setText(data.getProgram());
            image.setImageResource(data.getImage());
        }

        return view;
    }
}
