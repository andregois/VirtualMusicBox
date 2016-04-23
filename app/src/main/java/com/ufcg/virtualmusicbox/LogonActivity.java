package com.ufcg.virtualmusicbox;

import android.content.Intent;
import android.os.StrictMode;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;


public class LogonActivity extends CaptureActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.
                ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_logon);
        ImageView imageViewQRCode = (ImageView) findViewById(R.id.imageView);
        imageViewQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(LogonActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Escaneamento do QR Code");
                integrator.setCaptureActivity(LogonActivity.class);
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });


    }

    public Music[]  downloadData(String input){

        HttpGet http = new HttpGet(input);
        String playList = conectToServer(http);
        try {
            JSONObject json = new JSONObject(playList);
            Log.i(Music.class.getName(), String.valueOf(json));

            Gson gson = new Gson();
            Music[] arr = gson.fromJson(input, Music[].class);
            for (Music m: arr){
                Log.d("cantor: ",m.cantor);
                Log.d("titulo: ",m.titulo);
                Log.d("votos: ",m.votos + "");

            }
            return arr;
        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public String conectToServer( HttpGet httpGet) {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
//        HttpGet httpGet = new HttpGet("https://bugzilla.mozilla.org/rest/bug?assigned_to=lhenry@mozilla.com");
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.e("Failed to download file", "error");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Qr Code escaneado: " + result.getContents(), Toast.LENGTH_LONG).show();
                Intent i = new Intent(LogonActivity.this, MainActivity.class);
                String aux = "https://api.myjson.com/bins/4ojbi";
                Music [] playlist = downloadData(aux);
//                Music[] playlist = downloadData(result.getContents());
                i.putExtra("PLAYLIST", playlist);
                startActivity(i);


//                startActivity(new Intent(this, MainActivity.class));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
