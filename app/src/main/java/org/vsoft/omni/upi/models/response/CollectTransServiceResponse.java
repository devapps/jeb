package org.vsoft.omni.upi.models.response;

/**
 * Created by amishra on 18/03/16.
 */
public class CollectTransServiceResponse extends Response {

    String txnId;
    String txnNote;
    String result;
    String resultDesc;

    public CollectTransServiceResponse(){
    }

    public CollectTransServiceResponse(String txnId, String txnNote, String result, String resultDesc){
        this.txnId = txnId;
        this.txnNote = txnNote;
        this.result = result;
        this.resultDesc = resultDesc;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }
}
