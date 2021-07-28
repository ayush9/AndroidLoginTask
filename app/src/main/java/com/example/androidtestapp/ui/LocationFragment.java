package com.example.androidtestapp.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.androidtestapp.R;
import com.example.androidtestapp.viewmodel.HomeViewModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationFragment extends Fragment {

    private SupportMapFragment mSupportMapFragment;
    private GoogleMap gMap;
    private HomeViewModel homeViewModel;
    private String key;
    private String token;
    private double latitute1, latitute2;
    private double longitute1, longitute2;

    public LocationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        key = requireArguments().getString("key");
        token = requireArguments().getString("token");
        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.myMap);
        if (mSupportMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mSupportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.myMap, mSupportMapFragment).commit();
        }

        if (mSupportMapFragment != null) {
            mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    if (googleMap != null) {
                        gMap = googleMap;
                        googleMap.getUiSettings().setAllGesturesEnabled(true);
                    }

                }
            });
        }
        return inflater.inflate(R.layout.map_view_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isAdded()) {
            homeViewModel = new HomeViewModel(requireActivity().getApplication());
            homeViewModel.fetchUserLocationDetails(token);

            homeViewModel.getLocationResponse().observe(getViewLifecycleOwner(), locationDataResponses -> {
                if (locationDataResponses != null) {
                    latitute1 = locationDataResponses.get(0).getLat();
                    longitute1 = locationDataResponses.get(0).getLng();
                    latitute2 = locationDataResponses.get(1).getLat();
                    longitute2 = locationDataResponses.get(1).getLng();
                    LatLng latLng1 = new LatLng(latitute1, longitute1);
                    LatLng latLng2 = new LatLng(latitute2, longitute2);

                    CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng1).zoom(10.0f).build();
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                    gMap.moveCamera(cameraUpdate);
                    gMap.addMarker(new MarkerOptions()
                            .position(latLng1)
                            .title("First Pit Stop")
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                    Bitmap bitmap = getBitmapFromVectorDrawable(getContext(),R.drawable.ic_baseline_map_24);
                    BitmapDescriptor descriptor =BitmapDescriptorFactory.fromBitmap(bitmap);

                    gMap.addMarker(new MarkerOptions()
                            .position(latLng2)
                            .title("Wrong Turn!")
                            .icon(descriptor));
                } else {
                    Toast.makeText(requireContext(), "LOCATION NOT AVAILABLE", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(requireContext(), "SOMETHING WENT WRONG", Toast.LENGTH_LONG).show();
        }
    }

    private Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable =  AppCompatResources.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

}
