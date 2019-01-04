package se.kth.id1212.taskmanagerandroidclient.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;
import java.time.LocalDate;

import se.kth.id1212.taskmanagerandroidclient.R;
import se.kth.id1212.taskmanagerandroidclient.controller.Controller;
import se.kth.id1212.taskmanagerandroidclient.model.User;


/**
 * The activity that is used under the complete duration (after sign in) of the application.
 * Controls all the UI logic and views for the choosen task list, the menu, and the add new
 * task button.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Controller controller;

    public Controller getController() {
        return controller;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        User loggedInUser = (User) bundle.getSerializable("LOGGED_IN_USER");

        controller = new Controller(loggedInUser);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView list = (TextView) findViewById(R.id.list);
        TextView date = (TextView) findViewById(R.id.date);
        list.setText("Today");
        date.setText(LocalDate.now().toString());
        setTaskListFragment("TODAY");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
        getMenuInflater().inflate(R.menu.main, menu);
        TextView userName = (TextView) findViewById(R.id.userName);
        userName.setText(controller.getLoggedInUser().getEmail());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
        TextView list = (TextView) findViewById(R.id.list);
        TextView date = (TextView) findViewById(R.id.date);
        if (id == R.id.nav_camera) {
            list.setText("Today");
            date.setText(LocalDate.now().toString());
            setTaskListFragment("TODAY");
        } else if (id == R.id.nav_gallery) {
            list.setText("Upcoming");
            date.setText("");
            setTaskListFragment("UPCOMING");
        } else if (id == R.id.nav_slideshow) {
            list.setText("Done");
            date.setText("");
            setTaskListFragment("DONE");
        } else if (id == R.id.nav_manage) {
            controller.signOut();
            Intent myIntent = new Intent(this, MainActivity.class);
            finish();
            startActivity(myIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setTaskListFragment(String type){
        TaskListFragment taskListFragment = new TaskListFragment();
        Bundle bundle = new Bundle();
        switch (type){
            case "TODAY":
                bundle.putSerializable("START_DATE",null);
                bundle.putSerializable("END_DATE",LocalDate.now());
                bundle.putBoolean("IS_DONE",false);
                taskListFragment.setArguments(bundle);
                break;
            case "UPCOMING":
                bundle.putSerializable("START_DATE",LocalDate.now().plusDays(1));
                bundle.putSerializable("END_DATE",null);
                bundle.putBoolean("IS_DONE",false);
                taskListFragment.setArguments(bundle);
                break;
            case "DONE":
                bundle.putSerializable("START_DATE",null);
                bundle.putSerializable("END_DATE",null);
                bundle.putBoolean("IS_DONE",true);
                taskListFragment.setArguments(bundle);
                break;
        }
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.listContainer, taskListFragment);
        fragmentTransaction.commit();
    }

    public void addTask(View view){
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AddEditTaskFragment addEditTaskFragment = new AddEditTaskFragment();
        fragmentTransaction.replace(R.id.listContainer, addEditTaskFragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

     public void showError(String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();
            }
        });
    }

}
