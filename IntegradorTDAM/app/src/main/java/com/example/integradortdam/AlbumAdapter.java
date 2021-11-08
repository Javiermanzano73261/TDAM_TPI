package com.example.integradortdam;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        final AlbumModel item = albumModelList.get(position);
        String title = item.getTitle();
        Integer tamanio = item.getCount_photos();
        ArrayList<FotoModel> fotos = item.getPhoto();
        loadImage(fotos.get(0).getImageUrl(), holder.imagen1 );
        loadImage(fotos.get(1).getImageUrl(), holder.imagen2 );
        loadImage(fotos.get(2).getImageUrl(), holder.imagen3 );
        loadImage(fotos.get(3).getImageUrl(), holder.imagen4 );
        holder.name.setText(title);
        holder.tamanio.setText(tamanio.toString() + " fotos");






        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), SecondActivity.class);

                intent.putExtra("AlbumClick", item);
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



}
