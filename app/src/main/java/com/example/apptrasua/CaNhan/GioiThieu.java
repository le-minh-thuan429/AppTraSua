package com.example.apptrasua.CaNhan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.apptrasua.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GioiThieu extends AppCompatActivity implements OnMapReadyCallback {

    TextView quaylai;
    Button Thoat;

    MapView mapViewId;
    GoogleMap gMap;
    String address;
    MarkerOptions markerOptions;
    TextView diachi;
    Button call;
    String sdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioi_thieu);
        quaylai=findViewById(R.id.quaylai);
        Thoat=findViewById(R.id.Thoat);

        diachi=findViewById(R.id.diachi);

        call=findViewById(R.id.call);


        Animation animFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.lac);
        call.startAnimation(animFade);
        sdt = "0825316338";


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+sdt));
               /* if (ActivityCompat.checkSelfPermission(GioiThieu.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(GioiThieu.this,new String[]{Manifest.permission.CALL_PHONE},1);

                    return;
                }*/
                startActivity(intent);
            }
        });

        mapViewId=findViewById(R.id.MapViewId);
        mapViewId.getMapAsync(this);


        mapViewId.onCreate(savedInstanceState);
        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void checkPermission() {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted, open the camera
                       // getCurrentLocation();
                        // Toast.makeText(DiaChiMaps.this, "Permission GRANTED", Toast.LENGTH_LONG).show();
                        refreshLocation(21.052659534610523,105.83772327957993);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            // navigate user to app settings
                        }
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    public void getCurrentLocation() {
        FusedLocationProviderClient mFusedLocationClient =
                LocationServices.getFusedLocationProviderClient(getBaseContext());
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location == null) {
                            return;
                        }
                        refreshLocation(location.getLatitude(),location.getLongitude());

                    }
                });
    }
    public void refreshLocation(double Latitude,double Longitude)  {
        // if(location!=null){
        Geocoder geocoder= new Geocoder(GioiThieu.this, Locale.getDefault());
        List<Address> addressList= null;
        try {
            addressList = geocoder.getFromLocation(Latitude,Longitude,1);
            address=addressList.get(0).getAddressLine(0);



            LatLng currentLocation =
                    new LatLng(Latitude, Longitude);
            //   gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 18));
            markerOptions=new MarkerOptions();
            markerOptions.position(currentLocation);
            markerOptions.title(address).visible(true);
            gMap.addMarker(markerOptions);
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 18));
            gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            gMap.animateCamera(CameraUpdateFactory.newLatLng(currentLocation));

            diachi.setText("Địa chỉ:"+address);

            //  Log.d("Location",address);
            //  Toast.makeText(getBaseContext(), address, Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // }

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap=googleMap;
        gMap.setMinZoomPreference(12);
        checkPermission();
    }
    @Override
    protected void onStart() {
        super.onStart();
        mapViewId.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mapViewId.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapViewId.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapViewId.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapViewId.onLowMemory();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapViewId.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapViewId.onDestroy();
    }

}