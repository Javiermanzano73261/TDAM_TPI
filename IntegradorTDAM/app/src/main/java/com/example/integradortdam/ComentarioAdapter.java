package com.example.integradortdam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.integradortdam.entities.ComentarioModel;

import java.util.List;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ViewHolder>{


    private List<ComentarioModel> comentarioModelList;

    public ComentarioAdapter(List<ComentarioModel> comentarioModelList) { this.comentarioModelList = comentarioModelList; }

    @Override
    public ComentarioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comentario_row, parent, false);
        ComentarioAdapter.ViewHolder viewHolder = new ComentarioAdapter.ViewHolder(v);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ComentarioAdapter.ViewHolder holder, int position) {
        final ComentarioModel item = comentarioModelList.get(position);
        holder.user.setText(item.getRealname());
        holder.coment.setText(item.get_content());
    }

    @Override
    public int getItemCount() {
        return comentarioModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView user;
        private TextView coment;
        public ViewHolder(View v) {
            super(v);
            user = (TextView) v.findViewById(R.id.txtUser);
            coment = (TextView) v.findViewById(R.id.txtComentario);
        }
    }

    public void setComentarioModelList(List<ComentarioModel> comentarioModelList) {
        this.comentarioModelList = comentarioModelList;
    }




}
