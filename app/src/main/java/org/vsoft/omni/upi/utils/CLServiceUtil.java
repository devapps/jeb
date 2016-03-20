package org.vsoft.omni.upi.utils;

import android.content.Context;
import android.util.Log;

import org.npci.upi.security.services.CLServices;
import org.npci.upi.security.services.ServiceConnectionStatusNotifier;
import org.vsoft.omni.upi.interfaces.OnTaskCompleted;

public class CLServiceUtil implements ServiceConnectionStatusNotifier {

    public CLServices clServices = null;
    Context context = null;
    OnTaskCompleted onTaskCompleted = null;
    int requestCode = RequestCodeUtil.DEFAULT;

    public boolean isConnected = false;

    public CLServiceUtil(Context context, int requestCode, OnTaskCompleted onTaskCompleted){
        this.context = context;
        this.onTaskCompleted = onTaskCompleted;
        this.requestCode = requestCode;
    }

    public void init(){
        if(clServices==null)
            CLServices.initService(context, this);
    }

    public void serviceConnected(CLServices clServices){
        this.clServices = clServices;
        isConnected = true;

        Log.e(" isConnected ","true");
        String deviceId = "74235ae00124fab8";
        String appId = "org.vsoft.omni.upi";
        String mobileNumber = "8899229999";
        //clServices.registerApp(appId, mobileNumber, deviceId, "");
        onTaskCompleted.onCLServiceInitialized(requestCode);
    }

    public void serviceDisconnected(){
        Log.e(" isConnected ","false");
        isConnected = false;
    }

    public String getChallenge(String type, String deviceId) {
        if(clServices!=null)
            return clServices.getChallenge(type, deviceId);
        else
            return "";
    }

}
