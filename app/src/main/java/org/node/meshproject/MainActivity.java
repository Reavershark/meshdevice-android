package org.node.meshproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Messages
    private static final int MESSAGE_FORM_REQUEST_CODE = 0;
    ArrayList<String> messagesList = new ArrayList<>();
    ListView messages;
    ArrayAdapter messagesArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // New message floating action button
        FloatingActionButton newMessageFab = (FloatingActionButton) findViewById(R.id.newMessageFab);
        newMessageFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageForm(view);
            }
        });

        // Message list
        messages = (ListView) findViewById(R.id.messages);
        messagesArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messagesList);
        messages.setAdapter(messagesArrayAdapter);
    }

    // Create message form
    public void messageForm(View view) {
        Intent intent = new Intent(this, MessageFormActivity.class);
        startActivityForResult(intent, MESSAGE_FORM_REQUEST_CODE);
    }

    // On message form submit
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MESSAGE_FORM_REQUEST_CODE)
        if (resultCode == RESULT_OK) {
            String uuid = data.getStringExtra("uuid");
            String message = data.getStringExtra("message");
            //TODO: Proper message history
            messagesArrayAdapter.add("To " + uuid + " on " + new Date().toString() + " : " + message);
            // Scroll to bottom message
            messages.setSelection(messages.getCount() - 1);
        }
    }

    // Side drawer buttons
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_chat) {
            // TODO: Create multiple menu's using the drawer

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            //TODO: Share app executable or it's link
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Reavershark/meshdevice-android/releases"));
            startActivity(browserIntent);
        } else if (id == R.id.nav_feedback) {
            //TODO: Proper link
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Reavershark/meshdevice-android"));
            startActivity(browserIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Options menu (overflow)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    // Physical back button
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}