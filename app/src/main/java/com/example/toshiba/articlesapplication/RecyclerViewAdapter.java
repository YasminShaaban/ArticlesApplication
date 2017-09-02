package com.example.toshiba.articlesapplication;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by toshiba on 8/12/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder> {

    ArrayList<Data> data;
    String imageurl;
    Context c;
    //Context c;

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row,null);
        return new CustomViewHolder(view);

    }

    public RecyclerViewAdapter(ArrayList<Data> data) {

        this.data = data;
        //imageurl=data.
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
          holder.txt.setText(data.get(position).getTxt1());
          holder.txt2.setText(data.get(position).getTxt2());
          if(!data.get(position).getImage().matches(""))
          Picasso.with(c).load(data.get(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView txt;
        TextView txt2;
        ImageView image;

        public CustomViewHolder(View itemView) {
            super(itemView);
            c=itemView.getContext();
            txt=(TextView)itemView.findViewById(R.id.text1);
            txt2=(TextView)itemView.findViewById(R.id.text2);
            image=(ImageView)itemView.findViewById(R.id.imageView);

        }

    }
}
