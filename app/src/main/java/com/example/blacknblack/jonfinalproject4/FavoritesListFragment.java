package com.example.blacknblack.jonfinalproject4;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesListFragment extends Fragment {
    MyCusrorAdapter myCusrorAdapter;

    public FavoritesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_favorites_list, container, false);

        ListView flv = (ListView) view.findViewById(R.id.favoriteslist);
        myCusrorAdapter = new MyCusrorAdapter(getActivity(), null);
        flv.setAdapter(myCusrorAdapter);


        return view;
    }

}
