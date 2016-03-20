package org.vsoft.omni.upi.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.vsoft.omni.upi.R;
import org.vsoft.omni.upi.interfaces.OnTaskCompleted;
import org.vsoft.omni.upi.models.data.Credentials;
import org.vsoft.omni.upi.models.data.Global;
import org.vsoft.omni.upi.models.data.Transaction;
import org.vsoft.omni.upi.models.response.MobileRegistrationResponse;
import org.vsoft.omni.upi.models.response.PayTransServiceResponse;
import org.vsoft.omni.upi.models.response.Response;
import org.vsoft.omni.upi.utils.CredUtil;
import org.vsoft.omni.upi.utils.HttpUtil;
import org.vsoft.omni.upi.utils.RequestCodeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SignupActivity extends AppCompatActivity implements OnTaskCompleted {

    Transaction transaction = null;
    EditText firstName = null;
    EditText lastName = null;
    EditText userName = null;
    EditText password = null;
    EditText confirmPassword = null;
    EditText mobileNo = null;
    EditText aadharNo = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().hide();

        firstName = (EditText) findViewById(R.id.signup_first_name);
        lastName = (EditText) findViewById(R.id.signup_last_name);
        userName = (EditText) findViewById(R.id.signup_username);
        password = (EditText) findViewById(R.id.signup_password);
        confirmPassword = (EditText) findViewById(R.id.signup_confirm_password);
        mobileNo = (EditText) findViewById(R.id.signup_mobile_no);
        aadharNo = (EditText) findViewById(R.id.signup_aadhar_no);

        Button proceedButton = (Button) findViewById(R.id.proceed_button);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registerMobile();
            }
        });

        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                firstName.setText("");
                lastName.setText("");
                userName.setText("");
                password.setText("");
                confirmPassword.setText("");
                mobileNo.setText("");
                aadharNo.setText("");

                finish();
            }
        });

    }

    public void registerMobile(){

        View view = findViewById(R.id.layoutSignupProgress);
        view.setVisibility(View.VISIBLE);

        View viewPay = findViewById(R.id.scrollViewSignup);
        viewPay.setVisibility(View.GONE);

        transaction = new Transaction();

        String payerName = firstName.getText().toString()+" "+lastName.getText().toString();

        transaction.setPayeeName("");
        transaction.setPayerAcAddressType(Global.payerAcAddressType);
        transaction.setPayerAddress(Global.payerAddress);
        transaction.setPayerName(payerName);
        transaction.setMobNum(mobileNo.getText().toString());
        transaction.setIfsc("MAPP");
        transaction.setIin("");
        transaction.setAcType("");
        transaction.setAcNum("");
        transaction.setTxnAmount("");
        transaction.setTxnNote("");
        transaction.setPayeeAddress("");

        Intent intent = CredUtil.getCommonLibraryIntent(this, null, null, CredUtil.transactionId, null, null);
        startActivityForResult(intent, RequestCodeUtil.REGISTER_MOBILE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent responseIntent) {
        super.onActivityResult(requestCode, resultCode, responseIntent);

        /* 101 - pay */
        if (requestCode == RequestCodeUtil.REGISTER_MOBILE) {

            Credentials credentials = CredUtil.getCredentials((HashMap < String, String >)responseIntent.getSerializableExtra("credBlocks"));
            String subType = credentials.getSubType();
            String type = credentials.getType();
            String ki = credentials.getKi();
            String code = credentials.getCode();
            String encryptedBase64String = credentials.getEncryptedBase64String();

            HttpUtil httpUtil = new HttpUtil(getApplicationContext(), this,
                    type,subType,code,ki,encryptedBase64String, CredUtil.getTransactionId(), transaction, requestCode);
            httpUtil.execute();

        }

    }

    @Override
    public void onTaskCompleted(Response response, int requestCode) {

        View view = findViewById(R.id.layoutSignupProgress);
        view.setVisibility(View.GONE);
        View viewPay = findViewById(R.id.scrollViewSignup);
        viewPay.setVisibility(View.VISIBLE);

        if(RequestCodeUtil.REGISTER_MOBILE == requestCode) {

            int duration = Toast.LENGTH_SHORT;

            MobileRegistrationResponse mobileRegistrationResponse = ((MobileRegistrationResponse) response);
            String resultDesc = mobileRegistrationResponse.getResultDesc();

            Toast msg = Toast.makeText(getApplicationContext(), resultDesc, Toast.LENGTH_LONG);
            msg.show();

            firstName.setText("");
            lastName.setText("");
            userName.setText("");
            password.setText("");
            confirmPassword.setText("");
            mobileNo.setText("");
            aadharNo.setText("");

            finish();
        }
    }

    @Override
    public void onCLServiceInitialized(int requestCode) {
    }


}
