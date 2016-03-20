
package org.vsoft.omni.upi.pspServices;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.vsoft.omni.upi.models.data.Global;
import org.vsoft.omni.upi.models.data.Transaction;
import org.vsoft.omni.upi.models.response.BalanceEnquiryResponse;
import org.vsoft.omni.upi.models.response.CollectTransServiceResponse;
import org.vsoft.omni.upi.models.response.Response;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class BalanceEnquiryService extends Service{

    String url = urlBase+"/balanceEnquiry";

    String type = null;
    String subType = null;
    String code = null;
    String ki = null;
    String encryptedBase64String = null;
    String transactionId = null;
    Transaction transaction = null;

    public BalanceEnquiryService(String type, String subType, String code, String ki, String encryptedBase64String, String transactionId){
        this.type = type;
        this.subType = subType;
        this.code = code;
        this.ki = ki;
        this.encryptedBase64String = encryptedBase64String;
        this.transactionId = transactionId;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Response execute() {

        String json="";
        String payload = null;

        try {

            String payeeName = transaction.getPayeeName();
            String payeeAddress = transaction.getPayeeAddress();
            String payerName = transaction.getPayerName();
            String payerAddress = transaction.getPayerAddress();
            String payerAcAddressType = transaction.getPayerAcAddressType();
            String txnAmount = transaction.getTxnAmount();
            String txnNote = transaction.getTxnNote();

            String ifsc = transaction.getIfsc();
            String acType = transaction.getAcType();
            String acNum = transaction.getAcNum();
            String iin = transaction.getIin();
            String uIdNum = transaction.getuIdNum();
            String mmId = transaction.getMmId();
            String mobNum = transaction.getMobNum();
            String cardNum = transaction.getCardNum();


            payload = "{" +
                    "\"txnId\": \""+transactionId+"\"," +
                    "\"refId\": \""+transactionId+"\"," +
                    "\"payerAddress\": \""+payerAddress+"\"," +
                    "\"payerName\": \""+payerName+"\"," +
                    "\"mobileNumber\": \""+ Global.linkValue+"\"," +
                    "\"geoCode\": \"4515454545\"," +
                    "\"location\": \"Mumbai,Maharashtra\"," +
                    "\"ip\": \"172.16.50.32\"," +
                    "\"type\": \"mob\"," +
                    "\"id\": \"12345678987\"," +
                    "\"os\": \"android\"," +
                    "\"app\": \"MGSAPP\"," +
                    "\"capability\": \"487ER4ER7D4FD5FD8FE8RE\"," +
                    "\"accountAddressType\": \"ACCOUNT\"," +
                    "\"detailsJson\": [" +
                    "{" +
                    "\"ifsc\": \""+ifsc+"\"," +
                    "\"acType\": \""+acType+"\"," +
                    "\"acNum\": \""+acNum+"\"" +
                    "}" +
                    "]," +
                    "\"credType\":\""+type+"\","+
                    "\"credSubType\":\""+subType+"\","+
                    "\"credDataValue\":\""+encryptedBase64String+"\","+
                    "\"credDataCode\":\""+code+"\","+
                    "\"credDataKi\":\""+ki+"\""+
                    "}";


            //Log.e("@Request : ",payload);

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity((new StringEntity(payload, "UTF-8")));
            httpPost.setHeader("Authorization",
                    "Basic bWdzdXBpOmFkbWluQDEyMw=="); // +
            // LoginActivity.token.getToken());
            //httpPost.setHeader("host", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream is = httpEntity.getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            //Log.e("@Response", json);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().create();
        BalanceEnquiryResponse balanceEnquiryResponse =
                gson.fromJson(json.toString(), BalanceEnquiryResponse.class);

        return balanceEnquiryResponse;

    }
}
