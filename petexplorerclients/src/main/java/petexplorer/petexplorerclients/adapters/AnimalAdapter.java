package petexplorer.petexplorerclients.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeParseException;
import java.util.Locale;

import com.bumptech.glide.Glide;
import java.util.List;

import domain.AnimalPierdut;
import petexplorer.petexplorerclients.R;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(AnimalPierdut animal);
    }

    private List<AnimalPierdut> animale;
    private final OnItemClickListener listener;

    public AnimalAdapter(List<AnimalPierdut> animale, OnItemClickListener listener) {
        this.animale = animale;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_animal, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        holder.bind(animale.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return animale.size();
    }

    public static class AnimalViewHolder extends RecyclerView.ViewHolder {
        TextView tvNume, tvData, tvDescriere, tvTelefon;
        ImageView imgPoza;

        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNume = itemView.findViewById(R.id.tvNume);
            tvData = itemView.findViewById(R.id.tvData);
            tvDescriere = itemView.findViewById(R.id.tvDescriere);
            tvTelefon = itemView.findViewById(R.id.tvTelefon);
            imgPoza = itemView.findViewById(R.id.imgPoza);
        }

        public void bind(AnimalPierdut animal, OnItemClickListener listener) {
            tvNume.setText(animal.getNumeAnimal());

            String dataRaw = animal.getDataCaz();
            String dataFormatted;
            if (dataRaw != null && !dataRaw.isEmpty()) {
                try {
                    LocalDateTime data = LocalDateTime.parse(dataRaw);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm", new Locale("ro"));
                    dataFormatted = data.format(formatter);
                } catch (Exception e) {
                    dataFormatted = "data invalida";
                }
            } else {
                dataFormatted = "fără dată";
            }

            tvData.setText(dataFormatted);
            tvDescriere.setText(animal.getDescriere());
            tvTelefon.setText(animal.getNrTelefon());

            if (animal.getPoza() != null && !animal.getPoza().isEmpty()) {
                String imageUrl = "http://10.0.2.2:8080" + animal.getPoza();
                Glide.with(itemView.getContext())
                        .load(imageUrl)
                        .placeholder(R.drawable.dog2) // imagine default
                        .error(R.drawable.warning)
                        .fallback(R.drawable.dog2)
                        .into(imgPoza);
            } else {
                imgPoza.setImageResource(R.drawable.dog2);
            }



            itemView.setOnClickListener(v -> listener.onItemClick(animal));
        }
    }

    public void updateData(List<AnimalPierdut> newList) {
        this.animale = newList;
        notifyDataSetChanged();
    }
}
