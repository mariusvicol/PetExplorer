package petexplorer.petexplorerclients;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import domain.utils.CustomInfoWindowData;
import domain.utils.LocatieFavoritaDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.ApiService;

public class PlaceBottomSheet extends BottomSheetDialogFragment {

    public interface OnFavoriteChangedListener {
        void onFavoriteChanged(LocatieFavoritaDTO place, boolean added);
    }

    protected CustomInfoWindowData data;
    private Integer userId;
    private OnFavoriteChangedListener favoriteChangedListener;

    public PlaceBottomSheet() { }

    public void setData(CustomInfoWindowData data, Integer userId) {
        this.data = data;
        this.userId = userId;
    }

    public void setFavoriteChangedListener(OnFavoriteChangedListener listener) {
        this.favoriteChangedListener = listener;
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

            checkBox.setOnCheckedChangeListener((btnView, isChecked) -> {
                ApiService apiService = RetrofitClient.getApiService();

                LocatieFavoritaDTO place = new LocatieFavoritaDTO();
                place.setIdUser(userId);
                place.setType(data.getLocationType());
                place.setIdLocation(data.getEntityId());

                if (isChecked) {
                    Log.d("Tag", "Is about to add: " + place);
                    Call<Void> addCall = apiService.addFavoritePlace(place);
                    addCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Locație adăugată la favorite cu succes!", Toast.LENGTH_SHORT).show();
                                data.setChecked(true);

                                if (favoriteChangedListener != null) {
                                    favoriteChangedListener.onFavoriteChanged(place, true);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getContext(), "Eroare la salvare", Toast.LENGTH_SHORT).show();
                            checkBox.setChecked(false);
                        }
                    });
                } else {
                    Log.d("Tag", "Is about to be deleted: " + place);
                    Call<Void> deleteCall = apiService.deleteFavoritePlace(place.getIdUser(), place.getIdLocation(), place.getType());

                    deleteCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Locație ștearsă de la favorite cu succes!", Toast.LENGTH_SHORT).show();
                                data.setChecked(false);

                                if (favoriteChangedListener != null) {
                                    favoriteChangedListener.onFavoriteChanged(place, false);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getContext(), "Eroare la ștergere: ", Toast.LENGTH_SHORT).show();
                            checkBox.setChecked(false);
                        }
                    });
                }
            });
        }

        Button closeButton = rootView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> dismiss());

        return rootView;
    }
}
