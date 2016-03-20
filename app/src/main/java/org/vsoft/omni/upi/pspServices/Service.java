package org.vsoft.omni.upi.pspServices;

import org.vsoft.omni.upi.models.response.Response;

public abstract class Service {
    public final String urlBase = "http://103.14.161.149:12001/UpiService/upi";
    public abstract Response execute();
}
