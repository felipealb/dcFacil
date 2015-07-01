package br.ufc.dc.sd4mp.dcfacil;

//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import DAO.NoticiaDAO;
import Modelo.Noticia;

/**
 * Created by jonathan on 03/06/15.
 */
public class noticia_Fragment extends Fragment {
    View rootView;
    Context context;
    ListView lista;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstance){
        View v = inflater.inflate(R.layout.noticia_layout,container,false);
        lista = (ListView) v.findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        List<Noticia> dados;

        lista.setAdapter(adapter);
        try {
            NoticiaDAO dao = new NoticiaDAO(getActivity());
            dados = dao.list();
            ArrayList<String> not = new ArrayList<String>();

            for (int i = 0; i < dados.size(); i++) {
                adapter.add(dados.get(i).getTitulo());
            }
            //String[] teste = new String[]{"um","dois","tres"};
            adapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        rootView = inflater.inflate(R.layout.noticia_layout,container,false);
        return rootView;
    }

    public void onClick(View view){


    }

}
