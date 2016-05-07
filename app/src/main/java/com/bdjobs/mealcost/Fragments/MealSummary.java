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
import com.bdjobs.mealcost.RealmClass.Meal;

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
public class MealSummary extends Fragment {
    View view;
    Realm realm;
    RealmConfiguration realmConfig;
    Date from,to;
    int size;
    LinearLayout meal_summary_layout;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.meal_summary_fragment, container, false);
        meal_summary_layout = (LinearLayout) view.findViewById(R.id.meal_summary_layout);
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
        RealmResults<Meal> meals = realm.where(Meal.class).between("date",from,to).findAllSorted("date", Sort.ASCENDING);
        size=meals.size();
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
            nameTV.setText(meals.get(i).getName());

            TextView dateTV = new TextView(getActivity());
            dateTV.setLayoutParams(params);
            String dateLast =new SimpleDateFormat("dd/MM/yy").format(meals.get(i).getDate());
            dateTV.setText(dateLast);

            TextView breakfastTV = new TextView(getActivity());
            breakfastTV.setLayoutParams(params);
            breakfastTV.setText(String.valueOf(meals.get(i).getBreakfast()));

            TextView launchTV = new TextView(getActivity());
            launchTV.setLayoutParams(params);
            launchTV.setText(String.valueOf(meals.get(i).getLaunch()));

            TextView dinnerTV = new TextView(getActivity());
            dinnerTV.setLayoutParams(params);
            dinnerTV.setText(String.valueOf(meals.get(i).getDinner()));

            linearLayout.addView(dateTV);
            linearLayout.addView(nameTV);
            linearLayout.addView(breakfastTV);
            linearLayout.addView(launchTV);
            linearLayout.addView(dinnerTV);

            meal_summary_layout.addView(linearLayout);

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
