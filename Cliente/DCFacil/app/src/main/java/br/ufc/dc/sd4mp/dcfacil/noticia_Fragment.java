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
import android.widget.ListView;

import java.util.List;

import DAO.NoticiaDAO;
import Modelo.Noticia;

/**
 * Created by jonathan on 03/06/15.
 */
public class noticia_Fragment extends Fragment {
    View rootView;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstance){
        ListView lista = (ListView) container.findViewById(R.id.listView);
        List<Noticia> dados;
        NoticiaDAO dao = new NoticiaDAO(getActivity());
        dados = dao.list();

        rootView = inflater.inflate(R.layout.noticia_layout,container,false);
        return rootView;
    }

}
