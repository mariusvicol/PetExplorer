package petexplorer.petexplorerclients.notification;

import android.content.Context;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.os.Handler;

import petexplorer.petexplorerclients.R;

public class FloatingNotification {

    public static void show(Context context, ViewGroup rootLayout, String title, String message) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View notificationView = inflater.inflate(R.layout.view_floating_notification, rootLayout, false);

        TextView titleView = notificationView.findViewById(R.id.notificationTitle);
        TextView messageView = notificationView.findViewById(R.id.notificationMessage);

        titleView.setText(title);
        messageView.setText(message);

        // Adaugă notificarea în partea de sus
        rootLayout.addView(notificationView, 0);

        // Animare apariție
        notificationView.setTranslationY(-200);
        notificationView.animate()
                .translationY(0)
                .alpha(1)
                .setDuration(300)
                .start();

        // Dispare automat după 4 secunde
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            notificationView.animate()
                    .translationY(-200)
                    .alpha(0)
                    .setDuration(300)
                    .withEndAction(() -> rootLayout.removeView(notificationView))
                    .start();
        }, 4000);
    }
}
