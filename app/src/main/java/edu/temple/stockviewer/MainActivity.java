package edu.temple.stockviewer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity implements SearchBarFragment.FragComSBar, StockListFragment.StockListInterface {
    SearchBarFragment sBarFragment;
    StockListFragment sListFragment;
    StockDetailsFragment sDetailsFragment;
    File stockList;
    FileOutputStream stockListOutput;
    FileInputStream stockListInput;
    ArrayList list = new ArrayList();
    int currentIndex = 0;
    String stockListFileName = "StockList";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnNew = findViewById(R.id.button);
        stockList = new File(this.getFilesDir(), stockListFileName);
        try{
            stockListInput = openFileInput(stockListFileName);
        }catch (Exception e){
            e.printStackTrace();
        }
        String buffer = "";
        int c = 0;
        try {
            c = stockListInput.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(c != -1) {
            while ((char) c != '-' && c != -1) {
                buffer = buffer + Character.toString((char) c);
                try {
                    c = stockListInput.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            list.add(currentIndex, buffer);
            currentIndex++;
            buffer = "";
            if(c != -1) {
                try {
                    c = stockListInput.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //list.add(0, "HI");
        if(list.size() > 0) {
            sListFragment = StockListFragment.newInstance(list, "was");
            getFragmentManager().beginTransaction().add(R.id.stockListFragment, sListFragment).commit();
        }else{
            Toast.makeText(this, "No Stocks have been added", Toast.LENGTH_LONG).show();
        }


        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sBarFragment = SearchBarFragment.newInstance("eh","was");
                getFragmentManager().beginTransaction().add(R.id.searchBarFragment, sBarFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public void sBarClick(String x) {
        try {
            stockListOutput = openFileOutput(stockListFileName, Context.MODE_APPEND);
            stockListOutput.write(x.getBytes());
            char newLine = '-';
            stockListOutput.write(newLine);
            list.add(currentIndex, x);
            currentIndex++;
        } catch (IOException e) {
            e.printStackTrace();
        }

        sListFragment = StockListFragment.newInstance(list,"was");
        getFragmentManager().beginTransaction().replace(R.id.stockListFragment, sListFragment).commit();
        getFragmentManager().popBackStack();
        Toast.makeText(this,"Removed",Toast.LENGTH_LONG).show();
    }

    @Override
    public void stockClick(int i) {
        sDetailsFragment = StockDetailsFragment.newInstance("we","we");
        getFragmentManager().beginTransaction().add(R.id.stockDetailsFragment, sDetailsFragment).addToBackStack(null).commit();
        getFragmentManager().beginTransaction().hide(sListFragment).commit();

    }
}
