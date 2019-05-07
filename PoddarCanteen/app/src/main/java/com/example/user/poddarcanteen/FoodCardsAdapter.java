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

import com.squareup.picasso.Picasso;

public class FoodCardsAdapter extends ArrayAdapter<Food> {

    public FoodCardsAdapter(Context context) {
        super(context, R.layout.food_card_item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

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
            Picasso.get()
                    .load(String.valueOf(url))
                    .placeholder(R.drawable.foodplaceholder)
                    .error(R.drawable.foodplaceholder)
                    .into(holder.imageView);
        }
        return convertView;
    }

    static class ViewHolder {
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

    void downloadfile(String fileurl,ImageView img)
    {
        URL myfileurl =null;
        try
        {
            myfileurl= new URL(fileurl);

        }
        catch (MalformedURLException e)
        {

            e.printStackTrace();
        }

        try
        {
            HttpURLConnection conn= (HttpURLConnection)myfileurl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            int length = conn.getContentLength();
            int[] bitmapData =new int[length];
            byte[] bitmapData2 =new byte[length];
            InputStream is = conn.getInputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();

            Bitmap bmImg = BitmapFactory.decodeStream(is,null,options);

            img.setImageBitmap(bmImg);

            //dialog.dismiss();
        }
        catch(IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
//          Toast.makeText(PhotoRating.this, "Connection Problem. Try Again.", Toast.LENGTH_SHORT).show();
        }


    }

}





