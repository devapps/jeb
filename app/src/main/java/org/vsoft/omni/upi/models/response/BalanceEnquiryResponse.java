package org.vsoft.omni.upi.models.response;

/**
 * Created by amishra on 19/03/16.
 */
public class BalanceEnquiryResponse extends Response {

    String txnId;
    String txnNote;
    String refId;
    String refUrl;
    String result;
    String resultCode;
    String balanceDataCode;
    String balanceDataKi;
    String balanceDataValue;

    public BalanceEnquiryResponse(){

    }

    public BalanceEnquiryResponse(String txnId,
                                  String txnNote,
                                  String refId,
                                  String refUrl,
                                  String result,
                                  String resultCode,
                                  String balanceDataCode,
                                  String balanceDataKi,
                                  String balanceDataValue){
        this.txnId = txnId;
        this.txnNote = txnNote;
        this.refId = refId;
        this.refUrl = refUrl;
        this.result = result;
        this.resultCode = resultCode;
        this.balanceDataCode = balanceDataCode;
        this.balanceDataKi = balanceDataKi;
        this.balanceDataValue = balanceDataValue;
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

    public String getBalanceDataCode() {
        return balanceDataCode;
    }

    public void setBalanceDataCode(String balanceDataCode) {
        this.balanceDataCode = balanceDataCode;
    }

    public String getBalanceDataKi() {
        return balanceDataKi;
    }

    public void setBalanceDataKi(String balanceDataKi) {
        this.balanceDataKi = balanceDataKi;
    }

    public String getBalanceDataValue() {
        return balanceDataValue;
    }

    public void setBalanceDataValue(String balanceDataValue) {
        this.balanceDataValue = balanceDataValue;
    }
}
