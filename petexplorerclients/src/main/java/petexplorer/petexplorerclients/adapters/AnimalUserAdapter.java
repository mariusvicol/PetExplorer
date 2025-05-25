package petexplorer.petexplorerclients.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.List;

import domain.AnimalPierdut;
import petexplorer.petexplorerclients.R;

public class AnimalUserAdapter extends RecyclerView.Adapter<AnimalUserAdapter.AnimalUserViewHolder> {

    public interface OnResolveClickListener {
        void onResolveClick(AnimalPierdut animal);
    }

    private List<AnimalPierdut> animale;
    private final OnResolveClickListener resolveClickListener;

    public AnimalUserAdapter(List<AnimalPierdut> animale, OnResolveClickListener resolveClickListener) {
        this.animale = animale;
        this.resolveClickListener = resolveClickListener;
    }

    @NonNull
    @Override
    public AnimalUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_animal_user, parent, false);
        return new AnimalUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalUserViewHolder holder, int position) {
        holder.bind(animale.get(position), resolveClickListener);
    }

    @Override
    public int getItemCount() {
        return animale.size();
    }

    public void updateData(List<AnimalPierdut> newList) {
        this.animale = newList;
        notifyDataSetChanged();
    }

    public static class AnimalUserViewHolder extends RecyclerView.ViewHolder {
        TextView tvNume, tvData, tvDescriere, tvTelefon, tvRezolvat;
        ImageView imgPoza;
        Button btnRezolvat;

        public AnimalUserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNume = itemView.findViewById(R.id.tvNume);
            tvData = itemView.findViewById(R.id.tvData);
            tvDescriere = itemView.findViewById(R.id.tvDescriere);
            tvTelefon = itemView.findViewById(R.id.tvTelefon);
            imgPoza = itemView.findViewById(R.id.imgPoza);
            btnRezolvat = itemView.findViewById(R.id.btnMarkResolved);
            tvRezolvat = itemView.findViewById(R.id.tvRezolvat);
        }

        public void bind(AnimalPierdut animal, OnResolveClickListener resolveClickListener) {
            tvNume.setText(animal.getNumeAnimal());
            tvData.setText(animal.getDataCaz() != null ? animal.getDataCaz().toString() : "fără dată");
            tvDescriere.setText(animal.getDescriere());
            tvTelefon.setText(animal.getNrTelefon());

            if (animal.getPoza() != null && !animal.getPoza().isEmpty()) {
                String imageUrl = "http://10.0.2.2:8080" + animal.getPoza();
                Glide.with(itemView.getContext())
                        .load(imageUrl)
                        .placeholder(R.drawable.dog2)
                        .error(R.drawable.error_image)
                        .fallback(R.drawable.dog2)
                        .into(imgPoza);
            } else {
                imgPoza.setImageResource(R.drawable.dog2);
            }

            if (animal.getRezolvat()) {
                itemView.setAlpha(0.7f);
                btnRezolvat.setVisibility(View.GONE);
                tvRezolvat.setVisibility(View.VISIBLE);
            } else {
                itemView.setAlpha(1f);
                btnRezolvat.setVisibility(View.VISIBLE);
                tvRezolvat.setVisibility(View.GONE);
                btnRezolvat.setOnClickListener(v -> resolveClickListener.onResolveClick(animal));
            }

        }

    }
}
