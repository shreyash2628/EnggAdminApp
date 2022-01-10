package com.nutan.enggadminapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class clgImagesAdapter extends RecyclerView.Adapter<clgImagesAdapter.ProductSuggestionVH> {

    private Context context;
    private String TAG = "clgImagesAdapter";
    private ArrayList<Uri> productImagesList;

    public clgImagesAdapter(ArrayList<Uri> productImagesList) {
        this.productImagesList = productImagesList;
    }

    @NonNull
    @Override
    public ProductSuggestionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_image_item,parent,false);
        context = parent.getContext();
        return new ProductSuggestionVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductSuggestionVH holder, int position) {


        holder.image.setImageURI(productImagesList.get(position));
        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productImagesList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (productImagesList == null) ? 0 : productImagesList.size();
    }


    public static class ProductSuggestionVH extends RecyclerView.ViewHolder{

        AppCompatImageView image;
        AppCompatImageView removeBtn;
        View mItem;

        public ProductSuggestionVH(@NonNull View itemView) {
            super(itemView);

            mItem = itemView;
            image = mItem.findViewById(R.id.productImageItem_id);
            removeBtn = mItem.findViewById(R.id.productImageRemoveBtn_id);

        }
    }
}