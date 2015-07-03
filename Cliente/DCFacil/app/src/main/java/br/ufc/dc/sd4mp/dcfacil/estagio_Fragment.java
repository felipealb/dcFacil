package br.ufc.dc.sd4mp.dcfacil;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import DAO.EstagioDAO;
import DAO.NoticiaDAO;
import Modelo.Estagio;
import Modelo.Noticia;

/**
 * Created by jonathan on 01/07/15.
 */
public class estagio_Fragment extends Fragment {
    View rootView;
    ListView lista;
    EstagioDAO dao;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstance){
        rootView = inflater.inflate(R.layout.estagio_layout,container,false);
        lista = (ListView) rootView.findViewById(R.id.listView3);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        List<Estagio> dados;

        /*lista.setAdapter(adapter);
        try {
            dao = new EstagioDAO(getActivity());
            dados = dao.list();
            ArrayList<String> not = new ArrayList<String>();

            for (int i = 0; i < dados.size(); i++) {
                adapter.add(dados.get(i).getLocal());
            }
            adapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }*/

        return rootView;
    }
}
