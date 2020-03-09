package com.cagriorhan.currenyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView chfText;
    TextView usdText;
    TextView jpyText;
    TextView tryText;
    TextView cadText;
    TextView baseText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chfText=findViewById(R.id.chfText);
        usdText=findViewById(R.id.usdText);
        jpyText=findViewById(R.id.jpyText);
        tryText=findViewById(R.id.tryText);
        cadText=findViewById(R.id.cadText);

        baseText=findViewById(R.id.baseText);

    }



    public void getRates(View view){
        DownloadData downloadData=new DownloadData();
        try{
            String url="http://data.fixer.io/api/latest?access_key=301c1b730bb3dbb3c2fc9b7d7f6543dd&format=1";
            downloadData.execute(url);
        }catch (Exception e){

        }


    }

    private class DownloadData extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String result="";
            URL url;
            HttpURLConnection httpURLConnection;
            try {
                url=new URL(strings[0]);
                httpURLConnection=(HttpURLConnection) url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);

                int data=inputStreamReader.read();
                while (data>0){
                    char character= (char) data;
                    result+=character;
                    data=inputStreamReader.read();
                }

                return result;
            }catch (Exception e){
                return null;
            }


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject=new JSONObject(s);
                String base=jsonObject.getString("base");
                String rates=jsonObject.getString("rates");

                JSONObject jsonObject1=new JSONObject(rates);
                String turkishLira=jsonObject1.getString("TRY");
                String chf=jsonObject1.getString("CHF");
                String usd=jsonObject1.getString("USD");
                String jpy=jsonObject1.getString("JPY");
                String cad=jsonObject1.getString("CAD");

                baseText.setText("BASE:"+base);

                tryText.setText("TRY: "+turkishLira);
                chfText.setText("CHF: "+chf);
                usdText.setText("USD: "+usd);
                jpyText.setText("JPY: "+jpy);
                cadText.setText("CAD: "+cad);


            }catch (Exception e){

            }

        }
    }

}

