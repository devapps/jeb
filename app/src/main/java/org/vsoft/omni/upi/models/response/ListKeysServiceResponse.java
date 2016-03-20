package org.vsoft.omni.upi.models.response;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by amishra on 18/03/16.
 */
public class ListKeysServiceResponse extends Response{

    String txnId;
    String refId;
    String refUrl;
    ArrayList<HashMap<String,String>> listKeys;
    String reqMsgId;
    String result;

    public ListKeysServiceResponse(){
    }

    public ListKeysServiceResponse(String txnId,
                                   String refId,
                                   String refUrl,
                                   ArrayList<HashMap<String,String>> listKeys,
                                   String reqMsgId,
                                   String result){
        this.txnId = txnId;
        this.refId = refId;
        this.refUrl = refUrl;
        this.listKeys = listKeys;
        this.reqMsgId = reqMsgId;
        this.result = result;
    }


    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getRefUrl() {
        return refUrl;
    }

    public void setRefUrl(String refUrl) {
        this.refUrl = refUrl;
    }

    public ArrayList<HashMap<String, String>> getListKeys() {
        return listKeys;
    }

    public void setListKeys(ArrayList<HashMap<String, String>> listKeys) {
        this.listKeys = listKeys;
    }

    public String getReqMsgId() {
        return reqMsgId;
    }

    public void setReqMsgId(String reqMsgId) {
        this.reqMsgId = reqMsgId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
