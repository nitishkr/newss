package com.journaldev.mvpdagger2retroiftrxjava;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.journaldev.mvpdagger2retroiftrxjava.pojo.Article;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Article> data;
    private RecyclerViewAdapter.ClickListener clickListener;

    @Inject
    public RecyclerViewAdapter(ClickListener clickListener) {
        this.clickListener = clickListener;
        data = new ArrayList<>();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        /*holder.nTitle.setText(data.get(position).symbol);
        holder.nSource.setText(data.get(position).priceUsd);
        holder.txt1HourChange.setText(data.get(position).percentChange1h + "%");
        holder.txt24HourChange.setText(data.get(position).percentChange24h + "%");
        holder.txt7DayChange.setText(data.get(position).percentChange7d + "%");*/
        String title = data.get(position).title;
        int pos = title.lastIndexOf("-");
        String source = title.substring(pos+1);
        source.trim();
        title = title.substring(0,pos-1);
        title.trim();
        holder.nTitle.setText(title);

        holder.nSource.setText(source);
        String dtm = data.get(position).publishedAt;
        String date = dtm.substring(0,dtm.indexOf("T"));
        date =date.trim();
        String time = dtm.substring(dtm.indexOf("T")+1,dtm.indexOf("Z"));
        time = time.trim();
        SimpleDateFormat format  = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

        try {
            Date d1 = format.parse(date+" "+time);
            Log.e("Publish",dtm+" "+date+" "+time);
            Log.e("Date",d1.toString());
            long d2  = System.currentTimeMillis();
            Double diff =d2-d1.getTime()-(5*60+30)*60*1000.0-120*24*3600000.0;
            Log.e("dddd",String.valueOf(d1.getTime()) +" "+String.valueOf(d2)+" "+String.valueOf(diff));
            if (diff/3600000.0 >24 ) {
                String days = String.valueOf((int)(diff/(3600000 * 24.0)));
                holder.tm.setText(days+ " days ago");
            }
            else if (diff/60000.0> 60)
            {
                String hours = String.valueOf((int)(diff / (3600000.0)));
                holder.tm.setText(hours+" hours ago");
            }
            else
            {
                String minutes = String.valueOf((int)(diff/60000.0));
                holder.tm.setText(minutes+ " minutes ago");
            }

        } catch (ParseException e) {
            e.printStackTrace();

        }
        try {
           // holder.nSource.setText("####"+"!!@!@?>");
           Log.e("SOURCE",data.get(position).title);
           Log.e("SOURCE",data.get(position).source.getString("def"));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Error",e.getMessage());
        }
        Picasso.get().load(data.get(position).urlToImage).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nTitle;
        private TextView nSource;
        private ImageView img;
        private RelativeLayout constraintLayoutContainer;
        private TextView tm;
        ViewHolder(View itemView) {
            super(itemView);

            nTitle = itemView.findViewById(R.id.NewsTitle);
            nSource = itemView.findViewById(R.id.MediaSource);
            img = itemView.findViewById(R.id.imageContent);
            tm = itemView.findViewById(R.id.UpdatedTime);
            constraintLayoutContainer = itemView.findViewById(R.id.constraintLayout);

            constraintLayoutContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.launchIntent(data.get(getAdapterPosition()).url);
                }
            });
        }
    }

    public interface ClickListener {
        void launchIntent(String name);
    }

    public void setData(List<Article> data) {

        for(int i=data.size()-1;i>9;i--)
            data.remove(i);
        this.data.addAll(data);
        Log.e("SIZE",String.valueOf(data.size()));
        notifyDataSetChanged();
    }
}

