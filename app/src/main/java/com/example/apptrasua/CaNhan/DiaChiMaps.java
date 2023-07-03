package com.example.apptrasua.CaNhan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptrasua.GioHang.ThongTinThanhToan;
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

public class DiaChiMaps extends AppCompatActivity implements OnMapReadyCallback {

    MapView mapView;

    GoogleMap gMap;
    MarkerOptions markerOptions;
    SearchView Search;
    Button XacNhan;
    TextView quaylai;
    String address;
    String KhoangCach;
    float distance=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_chi_maps);
        mapView = findViewById(R.id.MapViewId);

        Search = findViewById(R.id.Search);
        XacNhan = findViewById(R.id.XacNhan);

        // navigate user to app settings
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);
        quaylai = findViewById(R.id.quaylai);

   //     String diachi=getIntent().getStringExtra("DiaChi");

        XacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String khoangcach=distance+"";

                if(address!=null || !address.equals("")){
                    Intent intent = new Intent();
                    intent.putExtra("DiaChi",address);
                    intent.putExtra("KhoangCach",khoangcach);
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                  //  Toast.makeText(DiaChiMaps.this, diachi, Toast.LENGTH_LONG).show();
                }else if(address.equals("")||address==null) {
                    Toast.makeText(DiaChiMaps.this, "Mời bạn nhập lại địa chỉ", Toast.LENGTH_LONG).show();
                }

                //  Toast.makeText(DiaChiMaps.this, diachi, Toast.LENGTH_LONG).show();

            }
        });

        Search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                OnMapReady(query);
               // Toast.makeText(DiaChiMaps.this, "Permission GRANTED", Toast.LENGTH_LONG).show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
              //  OnMapReady(newText);
                return false;
            }
        });//
        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void OnMapReady(String timkiem) {

        List<Address> addresses=null;
        if(timkiem!=null||!timkiem.equals("")){
            Geocoder geocoder= new Geocoder(DiaChiMaps.this);

            try {
                addresses=geocoder.getFromLocationName(timkiem,1);
                Address address=addresses.get(0);

                refreshLocation(address.getLatitude(),address.getLongitude());
                //   gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 18));
               /* LatLng sydney = new LatLng(address.getLatitude(), address.getLongitude());
                markerOptions=new MarkerOptions();
                markerOptions.position(sydney);
                markerOptions.title(timkiem).visible(true);;
                gMap.addMarker(markerOptions);
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 18));
                gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                gMap.animateCamera(CameraUpdateFactory.newLatLng(sydney));*/
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public void checkPermission() {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted, open the camera
                        getCurrentLocation();
                       // Toast.makeText(DiaChiMaps.this, "Permission GRANTED", Toast.LENGTH_LONG).show();
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
    public void KhoangCach(double Latitude,double Longitude){

        Location crntLocation=new Location("crntlocation");
        crntLocation.setLatitude( 21.052659534610523);
        crntLocation.setLongitude(105.83772327957993);


        Location newLocation=new Location("newlocation");
        newLocation.setLatitude(Latitude);
        newLocation.setLongitude(Longitude);
        distance =crntLocation.distanceTo(newLocation)/1000;
     //   Toast.makeText(DiaChiMaps.this, "Khoảng cách là"+distance, Toast.LENGTH_LONG).show();
    }
    public void refreshLocation(double Latitude,double Longitude)  {
       // if(location!=null){
            Geocoder geocoder= new Geocoder(DiaChiMaps.this, Locale.getDefault());
            List<Address> addressList= null;
            try {
                addressList = geocoder.getFromLocation(Latitude,Longitude,1);
                address=addressList.get(0).getAddressLine(0);

                KhoangCach(Latitude,Longitude);

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
        mapView.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

}