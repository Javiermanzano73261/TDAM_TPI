package com.example.integradortdam;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.integradortdam.entities.FotoModel;

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
        final FotoModel item = fotoModelList.get(position);
        loadImage(item.getImageUrl(), holder.imagen1 );



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ThirdActivity.class);
                intent.putExtra("FotoClick", item);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fotoModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagen1;
        public ViewHolder(View v) {
            super(v);
            imagen1 = (ImageView) v.findViewById(R.id.imageName1);
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