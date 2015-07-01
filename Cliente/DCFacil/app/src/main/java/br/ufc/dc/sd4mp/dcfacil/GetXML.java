package br.ufc.dc.sd4mp.dcfacil;

import android.os.AsyncTask;

import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by 374175 on 29/06/15.
 */
public class GetXML extends AsyncTask<String, Void, Void> {

    protected Void doInBackground(String... url_) {
        URL url = null;
        //        "http://lia.ufc.br/~felipe.alb/XML/arquivos.xml"
        try {
            url = new URL(url_[0]);
            InputSource i_S = new InputSource(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //create the new connection

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //set up some things on the connection

        try {
            urlConnection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        urlConnection.setDoOutput(true);

        //and connect!

        try {
            urlConnection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //set the path where we want to save the file
        //in this case, going to save it on the root directory of the
        //sd card.

        File SDCardRoot = new File("/mnt/sdcard/" + "dcfacil/");

        //create a new file, specifying the path, and the filename
        //which we want to save the file as.

        File file = new File(SDCardRoot, "superxml.xml");

        //this will be used to write the downloaded data into the file we created

        FileOutputStream fileOutput = null;
        try {
            fileOutput = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //this will be used in reading the data from the internet

        InputStream inputStream = null;
        try {
            inputStream = urlConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

//this is the total size of the file

        int totalSize = urlConnection.getContentLength();

//variable to store total downloaded bytes

        int downloadedSize = 0;

//create a buffer...

        byte[] buffer = new byte[1024];

        int bufferLength = 0; //used to store a temporary size of the buffer

//now, read through the input buffer and write the contents to the file

        try {
            while ((bufferLength = inputStream.read(buffer)) > 0)

            {

    //add the data in the buffer to the file in the file output stream (the file on the sd card

                fileOutput.write(buffer, 0, bufferLength);

    //add up the size so we know how much is downloaded

                downloadedSize += bufferLength;

                int progress = (int) (downloadedSize * 100 / totalSize);

    //this is where you would do something to report the prgress, like this maybe

    //updateProgress(downloadedSize, totalSize);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//close the output stream when done

        try {
            fileOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
