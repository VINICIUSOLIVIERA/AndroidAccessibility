package production.tcc.android.androidaccessibility.Fragment;

import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import production.tcc.android.androidaccessibility.Helpers.MapHelper;
import production.tcc.android.androidaccessibility.R;

public class MapsFragment extends MapHelper {

    private View viewMap;
    private MapView mapView;
    private GoogleMap map;
    private LatLng location;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewMap = inflater.inflate(R.layout.fragment_map, container, false);
        return viewMap;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = viewMap.findViewById(R.id.map);

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync((OnMapReadyCallback) this);
        }
    }
}
