package com.bdjobs.mealcost;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.bdjobs.mealcost.Fragments.AddCost;
import com.bdjobs.mealcost.Fragments.AddMeal;
import com.bdjobs.mealcost.Fragments.Addmember;
import com.bdjobs.mealcost.Fragments.BazarSummary;
import com.bdjobs.mealcost.Fragments.CurrentMonthSummary;
import com.bdjobs.mealcost.Fragments.MealSummary;
import com.bdjobs.mealcost.Fragments.Overview;

public class LandingPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction =fragmentManager.beginTransaction();
        Overview overview = new Overview();
        transaction.add(R.id.landing_page,overview,"overview");
        transaction.commit();





    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.overView) {
            FragmentTransaction transaction =fragmentManager.beginTransaction();
            Overview overview = new Overview();
            transaction.replace(R.id.landing_page,overview,"overview");
            transaction.commit();

            // Handle the camera action
        } else if (id == R.id.add_cost) {
            AddCost addCost  = new AddCost();
            FragmentTransaction transactionL =fragmentManager.beginTransaction();
            transactionL.replace(R.id.landing_page,addCost,"addCost");
            transactionL.addToBackStack("addCost");
            transactionL.commit();

        } else if (id == R.id.add_meal) {

            AddMeal addMeal = new AddMeal();
            FragmentTransaction transactionL =fragmentManager.beginTransaction();
            transactionL.replace(R.id.landing_page,addMeal,"addMeal");
            transactionL.addToBackStack("addMeal");
            transactionL.commit();

        } else if (id == R.id.add_member) {
            Addmember addmember =new Addmember();
            FragmentTransaction transactionL =fragmentManager.beginTransaction();
            transactionL.replace(R.id.landing_page,addmember,"addmember");
            transactionL.addToBackStack("addmember");
            transactionL.commit();



        } else if (id == R.id.bazar_summary) {
            BazarSummary bazarSummary =new BazarSummary();
            FragmentTransaction transactionL =fragmentManager.beginTransaction();
            transactionL.replace(R.id.landing_page,bazarSummary,"bazarSummary");
            transactionL.addToBackStack("bazarSummary");
            transactionL.commit();

        } else if (id == R.id.meal_summary) {
            MealSummary mealSummary = new MealSummary();
            FragmentTransaction transactionL =fragmentManager.beginTransaction();
            transactionL.replace(R.id.landing_page,mealSummary,"mealSummary");
            transactionL.addToBackStack("mealSummary");
            transactionL.commit();

        }
        else if (id == R.id.current_month_summary) {
            CurrentMonthSummary currentMonthSummary = new CurrentMonthSummary();
            FragmentTransaction transactionL =fragmentManager.beginTransaction();
            transactionL.replace(R.id.landing_page,currentMonthSummary,"currentMonthSummary");
            transactionL.addToBackStack("currentMonthSummary");
            transactionL.commit();

        }

       

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
