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
import com.app.urbfs.model.HistoryModel;

import java.util.List;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context context;
    private List<HistoryModel> superHeroes;

    public HistoryAdapter(List<HistoryModel> superHeroes, Context context) {
        super();

        this.superHeroes = superHeroes;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final HistoryModel m = superHeroes.get(position);

        holder.tvName.setText(m.getPayname());
        holder.tvDate.setText(m.getPayoutdate());
        holder.tvAmount.setText(m.getAmount());
        holder.tvTds.setText(m.getTds());
        holder.tvAdmin.setText(m.getAdmin());
        holder.tvNetPay.setText(m.getNetpay());

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
        public TextView tvName, tvDate, tvAmount, tvTds, tvAdmin, tvNetPay, tvPackage, tvPv;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvTds = itemView.findViewById(R.id.tvTds);
            tvAdmin = itemView.findViewById(R.id.tvAdmin);
            tvNetPay = itemView.findViewById(R.id.tvNetPay);


        }
    }


}
