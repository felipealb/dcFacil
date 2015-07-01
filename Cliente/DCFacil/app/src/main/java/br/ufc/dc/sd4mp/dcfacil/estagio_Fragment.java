package br.ufc.dc.sd4mp.dcfacil;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jonathan on 01/07/15.
 */
public class estagio_Fragment extends Fragment {
    View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstance){
        rootView = inflater.inflate(R.layout.estagio_layout,container,false);
        return rootView;
    }
}
