package com.hsdroid.zomatoapi;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class CustomAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    public static int clientIdNu;

    // DBAdapter obj;
    ArrayList<String> shopName;
    ArrayList<String> Address;
    ArrayList<String> mobile;
    ArrayList<String> img;
    Context context;
    CustomAdapter adapter;

    public CustomAdapter(Context incomeFragment, ArrayList<String> shopName, ArrayList<String> Address, ArrayList<String> mobile, ArrayList<String> img) {
        context = incomeFragment;
        this.shopName = shopName;
        this.Address = Address;
        this.mobile = mobile;
        this.img = img;
        inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        this.adapter = this;
    }

    @Override
    public int getCount() {
        return shopName.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.listdata, null);
        Holder holder = new Holder();
        holder.txtName = convertView.findViewById(R.id.txtRestName);
        holder.txtAddress = convertView.findViewById(R.id.txtRestAddress);
        holder.txtMobile = convertView.findViewById(R.id.txtRestMobile);
        holder.imgProfile = convertView.findViewById(R.id.profile_image);
        holder.txtName.setText(shopName.get(position));
        holder.txtAddress.setText(Address.get(position));
        holder.txtMobile.setText(mobile.get(position));
        if (img.get(position) != "") {
//            Glide.with(context).load(img.get(position)).into(holder.imgProfile);
//            System.out.println(img.get(position));

            Glide.with(context).load(img.get(position))
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .error(R.drawable.ic__restaurant)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    }).into(holder.imgProfile);



        } else {
            Glide.with(context).load("https://www.freeiconspng.com/thumbs/restaurant-icon-png/restaurant-icon-png-plate-1.png").into(holder.imgProfile);
        }
        return convertView;
    }

    class Holder {
        TextView txtName, txtAddress, txtMobile;
        ImageView imgProfile;
    }
}
