package br.ufc.dc.sd4mp.dcfacil;

//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by jonathan on 03/06/15.
 */
public class cardapio_Fragment extends Fragment {
    View rootView;
    ListView lista;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstance){
        rootView = inflater.inflate(R.layout.cardapio_layout,container,false);
        lista = (ListView) rootView.findViewById(R.id.listView2);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),CardapioOpcoes.class);
                intent.putExtra("DIA",i);
                startActivity(intent);
            }
        });
        return rootView;
    }

}
