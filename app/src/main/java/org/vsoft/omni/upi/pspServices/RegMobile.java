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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class RegMobile extends Service{

    String url = urlBase+"/mobileRegistration";

    public String execute() {

        String json="";
        JSONObject payload = null;

        try {

            payload = new JSONObject();
            payload.put("txnId", "12332423");
            payload.put("payerAddress", "nikhil@mgat");
            payload.put("payerName", "Nikhil M");
            payload.put("mobileNumber", "8654589562");
            //payload.put("geoCode", "123466454 ");
            //payload.put("location", "Mumbai,Maharashtra ");
            payload.put("ip", "142.12.26.52");
            payload.put("type", "mob");
            payload.put("id", "154fer53dfdf");
            payload.put("os", "android");

            payload.put("app", "Login");
            //payload.put("capability", "453453d4f5343434df354");
            payload.put("accountAddressType", "ACCOUNT");
            payload.put("accountIfsc", "MAPP");
            payload.put("regDetailMobile", "8654589562");

            JSONArray credList = new JSONArray();
            JSONObject cred1 = new JSONObject();
            cred1.put("type", "PIN");
            cred1.put("subType", "MPIN");
            cred1.put("code", "NPCI");
            cred1.put("ki", "20150822");
            cred1.put("value", "1.0|D/k8N8JCmc2TW59dgCzHjUwkWPcABCmpPRyhHm8e/DPyDrRLSZ/MXeGDjP4eKxCXn2YZcVuVp9ItgHghonOyYY6pa7dJK04PjRSPh0W0zkejZktQFD1Rbi03h8Kh+LaFPZgK7N6MH5PHORl8Q7qiNGTh7+rI1qJNzWFZ6My6oQw4pjUVBcRRQNV95dxXe2nLhaeyfv2P0N0rimUQIn4WQzDqteB+KvTbS98tjv7xMDb+SLInhCQs/LQHNKLVUinFHEp/mAxUCDP6OCUCmEszILaj3qp+66t3xjAjEmmPQmO8zLXf/UXBTRHQdafRaaXgdQx6ORiqM27Zm+tt0TzIvg==");
            credList.put(cred1);
            payload.put("credList", credList);

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

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String response = json.toString();

        return response;

    }
}
*/