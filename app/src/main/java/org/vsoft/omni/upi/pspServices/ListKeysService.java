package org.vsoft.omni.upi.pspServices;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.vsoft.omni.upi.models.response.ListKeysServiceResponse;
import org.vsoft.omni.upi.models.response.Response;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ListKeysService extends Service{

    String url = urlBase+"/listKeysService";

    String type = null;
    String subType = null;
    String code = null;
    String ki = null;
    String encryptedBase64String = null;
    String transactionId = null;

    public ListKeysService(String type, String subType, String code, String ki, String encryptedBase64String, String transactionId){
        this.type = type;
        this.subType = subType;
        this.code = code;
        this.ki = ki;
        this.encryptedBase64String = encryptedBase64String;
        this.transactionId = transactionId;
    }


    public Response execute() {

        String json="";
        JSONObject payload = null;

        try {

            payload = new JSONObject();
            payload.put("txnId", this.transactionId);
            payload.put("txnType", "LIST_KEYS");
            payload.put("credType", "CHALLENGE");
            payload.put("credSubType", "INITIAL");
            payload.put("credDataCode", "NPCI");
            payload.put("credDataKi", "20150822");


            String mpin = "123456";
            String base64_encoded_mpin = Base64.encodeToString(mpin.getBytes(),Base64.NO_PADDING);

            payload.put("credDataValue", base64_encoded_mpin);

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity((new StringEntity(payload.toString(), "UTF-8")));
            httpPost.setHeader("Authorization",
                    "Basic bWdzdXBpOmFkbWluQDEyMw=="); //
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
        ListKeysServiceResponse listKeysServiceResponse =
                gson.fromJson(json.toString(), ListKeysServiceResponse.class);

        return listKeysServiceResponse;

    }
}
