package org.vsoft.omni.upi.models.data;

/**
 * Created by amishra on 18/03/16.
 */
public class Credentials extends Data {

    String subType;
    String type;
    String ki;
    String code;
    String encryptedBase64String;

    public Credentials(String type, String subType, String ki, String code, String encryptedBase64String){
        this.type = type;
        this.subType = subType;
        this.ki = ki;
        this.code = code;
        this.encryptedBase64String = encryptedBase64String;
    }

    public String getEncryptedBase64String() {
        return encryptedBase64String;
    }

    public void setEncryptedBase64String(String encryptedBase64String) {
        this.encryptedBase64String = encryptedBase64String;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKi() {
        return ki;
    }

    public void setKi(String ki) {
        this.ki = ki;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

}
