package hoangpoly.vn.nc_lab6;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.room.Room;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText edtKinhDo;
    private EditText edtViDo;
    private EditText edtTenDiaDiem;
    private Button btnThem;
    private Button btnSua;
    private Button btnXoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        edtKinhDo = findViewById(R.id.edtKinhDo);
        edtViDo = findViewById(R.id.edtViDo);
        edtTenDiaDiem = findViewById(R.id.edtTenDiaDiem);
        btnThem = findViewById(R.id.btnThem);
        btnSua = findViewById(R.id.btnSua);
        btnXoa = findViewById(R.id.btnXoa);






    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final AppDatabase db= Room.databaseBuilder(MapsActivity.this, AppDatabase.class, "data.db").allowMainThreadQueries().build();
        List<DiaDiem> list = db.diaDiemDAO().getDiaDiem();

        for (int i=0; i<list.size();i++){
            DiaDiem diaDiem= list.get(i);
            LatLng dd = new LatLng(diaDiem.viDo,diaDiem.kinhDo);
            mMap.addMarker(new MarkerOptions().position(dd).title(diaDiem.tenDiaDiem));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(dd));
        }





//tren android 6 can xin quyen ACCESS_COARSE_LOCATION
        if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    444);
        }

        else if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    555);

        }else {
            mMap.setMyLocationEnabled(true);
            Log.e("la", mMap.getCameraPosition()+"");
            //Log.e("la", mMap.getMyLocation().getLongitude()+"");
        }

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double kd= Double.valueOf(edtKinhDo.getText().toString());
                Double vd= Double.valueOf(edtViDo.getText().toString());
                String ten= edtTenDiaDiem.getText().toString();

                DiaDiem diaDiem= new DiaDiem();
                diaDiem.maDiaDiem=kd+""+vd;
                diaDiem.kinhDo= kd;
                diaDiem.viDo= vd;
                diaDiem.tenDiaDiem= ten;
                long[] kq = db.diaDiemDAO().insertDiaDiem(diaDiem);
                LatLng vitri = new LatLng(vd,kd);

                mMap.addMarker(new MarkerOptions().position(vitri).title(ten));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(vitri));
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                Toast.makeText(MapsActivity.this, "Bạn đã click vào" +marker.getTitle(), Toast.LENGTH_SHORT).show();

                edtTenDiaDiem.setText(marker.getTitle());
                edtViDo.setText(marker.getPosition().latitude+"");
                edtKinhDo.setText(marker.getPosition().longitude+"");

                final String id1= Double.valueOf(marker.getPosition().longitude)+""+Double.valueOf(marker.getPosition().latitude);
                final String kd1= String.valueOf(Double.valueOf(marker.getPosition().longitude+""));
                final String vd1= String.valueOf(Double.valueOf(marker.getPosition().latitude+""));

                btnXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Double kd= Double.valueOf(edtKinhDo.getText().toString());
                        Double vd= Double.valueOf(edtViDo.getText().toString());
                        String ten= edtTenDiaDiem.getText().toString();

                        DiaDiem diaDiem= new DiaDiem();
                        diaDiem.maDiaDiem=kd+""+vd;
                        diaDiem.kinhDo= kd;
                        diaDiem.viDo= vd;
                        diaDiem.tenDiaDiem= ten;

                        int kq = db.diaDiemDAO().deleteDiaDiem(diaDiem);
                      /*  if(kq==1){
                            Toast.makeText(MapsActivity.this, "Da xoa vi tri " +marker.getTitle(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MapsActivity.this, "Xoa that bai", Toast.LENGTH_SHORT).show();
                        }*/
                        marker.remove();
                        edtTenDiaDiem.setText("");
                        edtViDo.setText("");
                        edtKinhDo.setText("");


                    }
                });

                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Double kd= Double.valueOf(edtKinhDo.getText().toString());
                        Double vd= Double.valueOf(edtViDo.getText().toString());
                        String ten= edtTenDiaDiem.getText().toString();
                        Log.e("sd", kd+""+vd);

                        DiaDiem diaDiem= new DiaDiem();
                        diaDiem.maDiaDiem=kd+""+vd;
                        diaDiem.kinhDo= kd;
                        diaDiem.viDo= vd;
                        diaDiem.tenDiaDiem= ten;

                        DiaDiem diaDiem1= new DiaDiem();
                        diaDiem1.maDiaDiem=id1;
                        diaDiem1.kinhDo= Double.valueOf(kd1);
                        diaDiem1.viDo= Double.valueOf(vd1);
                        diaDiem.tenDiaDiem= ten;
                        int kq1 = db.diaDiemDAO().deleteDiaDiem(diaDiem1);
                        long[] kq = db.diaDiemDAO().insertDiaDiem(diaDiem);
                       /* if(kq==1){
                            Toast.makeText(MapsActivity.this, "Da cap nhat vi tri " +marker.getTitle(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MapsActivity.this, "Cap nhat that bai", Toast.LENGTH_SHORT).show();
                        }*/
                        marker.remove();
                        LatLng vitri = new LatLng(vd,kd);
                        mMap.addMarker(new MarkerOptions().position(vitri).title(ten));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(vitri));

                    }
                });

                return true;
            }
        });






    }
}
