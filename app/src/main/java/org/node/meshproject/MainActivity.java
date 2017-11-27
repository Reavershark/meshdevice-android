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
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Messages
    private static final int MESSAGE_FORM_REQUEST_CODE = 0;
    private static final int MANAGE_REQUEST_CODE = 1;
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
            String sender = data.getStringExtra("sender");
            // TODO: Proper message history
            messagesArrayAdapter.add("From " + sender + " to " + uuid + " on " + new Date().toString() + " : " + message);
            // Scroll to bottom message
            messages.setSelection(messages.getCount() - 1);

            // TODO: HTTP POST
            Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();
        }
    }

    // Side drawer buttons
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // TODO: Create actual navigation menu's using the drawer
        if (id == R.id.nav_chat) {

        } else if (id == R.id.nav_manage) {
            // TODO: User settings
        } else if (id == R.id.nav_share) {
            // TODO: Share actual app form /data/app/org.node.meshproject-1.apk
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Reavershark/meshdevice-android/releases"));
            startActivity(browserIntent);
        } else if (id == R.id.nav_feedback) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Reavershark/meshdevice-android/issues"));
            startActivity(browserIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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