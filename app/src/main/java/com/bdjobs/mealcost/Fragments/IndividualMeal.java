package com.bdjobs.mealcost.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bdjobs.mealcost.R;
import com.bdjobs.mealcost.RealmClass.Cost;
import com.bdjobs.mealcost.RealmClass.Meal;
import com.bdjobs.mealcost.RealmClass.Member;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Tabriz on 08-May-16.
 */
public class IndividualMeal extends Fragment {

    View view;
    EditText dateFromET, dateToET;
    Spinner nameSP;
    Button searchBTN;
    String fromDate, toDate;
    Realm realm;
    RealmConfiguration realmConfig;
    Date from, to;
    final Calendar myCalendar = Calendar.getInstance();
    int size;
    LinearLayout individualBazarLayout;
    List<String> MemberNames = new ArrayList<String>();
    RealmResults<Member> menbers;
    String selectedMember;
    List<LinearLayout> linearLayouts = new ArrayList<LinearLayout>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.individual_meal_fragment,container,false);
        initializer();
        return view;
    }
    private void initializer() {
        dateFromET = (EditText) view.findViewById(R.id.IMdateFrom);
        dateToET = (EditText) view.findViewById(R.id.IMdateTo);
        nameSP = (Spinner) view.findViewById(R.id.IMmemberNameSP);
        searchBTN = (Button) view.findViewById(R.id.IMsearchBtn);
        individualBazarLayout = (LinearLayout) view.findViewById(R.id.individual_meal_layout);

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        realmDB();
        datePickerFrom();
        datePickerTo();
        settingSpinner();
        onClickListener();

    }
    private void settingSpinner() {
        menbers = realm.where(Member.class).findAll();
        if (menbers.size() > MemberNames.size()) {
            MemberNames.clear();
            for (int i = 0; i < menbers.size(); i++) {
                MemberNames.add(i, menbers.get(i).getName());
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, MemberNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nameSP.setAdapter(dataAdapter);
        nameSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMember = menbers.get(position).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void onClickListener() {
        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDate = dateFromET.getText().toString();
                toDate = dateToET.getText().toString();
                dateFormate();
                setField();
            }
        });
    }
    private void realmDB() {
        realmConfig = new RealmConfiguration.Builder(getActivity()).build();
        realm = Realm.getInstance(realmConfig);
    }
    private void dateFormate() {
        String pattern = "MM/dd/yy";
        DateFormat formatter = new SimpleDateFormat(pattern);
        try {
            from = formatter.parse(fromDate);
            to = formatter.parse(toDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private void datePickerFrom() {

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelFrom();
            }

        };

        dateFromET.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    private void datePickerTo() {

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelTo();
            }

        };


        dateToET.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void updateLabelFrom() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        dateFromET.setText(sdf.format(myCalendar.getTime()));

    }
    private void updateLabelTo() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        dateToET.setText(sdf.format(myCalendar.getTime()));

    }
    private void setField() {

        if(linearLayouts.size()>0)
        {
            for(int ll = 0;ll<linearLayouts.size();ll++)
            {
                individualBazarLayout.removeView(linearLayouts.get(ll));
            }

        }

        linearLayouts.clear();
        RealmResults<Meal> meals = realm.where(Meal.class).equalTo("name",selectedMember).between("date",from,to).findAllSorted("date", Sort.ASCENDING);
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

            TextView brkTV = new TextView(getActivity());
            brkTV.setLayoutParams(params);
            brkTV.setText(String.valueOf(meals.get(i).getBreakfast()));

            TextView lncTV = new TextView(getActivity());
            lncTV.setLayoutParams(params);
            lncTV.setText(String.valueOf(meals.get(i).getLaunch()));

            TextView dinnerTV = new TextView(getActivity());
            dinnerTV.setLayoutParams(params);
            dinnerTV.setText(String.valueOf(meals.get(i).getDinner()));

            linearLayout.addView(dateTV);
            linearLayout.addView(nameTV);
            linearLayout.addView(brkTV);
            linearLayout.addView(lncTV);
            linearLayout.addView(dinnerTV);

            individualBazarLayout.addView(linearLayout);
            linearLayouts.add(linearLayout);

        }

        int breakFast = meals.sum("breakfast").intValue();
        int launch = meals.sum("launch").intValue();
        int dinner = meals.sum("dinner").intValue();
        int totalMeal = breakFast+launch+dinner;

        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1.0f;
        params.gravity = Gravity.CENTER;


        TextView mainCostsTV = new TextView(getActivity());
        mainCostsTV.setLayoutParams(params);
        mainCostsTV.setText("Breakfast: "+String.valueOf(breakFast));


        TextView launchTV = new TextView(getActivity());
        launchTV.setLayoutParams(params);
        launchTV.setText("Launch: "+String.valueOf(launch));

        TextView extraCostsTV = new TextView(getActivity());
        extraCostsTV.setLayoutParams(params);
        extraCostsTV.setText("Dinner: "+String.valueOf(dinner));

        TextView totalCostsTV = new TextView(getActivity());
        totalCostsTV.setLayoutParams(params);
        totalCostsTV.setText("Total Meal: "+String.valueOf(totalMeal));

        linearLayout.addView(mainCostsTV);
        linearLayout.addView(launchTV);
        linearLayout.addView(extraCostsTV);
        linearLayout.addView(totalCostsTV);

        individualBazarLayout.addView(linearLayout);
        linearLayouts.add(linearLayout);

    }
}
