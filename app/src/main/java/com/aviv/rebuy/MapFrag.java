package com.aviv.rebuy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aviv.rebuy.Model.Model;
import com.aviv.rebuy.Model.ModelFirebase;
import com.aviv.rebuy.Model.Product;
import com.aviv.rebuy.Model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MapFrag extends Fragment implements OnMapReadyCallback {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MapFrag() {}
    public static MapFrag newInstance(String param1, String param2) {
        MapFrag fragment = new MapFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    public void addUsersMarkers(List<User> users, GoogleMap googleMap) {
        for (User user : users) {
            LatLng location = new LatLng(user.getLatitude(), user.getLongitude());
            String userName=user.getName();
            googleMap.addMarker(new MarkerOptions().position(location).title(userName).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.logo)));
        }
    }
    public void addProductsMarkers(List<Product> products, GoogleMap googleMap) {
        for (Product product : products) {
            Log.d("*******************Product**************** : ",product.getName().toString()+" - "+product.getOwnerId().toString());
            Model.instance.modelFirebase.getUser(product.getOwnerId(),new Model.GetUserListener() {
                @Override
                public void onComplete(User user) {
                    if(user!=null) {
                        Log.d("*******************USER**************** : ",user.getId().toString());
                        LatLng location = new LatLng(user.getLatitude(), user.getLongitude());
                        Log.d("*******************location**************** : ",location.toString());
                        String userName = user.getName();
                        MarkerOptions mo = new MarkerOptions().position(location).title(product.getName());
                        final Bitmap[] image = {null};
                        if (product.getImageUrl() != null) Picasso.get().load(product.getImageUrl()).into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                googleMap.addMarker(new MarkerOptions().position(location).title(product.getName()).icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                googleMap.addMarker(new MarkerOptions().position(location).title(product.getName()));
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        });
                        else{
                            googleMap.addMarker(new MarkerOptions().position(location).title(product.getName()));
                        }
                    }
                }
            });

        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
//        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_rebuyapple);
//        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(10, 5, vectorDrawable.getIntrinsicWidth() + 1, vectorDrawable.getIntrinsicHeight() + 1);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
//        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null) {
            Model.instance.modelFirebase.getAllUsers(list -> addUsersMarkers(list, googleMap));
//            Model.instance.modelFirebase.getAllProducts(list -> addProductsMarkers(list, googleMap));
            LatLng colman = new LatLng(31.969942746673553, 34.77286230673707);
            googleMap.addMarker(new MarkerOptions().position(colman).title("COLMAN"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(colman, 13));
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            googleMap.setTrafficEnabled(true);
            googleMap.setBuildingsEnabled(true);
            googleMap.setIndoorEnabled(true);
        }
    }
}