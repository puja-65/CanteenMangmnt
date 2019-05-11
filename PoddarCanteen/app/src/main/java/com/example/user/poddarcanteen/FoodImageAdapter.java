package com.example.user.poddarcanteen;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodImageAdapter extends RecyclerView.Adapter<FoodImageAdapter.ImageViewHolder> {
    private Context mcontext;
    private List<Food> mfood;

    public FoodImageAdapter(Context context, List<Food> foods) {
        mcontext = context;
        mfood = foods;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.food_item, viewGroup, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder imageViewHolder, int i) {
        Food foodCur = mfood.get(i);
        imageViewHolder._nameFood.setText(foodCur.getFoodName());
        imageViewHolder._priceFood.setText(foodCur.getFoodPrice());
        imageViewHolder._typeFood.setText(foodCur.getFoodType());
        if (foodCur.serverurlList.size() > 0) {
            Uri url = Uri.parse(foodCur.serverurlList.get(0));

            Glide.with(this.mcontext)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(true)
                    .into(imageViewHolder._imageFood);
        }

//        ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
//
//        imageLoader.loadImage(foodCur.serverurlList.get(0), new SimpleImageLoadingListener() {
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                imageViewHolder._imageFood.setImageBitmap(loadedImage);
//                // Do whatever you want with Bitmap
//            }
//        });

//        Picasso.with(mcontext)
//                .load(foodCur.serverurlList.get(0))
//                .placeholder(R.drawable.ic_image_black_24dp)
//                .fit()
//                .centerCrop()
//                .into(imageViewHolder._imageFood);
    }


    @Override
    public int getItemCount() {
        return mfood.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView _nameFood;
        public TextView _priceFood;
        public TextView _typeFood;
        public ImageView _imageFood;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            _nameFood = itemView.findViewById(R.id.name_food);
            _priceFood = itemView.findViewById(R.id.price_food);
            _typeFood = itemView.findViewById(R.id.type_food);
            _imageFood = itemView.findViewById(R.id.image_food);
        }
    }
}
