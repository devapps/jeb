package org.vsoft.omni.upi.interfaces;

import org.vsoft.omni.upi.models.response.Response;

/**
 * Created by amishra on 18/03/16.
 */
public interface OnTaskCompleted {
    void onTaskCompleted(Response response, int requestCode);
    void onCLServiceInitialized(int requestCode);
}
