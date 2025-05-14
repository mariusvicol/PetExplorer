package petexplorer.petexplorerclients;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import domain.utils.CustomInfoWindowData;

public class PlaceBottomSheet extends BottomSheetDialogFragment {

    protected CustomInfoWindowData data;

    public PlaceBottomSheet() { }

    public void setData(CustomInfoWindowData data) {
        this.data = data;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.place_bottom_sheet, container, false);

        // setarea textview-urilor cu valorile de care am nevoie
        if (data != null) {
            TextView nameTextView = rootView.findViewById(R.id.nameTextView);
            TextView phoneTextView = rootView.findViewById(R.id.nrTelTextView);
            TextView programTextView = rootView.findViewById(R.id.programTextView);
            ImageView iconImageView = rootView.findViewById(R.id.iconImageView);
            CheckBox checkBox = rootView.findViewById(R.id.checkBox);

            nameTextView.setText(data.getTitle());
            phoneTextView.setText(data.getNrTel());
            programTextView.setText(data.getProgram());
            iconImageView.setImageResource(data.getImage());
            checkBox.setChecked(data.isChecked());
        }

        Button closeButton = rootView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> dismiss());

        return rootView;
    }
}
