package org.vsoft.omni.upi.models.data;

import android.content.Context;
import android.support.v7.widget.AppCompatSpinner;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by amishra on 19/03/16.
 */
public class Global {

    public static String loggedInUser = "";

    public static ArrayList<String> payerAccounts = new ArrayList<String>();
    public static ArrayList<HashMap<String,String>> payerAccountsDetails = new ArrayList<HashMap<String,String>>();

    public static AppCompatSpinner payfromAddr = null;
    public static Context payContext = null;

    public static AppCompatSpinner collectToAddr = null;
    public static Context collectContext = null;

    public static String payerName = "Rohit P";
    public static String payerAddress = "rohit.p@mapp";
    public static String payerAcAddressType = "ACCOUNT";

    public static String linkType = "MOBILE";
    public static String linkValue = "9930993050";

    public static String deviceId = android.provider.Settings.Secure.ANDROID_ID;
    public static String bankName = "Vsoft Bank";

}
