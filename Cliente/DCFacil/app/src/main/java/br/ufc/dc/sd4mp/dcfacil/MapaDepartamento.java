package br.ufc.dc.sd4mp.dcfacil;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapaDepartamento extends Activity{

    private GoogleMap googleMap;
    private GoogleApiClient googleApiClient;
    private TextView addressLabel;
    private TextView locationLabel;
    private SensorManager manager;
    private Sensor sensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_departamento);
        initMap();
        getLocation();

    }

    private void initMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
            } else {
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        }
    }

    public void getLocation() {
        double latitude = -3.7460927;
        double longitude = -38.5743825;
        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!");
        googleMap.addMarker(marker);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(16).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void voltar(View view){
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initMap();
    }
}
