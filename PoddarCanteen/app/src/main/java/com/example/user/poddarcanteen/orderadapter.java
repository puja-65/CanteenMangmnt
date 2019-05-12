package com.example.user.poddarcanteen;

import java.math.BigDecimal;
import java.util.ArrayList;
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

public class orderadapter extends ArrayAdapter<cartValue> {

    private Context mcontext;
    private DatabaseReference mdatabaseRef;
    ArrayList food;
    TextView tv;
    public orderadapter(Context context) {
        super(context, R.layout.orderitem);
        mdatabaseRef = FirebaseDatabase.getInstance().getReference("users");

    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final orderadapter.ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.orderitem, parent, false);
            holder = new orderadapter.ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (orderadapter.ViewHolder) convertView.getTag();
        }

        cartValue cart = getItem(position);
        food = cart.getFoodList();
        mdatabaseRef = FirebaseDatabase.getInstance().getReference("users");
        mdatabaseRef.orderByChild("userId").equalTo(cart.getUserID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    user userValue = postSnapshot.getValue(user.class);
                    holder.name.setText(userValue.username);
                    getFoodDetails();
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


        tv = holder.items;



//        holder.quantity.setText(food.getquantity().toString() );
//        holder.totalPrice.setText("RS:"+food.gettotal().toString() );
        return convertView;
    }


    void getFoodDetails(){
        mdatabaseRef = FirebaseDatabase.getInstance().getReference("foodItem");
        if (food.size()> 0) {
            cartfood cFood = (cartfood) food.get(0);
            mdatabaseRef.orderByChild("foodId").equalTo(cFood.getfoodID()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        Food foodValue = postSnapshot.getValue(Food.class);
                        tv.append("*");
                        tv.append(foodValue.getFoodName());
                        tv.append("\n");
                    }
                    food.remove(0);
                    getFoodDetails();
                }

                @Override
                public void onCancelled(DatabaseError firebaseError) {
                    System.out.println("The read failed: " + firebaseError.getMessage());
                }
            });

        }
    }

    static class ViewHolder {
        TextView name;
        TextView items;
        TextView quantity;
        TextView totalPrice;

        ViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.name);
            items = (TextView) view.findViewById(R.id.list_items);
//            quantity = (TextView) view.findViewById(R.id.tvCartItemQuantity);
//            totalPrice = (TextView) view.findViewById(R.id.tvCartItemPrice);
        }
    }

}
