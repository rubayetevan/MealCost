package com.bdjobs.mealcost.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bdjobs.mealcost.R;
import com.bdjobs.mealcost.RealmClass.Cost;
import com.bdjobs.mealcost.RealmClass.Meal;
import com.bdjobs.mealcost.RealmClass.Member;

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
    Date from,to;
    TextView tmainCost,extraCostTV,mealTV,mealRateTV,exCostPPTV;
    double totalMainCosts,totalExtraCosts,totalMeals,mealRate,extraCostPerPerson;
    int breakfast,launch,dinner, numberOfMembers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.overview_fragment_layout, container, false);
        initializer();
        return view;
    }

    private void initializer() {
        tmainCost = (TextView) view.findViewById(R.id.mainCostTV);
        extraCostTV = (TextView) view.findViewById(R.id.extraCostTV);
        mealTV = (TextView) view.findViewById(R.id.mealTV);
        mealRateTV = (TextView) view.findViewById(R.id.mealRateTV);
        exCostPPTV = (TextView) view.findViewById(R.id.exCostPPTV);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        dateFormate();
        realmDB();
        calculation();
        settingTextView();

    }

    private void settingTextView() {
        tmainCost.setText("MainCost :"+String.valueOf(totalMainCosts));
        extraCostTV.setText("ExtraCost :"+String.valueOf(totalExtraCosts));
        mealTV.setText("TotalMeal :"+String.valueOf(totalMeals));
        mealRateTV.setText("MealRate :"+String.valueOf(mealRate));
        exCostPPTV.setText("ExCpP :"+String.valueOf(extraCostPerPerson));
    }

    private void calculation() {

        RealmResults<Cost> costs = realm.where(Cost.class).between("date",from,to).findAll();
        totalMainCosts = costs.sum("mainCost").doubleValue();
        totalExtraCosts= costs.sum("extraCost").doubleValue();

        RealmResults<Meal> meals = realm.where(Meal.class).between("date",from,to).findAll();
        breakfast = meals.sum("breakfast").intValue();
        launch = meals.sum("launch").intValue();
        dinner = meals.sum("dinner").intValue();

        RealmResults<Member> members = realm.where(Member.class).findAll();
        numberOfMembers = members.size();

        totalMeals = Double.valueOf(breakfast+launch+dinner);
        mealRate = totalMainCosts/totalMeals;
        extraCostPerPerson = totalExtraCosts/Double.valueOf(numberOfMembers);





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

    private void realmDB() {
        realmConfig = new RealmConfiguration.Builder(getActivity()).build();
        realm = Realm.getInstance(realmConfig);
    }
}
