package edu.montclair.mobilecomputing.m_alrajab.assignment_1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.Util.getListFromFile;


public class TileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    String key;

    private OnFragmentInteractionListener mListener;

    public TileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null) {

            key = getArguments().getString("Name");
        }
           }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // key=this.getArguments().getString("Name");

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tile, container, false);
        ListView ls=(ListView)view.findViewById(R.id.list_frg);

       // Log.d("User Name ",key);
        System.out.print("User NAme :"+key);
      // savedInstanceState.getString("userName");

        //Get list from files
        ls.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,
                getListFromFile(getContext(),"Title")));
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String itemValue=(String)adapterView.getItemAtPosition(i);

                mListener.onFragmentInteraction(getListFromFile(getContext(),itemValue)[0]);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String  uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String uri);
    }
}
