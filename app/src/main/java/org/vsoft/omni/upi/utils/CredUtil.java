package org.vsoft.omni.upi.utils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.npci.upi.security.pinactivitycomponent.PinActivityComponent;
import org.vsoft.omni.upi.models.data.Credentials;
import org.vsoft.omni.upi.models.data.Global;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by amishra on 18/03/16.
 */
public class CredUtil {

    public static String transactionId = null;
    public static Credentials credentials = null;

    public static String regenerateTransactionId(){
        transactionId = String.valueOf(new Random().nextInt(99999999));
        return transactionId;
    }

    public static String getPayInfo(String payeeName, String note, String refId, String refUrl, String account){
        // Create Pay Info
        JSONArray payInfoArray=new JSONArray();

        try {

            if(payeeName!=null && payeeName.isEmpty()==false) {
                JSONObject jsonPayeeName = new JSONObject();
                jsonPayeeName.put("name", "payeeName");
                jsonPayeeName.put("value", payeeName);
                payInfoArray.put(jsonPayeeName);
            }

            if(note!=null && note.isEmpty()==false) {
                JSONObject jsonNote = new JSONObject();
                jsonNote.put("name", "note");
                jsonNote.put("value", note);
                payInfoArray.put(jsonNote);
            }

            if(refId!=null && refId.isEmpty()==false) {
                JSONObject jsonRefId = new JSONObject();
                jsonRefId.put("name", "refId");
                jsonRefId.put("value", refId);
                payInfoArray.put(jsonRefId);
            }

            if(refUrl!=null && refUrl.isEmpty()==false) {
                JSONObject jsonRefUrl = new JSONObject();
                jsonRefUrl.put("name", "refUrl");
                jsonRefUrl.put("value", refUrl);
                payInfoArray.put(jsonRefUrl);
            }

            if(account!=null && account.isEmpty()==false) {
                JSONObject jsonAccount = new JSONObject();
                jsonAccount.put("name", "account");
                jsonAccount.put("value", account);
                payInfoArray.put(jsonAccount);
            }

            //Log.i("payInfo", payInfoArray.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return payInfoArray.toString();
    }

    public static Intent getCommonLibraryIntent(Activity activity,
                                                String payeeName,
                                                String note,
                                                String refId,
                                                String refUrl,
                                                String account){

        String keyCode = "NPCI";

        String xmlPayloadString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ns2:RespListKeys xmlns:ns2=\"http://npci.org/upi/schema/\"><Head msgId=\"5skv5ptRj1uBlSYA8Ny\" orgId=\"NPCI\" ts=\"2015-12-31T17:42:26+05:30\" ver=\"1.0\"/><Resp reqMsgId=\"d0kSkoT7PaUSbv0u\" result=\"SUCCESS\"/><Txn id=\"d0kSkoT7PaUStXEf\" ts=\"2015-12-31T17:42:23+05:30\"/><keyList><key code=\"NPCI\" ki=\"20150822\" owner=\"NPCI\" type=\"PKI\"><keyValue xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:string\">MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4rIIEHkJ2TYgO/JUJQI/sxDgbDEAIuy9uTf4DItWeIMsG9AuilOj9R+dwAv8S6/9No/z0cwsw4UnsHQG1ALVIxFznLizMjaVJ7TJ+yTS9C9bYEFakRqH8b4jje7SC7rZ9/DtZGsaWaCaDTyuZ9dMHrgcmJjeklRKxl4YVmQJpzYLrK4zOpyY+lNPBqs+aiwJa53ZogcUGBhx/nIXfDDvVOtKzNb/08U7dZuXoiY0/McQ7xEiFcEtMpEJw5EB4o3RhE9j/IQOvc7l/BfD85+YQ5rJGk4HUb6GrQXHzfHvIOf53l1Yb0IX4v9q7HiAyOdggO+PVzXMSbrcFBrEjGZD7QIDAQAB</keyValue></key></keyList><Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\"><SignedInfo><CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/><SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256\"/><Reference URI=\"\"><Transforms><Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/></Transforms><DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/><DigestValue>GYiC65ThmzF0ka0DrD7/lLJuKcgTweu/DGNgp+n0JQE=</DigestValue></Reference></SignedInfo><SignatureValue>GjGo2YkxpUAOj+RLU3RUCLDhsPzaqHDEiUEOELjj0XYu9AuQEBEAvr6phgulwIi7zkmsrK/In47Y\n" +
                "QqsVhn9MVJZM/gr8Uym7EtgJzvx8M8TsecKoRf8NNkex8fYpBmX728kuQ73RPpbinVhn+zlqDl55\n" +
                "adDBevsucJNFkr0/fWDUX21coYYqZs5O/0OdHXBG+a/K5ruZ4DIsVaxYfbOMmZzVBjKHX0I88gqw\n" +
                "zHpiIBonax2YEhOaoDF9NaKcwKqt+xNdOJZbSj6gCE0+tKUfcpY15Xv40jheoEXiNl8xJvjX8wkd\n" +
                "SrIpNZFETpCpxnLl1dcBZFUrF+ZCZAyohmdGBw==</SignatureValue><KeyInfo><KeyValue><RSAKeyValue><Modulus>01DqzBsJTyMHT2S9MK5AIyFXNU646kwiOK3uymXIy9EW0nRKNKRkeIRTlGwX4wEnymGtGgX5B/Ij\n" +
                "1elkLN4VJ9GplDV+wf0Lp2i2q4E6uRiWIzsqq42MCQgv8Fq/IPqjqPbeP9yh/8YPmBiMehBmhQd3\n" +
                "qzl77C03k6d0yBIO5q/zXneTK9uFBNEL5yNpukrLGBcf3b9VHsjXpEaQrxGSMHCgNWpQgXpEcBr5\n" +
                "OJ0/XxWbgMCZMlkYe1d6gswjuCRZ/xxJwEfbSO5AsnPtyqxSIjyhgEi9REtYnzaWwOBN4JCqt0pM\n" +
                "L0ja23lUwVJuNwkwNGKBXvkGoXUln8Sf7PIv7w==</Modulus><Exponent>AQAB</Exponent></RSAKeyValue></KeyValue></KeyInfo></Signature></ns2:RespListKeys>";

        transactionId = String.valueOf(new Random().nextInt(99999999));

        String credAllowedString = "{\n" +
                "\t\"CredAllowed\": [{\n" +
                "\t\t\"type\": \"PIN\",\n" +
                "\t\t\"subtype\": \"MPIN\",\n" +
                "\t\t\"dType\": \"ALPH | NUM\",\n" +
                "\t\t\"dLength\": 6\n" +
                //"\t}, {\n" +
                //"\t\t\"type\": \"OTP\",\n" +
                //"\t\t\"subtype\": \"OTP\",\n" +
                //"\t\t\"dType\": \"NUM\",\n" +
                //"\t\t\"dLength\": 6\n" +
                "\t}]\n" +
                "}";

        Intent intent = new Intent(activity, PinActivityComponent.class);


        // Create Keycode
        intent.putExtra("keyCode", keyCode);

        // Create xml payload
        if(xmlPayloadString.isEmpty()){
            Toast.makeText(activity, "XML List Key API is not loaded.", Toast.LENGTH_LONG).show();
            return null;
        }
        intent.putExtra("keyXmlPayload", xmlPayloadString);  // It will get the data from list keys API response

        // Create Controls
        if(credAllowedString.isEmpty()){
            Toast.makeText(activity,"Required Credentials could not be loaded.",Toast.LENGTH_LONG).show();
            return null;
        }
        intent.putExtra("controls", credAllowedString);

        // Create Configuration
        try {
            JSONObject configuration = new JSONObject();
            configuration.put("payerBankName", Global.bankName);
            configuration.put("backgroundColor","#FFFFFF");
            //Log.i("configuration",configuration.toString());
            intent.putExtra("configuration", configuration.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        intent.putExtra("payInfo", getPayInfo(payeeName, note, refId, refUrl, account));

        // Create Salt
        try {
            JSONObject salt = new JSONObject();
            salt.put("txnId", transactionId);
            salt.put("txnAmount", "");
            salt.put("deviceId", Global.deviceId);
            salt.put("appId", activity.getApplicationContext().getPackageName());
            //Log.i("salt", salt.toString());
            intent.putExtra("salt", salt.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create Language Pref
        intent.putExtra("languagePref", "en_US");

        return intent;
    }

    public static Credentials getCredentials(HashMap<String, String> credBlocks){

        HashMap<String, String> credListHashMap = credBlocks;

        for(String cred : credListHashMap.keySet()){ // This will return the list of field name e.g mpin,otp etc...
            try {

                JSONObject credBlock=new JSONObject(credListHashMap.get(cred));
                ////Log.i("enc_msg", credBlock.toString());

                String subType = credBlock.getString("subType");
                String type = credBlock.getString("type");
                String ki = credBlock.getJSONObject("data").getString("ki");
                String code = credBlock.getJSONObject("data").getString("code");
                String encryptedBase64String = credBlock.getJSONObject("data").getString("encryptedBase64String").replace("\n", "");

                credentials = new Credentials(type,subType,ki,code,encryptedBase64String);

                return credentials;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static Credentials getCredentials(){
        return credentials;
    }

    public static String getTransactionId(){
        return transactionId;
    }
}
