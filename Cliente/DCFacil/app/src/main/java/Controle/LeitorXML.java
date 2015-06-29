package Controle;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import DAO.NoticiaDAO;
import Modelo.Noticia;

/**
 * Created by jonathan on 28/06/15.
 */
public class LeitorXML extends AsyncTask<String,String,String>{

    public String dadosXML;
    public String tipo;
    Context context;

    public LeitorXML(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        try {

            String link = (String) params[0];
            URL url = new URL(link);

            if(link.contains("news.xml")){
                this.tipo = "noticia";
            }

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String data = null;
            String webPage = "";
            while ((data = reader.readLine()) != null) {
                webPage += data + "\n";
            }
            return webPage;
        }
        catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }
    @Override
    protected void onPostExecute(String result) {
        this.dadosXML = result;
        tratarXML();
    }

    public void tratarXML(){
        if(tipo.equals("noticia")){
            tratarNoticia();
        }
    }

    private void tratarNoticia(){

        String[] dados = dadosXML.split("\n");
        Noticia noticia;
        String aux;
        NoticiaDAO dao = new NoticiaDAO(context);

        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

        try {
            for(int i=0;i<dados.length;i++){
                noticia = new Noticia();
                if(dados[i].contains("<noticia>")){
                    aux = dados[i].replace("<noticia>","");
                    aux = aux.replace("</noticia>", "");
                    noticia.setTitulo(aux);
                    i++;
                    aux = dados[i].replace("<link>","");
                    aux = aux.replace("</link>","");
                    noticia.setLink(aux);
                    noticia.setData(formatador.format(data));
                    dao.create(noticia);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
