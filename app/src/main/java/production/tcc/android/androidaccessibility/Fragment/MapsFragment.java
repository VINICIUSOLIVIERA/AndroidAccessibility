//package production.tcc.android.androidaccessibility.Fragment;
//
//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Criteria;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.Settings;
//import android.support.annotation.Nullable;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.content.PermissionChecker;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapView;
//import com.google.android.gms.maps.MapsInitializer;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//import production.tcc.android.androidaccessibility.R;
//
//import static android.support.v4.content.ContextCompat.checkSelfPermission;
//
//public class MapsFragment extends Fragment implements OnMapReadyCallback,
//        GoogleMap.OnMarkerClickListener,
//        GoogleMap.OnMapLongClickListener,
//        LocationListener{
//
//    private View viewMap;
//    private MapView mapView;
//    private GoogleMap map;
//    private LatLng location;
//
//    // ZOOM
//    private int ZOOM_WORLD = 1, // MIN ZOOM
//            ZOOM_EARTH = 5,
//            ZOOM_CITY = 10,
//            ZOOM_STREET = 15,
//            ZOOM_BUILDINGS = 20; // MAX ZOOM
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        viewMap = inflater.inflate(R.layout.fragment_map, container, false);
//        return viewMap;
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        mapView = viewMap.findViewById(R.id.map);
//
//        if (mapView != null) {
//            mapView.onCreate(null);
//            mapView.onResume();
//            mapView.getMapAsync((OnMapReadyCallback) this);
//        }
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        MapsInitializer.initialize(getContext());
//        map = googleMap;
//        this.addMarker("TESTE 1", "Teste Marker 1", -7.115, -34.86306);
//        this.addMarker("TESTE 2", "Teste Marker 2", -8.115, -35.86306);
//        this.addMarker("TESTE 3", "Teste Marker 3", -9.115, -31.86306);
//        this.addMarker("TESTE 4", "Teste Marker 4", -7.300, -34.900);
//        map.setOnMarkerClickListener(this);
//        map.setOnMapLongClickListener(this);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            LocationManager locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
//            Criteria criteria = new Criteria();
//            String bestProvider = locationManager.getBestProvider(criteria, true);
//            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(getActivity(), "RETURN", Toast.LENGTH_LONG).show();
//                ActivityCompat.requestPermissions(getActivity(), , 0);
//                return;
//            }
//
//            Location location = locationManager.getLastKnownLocation(bestProvider);
//            if (location != null) {
//                onLocationChanged(location);
//            }
//            locationManager.requestLocationUpdates(bestProvider, 2000, 0, this);
//        }
//
//        map.setMyLocationEnabled(true);
//
//
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////            try {
////                if (checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////                    Toast.makeText(getContext(), "TRY", Toast.LENGTH_SHORT).show();
//////                int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);/
////                    Toast.makeText(getContext(), "IF", Toast.LENGTH_SHORT).show();
////                }
////            } catch (Exception e) {
////                Toast.makeText(getContext(), "CATCH", Toast.LENGTH_SHORT).show();
////            }
////        }
////
////        map.setMyLocationEnabled(true);
//    }
//
//    public void addMarker(String title, String description, double lat, double lng){
////         ICON BLUE
////        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
////        ICON CUSTOM
////        BitmapDescriptorFactory.fromResource(R.drawable.arrow))
////        ROTATE ICON
////        .rotation(45)
//        this.location = new LatLng(lat, lng);
//        MarkerOptions marker = new MarkerOptions().position(this.location).title(title).snippet(description);
//        map.addMarker(marker);
//        this.moveCamera(lat, lng, this.ZOOM_STREET);
//        this.location = null;
//    }
//
//    @Override
//    public boolean onMarkerClick(Marker marker) {
//        return false;
//    }
//
//    public void moveCamera(double lat, double lng, int zoom){
//        this.location = new LatLng(lat, lng);
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(this.location, zoom));
//        this.location = null;
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//        this.addMarker("VINCIISU", "VINCUCIUS", location.getLatitude(), location.getLongitude());
//        Toast.makeText(getContext(), "onLocationChanged", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onStatusChanged(String s, int i, Bundle bundle) {
//        Toast.makeText(getContext(), "onStatusChanged", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onProviderEnabled(String s) {
//        Toast.makeText(getContext(), "onProviderEnabled", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onProviderDisabled(String s) {
//        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//        startActivity(intent);
//        Toast.makeText(getContext(), "onProviderDisabled", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onMapLongClick(LatLng latLng) {
//        this.addMarker("Marker", "Clique longo", latLng.latitude, latLng.longitude);
//    }
//
//}
