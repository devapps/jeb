package org.vsoft.omni.upi.models.response;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by amishra on 18/03/16.
 */
public class ListAccountBankServiceResponse extends Response{

    String txnId;
    String txnNote;
    String refId;
    String refUrl;
    String result;
    String resultCode;
    ArrayList<HashMap<String,String>> accountList;

    public ListAccountBankServiceResponse(){
    }

    public ListAccountBankServiceResponse(String txnId,
                                          String txnNote,
                                          String refId,
                                          String refUrl,
                                          String result,
                                          String resultCode,
                                          ArrayList<HashMap<String,String>> accountList){
        this.txnId = txnId;
        this.txnNote = txnNote;
        this.refId = refId;
        this.refUrl = refUrl;
        this.result = result;
        this.resultCode = resultCode;
        this.accountList = accountList;

    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getTxnNote() {
        return txnNote;
    }

    public void setTxnNote(String txnNote) {
        this.txnNote = txnNote;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public ArrayList<HashMap<String, String>> getAccountList() {
        return accountList;
    }

    public void setAccountList(ArrayList<HashMap<String, String>> accountList) {
        this.accountList = accountList;
    }
}
