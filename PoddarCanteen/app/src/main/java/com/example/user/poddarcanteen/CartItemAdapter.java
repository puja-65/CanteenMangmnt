package com.example.user.poddarcanteen;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;



import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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

public class CartItemAdapter extends ArrayAdapter<cartfood> {

    private Context mcontext;
    private DatabaseReference mdatabaseRef;

    public CartItemAdapter(Context context) {
        super(context, R.layout.adapter_cart_item);
        mdatabaseRef = FirebaseDatabase.getInstance().getReference("foodItem");

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CartItemAdapter.ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.adapter_cart_item, parent, false);
            holder = new CartItemAdapter.ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (CartItemAdapter.ViewHolder) convertView.getTag();
        }

        cartfood food = getItem(position);

        mdatabaseRef.orderByChild("foodId").equalTo(food.getfoodID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Food food = postSnapshot.getValue(Food.class);
                    holder.itemTitle.setText(food.getFoodName());
                    holder.cartItemUnitPrice.setText("RS:"+food.getFoodPrice().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


        holder.quantity.setText(food.getquantity().toString() );
        holder.totalPrice.setText("RS:"+food.gettotal().toString() );
        return convertView;
    }

    static class ViewHolder {
        TextView itemTitle;
        TextView cartItemUnitPrice;
        TextView quantity;
        TextView totalPrice;

        ViewHolder(View view) {
            itemTitle = (TextView) view.findViewById(R.id.tvCartItemName);
            cartItemUnitPrice = (TextView) view.findViewById(R.id.tvCartItemUnitPrice);
            quantity = (TextView) view.findViewById(R.id.tvCartItemQuantity);
            totalPrice = (TextView) view.findViewById(R.id.tvCartItemPrice);
        }
    }



}


