package com.bdjobs.mealcost.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bdjobs.mealcost.R;
import com.bdjobs.mealcost.RealmClass.Cost;
import com.bdjobs.mealcost.RealmClass.Member;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Tabriz on 04-May-16.
 */
public class AddCost extends Fragment {
    View view;
    EditText dateCostET,mainCostET,extraCostET;
    Button addCostBTN;
    Realm realm;
    RealmConfiguration realmConfig;
    RealmResults<Member> menbers;
    List<String> MemberNames = new ArrayList<String>();
    Spinner Namespinner;
    Member selectedMember;
    String date;
    double extraCost,mainCost;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_bazar_fragment, container, false);
        initializer();
        onClickListener();
        return view;
    }

    private void onClickListener() {
        addCostBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                date = dateCostET.getText().toString();
                mainCost = Double.valueOf(mainCostET.getText().toString());
                extraCost = Double.valueOf(extraCostET.getText().toString());




                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm bgRealm) {

                        Cost cost = bgRealm.createObject(Cost.class);
                        cost.setMember(selectedMember);
                        cost.setDate(date);
                        cost.setExtraCost(extraCost);
                        cost.setMainCost(mainCost);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getActivity(), "New Cost Added", Toast.LENGTH_SHORT).show();
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        realmDB();
        settingSpinner();
    }

    private void settingSpinner() {
        menbers = realm.where(Member.class).findAll();
        if(menbers.size()>MemberNames.size()) {
            MemberNames.clear();
            for (int i = 0; i < menbers.size(); i++) {
                MemberNames.add(i, menbers.get(i).getName());
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, MemberNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Namespinner.setAdapter(dataAdapter);
        /*Namespinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedMember = menbers.get(position);
            }
        });*/

    }

    private void realmDB() {
        realmConfig = new RealmConfiguration.Builder(getActivity()).build();
        realm = Realm.getInstance(realmConfig);
    }

    private void initializer() {
        addCostBTN = (Button) view.findViewById(R.id.addCostBTN);
        dateCostET = (EditText) view.findViewById(R.id.dateCostET);
        mainCostET = (EditText) view.findViewById(R.id.mainCostET);
        extraCostET = (EditText) view.findViewById(R.id.extraCostET);
        Namespinner = (Spinner) view.findViewById(R.id.memberCostSP);

    }
}
