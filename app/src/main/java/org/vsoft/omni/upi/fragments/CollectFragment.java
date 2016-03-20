package org.vsoft.omni.upi.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.vsoft.omni.upi.R;
import org.vsoft.omni.upi.interfaces.OnTaskCompleted;
import org.vsoft.omni.upi.models.data.Credentials;
import org.vsoft.omni.upi.models.data.Transaction;
import org.vsoft.omni.upi.models.response.CollectTransServiceResponse;
import org.vsoft.omni.upi.models.response.ListAccountBankServiceResponse;
import org.vsoft.omni.upi.models.response.PayTransServiceResponse;
import org.vsoft.omni.upi.models.response.Response;
import org.vsoft.omni.upi.utils.CredUtil;
import org.vsoft.omni.upi.models.data.Global;
import org.vsoft.omni.upi.utils.HttpUtil;
import org.vsoft.omni.upi.utils.RequestCodeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CollectFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CollectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectFragment extends Fragment implements OnTaskCompleted {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<String> fromAddrList = null;
    private ArrayList<String> toAccList = null;
    Transaction transaction = null;
    // private OnFragmentInteractionListener mListener;

    public CollectFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_collect, container, false);

        android.support.v7.widget.AppCompatSpinner fromAddr =
                (android.support.v7.widget.AppCompatSpinner) view.findViewById(R.id.collect_to_text);

        Global.collectContext = getContext();
        Global.collectToAddr = fromAddr;

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "icomoon.ttf");
        Button qrcode = (Button) view.findViewById(R.id.collect_qrcode_button);
        qrcode.setTypeface(typeface);
        qrcode.setText(String.valueOf((char) 0xe908));

        Button contacts = (Button) view.findViewById(R.id.collect_contacts_button);
        contacts.setTypeface(typeface);
        contacts.setText(String.valueOf((char) 0xe907));

        Button button = (Button) view.findViewById(R.id.proceed_collect_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                collect();
            }
        });

        Button button1 = (Button) view.findViewById(R.id.cancel_collect_button);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppCompatSpinner fromAccount = (AppCompatSpinner)getActivity().findViewById(R.id.collect_to_text);
                fromAccount.setSelection(0);
                EditText amount = (EditText)getActivity().findViewById(R.id.collect_amount_text);
                amount.setText("");
                EditText purpose = (EditText)getActivity().findViewById(R.id.collect_purpose_text);
                purpose.setText("");
                EditText payeeAddress = (EditText)getActivity().findViewById(R.id.collect_from_text);
                payeeAddress.setText("");
            }
        });

        return view;
    }

    public void collect(){

        View view = getActivity().findViewById(R.id.layoutCollectProgress);
        view.setVisibility(View.VISIBLE);

        View viewPay = getActivity().findViewById(R.id.collectLayout);
        viewPay.setVisibility(View.GONE);

        transaction = new Transaction();
        transaction.setPayeeName("");
        transaction.setPayerAcAddressType(Global.payerAcAddressType);
        transaction.setPayerAddress(Global.payerAddress);
        transaction.setPayerName(Global.payerName);
        transaction.setMobNum(Global.linkValue);

        AppCompatSpinner toAccount = (AppCompatSpinner)getActivity().findViewById(R.id.collect_to_text);
        String accountInfo = toAccount.getSelectedItem().toString();
        String accountNo = (accountInfo.split("/"))[0].replace(" ", "");
        String ifsc = (accountInfo.split("/"))[1].replace(" ", "");

        ArrayList<HashMap<String,String>> accounts = Global.payerAccountsDetails;
        Iterator accountListIterator = accounts.listIterator();
        while(accountListIterator.hasNext()){
            HashMap<String,String> account = (HashMap<String,String>)accountListIterator.next();
            String maskedAccNumber = account.get("maskedAccNumber");
            if(maskedAccNumber.equals(accountNo)){
                transaction.setIfsc(account.get("ifsc"));
                transaction.setIin(account.get("iin"));
                transaction.setAcType("SAVINGS");
                transaction.setAcNum(account.get("accRefNumber"));
                break;
            }
        }

        EditText amount = (EditText)getActivity().findViewById(R.id.collect_amount_text);
        transaction.setTxnAmount(amount.getText().toString());

        EditText purpose = (EditText)getActivity().findViewById(R.id.collect_purpose_text);
        transaction.setTxnNote(purpose.getText().toString());

        EditText payeeAddress = (EditText)getActivity().findViewById(R.id.collect_from_text);
        transaction.setPayeeAddress(payeeAddress.getText().toString());

        Intent intent = CredUtil.getCommonLibraryIntent(getActivity(),null,null,CredUtil.transactionId,null,null);
        startActivityForResult(intent, RequestCodeUtil.COLLECT_SERVICE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent responseIntent) {
        super.onActivityResult(requestCode, resultCode, responseIntent);

        if (requestCode == RequestCodeUtil.COLLECT_SERVICE) {

            Credentials credentials = CredUtil.getCredentials((HashMap < String, String >)responseIntent.getSerializableExtra("credBlocks"));
            String subType = credentials.getSubType();
            String type = credentials.getType();
            String ki = credentials.getKi();
            String code = credentials.getCode();
            String encryptedBase64String = credentials.getEncryptedBase64String();

            HttpUtil httpUtil = new HttpUtil(getActivity().getApplicationContext(), this,
                    type,subType,code,ki,encryptedBase64String, CredUtil.getTransactionId(), transaction, requestCode);
            httpUtil.execute();

        }

    }

    @Override
    public void onTaskCompleted(Response response, int requestCode) {
        View view = getActivity().findViewById(R.id.layoutCollectProgress);
        view.setVisibility(View.GONE);

        View viewPay = getActivity().findViewById(R.id.collectLayout);
        viewPay.setVisibility(View.VISIBLE);

        if(RequestCodeUtil.COLLECT_SERVICE == requestCode) {
            int duration = Toast.LENGTH_SHORT;

            CollectTransServiceResponse collectTransServiceResponse = ((CollectTransServiceResponse) response);
            String resultDesc = collectTransServiceResponse.getResultDesc();

            Toast msg = Toast.makeText(getActivity().getApplicationContext(), resultDesc, Toast.LENGTH_LONG);
            msg.show();

            if(collectTransServiceResponse.getResult().toUpperCase().equals("SUCCESS")){
                AppCompatSpinner fromAccount = (AppCompatSpinner)getActivity().findViewById(R.id.collect_to_text);
                fromAccount.setSelection(0);
                EditText amount = (EditText)getActivity().findViewById(R.id.collect_amount_text);
                amount.setText("");
                EditText purpose = (EditText)getActivity().findViewById(R.id.collect_purpose_text);
                purpose.setText("");
                EditText payeeAddress = (EditText)getActivity().findViewById(R.id.collect_from_text);
                payeeAddress.setText("");
            }
        }
    }

    @Override
    public void onCLServiceInitialized(int requestCode) {
    }


}