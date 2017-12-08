package edu.temple.stockviewer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import java.io.File;

public class StockService extends Service {

    private static final String TAG = StockService.class.getSimpleName();

    public StockService(){
    }

    public StockService(Context context) {
        File stockList = new File(context.getFilesDir(), "StockList");
        //File stockDetails = new File(context.getFilesDir(), "StockDetails");

        while(true){
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            BufferedReader br;
            String[] stockName;
            stockName = new String[100];

            try {
                br = new BufferedReader(new FileReader(stockList));
                String buffer = null;
                try {
                    buffer = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for(int i = 0; buffer != null; i++ ){
                    stockName[i] = buffer;
                    try {
                        buffer = br.readLine();
                    } catch (IOException e) {
                        buffer = null;
                        e.printStackTrace();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            for(int i = 0; stockName[i] != null; i++) {
                String url = "http://dev.markitondemand.com/MODApis/Api/v2/Quote/json/?symbol=" + stockName[i];
                String jsonStr = makeServiceCall(url);
                JSONObject sDetails = null;
                File stockDetails = new File(context.getFilesDir(), stockName[i]);
                if(jsonStr != null){
                    try{
                        sDetails = new JSONObject(jsonStr);

                    }catch (final JSONException e){

                    }
                    BufferedWriter wr;
                    try {
                        wr = new BufferedWriter(new FileWriter(stockDetails));
                        try {
                            wr.write(sDetails.getString("Name"));
                            wr.write(sDetails.getString("Symbol"));
                            wr.write(sDetails.getString("LastPrice"));
                            wr.write(sDetails.getString("Change"));
                            wr.write(sDetails.getString("ChangePercent"));
                            wr.write(sDetails.getString("MSDate"));
                            wr.write(sDetails.getString("MarketCap"));
                            wr.write(sDetails.getString("Volume"));
                            wr.write(sDetails.getString("ChangeYTD"));
                            wr.write(sDetails.getString("ChangePercentYTD"));
                            wr.write(sDetails.getString("High"));
                            wr.write(sDetails.getString("Low"));
                            wr.write(sDetails.getString("Open"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } catch (IOException e) {
                            e.printStackTrace();
                    }
                }

            }

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String makeServiceCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
