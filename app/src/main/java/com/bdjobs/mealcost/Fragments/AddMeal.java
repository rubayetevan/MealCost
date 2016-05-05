package com.bdjobs.mealcost.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Rubayet on 05-May-16.
 */
public class AddMeal extends Fragment {
    View view;
    LinearLayout addMealLeayout;
    Realm realm;
    RealmConfiguration realmConfig;
    int size;
    RealmResults<Member> menbers;
    List<EditText> allEditText = new ArrayList<EditText>();
    Button saveBTN;
    String[] edString;
    final Calendar myCalendar = Calendar.getInstance();
    EditText mealDateET;
    String date;
    Date dateF = null;
    String name, brakfast, launch, dinner;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_meal_fragment, container, false);
        initializer();
        return view;
    }

    private void onClickListener() {
        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edString = new String[(allEditText.size())];
                for (int i = 0; i < allEditText.size(); i++) {
                    if (allEditText.get(i).getText().toString().matches("") || mealDateET.getText().toString().matches("")) {
                        Toast.makeText(getActivity(), "Please fill up every blank field by 0", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        edString[i] = allEditText.get(i).getText().toString();
                    }

                }

                date = mealDateET.getText().toString();

                DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
                try {
                    dateF = formatter.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (checkIfExists(dateF)) {
                    Toast.makeText(getActivity(), "Data Already Exists", Toast.LENGTH_SHORT).show();
                } else {
                    int meal = 0;
                    for (int member = 0; member < menbers.size(); member++) {
                        name = menbers.get(member).getName();

                        brakfast = edString[meal];
                        launch = edString[meal + 1];
                        dinner = edString[meal + 2];
                        int mealLast = meal + 2;
                        meal = mealLast + 1;

                        System.out.println(name + ":" + brakfast + "/" + launch + "/" + dinner);

                        realm.beginTransaction();
                        Meal meals = realm.createObject(Meal.class);
                        meals.setName(name);
                        meals.setDate(dateF);
                        meals.setBreakfast(Integer.parseInt(brakfast));
                        meals.setLaunch(Integer.parseInt(launch));
                        meals.setDinner(Integer.parseInt(dinner));
                        realm.commitTransaction();
                    }
                }
            }
        });

    }

    private void initializer() {
        addMealLeayout = (LinearLayout) view.findViewById(R.id.addMealLayout);
        mealDateET = (EditText) view.findViewById(R.id.mealDateET);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        datePicker();
        mealDateET.setKeyListener(null);
        realmDB();
        setField();
        onClickListener();
    }

    private void setField() {

        menbers = realm.where(Member.class).findAll();
        size = menbers.size();
        for (int i = 0; i < size; i++) {
            LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1.0f;
            params.gravity = Gravity.CENTER;


            TextView nameTV = new TextView(getActivity());
            nameTV.setLayoutParams(params);
            nameTV.setText(menbers.get(i).getName());

            EditText breakFastET = new EditText(getActivity());
            breakFastET.setInputType(InputType.TYPE_CLASS_NUMBER);
            //breakFastET.setId(); //Set id so that you can remove that EditText in the future.
            breakFastET.setLayoutParams(params);

            EditText launchET = new EditText(getActivity());
            launchET.setInputType(InputType.TYPE_CLASS_NUMBER);
            //launchET.setId(); //Set id so that you can remove that EditText in the future.
            launchET.setLayoutParams(params);

            EditText dinnerET = new EditText(getActivity());
            dinnerET.setInputType(InputType.TYPE_CLASS_NUMBER);
            //dinnerET.setId(); //Set id so that you can remove that EditText in the future.
            dinnerET.setLayoutParams(params);

            linearLayout.addView(nameTV);
            linearLayout.addView(breakFastET);
            linearLayout.addView(launchET);
            linearLayout.addView(dinnerET);

            allEditText.add(breakFastET);
            allEditText.add(launchET);
            allEditText.add(dinnerET);


            addMealLeayout.addView(linearLayout);
        }

        saveBTN = new Button(getActivity());
        saveBTN.setText("Save");
        saveBTN.setGravity(Gravity.CENTER_HORIZONTAL);
        addMealLeayout.addView(saveBTN);


    }

    private void realmDB() {
        realmConfig = new RealmConfiguration.Builder(getActivity()).build();
        realm = Realm.getInstance(realmConfig);
    }

    private void datePicker() {

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        mealDateET.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        mealDateET.setText(sdf.format(myCalendar.getTime()));
    }

    public boolean checkIfExists(Date date) {

        RealmQuery<Meal> query = realm.where(Meal.class).equalTo("date", date);

        return query.count() == 0 ? false : true;
    }
}
