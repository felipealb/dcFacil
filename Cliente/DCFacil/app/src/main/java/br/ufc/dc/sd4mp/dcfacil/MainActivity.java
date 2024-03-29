package br.ufc.dc.sd4mp.dcfacil;

import android.app.Activity;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
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
import android.widget.Toast;

import java.util.ArrayList;

import Controle.LeitorXML;
import DAO.CardapioDAO;
import DAO.EstagioDAO;
import DAO.NoticiaDAO;
import Modelo.Noticia;


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
    private LeitorXML leitor;


    private CharSequence mTitle;
    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavItems.add(new NavItem("Início", "Tela inicial", R.drawable.icone_inicial));
        mNavItems.add(new NavItem("Calendário", "Eventos do departamento", R.drawable.icone_calendario));
        mNavItems.add(new NavItem("Notícias", "Notícias do departmento", R.drawable.icone_noticias));
        mNavItems.add(new NavItem("Estágios/Bolsas", "Estágios e bolsas", R.drawable.icone_estagio));
        mNavItems.add(new NavItem("Hoje no RU", "Cardápio do RU", R.drawable.icone_cardapio));
        mNavItems.add(new NavItem("Sobre", "Informações do departamento", R.drawable.icone_sobre));

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
    public void onResume(){
        super.onResume();

        if(verificaConexao()) {
            NoticiaDAO daoNoticia = new NoticiaDAO(this);
            daoNoticia.deletarTudo();
            new LeitorXML(this).execute("http://www.lia.ufc.br/~felipe.alb/XML/news.xml");
            CardapioDAO daoCardapio = new CardapioDAO(this);
            daoCardapio.deletarTudo();
            new LeitorXML(this).execute("http://www.lia.ufc.br/~felipe.alb/XML/cardapio.xml");
            //EstagioDAO daoEstagio = new EstagioDAO(this);
            //daoEstagio.deletarTudo();
            //new LeitorXML(this).execute("http://lia.ufc.br/~felipe.alb/XML/estagio.xml");
        }
        else{
            Toast.makeText(this,"Sem Conexão!",Toast.LENGTH_SHORT).show();
        }

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
                fragment = new estagio_Fragment();
                break;
            case(4):
                fragment = new cardapio_Fragment();
                break;
            case(5):
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

    public  boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }
}
