package br.ufc.dc.sd4mp.dcfacil;

//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import DAO.NoticiaDAO;
import Modelo.Noticia;

import static android.widget.AdapterView.*;

/**
 * Created by jonathan on 03/06/15.
 */
public class noticia_Fragment extends Fragment {
    View v;
    ListView lista;
    NoticiaDAO dao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstance){
        v = inflater.inflate(R.layout.noticia_layout,container,false);
        lista = (ListView) v.findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        List<Noticia> dados;

        lista.setAdapter(adapter);
        try {
            dao = new NoticiaDAO(getActivity());
            dados = dao.list();
            ArrayList<String> not = new ArrayList<String>();

            for (int i = 0; i < dados.size(); i++) {
                adapter.add(dados.get(i).getTitulo());
            }
            adapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        lista.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Noticia noticia;
                dao = new NoticiaDAO(getActivity());
                noticia = null;
                noticia = dao.procurarPorTitulo(lista.getItemAtPosition(i).toString());
                if (noticia != null) {
                    iniciarLink(noticia);
                }
                else {
                    Toast.makeText(getActivity(), "Erro ao capturar Link", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    private void iniciarLink(Noticia noticia){
        String url = noticia.getLink().replace(" ","");
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

}
