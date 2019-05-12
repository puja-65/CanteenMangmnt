package com.example.user.poddarcanteen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.BreakIterator;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.picasso.Picasso;

public class FoodCardsAdapter extends ArrayAdapter<Food> {
    private Context mcontext;

    public FoodCardsAdapter(Context context) {
        super(context, R.layout.food_card_item);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.food_card_item, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Food food = getItem(position);



//        holder.imageView.setImageResource(food.getFoodName());
        holder.tvTitle.setText(food.getFoodName());
        holder.tvSubtitle.setText(food.getFoodType());
        holder.tvPrice.setText("RS:"+food.getFoodPrice() );
        if (food.serverurlList.size() > 0) {
            Uri url = Uri.parse(food.serverurlList.get(0));

            Glide.with(this.getContext())
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(true)
                    .into(holder.imageView);
        }
        return convertView;
    }

    static class ViewHolder {
        public BreakIterator itemTitle;
        ImageView imageView;
        TextView tvTitle;
        TextView tvSubtitle;
        TextView tvPrice;

        ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.image);
            tvTitle = (TextView) view.findViewById(R.id.text_title);
            tvSubtitle = (TextView) view.findViewById(R.id.text_subtitle);
            tvPrice = (TextView) view.findViewById(R.id.text_price);
        }
    }



}





