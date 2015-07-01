package br.ufc.dc.sd4mp.dcfacil;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.app.Fragment;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;


    private CharSequence mTitle;
    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavItems.add(new NavItem("Início", "Tela inicial", R.drawable.icone_inicial));
        mNavItems.add(new NavItem("Calendário", "Eventos do departamento", R.drawable.icone_calendario));
        mNavItems.add(new NavItem("Notícias", "Notícias do departmento", R.drawable.icone_noticias));
        mNavItems.add(new NavItem("Hoje no RU", "Cardápio do RU", R.drawable.icone_cardapio));
        mNavItems.add(new NavItem("Sobre", "Informações do aplicativo", R.drawable.icone_sobre));

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });

        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        try {
            getXML();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupDrawer(){
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("MENU");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    private void selectItemFromDrawer(int position) {
        Fragment fragment = null;

        switch(position){
            case(0):
                fragment = new inicio_Fragment();
                break;
            case(1):
                fragment = new calendario_Fragment();
                break;
            case(2):
                fragment = new noticia_Fragment();
                break;
            case(3):
                fragment = new cardapio_Fragment();
                break;
            case(4):
                fragment = new sobre_Fragment();
                break;
        }


        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        mDrawerList.setItemChecked(position, true);
        setTitle(mNavItems.get(position).mTitle);

        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        Fragment objFragment = new inicio_Fragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, objFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getXML() throws IOException {
        URL url = null;
        try {
            url = new URL("http://lia.ufc.br/~felipe.alb/XML/arquivos.xml");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

//create the new connection

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

//set up some things on the connection

        try {
            urlConnection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        urlConnection.setDoOutput(true);

//and connect!

        try {
            urlConnection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

//set the path where we want to save the file

//in this case, going to save it on the root directory of the

//sd card.

        File SDCardRoot = new File("/sdcard/" + "dcfacil/");

//create a new file, specifying the path, and the filename

//which we want to save the file as.

        File file = new File(SDCardRoot, "superxml.xml");

//this will be used to write the downloaded data into the file we created

        FileOutputStream fileOutput = null;
        try {
            fileOutput = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//this will be used in reading the data from the internet

        InputStream inputStream = null;
        try {
            inputStream = urlConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

//this is the total size of the file

        int totalSize = urlConnection.getContentLength();

//variable to store total downloaded bytes

        int downloadedSize = 0;

//create a buffer...

        byte[] buffer = new byte[1024];

        int bufferLength = 0; //used to store a temporary size of the buffer

//now, read through the input buffer and write the contents to the file

        while ((bufferLength = inputStream.read(buffer)) > 0)

        {

//add the data in the buffer to the file in the file output stream (the file on the sd card

            fileOutput.write(buffer, 0, bufferLength);

//add up the size so we know how much is downloaded

            downloadedSize += bufferLength;

            int progress = (int) (downloadedSize * 100 / totalSize);

//this is where you would do something to report the prgress, like this maybe

//updateProgress(downloadedSize, totalSize);

        }

//close the output stream when done

        try {
            fileOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
