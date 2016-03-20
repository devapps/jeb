
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
import org.vsoft.omni.upi.models.response.MobileRegistrationResponse;
import org.vsoft.omni.upi.models.response.Response;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MobileRegistrationService extends Service{

    String url = urlBase+"/mobileRegistration";

    String type = null;
    String subType = null;
    String code = null;
    String ki = null;
    String encryptedBase64String = null;
    String transactionId = null;
    Transaction transaction = null;

    public MobileRegistrationService(String type,
                                     String subType,
                                     String code,
                                     String ki,
                                     String encryptedBase64String,
                                     String transactionId){
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

            payload = "{" +
                    " \"txnId\": \""+transactionId+"\"," +
                    " \"refId\": \""+transactionId+"\"," +
                    " \"payerAddress\": \""+transaction.getPayeeAddress()+"\"," +
                    " \"payerName\": \""+transaction.getPayeeName()+"\"," +
                    " \"mobileNumber\": \""+transaction.getMobNum()+"\"," +
                    " \"geoCode\": \"288177\"," +
                    " \"location\": \"Mumbai,Maharashtra\"," +
                    " \"ip\": \"124.170.23.22\"," +
                    " \"type\": \"mob\"," +
                    " \"id\": \"750c6be243f1c4b5c9912b95a5742fc5\"," +
                    " \"os\": \"android\"," +
                    " \"app\": \"MGSAPP\"," +
                    " \"capability\": \"5200000200010004000639292929292\"," +
                    " \"accountAddressType\": \"ACCOUNT\"," +
                    " \"accountIfsc\": \""+transaction.getIfsc()+"\"," +
                    " \"accountType\": \""+transaction.getAcType()+"\"," +
                    " \"accountNumber\": \""+transaction.getAcNum()+"\"," +
                    " \"regCredType\": \"PIN\", " +
                    "\"regDetailMobile\": \""+transaction.getMobNum()+"\"," +
                    " \"regDetailCardDigits\": \"\"," +
                    " \"regDetailExpDate\": \"\"," +
                    " \"credList\": [{" +
                    " \"type\": \""+type+"\"," +
                    " \"subType\": \""+subType+"\"," +
                    " \"code\": \""+code+"\"," +
                    " \"ki\": \""+ki+"\"," +
                    " \"value\": \""+encryptedBase64String+"\"" +
                    "" +
                    " }]" +
                    "}";

            //Log.e("@Request : ",payload);

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity((new StringEntity(payload.toString(), "UTF-8")));
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
        MobileRegistrationResponse mobileRegistrationResponse =
                gson.fromJson(json.toString(), MobileRegistrationResponse.class);

        return mobileRegistrationResponse;

    }
}
