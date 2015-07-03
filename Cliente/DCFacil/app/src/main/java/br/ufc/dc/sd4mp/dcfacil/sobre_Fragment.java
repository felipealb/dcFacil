package br.ufc.dc.sd4mp.dcfacil;

//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by jonathan on 03/06/15.
 */
public class sobre_Fragment extends Fragment {
    View rootView;
    Button btnMapa;
    Button btnSite;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstance){
        rootView = inflater.inflate(R.layout.sobre_layout,container,false);

        btnMapa = (Button) rootView.findViewById(R.id.button);
        btnSite = (Button) rootView.findViewById(R.id.button2);

        btnSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://portal.dc.ufc.br/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),MapaDepartamento.class);
                startActivity(i);
            }
        });

        return rootView;
    }

}
