package com.trifrnd.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  implements LocationListener {
    private static final int LOCATION_PERMISSION_REQUEST = 1;
    private EditText usernameEditText;
    private EditText passwordEditText;
    Context context;
    TextView country;
    LocationManager locationManager;


    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public boolean isLocationEnabled() {
        int locationMode;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    private void enableLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location services not enabled");
            builder.setMessage("Please enable location services");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        finish();
                    }
                }
            }).setNegativeButton("No Thanks", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // dialogInterface.cancel();
                    String toastMessage = "You need to enable location to proceed to login";
                    Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        enableLocation();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grantPermission();
        checkLocationIsEnabledOrNot();
        getLocation();
        usernameEditText = findViewById(R.id.activity_main_usernameEditText);
        passwordEditText = findViewById(R.id.activity_main_passwordEditText);
        Button loginButton = findViewById(R.id.activity_main_loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = "user";
                String password = "password";
                boolean condition1 = usernameEditText.getText().toString().equals(user);
                boolean condition2 = passwordEditText.getText().toString().equals(password);
                boolean condition3 = isNetworkConnected();
                boolean condition4 = isLocationEnabled();
                if (condition3) {
                    if (condition4) {
                        if (condition1 && condition2) {
                            Intent intent = new Intent(getApplicationContext(), AdminDashboard.class);
                            startActivity(intent);
                            finish();
                        } else {
                            String toastMessage = "Wrong Username or Password ";
                            Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        String toastMessage = "Turn on your location before login";
                        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();}

                } else {
                    String toastMessage = "You are not connected to network";
                    Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button registerButton = findViewById(R.id.activity_main_registerButton);
        Button forgotPasswordButton = findViewById(R.id.activity_main_forgotPasswordButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
        country=findViewById(R.id.country);
    }

    private void getLocation() {
        try {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5,(LocationListener) this);

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void checkLocationIsEnabledOrNot() {
        LocationManager lm=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled=false;
        boolean networkEnabled=false;
        try {
            gpsEnabled=lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            networkEnabled=lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(!gpsEnabled&&!networkEnabled){
            new android.app.AlertDialog.Builder(MainActivity.this)
                    .setTitle("Enable gps service")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    });
        }
    }
    private void grantPermission() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {

        try {
            Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses= geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
            country.setText(addresses.get(0).getCountryName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}