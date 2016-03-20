/*
package org.vsoft.omni.upi.pspServices;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.vsoft.omni.upi.models.responses.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


public class OtpService extends Service{

    String url = urlBase+"/otpService";

    public Response execute() {

        String json="";
        JSONObject payload = null;

        try {

            payload = new JSONObject();
            payload.put("txnId", "12332423");
            payload.put("payerAddress", "nikhil@mapp");
            payload.put("payerName", "Nikhil M");
            payload.put("mobileNumber", "8654589562");
            payload.put("geoCode", "123466454 ");
            payload.put("location", "Mumbai,Maharashtra ");
            payload.put("ip", "142.12.26.52");
            payload.put("type", "mob");
            payload.put("id", "154fer53dfdf");
            payload.put("os", "android");

            payload.put("app", "org.vsoft.omni.upi");
            payload.put("capability", "453453d4f5343434df354");
            payload.put("accountAddressType", "ACCOUNT");

            JSONObject arrabj = new JSONObject();
            arrabj.put("ifsc", "MGAT0001865");
            arrabj.put("acType", "Savings");
            arrabj.put("acNum", "");
            arrabj.put("iin", "");
            arrabj.put("uIdNum", "");
            arrabj.put("mmId", "");
            arrabj.put("mobNum", "");
            arrabj.put("cardNum", "");
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(arrabj);
            payload.put("detailsJson", jsonArray);

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
            Log.e("@Response", json);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String response = json.toString();

        return response;

    }
}
*/