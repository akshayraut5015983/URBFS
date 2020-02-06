package com.app.urbfs.adapter;

/**
 * Created by Diu on 10/20/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.urbfs.R;
import com.app.urbfs.model.TotalTeamModel;

import java.util.List;


public class TotalTeamAdapter extends RecyclerView.Adapter<TotalTeamAdapter.ViewHolder> {

    private Context context;
    private List<TotalTeamModel> superHeroes;

    public TotalTeamAdapter(List<TotalTeamModel> superHeroes, Context context) {
        super();

        this.superHeroes = superHeroes;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_total_team, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final TotalTeamModel m = superHeroes.get(position);

        holder.tvLogId.setText(m.getNewid());
        holder.tvName.setText(m.getNewname());
        holder.tvSpon.setText(m.getPan());
        holder.tvSName.setText(m.getAadhar());
        holder.tvRData.setText(m.getDateodissue());
        holder.tvCity.setText(m.getSpillby());
        holder.tvPackage.setText(m.getSname());
        holder.tvPv.setText(m.getPinval());
        holder.tvPosition.setText(m.getPosition());
        holder.tvMobile.setText(m.getMobile());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Toast.makeText(context, "tt", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, LevelDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return superHeroes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvLogId, tvName, tvSpon, tvSName, tvRData, tvCity, tvPackage, tvPv,tvPosition,tvMobile;

        public ViewHolder(View itemView) {
            super(itemView);

            tvLogId = itemView.findViewById(R.id.tvLogId);
            tvName = itemView.findViewById(R.id.tvName);
            tvSpon = itemView.findViewById(R.id.tvSpon);
            tvSName = itemView.findViewById(R.id.tvSName);
            tvRData = itemView.findViewById(R.id.tvRData);
            tvCity = itemView.findViewById(R.id.tvCity);
            tvPackage = itemView.findViewById(R.id.tvPackage);
            tvPv = itemView.findViewById(R.id.tvPv);
            tvPosition = itemView.findViewById(R.id.tvPosition);
            tvMobile = itemView.findViewById(R.id.tvMobile);

        }
    }


}
