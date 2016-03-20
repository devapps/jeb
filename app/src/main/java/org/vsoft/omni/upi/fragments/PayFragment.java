package org.vsoft.omni.upi.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

//import org.npci.upi.security.services.ServiceConnectionStatusNotifier;
import org.vsoft.omni.upi.R;
import org.vsoft.omni.upi.interfaces.OnTaskCompleted;
import org.vsoft.omni.upi.models.data.Credentials;
import org.vsoft.omni.upi.models.data.Transaction;
import org.vsoft.omni.upi.models.response.ListAccountBankServiceResponse;
import org.vsoft.omni.upi.models.response.ListKeysServiceResponse;
import org.vsoft.omni.upi.models.response.PayTransServiceResponse;
import org.vsoft.omni.upi.models.response.Response;
import org.vsoft.omni.upi.utils.CLServiceUtil;
import org.vsoft.omni.upi.utils.CredUtil;
import org.vsoft.omni.upi.models.data.Global;
import org.vsoft.omni.upi.utils.HttpUtil;
import org.vsoft.omni.upi.utils.RequestCodeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import android.support.v7.widget.AppCompatSpinner;

public class PayFragment extends Fragment implements OnTaskCompleted{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<String> fromAddrList = null;
    private ArrayList<String> toAccList = null;

    CLServiceUtil clServiceUtil = null;

    Transaction transaction = null;

    //private OnFragmentInteractionListener mListener;

    public PayFragment(){
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
        View view = inflater.inflate(R.layout.fragment_pay, container, false);

        Button button = (Button) view.findViewById(R.id.proceed_pay_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               pay();
            }
        });

        Button button1 = (Button) view.findViewById(R.id.cancel_pay_button);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppCompatSpinner fromAccount = (AppCompatSpinner)getActivity().findViewById(R.id.pay_from_text);
                fromAccount.setSelection(0);
                EditText amount = (EditText)getActivity().findViewById(R.id.pay_amount_text);
                amount.setText("");
                EditText purpose = (EditText)getActivity().findViewById(R.id.pay_purpose_text);
                purpose.setText("");
                EditText payeeAddress = (EditText)getActivity().findViewById(R.id.pay_payee_va_text);
                payeeAddress.setText("");
            }
        });

        AppCompatSpinner fromAddr = (AppCompatSpinner) getActivity().findViewById(R.id.pay_from_text);
        Global.payContext = getContext();
        Global.payfromAddr = fromAddr;

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "icomoon.ttf");
        Button qrcode = (Button) view.findViewById(R.id.pay_qrcode_button);
        qrcode.setTypeface(typeface);
        qrcode.setText(String.valueOf((char) 0xe908));

        Button contacts = (Button) view.findViewById(R.id.pay_contacts_button);
        contacts.setTypeface(typeface);
        contacts.setText(String.valueOf((char) 0xe907));

        //clServiceUtil = new CLServiceUtil(getActivity().getApplicationContext(), RequestCodeUtil.DEFAULT, this);
        //clServiceUtil.init();


        return view;
    }


    public void pay(){

        View view = getActivity().findViewById(R.id.layoutPayProgress);
        view.setVisibility(View.VISIBLE);

        View viewPay = getActivity().findViewById(R.id.payLayout);
        viewPay.setVisibility(View.GONE);

        transaction = new Transaction();
        transaction.setPayeeName("");
        transaction.setPayerAcAddressType(Global.payerAcAddressType);
        transaction.setPayerAddress(Global.payerAddress);
        transaction.setPayerName(Global.payerName);
        transaction.setMobNum(Global.linkValue);

        AppCompatSpinner fromAccount = (AppCompatSpinner)getActivity().findViewById(R.id.pay_from_text);
        String accountInfo = fromAccount.getSelectedItem().toString();
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

        EditText amount = (EditText)getActivity().findViewById(R.id.pay_amount_text);
        transaction.setTxnAmount(amount.getText().toString());

        EditText purpose = (EditText)getActivity().findViewById(R.id.pay_purpose_text);
        transaction.setTxnNote(purpose.getText().toString());

        EditText payeeAddress = (EditText)getActivity().findViewById(R.id.pay_payee_va_text);
        transaction.setPayeeAddress(payeeAddress.getText().toString());

        Intent intent = CredUtil.getCommonLibraryIntent(getActivity(),null,null,CredUtil.transactionId,null,null);
        startActivityForResult(intent, RequestCodeUtil.PAY_SERVICE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent responseIntent) {
        super.onActivityResult(requestCode, resultCode, responseIntent);

        /* 101 - pay */
        if (requestCode == RequestCodeUtil.PAY_SERVICE) {

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
        View view = getActivity().findViewById(R.id.layoutPayProgress);
        view.setVisibility(View.GONE);

        View viewPay = getActivity().findViewById(R.id.payLayout);
        viewPay.setVisibility(View.VISIBLE);

        if(RequestCodeUtil.PAY_SERVICE == requestCode) {
            int duration = Toast.LENGTH_SHORT;

            PayTransServiceResponse payTransServiceResponse = ((PayTransServiceResponse) response);
            String resultDesc = payTransServiceResponse.getResultDesc();

            Toast msg = Toast.makeText(getActivity().getApplicationContext(), resultDesc, Toast.LENGTH_LONG);
            msg.show();

            if(payTransServiceResponse.getResult().toUpperCase().equals("SUCCESS")){
                AppCompatSpinner fromAccount = (AppCompatSpinner)getActivity().findViewById(R.id.pay_from_text);
                fromAccount.setSelection(0);
                EditText amount = (EditText)getActivity().findViewById(R.id.pay_amount_text);
                amount.setText("");
                EditText purpose = (EditText)getActivity().findViewById(R.id.pay_purpose_text);
                purpose.setText("");
                EditText payeeAddress = (EditText)getActivity().findViewById(R.id.pay_payee_va_text);
                payeeAddress.setText("");
            }
        }
    }

    @Override
    public void onCLServiceInitialized(int requestCode) {
    }


}
