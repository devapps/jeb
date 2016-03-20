package org.vsoft.omni.upi.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.vsoft.omni.upi.R;
import org.vsoft.omni.upi.activities.HomeActivity;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MoreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoreFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

    public MoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoreFragment newInstance(String param1, String param2) {
        MoreFragment fragment = new MoreFragment();
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
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        Spinner languageList = (Spinner) view.findViewById(R.id.languageSelection);
        ArrayList<String> languageListArray = new ArrayList<String>();

        Locale currentLocale = getResources().getConfiguration().locale;
        String language = currentLocale.getLanguage().toLowerCase();

        languageListArray.add(getResources().getString(R.string.language_en));
        languageListArray.add(getResources().getString(R.string.language_hi));
        languageListArray.add(getResources().getString(R.string.language3));
        languageListArray.add(getResources().getString(R.string.language4));

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, languageListArray);
        languageList.setAdapter(adapter);

        if(language.equals("hi")) {
            languageList.setSelection(1);
        }

        if(language.equals("en")) {
            languageList.setSelection(0);
        }

        languageList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                String language = "en";

                switch (arg2) {
                    case 1:
                        //Hindi
                        language = "hi";
                        break;

                    default:
                        //English
                        language = "en";
                        break;
                }

                Locale currentLocale = getResources().getConfiguration().locale;
                String currentLanguage = currentLocale.getLanguage().toLowerCase();

                if(!currentLanguage.equals(language)) {
                    Locale myLocale = new Locale(language);
                    Resources res = getActivity().getBaseContext().getResources();
                    DisplayMetrics dm = res.getDisplayMetrics();
                    Configuration conf = res.getConfiguration();
                    conf.locale = myLocale;

                    res.updateConfiguration(conf, dm);
                    getActivity().getBaseContext().getResources().updateConfiguration(
                            getActivity().getBaseContext().getResources().getConfiguration(),
                            getActivity().getBaseContext().getResources().getDisplayMetrics());

                    Intent home = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
                    startActivity(home);

                    getActivity().finish();
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        return view;
    }

    /*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
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
        void onFragmentInteraction(Uri uri);
    }
    */
}
