package com.example.integradortdam;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.integradortdam.entities.AlbumModel;
import com.example.integradortdam.entities.FotoModel;

import java.util.ArrayList;
import java.util.List;


public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {


    private List<AlbumModel> albumModelList;
    private AppRepository mRepository;
    private boolean fotosCargadas = false;

    public AlbumAdapter(List<AlbumModel> albumModelList, AppRepository repository) {
        this.albumModelList = albumModelList;
        this.mRepository = repository;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final AlbumModel item = albumModelList.get(position);

        if(!fotosCargadas){
            String title = item.getTitle();
            Integer tamanio = item.getCount_photos();
            ArrayList<FotoModel> fotos = new ArrayList<>();

            try { fotos = (ArrayList<FotoModel>) mRepository.getFotosDeAlbumDB(item.getId());
                if(fotos != null){setVistaPrevia(holder, fotos);}
                holder.name.setText(title);
                holder.tamanio.setText(tamanio.toString() + " fotos");}
            catch (Exception e) { e.printStackTrace();}

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), SecondActivity.class);
                intent.putExtra("AlbumClick", item);
                holder.itemView.getContext().startActivity(intent);

            }
        });
    }

    private void setVistaPrevia(ViewHolder holder, ArrayList<FotoModel> fotos){
        //Ocultamos todas las imageView y luego habilitamos las necesarias
        holder.imagen1.setVisibility(View.GONE);
        holder.imagen2.setVisibility(View.GONE);
        holder.imagen3.setVisibility(View.GONE);
        holder.imagen4.setVisibility(View.GONE);

        //Peso por defecto 1
        holder.imagen1.setLayoutParams(changeWeigth(holder.imagen1, 1.0f));
        holder.imagen2.setLayoutParams(changeWeigth(holder.imagen2, 1.0f));
        holder.imagen3.setLayoutParams(changeWeigth(holder.imagen3, 1.0f));
        holder.imagen4.setLayoutParams(changeWeigth(holder.imagen4, 1.0f));

        //Vista previa de imágenes de cada album
        if(fotos.size()>0) {
            loadImage(fotos.get(0).getImageUrl(), holder.imagen1);
            holder.imagen1.setVisibility(View.VISIBLE);
            //Adaptación de los pesos
            if (fotos.size() == 1) {
                holder.imagen1.setLayoutParams(changeWeigth(holder.imagen1, 0.0f));
            }
        }
        if(fotos.size()>1){
            loadImage(fotos.get(1).getImageUrl(), holder.imagen2 );
            holder.imagen2.setVisibility(View.VISIBLE);
            //Adaptación de los pesos
            if(fotos.size()==2){
                holder.imagen1.setLayoutParams(changeWeigth(holder.imagen1, 0.5f));
                holder.imagen2.setLayoutParams(changeWeigth(holder.imagen2, 0.5f));
            }
        }
        if(fotos.size()>2){
            loadImage(fotos.get(2).getImageUrl(), holder.imagen3 );
            holder.imagen3.setVisibility(View.VISIBLE);}
        if(fotos.size()>3){
            loadImage(fotos.get(3).getImageUrl(), holder.imagen4 );
            holder.imagen4.setVisibility(View.VISIBLE); }
    }

    private LinearLayout.LayoutParams changeWeigth(ImageView image, float weigth){
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) image.getLayoutParams();
        params.weight = weigth;
        return params;
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

    private void loadImage(String url, ImageView image) {
        ImageLoader imageLoader = MyApplication.getImageLoader();

        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //text.setText("Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                image.setImageBitmap(response.getBitmap());
            }
        });
;
    }

    public void setAlbumModelList(List<AlbumModel> albumModelList) {
        this.albumModelList = albumModelList;
    }



}
