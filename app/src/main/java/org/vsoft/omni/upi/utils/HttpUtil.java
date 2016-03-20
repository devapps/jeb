package org.vsoft.omni.upi.utils;

import android.os.AsyncTask;
import android.content.Context;

import org.vsoft.omni.upi.interfaces.OnTaskCompleted;
import org.vsoft.omni.upi.models.data.Credentials;
import org.vsoft.omni.upi.models.data.Data;
import org.vsoft.omni.upi.models.data.Transaction;
import org.vsoft.omni.upi.models.response.Response;
import org.vsoft.omni.upi.pspServices.BalanceEnquiryService;
import org.vsoft.omni.upi.pspServices.CollectService;
import org.vsoft.omni.upi.pspServices.ListAccountBankService;
import org.vsoft.omni.upi.pspServices.ListKeysService;
import org.vsoft.omni.upi.pspServices.MobileRegistrationService;
import org.vsoft.omni.upi.pspServices.PayService;

public class HttpUtil extends AsyncTask<Object,Object,Object>{

    public String urlBase  = "http://103.14.161.149:12001/UpiService";
    private OnTaskCompleted callback;

    Context applicationContext = null;
    Response response = null;

    String type = null;
    String subType = null;
    String code = null;
    String ki = null;
    String encryptedBase64String = null;
    String transactionId = null;
    Data data = null;

    int requestCode = RequestCodeUtil.DEFAULT;

    public HttpUtil(Context applicationContext, OnTaskCompleted onTaskCompleted){
        this.applicationContext = applicationContext;
        this.callback = onTaskCompleted;
    }

    public HttpUtil(Context         applicationContext,
                    OnTaskCompleted onTaskCompleted,
                    Credentials     credentials,
                    String          transactionId,
                    Data            data,
                    int             requestCode){
        this.applicationContext = applicationContext;
        this.type = credentials.getType();
        this.subType =  credentials.getSubType();
        this.code = credentials.getCode();
        this.ki = credentials.getKi();
        this.encryptedBase64String = credentials.getEncryptedBase64String();
        this.transactionId = transactionId;
        this.requestCode = requestCode;
        this.data = data;
        this.callback = onTaskCompleted;
    }

    public HttpUtil(Context applicationContext,
                    OnTaskCompleted onTaskCompleted,
                    String type,
                    String subType,
                    String code,
                    String ki,
                    String encryptedBase64String,
                    String transactionId,
                    Data   data,
                    int    requestCode){
        this.applicationContext = applicationContext;
        this.type = type;
        this.subType = subType;
        this.code = code;
        this.ki = ki;
        this.encryptedBase64String = encryptedBase64String;
        this.transactionId = transactionId;
        this.data = data;
        this.requestCode = requestCode;
        this.callback = onTaskCompleted;
    }

    @Override
    protected String doInBackground(Object... params){

        //try {
        //    String operation = params[0];
            //if(operation.equals("otpService")){
                //OtpService otpService = new OtpService();


        if(RequestCodeUtil.REGISTER_MOBILE==requestCode) {
            MobileRegistrationService mobileRegistrationService =
                    new MobileRegistrationService(type, subType, code, ki, encryptedBase64String, transactionId);
            mobileRegistrationService.setTransaction((Transaction) data);
            response = mobileRegistrationService.execute();

            return "Success"; // ((PayTransServiceResponse)response).getResultDesc();
        }
        if(RequestCodeUtil.PAY_SERVICE==requestCode) {
            PayService payService = new PayService(type, subType, code, ki, encryptedBase64String, transactionId);
            payService.setTransaction((Transaction) data);
            response = payService.execute();

            return "Success"; // ((PayTransServiceResponse)response).getResultDesc();
        }
        if(RequestCodeUtil.COLLECT_SERVICE==requestCode) {
            CollectService collectService = new CollectService(type, subType, code, ki, encryptedBase64String, transactionId);
            collectService.setTransaction((Transaction) data);
            response = collectService.execute();

            return "Success"; // ((PayTransServiceResponse)response).getResultDesc();
        }
        if(RequestCodeUtil.LIST_ACCOUNT_SERVICE==requestCode) {
            ListAccountBankService listAccountBankService =
                    new ListAccountBankService(type, subType, code, ki, encryptedBase64String, transactionId);
            response = listAccountBankService.execute();

            return "Success"; //((ListAccountBankServiceResponse) response).getAccountList().toString();
        }
        if(RequestCodeUtil.BALANCE_ENQUIRY==requestCode) {
            BalanceEnquiryService balanceEnquiryService =
                    new BalanceEnquiryService(type, subType, code, ki, encryptedBase64String, transactionId);
            balanceEnquiryService.setTransaction((Transaction) data);
            response = balanceEnquiryService.execute();

            return "Success"; //((ListAccountBankServiceResponse) response).getAccountList().toString();
        }
        if(RequestCodeUtil.LIST_KEYS_FOR_LIST_ACCOUNT_SERVICE==requestCode) {
            ListKeysService listKeysService =
                    new ListKeysService(type, subType, code, ki, encryptedBase64String, transactionId);
            response = listKeysService.execute();

            return "Success"; //((ListAccountBankServiceResponse) response).getAccountList().toString();
        }
            //}
        //}
        //catch(Exception e){
        //    Log.d(HttpUtil.class.getSimpleName(),e.getMessage());
        //}

        return null;
    }

    protected void onPostExecute(Object o){
        this.callback.onTaskCompleted(response, requestCode);
    }

}
