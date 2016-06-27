package com.example.blacknblack.jonfinalproject4;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jbt on 5/18/2016.
 */
public class MyCusrorAdapter extends CursorAdapter {

    public MyCusrorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View myItem = LayoutInflater.from(context).inflate(R.layout.myadapterxml, null);
        return myItem;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTV = (TextView) view.findViewById(R.id.textView3);
        TextView address = (TextView) view.findViewById(R.id.textView4);
        int nameCol = cursor.getColumnIndex(DBConstants.NAME);
        int addressCol = cursor.getColumnIndex(DBConstants.ADDRESS);
        String placename = cursor.getString(nameCol);
        nameTV.setText(placename);
        String placeaddress = cursor.getString(addressCol);
        address.setText(placeaddress);

        //**************get image 1 or 0
        ImageView imageView=(ImageView)view.findViewById(R.id.imageView);
        int opencloseCol = cursor.getColumnIndex(DBConstants.OPEN);
        int openClose = cursor.getInt(opencloseCol);
            String imageName;
        if (openClose==1){
            imageName="openicon";
        }
        else {
             imageName="closeicon";
        }
        int picId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        imageView.setImageResource(picId);
    }
}
