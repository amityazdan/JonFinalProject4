package com.example.blacknblack.jonfinalproject4;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    ListFragment listFragment;
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listFragment = new ListFragment();
        manager = getFragmentManager();

        final Button searchBTN = (Button) findViewById(R.id.BTN1);
        final Button BTN2 = (Button) findViewById(R.id.BTN2);
        final Button BTN3 = (Button) findViewById(R.id.BTN3);
        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction().replace(R.id.mainFrame, listFragment).commit();
                searchBTN.setBackgroundColor(Color.argb(255, 176, 74, 234));
                BTN2.setBackgroundColor(Color.argb(150, 176, 74, 234));
                BTN3.setBackgroundColor(Color.argb(150, 176, 74, 234));
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (manager.getBackStackEntryCount() == 0) {
            AlertDialog.Builder ab=new AlertDialog.Builder(MainActivity.this);
            ab.setMessage("Are you sure?");
            ab.setPositiveButton("yes,im sure", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    supportFinishAfterTransition();
                }
            });
            ab.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            ab.show();
        }
        else {
            manager.popBackStack();
        }

    }

    public void changePlace(MyPlace p) {
        Log.e("amit", p.getName());

        MyMapFragment myMapFrag = new MyMapFragment();

        myMapFrag.name = p.name;
        myMapFrag.address = p.address;
        myMapFrag.latitude = p.latitude;
        myMapFrag.longitude = p.longitude;
        myMapFrag.icon = p.icon;
        myMapFrag.open = p.open;
        myMapFrag.photo = p.photo;
        myMapFrag.type = p.type;

        getFragmentManager().beginTransaction().replace(R.id.mainFrame, myMapFrag).addToBackStack("addToStack").commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
