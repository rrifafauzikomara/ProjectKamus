package com.example.rifafauzi6.projectkamus.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rifafauzi6.projectkamus.DetailActivity;
import com.example.rifafauzi6.projectkamus.CustomOnItemClickListener;
import com.example.rifafauzi6.projectkamus.model.KamusIndModel;

import java.util.ArrayList;

import static com.example.rifafauzi6.projectkamus.DetailActivity.EXTRA_DETAIL;
import static com.example.rifafauzi6.projectkamus.DetailActivity.EXTRA_WORD;

public class IndonesiaAdapter extends RecyclerView.Adapter<IndonesiaAdapter.IndonesiaHolder> {
    private ArrayList<KamusIndModel> kamusIndModels = new ArrayList<>();
    private Activity activity;

    public IndonesiaAdapter(Activity activity) {
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
    }

    @Override
    public IndonesiaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new IndonesiaHolder(view);
    }

    public void addItem(ArrayList<KamusIndModel> kamusIndModels){
        this.kamusIndModels = kamusIndModels;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(IndonesiaHolder holder, int position) {
        holder.tvWord.setText(kamusIndModels.get(position).getWord());
        holder.tvWord.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, DetailActivity.class);
                intent.putExtra(EXTRA_WORD, kamusIndModels.get(position).getWord());
                intent.putExtra(EXTRA_DETAIL, kamusIndModels.get(position).getDescription());
                activity.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return kamusIndModels.size();
    }

    class IndonesiaHolder extends RecyclerView.ViewHolder {
        private TextView tvWord;
        IndonesiaHolder(View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(android.R.id.text1);
        }
    }
}
