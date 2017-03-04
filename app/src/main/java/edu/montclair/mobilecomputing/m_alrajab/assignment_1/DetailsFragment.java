package edu.montclair.mobilecomputing.m_alrajab.assignment_1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String str;
    String userName;

    public DetailsFragment() {
        // Required empty public constructor
    }

        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            if(getArguments()!=null) {
                str = getArguments().getString("KEY");
               }
            if(getActivity().getIntent().getStringExtra("MSG")!=null)
                str=getActivity().getIntent().getStringExtra("MSG");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_details, container, false);
        ((TextView)view.findViewById(R.id.details_tv)).setText(str);
        return view;
    }




}
