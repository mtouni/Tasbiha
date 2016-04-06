package com.mal.amr.tasbiha.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mal.amr.tasbiha.R;
import com.mal.amr.tasbiha.utilties.CustomFontLoader;

/**
 * Created by Amr on 4/6/2016.
 */
public class BaadAlsalahAdapter extends RecyclerView.Adapter<BaadAlsalahAdapter.ViewHolder> {

    Context context;
    String[] azkar_list;
    int[] num_list;

    public BaadAlsalahAdapter(Context context, String[] azkar_list, int[] num_list) {
        this.context = context;
        this.azkar_list = azkar_list;
        this.num_list = num_list;
    }

    @Override
    public BaadAlsalahAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.baad_alsalah_item, parent, false), context);
    }

    @Override
    public void onBindViewHolder(BaadAlsalahAdapter.ViewHolder holder, final int position) {
        holder.zekr.setText(azkar_list[position]);
        holder.num.setText(String.valueOf(num_list[position]));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView zekr, num;
        Context context;
        View itemView;
        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            this.itemView = itemView;

            zekr = (TextView) itemView.findViewById(R.id.zekr);
            zekr.setTypeface(CustomFontLoader.getCustomFont(context));
            num = (TextView) itemView.findViewById(R.id.num);
        }
    }
}
