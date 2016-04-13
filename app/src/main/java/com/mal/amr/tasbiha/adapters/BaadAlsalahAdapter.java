package com.mal.amr.tasbiha.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mal.amr.tasbiha.CounterActivity;
import com.mal.amr.tasbiha.R;
import com.mal.amr.tasbiha.db.Contract;
import com.mal.amr.tasbiha.utilties.CustomFontLoader;

/**
 * Created by Amr on 4/6/2016.
 */
public class BaadAlsalahAdapter extends RecyclerView.Adapter<BaadAlsalahAdapter.ViewHolder> {

    Context context;
    String[] azkar_list;
    public static int[] num_list;
    public static String[] azkarDB = {Contract.SOBHAN_ALLAH, Contract.ALHAMDULELLAH, Contract.ALLAH_AKBAR};

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
    public void onBindViewHolder(final BaadAlsalahAdapter.ViewHolder holder, final int position) {
        holder.zekr.setText(azkar_list[position]);
        holder.num.setText(String.valueOf(num_list[position]));

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, CounterActivity.class);
//                intent.putExtra("zekr", holder.getAdapterPosition());
//                intent.putExtra("num", num_list[holder.getAdapterPosition()]);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (num_list[getAdapterPosition()] == 33) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                builder.setMessage(R.string.hadith)
                        .setPositiveButton("تم", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            } else {
                int restSum = 0;
                for (int i = 0; i < num_list.length; i++) {
                    if (i != getAdapterPosition()) {
                        restSum += num_list[i];
                    }
                }


                Intent intent = new Intent(context, CounterActivity.class);
                intent.putExtra("zekr", getAdapterPosition());
                intent.putExtra("num", num_list[getAdapterPosition()]);
                intent.putExtra("sum", restSum);
                intent.putExtra("nameInDB", azkarDB[getAdapterPosition()]);
                context.startActivity(intent);
            }
        }
    }
}
