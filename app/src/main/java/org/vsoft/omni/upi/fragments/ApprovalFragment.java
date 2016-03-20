package org.vsoft.omni.upi.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.vsoft.omni.upi.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ApprovalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ApprovalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApprovalFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

    public ApprovalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApprovalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApprovalFragment newInstance(String param1, String param2) {
        ApprovalFragment fragment = new ApprovalFragment();
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
        View view = inflater.inflate(R.layout.fragment_approval, container, false);

        /*
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "icomoon.ttf");

        Button approve1 = (Button) view.findViewById(R.id.approve_button1);
        approve1.setTypeface(typeface);
        approve1.setText(String.valueOf((char) 0xf122)+String.valueOf(" "+R.string.approveButton));

        Button reject1 = (Button) view.findViewById(R.id.reject_button1);
        reject1.setTypeface(typeface);
        reject1.setText(String.valueOf((char) 0xf122)+String.valueOf(" "+R.string.rejectButton));

        Button approve2 = (Button) view.findViewById(R.id.approve_button2);
        approve2.setTypeface(typeface);
        approve2.setText(String.valueOf((char) 0xf122)+String.valueOf(" "+R.string.approveButton));

        Button reject2 = (Button) view.findViewById(R.id.reject_button2);
        reject2.setTypeface(typeface);
        reject2.setText(String.valueOf((char) 0xf122)+String.valueOf(" "+R.string.rejectButton));
        */

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
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
    }*/
}
