package com.bdjobs.mealcost.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bdjobs.mealcost.R;
import com.bdjobs.mealcost.RealmClass.Cost;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Tabriz on 04-May-16.
 */
public class Overview extends Fragment {

    View view;
    Realm realm;
    RealmConfiguration realmConfig;
    Date dateF,from,to;
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.overview_fragment_layout, container, false);
        textView = (TextView) view.findViewById(R.id.mainCostTV);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DateFormat formatter = new SimpleDateFormat("MM/dd/yy");

        try {
            from = formatter.parse("05/01/2016");
            to = formatter.parse("05/31/2016");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        realmDB();

        RealmResults<Cost> costs = realm.where(Cost.class).between("date",from,to).findAll();
        double mainCosts = costs.sum("mainCost").doubleValue();
        textView.setText(String.valueOf(mainCosts));
    }
    private void realmDB() {
        realmConfig = new RealmConfiguration.Builder(getActivity()).build();
        realm = Realm.getInstance(realmConfig);
    }
}
