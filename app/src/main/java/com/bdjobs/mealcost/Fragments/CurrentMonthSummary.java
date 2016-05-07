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

import com.bdjobs.mealcost.ModelClass.Summary;
import com.bdjobs.mealcost.R;
import com.bdjobs.mealcost.RealmClass.Cost;
import com.bdjobs.mealcost.RealmClass.Meal;
import com.bdjobs.mealcost.RealmClass.Member;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Tabriz on 07-May-16.
 */
public class CurrentMonthSummary extends Fragment {

    View view;
    Realm realm;
    RealmConfiguration realmConfig;
    Date from,to;
    LinearLayout current_month_summary_layout;
    String[] member;
    ArrayList<Summary> summaries = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.current_month_summary_fragment,container,false);
        current_month_summary_layout = (LinearLayout) view.findViewById(R.id.current_month_summary_layout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        realmDB();
        dateFormate();
        calculation();
        setField();
    }

    private void setField() {

        int size = summaries.size();

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
            nameTV.setText(summaries.get(i).getName());

            TextView mealTV = new TextView(getActivity());
            mealTV.setLayoutParams(params);
            mealTV.setText(summaries.get(i).getTotalMeal());

            TextView mealRate = new TextView(getActivity());
            mealRate.setLayoutParams(params);
            mealRate.setText(summaries.get(i).getMealRate());

            TextView mealCostTV = new TextView(getActivity());
            mealCostTV.setLayoutParams(params);
            mealCostTV.setText(summaries.get(i).getMealCost());


            TextView extraCostTV = new TextView(getActivity());
            extraCostTV.setLayoutParams(params);
            extraCostTV.setText(summaries.get(i).getExtraCost());

            TextView savingsTV = new TextView(getActivity());
            savingsTV.setLayoutParams(params);
            savingsTV.setText(summaries.get(i).getSavings());

            TextView paymentTV = new TextView(getActivity());
            paymentTV.setLayoutParams(params);
            paymentTV.setText(summaries.get(i).getPayment());




            linearLayout.addView(nameTV);
            linearLayout.addView(mealTV);
            linearLayout.addView(mealRate);
            linearLayout.addView(mealCostTV);
            linearLayout.addView(extraCostTV);
            linearLayout.addView(savingsTV);
            linearLayout.addView(paymentTV);


            current_month_summary_layout.addView(linearLayout);

        }
        summaries.clear();
    }

    private void calculation() {

        RealmResults<Member> members = realm.where(Member.class).findAll();
        member = new String[(members.size())];
        for(int i=0;i<members.size();i++)
        {
            member[i]=members.get(i).getName();
        }

        RealmResults<Meal> meals = realm.where(Meal.class).between("date",from,to).findAllSorted("date", Sort.ASCENDING);
        RealmResults<Cost> costs = realm.where(Cost.class).between("date",from,to).findAllSorted("date", Sort.ASCENDING);


        int breakfastt = meals.sum("breakfast").intValue();
        int launchh = meals.sum("launch").intValue();
        int dinnerr = meals.sum("dinner").intValue();

        double totalMainCosts = costs.sum("mainCost").doubleValue();
        double totalExtraCosts = costs.sum("extraCost").doubleValue();
        int numberOfMembers = members.size();

        Double totalMeals = Double.valueOf(breakfastt + launchh + dinnerr);
        Double mealRate = totalMainCosts / totalMeals;
        Double extraCostPerPerson = totalExtraCosts / Double.valueOf(numberOfMembers);


        for(int m=0;m<members.size();m++)
        {
            RealmResults<Meal> individualMeal = meals.where().equalTo("name", member[m]).findAll();
            int launch = individualMeal.sum("launch").intValue();
            int breakfast = individualMeal.sum("breakfast").intValue();
            int dinner = individualMeal.sum("dinner").intValue();

            double totalMeal = launch + dinner + breakfast;
            double mealCost = mealRate*totalMeal;

            RealmResults<Cost> individualCost = costs.where().equalTo("Name", member[m]).findAll();
            double extraCosts = individualCost.sum("extraCost").doubleValue();
            double mainCost = individualCost.sum("mainCost").doubleValue();

            double deposit = extraCosts+mainCost;

            double payment = deposit-(mealCost+extraCostPerPerson);

            Summary summary = new Summary(member[m],decimalFormater(totalMeal),decimalFormater(mealRate),decimalFormater(mealCost),
                    decimalFormater(deposit),decimalFormater(extraCostPerPerson),decimalFormater(payment));

            summaries.add(m,summary);
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

    private String decimalFormater(double number)
    {
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        return (numberFormat.format(number));
    }


}
