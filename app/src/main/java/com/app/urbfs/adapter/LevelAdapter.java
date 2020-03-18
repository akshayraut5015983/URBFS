package com.app.urbfs.adapter;

/**
 * Created by Diu on 10/20/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.urbfs.R;
import com.app.urbfs.activity.LevelDetailsActivitys;
import com.app.urbfs.model.LevelModel;

import java.util.List;


public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.ViewHolder> {

    private Context context;
    private List<LevelModel> superHeroes;

    public LevelAdapter(List<LevelModel> superHeroes, Context context) {
        super();

        this.superHeroes = superHeroes;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_level, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return new ViewHolder(v);

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final LevelModel m = superHeroes.get(position);

        holder.tvLavel.setText("Go to level-  " + String.valueOf(m.getLevel()));
        holder.tvActive.setText("Active-  " + String.valueOf(m.getActive()));
        holder.tvInactive.setText("Inactive-  " + String.valueOf(m.getInactive()));
        holder.tvCount.setText("NO of ID's-  " + String.valueOf(m.getCount()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, LevelDetailsActivitys.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("ID", String.valueOf(m.getLevel()));
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }

    @Override
    public int getItemCount() {
        return superHeroes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvLavel, tvCount, tvInactive, tvActive;

        public ViewHolder(View itemView) {
            super(itemView);

            tvLavel = itemView.findViewById(R.id.tvLavel);
            tvCount = itemView.findViewById(R.id.tvCount);
            tvInactive = itemView.findViewById(R.id.tvInactive);
            tvActive = itemView.findViewById(R.id.tvActive);

        }
    }


}
