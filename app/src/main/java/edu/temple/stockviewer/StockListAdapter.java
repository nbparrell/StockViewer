package edu.temple.stockviewer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by t410 on 12/8/2017.
 */

public class StockListAdapter extends ArrayAdapter {
    private ArrayList dataSet;
    private Context mContext;
    public StockListAdapter(ArrayList data, Context context, ListView lst) {
        super(context, android.R.layout.simple_selectable_list_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View mainRow;
        mainRow = super.getView(position, convertView, parent);
        return mainRow;
    }


}
