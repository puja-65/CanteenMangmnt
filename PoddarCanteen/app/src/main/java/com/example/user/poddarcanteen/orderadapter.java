package com.example.user.poddarcanteen;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;



import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Handler;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.picasso.Picasso;

public class orderadapter extends ArrayAdapter<cartValue> {

    public interface OnDataChangeOrderadapterListener{
        public void onDataChanged(int position, cartValue cart , int action);
        public void callNext();

    }

    OnDataChangeOrderadapterListener mOnDataChangeListener;
    public void setOnDataChangeListener(OnDataChangeOrderadapterListener onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }


    private Context mcontext;
    private DatabaseReference mdatabaseRef;
    ArrayList food;
    TextView tv;
    cartValue cart;
    public orderadapter(Context context) {
        super(context, R.layout.orderitem);
        mdatabaseRef = FirebaseDatabase.getInstance().getReference("users");

    }
    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final orderadapter.ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.orderitem, parent, false);
            holder = new orderadapter.ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (orderadapter.ViewHolder) convertView.getTag();
        }


        cart = getItem(position);
        cartValue carttemp = getItem(position);
        food = (ArrayList) carttemp.getFoodList();
        tv = holder.items;
        mdatabaseRef = FirebaseDatabase.getInstance().getReference("users");
        mdatabaseRef.orderByChild("userId").equalTo(carttemp.getUserID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    user userValue = postSnapshot.getValue(user.class);
                    holder.name.setText(userValue.username);
                }
            }


            @Override
            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        for(int l=0; l<=food.size() - 1; l++){

            cartfood cFood = (cartfood) food.get(0);
            tv.append("*");
            tv.append(cFood.getFoodName());
            tv.append("  {");
            tv.append(cFood.getquantity());
            tv.append("}");
            tv.append("\n");
        }


        holder.accept.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mOnDataChangeListener != null){
                    mOnDataChangeListener.onDataChanged(position,cart,1);
                }
            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mOnDataChangeListener != null){
                    mOnDataChangeListener.onDataChanged(position,cart,2);
                }
            }
        });



//        holder.quantity.setText(food.getquantity().toString() );
//        holder.totalPrice.setText("RS:"+food.gettotal().toString() );
        return convertView;
    }

    void getUserDetails(){


    }


    void getFoodDetails(){

    }






    static class ViewHolder {
        TextView name;
        TextView items;
        TextView quantity;
        TextView totalPrice;
        Button accept;
        Button reject;

        ViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.name);
            items = (TextView) view.findViewById(R.id.list_items);
            reject = (Button) view.findViewById(R.id.reject);
            accept = (Button) view.findViewById(R.id.accept);

//            quantity = (TextView) view.findViewById(R.id.tvCartItemQuantity);
//            totalPrice = (TextView) view.findViewById(R.id.tvCartItemPrice);
        }
    }



}
