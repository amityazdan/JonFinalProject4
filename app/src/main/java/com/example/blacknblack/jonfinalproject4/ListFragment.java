package com.example.blacknblack.jonfinalproject4;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, LocationListener {

    boolean bycity = true;
    Button byCityBTN;
    Button byLocationBTN;
    String search_str;
    private LocationManager locationManager;
    private String locationProvider;
    double latitude = 0.0;
    double longitude = 0.0;
    DBHelper dbHelper;

    MyCusrorAdapter myCusrorAdapter;
    ListService listService;

    public ListFragment() {
        // Required empty public constructor
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        locationProvider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            getActivity().requestPermissions(permissions, 8);
        }
        locationManager.requestLocationUpdates(locationProvider, 20, 1, this);


        ////************GO BUTTON
        final EditText searchTXT = (EditText) view.findViewById(R.id.editTextlist);
        ((Button) view.findViewById(R.id.buttonlist)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_str = searchTXT.getText().toString();
                listService = new ListService();
                Intent intent = new Intent(getActivity(), ListService.class);
                intent.putExtra("bycity", bycity);
                intent.putExtra("searchtext", search_str);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                getActivity().startService(intent);
            }
        });


        byCityBTN = (Button) view.findViewById(R.id.bycityBTN);
        byLocationBTN = (Button) view.findViewById(R.id.bylocationBTN);

        byCityBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bycity = true;
                byCityBTN.setBackgroundColor(Color.argb(255, 247, 178, 178));
                byLocationBTN.setBackgroundColor(Color.argb(255, 238, 202, 202));
                searchTXT.setHint("Search by City");
            }
        });


        byLocationBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bycity = false;
                byLocationBTN.setBackgroundColor(Color.argb(255, 247, 178, 178));
                byCityBTN.setBackgroundColor(Color.argb(255, 238, 202, 202));
                searchTXT.setHint("Search by your Location");
            }
        });

        ListView lv = (ListView) view.findViewById(R.id.listView);
        registerForContextMenu(lv);

        myCusrorAdapter = new MyCusrorAdapter(getActivity(), null);
        lv.setAdapter(myCusrorAdapter);
        getLoaderManager().initLoader(1, null, this);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = (Cursor) myCusrorAdapter.getItem(position);
                MyPlace p=bringPlace(c);

                ((MainActivity) getActivity()).changePlace(p);
            }
        });

        return view;
    }

    public MyPlace bringPlace(Cursor c){
        String name = c.getString(c.getColumnIndex(DBConstants.NAME));
        String address = c.getString(c.getColumnIndex(DBConstants.ADDRESS));
        double latitude = c.getDouble(c.getColumnIndex(DBConstants.LATITUDE));
        double longitude = c.getDouble(c.getColumnIndex(DBConstants.LONGITUDE));
        String icon = c.getString(c.getColumnIndex(DBConstants.ICON));
        int open = c.getInt(c.getColumnIndex(DBConstants.OPEN));
        String photo = c.getString(c.getColumnIndex(DBConstants.PHOTO));
        String type = c.getString(c.getColumnIndex(DBConstants.TYPE));
        MyPlace p = new MyPlace(name, address, latitude, longitude, icon, open, photo, type);
        return p;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = Uri.parse("content://com.example.blacknblack.jonfinalproject4.tablename/" + DBConstants.TABLENAME);
        return new CursorLoader(getActivity(), uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        myCusrorAdapter.swapCursor(data);
        Uri uri = Uri.parse("content://com.example.blacknblack.jonfinalproject4.tablename/" + DBConstants.TABLENAME);
        data.setNotificationUri(getActivity().getContentResolver(), uri);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.d("check","GPS changed");

        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getTitle() == "Add to favorites") {
            Cursor c = (Cursor) myCusrorAdapter.getItem(menuInfo.position);

            MyPlace myPlace=bringPlace(c);

            dbHelper= new DBHelper(getActivity());
            dbHelper.addToFavorites(myPlace);
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Edit chosen");
        menu.add(0, v.getId(), 0, "Add to favorites");
        menu.add(0, v.getId(), 0, "something");

    }


}
