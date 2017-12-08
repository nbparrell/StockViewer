package edu.temple.stockviewer;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchBarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchBarFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragComSBar win;

    public SearchBarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchBarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchBarFragment newInstance(String param1, String param2) {
        SearchBarFragment fragment = new SearchBarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View sFrag = inflater.inflate(R.layout.fragment_search_bar, container, false);

        Button btnAdd = sFrag.findViewById(R.id.Add);
        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText Name = (EditText)sFrag.findViewById(R.id.stockSearchBar);
                String name = Name.getText().toString();
                win.sBarClick(name);
                Toast.makeText(view.getContext(),"This runs", Toast.LENGTH_LONG).show();
            }
        });

        return sFrag;

    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof FragComSBar){
            win = (FragComSBar) context;
        }
    }


    public interface FragComSBar {
        void sBarClick(String x);
    }

}
