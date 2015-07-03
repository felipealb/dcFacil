package br.ufc.dc.sd4mp.dcfacil;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import Controle.NewAdapter;
import DAO.CardapioDAO;
import Modelo.Cardapio;


public class CardapioOpcoes extends ExpandableListActivity implements ExpandableListView.OnChildClickListener {

    ArrayList<String> groupItem = new ArrayList<String>();
    ArrayList<Object> childItem = new ArrayList<Object>();
    int diaSemana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle dados = getIntent().getExtras();
        this.diaSemana = dados.getInt("DIA");
        //setContentView(R.layout.activity_cardapio_opcoes);

        ExpandableListView expandbleLis = getExpandableListView();
        expandbleLis.setDividerHeight(3);
        expandbleLis.setGroupIndicator(null);
        expandbleLis.setClickable(true);
        

        setGroupData();
        setChildGroupData();

        NewAdapter mNewAdapter = new NewAdapter(groupItem, childItem);
        mNewAdapter
                .setInflater(
                        (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
                        this);
        getExpandableListView().setAdapter(mNewAdapter);
        expandbleLis.setOnChildClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cardapio_opcoes, menu);
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

    public void setGroupData() {
        groupItem.add("Café da manhã");
        groupItem.add("Almoço");
        groupItem.add("Jantar");
    }

    public void setChildGroupData() {
        CardapioDAO dao = new CardapioDAO(this);
        List<Cardapio> lista = new ArrayList<Cardapio>();

        lista = dao.list();
        ArrayList<String> child = new ArrayList<String>();
        child.add(lista.get(diaSemana).getCafe().replace("  ", ""));
        childItem.add(child);

        child = new ArrayList<String>();
        child.add(lista.get(diaSemana).getAlmoco().replace("  ", ""));
        childItem.add(child);

        child = new ArrayList<String>();
        child.add(lista.get(diaSemana).getJanta().replace("  ",""));
        childItem.add(child);

    }
}
