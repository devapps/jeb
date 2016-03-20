
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
import org.vsoft.omni.upi.models.data.Transaction;
import org.vsoft.omni.upi.models.response.CollectTransServiceResponse;
import org.vsoft.omni.upi.models.response.PayTransServiceResponse;
import org.vsoft.omni.upi.models.response.Response;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class CollectService extends Service{

    String url = urlBase+"/collectTransService";

    String type = null;
    String subType = null;
    String code = null;
    String ki = null;
    String encryptedBase64String = null;
    String transactionId = null;
    Transaction transaction = null;

    public CollectService(String type, String subType, String code, String ki, String encryptedBase64String, String transactionId){
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


            payload = "{"+
                    "\"txnId\":\""+transactionId+"\","+
                    "\"txnNote\":\""+txnNote+"\","+
                    "\"refId\":\""+transactionId+"\","+
                    "\"custRefId\":\""+transactionId+"\","+
                    "\"payerAddress\":\""+payerAddress+"\","+
                    "\"payerName\":\""+payerName+"\","+
                    "\"mobileNumber\":\""+mobNum+"\","+
                    "\"geoCode\":\"123466454\","+
                    "\"location\":\"Mumbai,Maharashtra\","+
                    "\"ip\":\"142.12.26.52\","+
                    "\"type\":\"mob\","+
                    "\"id\":\"154fer53dfdf\","+
                    "\"os\":\"android\","+
                    "\"app\":\"MGSAPP\","+
                    "\"capability\":\"453453d4f5343434df354\","+
                    "\"payerAcAddressType\":\""+payerAcAddressType+"\","+
                    "\"detailsJson\": [{"+
                    "\"ifsc\":\""+ifsc+"\","+
                    "\"acType\":\""+acType+"\","+
                    "\"acNum\":\""+acNum+"\","+
                    "\"iin\":\""+iin+"\","+
                    "\"uIdNum\":\""+uIdNum+"\","+
                    "\"mmId\":\""+mmId+"\","+
                    "\"mobNum\":\""+mobNum+"\","+
                    "\"cardNum\":\""+cardNum+"\""+
                    "}],"+
                    "\"credType\":\""+type+"\","+
                    "\"credSubType\":\""+subType+"\","+
                    "\"credDataValue\":\""+encryptedBase64String+"\","+
                    "\"credDataCode\":\""+code+"\","+
                    "\"credDataKi\":\""+ki+"\","+
                    "\"txnAmmount\":\""+txnAmount+"\","+
                    "\"payeeAddress\":\""+payeeAddress+"\","+
                    "\"payeeName\":\""+payeeName+"\","+
                    "\"payeeType\":\"PERSON\","+
                    "\"payeeCode\":\"0000\","+
                    "\"IdentityId\":\"45785454\""+
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
        CollectTransServiceResponse collectTransServiceResponse =
                gson.fromJson(json.toString(), CollectTransServiceResponse.class);

        return collectTransServiceResponse;

    }
}
