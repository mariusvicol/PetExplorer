package petexplorer.petexplorerclients.notification;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.google.gson.Gson;

import domain.AnimalPierdut;
import petexplorer.petexplorerclients.utils.ServerConfig;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class WebSocketStompClientManager {

    private StompClient stompClient;

    @SuppressLint("CheckResult")
    public void connect(Context context) {
        stompClient = Stomp.over(
                Stomp.ConnectionProvider.OKHTTP,
                ServerConfig.WS_URL
        );

        stompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    Log.d("Stomp", "Conectat la WebSocket");
                    break;
                case ERROR:
                    Log.e("Stomp", "Eroare WebSocket", lifecycleEvent.getException());
                    break;
                case CLOSED:
                    Log.d("Stomp", "WebSocket închis");
                    break;
            }
        });

        stompClient.topic("/topic/animale-pierdute").subscribe(topicMessage -> {
            String json = topicMessage.getPayload();
            AnimalPierdut animal = new Gson().fromJson(json, AnimalPierdut.class);

            ((Activity) context).runOnUiThread(() -> {
                ViewGroup rootLayout = ((Activity) context).findViewById(android.R.id.content);
                FloatingNotification.show(
                        context,
                        rootLayout,
                        "Animal pierdut: " + animal.getNumeAnimal(),
                        animal.getDescriere()
                );
                NotificationUtil.showNotification(
                        context,
                        "Animal pierdut: " + animal.getNumeAnimal(),
                        animal.getDescriere()
                );
            });
        });

        stompClient.connect();
    }

    public void disconnect() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
    }
}
