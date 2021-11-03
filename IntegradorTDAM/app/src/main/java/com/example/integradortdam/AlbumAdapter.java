package com.example.integradortdam;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.integradortdam.entities.AlbumModel;

import java.util.List;



public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private List<AlbumModel> albumModelList;

    public AlbumAdapter(List<AlbumModel> albumModelList) {
        this.albumModelList = albumModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = albumModelList.get(position).getTitle();
        Integer tamanio = albumModelList.get(position).getTotal();
        int imagen1 = albumModelList.get(position).getImagen1();
        int imagen2 = albumModelList.get(position).getImagen2();
        int imagen3 = albumModelList.get(position).getImagen3();
        int imagen4 = albumModelList.get(position).getImagen4();
        holder.name.setText(name);
        holder.tamanio.setText(tamanio.toString() + " fotos");
        holder.imagen1.setImageResource(imagen1);
        holder.imagen2.setImageResource(imagen2);
        holder.imagen3.setImageResource(imagen3);
        holder.imagen4.setImageResource(imagen4);

        final AlbumModel item = albumModelList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), SecondActivity.class);
               intent.putExtra("ItemDetail", item);
                holder.itemView.getContext().startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return albumModelList.size();
    }




    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView tamanio;
        private ImageView imagen1;
        private ImageView imagen2;
        private ImageView imagen3;
        private ImageView imagen4;
        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.albumName);
            tamanio = (TextView) v.findViewById(R.id.tamanioAlbum);
            imagen1 = (ImageView) v.findViewById(R.id.imageName1);
            imagen2 = (ImageView) v.findViewById(R.id.imageName2);
            imagen3 = (ImageView) v.findViewById(R.id.imageName3);
            imagen4 = (ImageView) v.findViewById(R.id.imageName4);
        }
    }

}
