package com.nativeninjas.vista;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.nativeninjas.prod1.R;

public class UbicacionActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // Coordenadas iniciales: 41.3825° N, 2.176944° E
        LatLng initialLocation = new LatLng(41.3825, 2.176944);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 3)); // Ajusta el nivel de zoom según sea necesario

        obtenerPartidasYMostrarEnMapa();
    }

    private void obtenerPartidasYMostrarEnMapa() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("partida").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    Log.d(TAG, "Número de documentos obtenidos: " + queryDocumentSnapshots.size());
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        Log.d(TAG, "Documento ID: " + document.getId());
                        Log.d(TAG, "Contenido del documento: " + document.getData());

                        GeoPoint geoPoint = document.getGeoPoint("localizacion");
                        if (geoPoint != null) {
                            double latitude = geoPoint.getLatitude();
                            double longitude = geoPoint.getLongitude();
                            Log.d(TAG, "Localización obtenida: Latitud: " + latitude + ", Longitud: " + longitude);

                            if (latitude != 0.0 && longitude != 0.0) {
                                LatLng ubicacion = new LatLng(latitude, longitude);
                                googleMap.addMarker(new MarkerOptions().position(ubicacion).title("Ubicación de partida"));
                            } else {
                                Log.d(TAG, "Coordenadas inválidas para el documento ID: " + document.getId());
                            }
                        } else {
                            Log.d(TAG, "El campo 'localizacion' es nulo para el documento ID: " + document.getId());
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error al obtener documentos: ", e);
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}