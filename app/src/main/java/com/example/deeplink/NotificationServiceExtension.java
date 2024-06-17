package com.example.deeplink;

import androidx.annotation.Keep;

import com.onesignal.notifications.IDisplayableMutableNotification;
import com.onesignal.notifications.INotificationReceivedEvent;
import com.onesignal.notifications.INotificationServiceExtension;

@Keep // Keep is required to prevent minification from renaming or removing your class
public class NotificationServiceExtension implements INotificationServiceExtension {

    @Override
    public void onNotificationReceived(INotificationReceivedEvent event) {
        IDisplayableMutableNotification notification = event.getNotification();


        // this is an example of how to modify the notification by changing the background color to blue
        String body = notification.getBody();
        String modifiedBody = body + " [MODIFIED]";
        notification.setExtender(builder -> builder.setContentText(body));

        //If you need to perform an async action or stop the payload from being shown automatically,
    }
}