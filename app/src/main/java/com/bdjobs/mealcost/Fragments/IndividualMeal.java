package com.bdjobs.mealcost.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bdjobs.mealcost.R;

/**
 * Created by Tabriz on 08-May-16.
 */
public class IndividualMeal extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.individual_meal_fragment,container,false);
        return view;
    }
}
