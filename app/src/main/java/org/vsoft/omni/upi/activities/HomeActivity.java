package org.vsoft.omni.upi.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.vsoft.omni.upi.R;
import org.vsoft.omni.upi.interfaces.OnTaskCompleted;
import org.vsoft.omni.upi.models.data.Transaction;
import org.vsoft.omni.upi.models.response.BalanceEnquiryResponse;
import org.vsoft.omni.upi.models.response.ListAccountBankServiceResponse;
import org.vsoft.omni.upi.models.response.Response;
import org.vsoft.omni.upi.utils.CredUtil;
import org.vsoft.omni.upi.models.data.Global;
import org.vsoft.omni.upi.utils.HttpUtil;
import org.vsoft.omni.upi.utils.RequestCodeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class HomeActivity extends AppCompatActivity  implements OnTaskCompleted {

    Transaction transaction = new Transaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_dashboard)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_pay)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_collect)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_approval)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_history)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_more)));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new HomeActivityFragmentsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 1) {
                    Spinner fromAddr = (Spinner) findViewById(R.id.pay_from_text);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Global.payContext,
                            android.R.layout.simple_spinner_item, Global.payerAccounts);
                    fromAddr.setAdapter(adapter);
                }

                if (tab.getPosition() == 2) {
                    Spinner toAddr = (Spinner) findViewById(R.id.collect_to_text);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Global.collectContext,
                            android.R.layout.simple_spinner_item, Global.payerAccounts);
                    toAddr.setAdapter(adapter);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        Typeface typeface = Typeface.createFromAsset(getAssets(), "icomoon.ttf");

        Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

                finish();
            }
        });

        logoutButton.setTypeface(typeface);
        logoutButton.setText(String.valueOf((char) 0xe616));

        Button micButton = (Button) findViewById(R.id.mic_button);
        micButton.setTypeface(typeface);
        micButton.setText(String.valueOf((char) 0xf461));

        if(Global.payerAccounts==null || Global.payerAccounts.size()==0) {
            Intent intent = CredUtil.getCommonLibraryIntent(this, null, null, CredUtil.transactionId, null, null);
            startActivityForResult(intent, RequestCodeUtil.LIST_ACCOUNT_SERVICE);
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // refresh your views here
        super.onConfigurationChanged(newConfig);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent responseIntent) {
        super.onActivityResult(requestCode, resultCode, responseIntent);

          /* 100 - List Bank Accounts */
        if (requestCode == RequestCodeUtil.LIST_ACCOUNT_SERVICE) {
            CredUtil.getCredentials((HashMap<String, String>) responseIntent.getSerializableExtra("credBlocks"));
            HttpUtil httpUtil = new HttpUtil(getApplicationContext(),
                    this, CredUtil.getCredentials(), CredUtil.getTransactionId(), null,
                    RequestCodeUtil.LIST_ACCOUNT_SERVICE);

            httpUtil.execute();

        }

        if (requestCode == RequestCodeUtil.BALANCE_ENQUIRY) {
            HttpUtil httpUtil = new HttpUtil(getApplicationContext(),
                    this, CredUtil.getCredentials(), CredUtil.getTransactionId(), transaction,
                    RequestCodeUtil.BALANCE_ENQUIRY);

            httpUtil.execute();
        }
    }

    @Override
    public void onTaskCompleted(Response response, int requestCode) {
        if(RequestCodeUtil.LIST_ACCOUNT_SERVICE == requestCode){
            ListAccountBankServiceResponse listAccountBankServiceResponse = (ListAccountBankServiceResponse) response;
            //Log.e("@AccountList ", ((ListAccountBankServiceResponse) response).getAccountList().toString());

            String ifscode = "";
            String accountNo = "";

            Global.payerAccounts.clear();
            Global.payerAccountsDetails = ((ListAccountBankServiceResponse) response).getAccountList();

            ArrayList<HashMap<String,String>> accounts = ((ListAccountBankServiceResponse) response).getAccountList();
            Iterator accountListIterator = accounts.listIterator();
            while(accountListIterator.hasNext()){
                HashMap<String,String> account = (HashMap<String,String>)accountListIterator.next();
                String maskedAccNumber = account.get("maskedAccNumber");
                String ifsc = account.get("ifsc");

                ifscode = account.get("ifsc");
                accountNo = account.get("accRefNumber");

                Global.payerAccounts.add(maskedAccNumber + " / " + ifsc);
            }


            if(Global.payfromAddr!=null) {
                ArrayAdapter<String> payFromAdapter = new ArrayAdapter<String>(Global.payContext,
                        android.R.layout.simple_spinner_item, Global.payerAccounts);
                Global.payfromAddr.setAdapter(payFromAdapter);
            }

            if(Global.collectToAddr!=null) {
                ArrayAdapter<String> collectToAdapter = new ArrayAdapter<String>(Global.collectContext,
                        android.R.layout.simple_spinner_item, Global.payerAccounts);
                Global.collectToAddr.setAdapter(collectToAdapter);
            }

            transaction.setPayeeName("");
            transaction.setPayerAcAddressType(Global.payerAcAddressType);
            transaction.setPayerAddress(Global.payerAddress);
            transaction.setPayerName(Global.payerName);
            transaction.setMobNum(Global.linkValue);
            transaction.setAcNum(accountNo);
            transaction.setIfsc(ifscode);
            transaction.setIin("");
            transaction.setAcType("SAVINGS");
            transaction.setTxnAmount("");
            transaction.setTxnNote("");
            transaction.setPayeeAddress("");

            //Intent intent = CredUtil.getCommonLibraryIntent(this, null, null, CredUtil.transactionId, null, null);
            //startActivityForResult(intent, RequestCodeUtil.BALANCE_ENQUIRY);

        }
        else if (RequestCodeUtil.BALANCE_ENQUIRY == requestCode){
            //BalanceEnquiryResponse listAccountBankServiceResponse = (BalanceEnquiryResponse) response;
            //Log.e("@BalanceDataValue ", ((BalanceEnquiryResponse) response).getBalanceDataValue());
        }
    }

    @Override
    public void onCLServiceInitialized(int requestCode) {
    }
}