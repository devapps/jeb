
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
import org.vsoft.omni.upi.models.data.Data;
import org.vsoft.omni.upi.models.data.Global;
import org.vsoft.omni.upi.models.response.ListAccountBankServiceResponse;
import org.vsoft.omni.upi.models.response.Response;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ListAccountBankService extends Service{

    String url = urlBase+"/listAccountBankService";

    String type = null;
    String subType = null;
    String code = null;
    String ki = null;
    String encryptedBase64String = null;
    String transactionId = null;


    public ListAccountBankService(String type,
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


    public Response execute() {

        String json="";
        String payload = null;

        try {

            payload = "{\n" +
                    "  \"txnId\": \""+transactionId+"\",\n" +
                    "  \"payerAddress\": \""+ Global.payerAddress+"\",\n" +
                    "  \"payerName\": \""+Global.payerName+"\",\n" +
                    "  \"linkType\": \""+Global.linkType+"\",\n" +
                    "  \"linkValue\": \""+Global.linkValue+"\",\n" +
                    "  \"accountAddressType\": \""+Global.payerAcAddressType+"\",\n" +
                    "  \"detailsJson\": [\n" +
                    "    {\n" +
                    "      \"ifsc\": \"MAPP\",\n" +
                    "      \"acType\": \"\",\n" +
                    "      \"acNum\": \"\",\n" +
                    "      \"iin\": null,\n" +
                    "      \"uIdNum\": null,\n" +
                    "      \"mmId\": null,\n" +
                    "      \"mobNum\": null,\n" +
                    "      \"cardNum\": null\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"credType\": \""+type+"\",\n" +
                    "  \"credSubType\": \""+subType+"\",\n" +
                    "  \"credDataValue\": \""+encryptedBase64String+"\",\n" +
                    "  \"credDataCode\": \""+code+"\",\n" +
                    "  \"credDataKi\": \""+ki+"\"\n" +
                    "}";

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
        ListAccountBankServiceResponse listAccountBankServiceResponse =
                gson.fromJson(json.toString(), ListAccountBankServiceResponse.class);

        return listAccountBankServiceResponse;

    }
}
