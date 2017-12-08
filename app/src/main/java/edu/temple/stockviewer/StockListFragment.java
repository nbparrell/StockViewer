package edu.temple.stockviewer;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StockListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StockListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ArrayList mParam1 = new ArrayList();
    private String mParam2;

    StockListInterface sli;
    public StockListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StockListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StockListFragment newInstance(ArrayList param1, String param2) {
        StockListFragment fragment = new StockListFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getStringArrayList(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View slFrag = inflater.inflate(R.layout.fragment_stock_list, container, false);

        ListView lst = (ListView) slFrag.findViewById(R.id.stockListView);
        StockListAdapter stlAdapter = new StockListAdapter(mParam1, getActivity(), lst);
        lst.setAdapter(stlAdapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                sli.stockClick(position);
            }
        });

        return slFrag;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof StockListInterface) {
            sli = (StockListInterface) context;
        }
    }

    public interface StockListInterface{
        public void stockClick(int i);
    }

}
