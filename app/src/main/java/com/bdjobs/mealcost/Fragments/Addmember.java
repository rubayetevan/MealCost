package com.bdjobs.mealcost.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bdjobs.mealcost.R;
import com.bdjobs.mealcost.RealmClass.Member;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;

/**
 * Created by Tabriz on 04-May-16.
 */
public class Addmember extends Fragment {

    View view;
    EditText nameET,mobileNumberET,emailET;
    Button addMemberBTN;
    String name,phone,email;
    Realm realm;
    RealmConfiguration realmConfig;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_member_fragment, container, false);
        initializer();
        onClickListener();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        realmDB();
    }

    private void realmDB() {
        realmConfig = new RealmConfiguration.Builder(getActivity()).build();
        realm = Realm.getInstance(realmConfig);
    }

    private void onClickListener() {
        addMemberBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameET.getText().toString();
                phone = mobileNumberET.getText().toString();
                email = emailET.getText().toString();

                if (checkIfExists(phone))
                {
                    Toast.makeText(getActivity(), "User Already Exists", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm bgRealm) {

                            Member member = bgRealm.createObject(Member.class);
                            member.setName(name);
                            member.setMobileNumber(phone);
                            member.setEmail(email);
                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(getActivity(), "New Member Successfully Added", Toast.LENGTH_SHORT).show();
                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

    }
    public boolean checkIfExists(String mobileNumber){

        RealmQuery<Member> query = realm.where(Member.class).equalTo("mobileNumber", mobileNumber);

        return query.count() == 0 ? false : true;
    }
    private void initializer() {
        nameET = (EditText) view.findViewById(R.id.nameET);
        mobileNumberET = (EditText) view.findViewById(R.id.phoneET);
        emailET = (EditText) view.findViewById(R.id.emailET);
        addMemberBTN = (Button) view.findViewById(R.id.addMemberBTN);
    }
}
