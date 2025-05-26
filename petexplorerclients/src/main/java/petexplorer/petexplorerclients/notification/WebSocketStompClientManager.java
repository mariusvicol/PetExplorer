package petexplorer.petexplorerclients.notification;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.google.gson.Gson;

import domain.AnimalPierdut;
import petexplorer.petexplorerclients.adapters.AnimalAdapter;
import petexplorer.petexplorerclients.utils.ServerConfig;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;



public class WebSocketStompClientManager {

    public interface OnAnimalReceivedListener {
        void onAnimalReceived(AnimalPierdut animal);
    }

    private static WebSocketStompClientManager instance;
    private StompClient stompClient;
    private OnAnimalReceivedListener animalReceivedListener;
    private boolean isConnected = false;

    private Context appContext;

    private WebSocketStompClientManager(Context context) {
        this.appContext = context.getApplicationContext();
        connectInternal();
    }

    public static synchronized WebSocketStompClientManager getInstance(Context context) {
        if (instance == null) {
            instance = new WebSocketStompClientManager(context);
        }
        return instance;
    }

    public void setOnAnimalReceivedListener(OnAnimalReceivedListener listener) {
        this.animalReceivedListener = listener;
    }

    @SuppressLint("CheckResult")
    private void connectInternal() {
        if (isConnected) return;

        stompClient = Stomp.over(
                Stomp.ConnectionProvider.OKHTTP,
                ServerConfig.WS_URL
        );

        stompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    Log.d("Stomp", "Conectat la WebSocket");
                    isConnected = true;
                    break;
                case ERROR:
                    Log.e("Stomp", "Eroare WebSocket", lifecycleEvent.getException());
                    break;
                case CLOSED:
                    Log.d("Stomp", "WebSocket închis");
                    isConnected = false;
                    break;
            }
        });

        stompClient.topic("/topic/animale-pierdute").subscribe(topicMessage -> {
            String json = topicMessage.getPayload();
            AnimalPierdut animal = new Gson().fromJson(json, AnimalPierdut.class);

            // Afișăm o singură dată notificarea
            NotificationUtil.showNotification(
                    appContext,
                    "Animal " + animal.getTipCaz() + ": " + animal.getNumeAnimal(),
                    animal.getDescriere()
            );

            if (animalReceivedListener != null) {
                animalReceivedListener.onAnimalReceived(animal);
            }
        });

        stompClient.topic("/topic/animale-pierdute/rezolved").subscribe(topicMessage -> {
            String json = topicMessage.getPayload();
            AnimalPierdut animal = new Gson().fromJson(json, AnimalPierdut.class);

            if (animalReceivedListener != null) {
                animalReceivedListener.onAnimalReceived(animal);
            }
        });

        stompClient.connect();
    }

    public void disconnect() {
        if (stompClient != null) {
            stompClient.disconnect();
            stompClient = null;
            instance = null;
            isConnected = false;
        }
    }
}
