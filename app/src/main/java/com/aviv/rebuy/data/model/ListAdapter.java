package com.aviv.rebuy.data.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aviv.rebuy.R;
import com.aviv.rebuy.dataForProduct;

public class ListAdapter extends RecyclerView.Adapter {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_list_items,parent,false);
        return  new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder ).bindView(position);
    }

    @Override
    public int getItemCount() {
        return dataForProduct.title.length;
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mItemText;
        private ImageView mItemImage;

        public ListViewHolder(View itemView){
            super(itemView);
            mItemText = (TextView) itemView.findViewById(R.id.textView4);
            mItemImage = (ImageView) itemView.findViewById(R.id.itemImage);
            itemView.setOnClickListener(this);

        }

        public void bindView(int position){
            mItemText.setText(dataForProduct.title[position]);
            mItemImage.setImageResource(dataForProduct.picturePath[position]);
        }

        public void onClick(View view){

        }
    }
}
