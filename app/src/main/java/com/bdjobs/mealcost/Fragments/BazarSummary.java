package com.bdjobs.mealcost.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import io.realm.Sort;

/**
 * Created by Tabriz on 07-May-16.
 */
public class BazarSummary extends Fragment {
    View view;
    Realm realm;
    RealmConfiguration realmConfig;
    Date from,to;
    int size;
    LinearLayout bazar_summary_layout;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bazar_summary_fragment, container, false);
        bazar_summary_layout = (LinearLayout) view.findViewById(R.id.bazar_summary_layout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        realmDB();
        dateFormate();
        setField();
    }

    private void setField() {
        RealmResults<Cost> costs = realm.where(Cost.class).between("date",from,to).findAllSorted("date", Sort.ASCENDING);
        size=costs.size();
        for(int i=0;i<size;i++)
        {
            LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1.0f;
            params.gravity = Gravity.CENTER;


            TextView nameTV = new TextView(getActivity());
            nameTV.setLayoutParams(params);
            nameTV.setText(costs.get(i).getName());

            TextView dateTV = new TextView(getActivity());
            dateTV.setLayoutParams(params);
            String dateLast =new SimpleDateFormat("dd/MM/yy").format(costs.get(i).getDate());
            dateTV.setText(dateLast);

            TextView mainCostTV = new TextView(getActivity());
            mainCostTV.setLayoutParams(params);
            mainCostTV.setText(String.valueOf(costs.get(i).getMainCost()));

            TextView extraCostTV = new TextView(getActivity());
            extraCostTV.setLayoutParams(params);
            extraCostTV.setText(String.valueOf(costs.get(i).getExtraCost()));

            linearLayout.addView(dateTV);
            linearLayout.addView(nameTV);
            linearLayout.addView(mainCostTV);
            linearLayout.addView(extraCostTV);

            bazar_summary_layout.addView(linearLayout);

        }




    }
    private void realmDB() {
        realmConfig = new RealmConfiguration.Builder(getActivity()).build();
        realm = Realm.getInstance(realmConfig);
    }
    private void dateFormate() {
        String pattern = "MM/dd/yy";

        Calendar c = Calendar.getInstance();   // this takes current date
        c.set(Calendar.DAY_OF_MONTH, 1);
        String dateFirst =new SimpleDateFormat(pattern).format(c.getTime());
        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.DAY_OF_MONTH, cl.getActualMaximum(Calendar.DAY_OF_MONTH));
        String dateLast =new SimpleDateFormat(pattern).format(cl.getTime());

        DateFormat formatter = new SimpleDateFormat(pattern);

        try {
            from = formatter.parse(dateFirst);
            to = formatter.parse(dateLast);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
