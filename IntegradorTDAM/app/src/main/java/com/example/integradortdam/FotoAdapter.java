package com.example.integradortdam;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;



public class FotoAdapter extends RecyclerView.Adapter<FotoAdapter.ViewHolder> {

    private List<FotoModel> fotoModelList;

    public FotoAdapter(List<FotoModel> fotoModelList) { this.fotoModelList = fotoModelList; }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fotos_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //String name = fotoModelList.get(position).getName();
        int imagen1 = fotoModelList.get(position).getImagen1();
        //int imagen2 = fotoModelList.get(position).getImagen2();
        //int imagen3 = fotoModelList.get(position).getImagen3();
        //holder.name.setText(name);
        holder.imagen1.setImageResource(imagen1);
       // holder.imagen2.setImageResource(imagen2);
       // holder.imagen3.setImageResource(imagen3);

        final FotoModel item = fotoModelList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ThirdActivity.class);
                //intent.putExtra("ItemDetail2", item);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fotoModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //private TextView name;
        private ImageView imagen1;
       // private ImageView imagen2;
       // private ImageView imagen3;
        public ViewHolder(View v) {
            super(v);
            //name = (TextView) v.findViewById(R.id.albumName);
            imagen1 = (ImageView) v.findViewById(R.id.imageName1);
         //   imagen2 = (ImageView) v.findViewById(R.id.imageName2);
         //   imagen3 = (ImageView) v.findViewById(R.id.imageName3);
        }
    }

}