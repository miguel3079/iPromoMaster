package project.ipromo.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.text.DecimalFormat;
import java.util.Collection;

import project.ipromo.Constants;
import project.ipromo.R;
import project.ipromo.activity.Promocion1;
import project.ipromo.iPromo;
import project.ipromo.ParseActivity.MainActivity;

// Service para hacer el scaneo de beacons BACKGROUND SCANNING
// - En Background la frecuencia de escaneo es de 5 minutos
public class iPromoBeaconService extends Service implements BeaconConsumer {
    public String idBeacon = "11111111-2222-3333-4444-555555555555";
    private BeaconManager beaconManager;

    // Determino que quiero 2 decimales para representar distancias posteriormente
    DecimalFormat numberFormat = new DecimalFormat("#.00");

    // TODO este es un id de un beacon para probar. Habria que pasar el id de todos los que despleguemos para la demo
    Identifier identifier = Identifier.parse(idBeacon);

    // Instancio la app para poder tirar mas adelante del metodo que me hice que me dice
    // si estoy en background o en foreground
    protected iPromo app;




    @Override
    public void onCreate() {
        super.onCreate();

        // Get the application instance
        app = (iPromo)getApplication();

        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.setBackgroundMode(true);

        // ESTO SE HA PUESTO TAN SOLO PARA PODER HACER LA DEMO, YA QUE POR DEFECTO EL SCAN EN BACKGROUND ES DE 5 MINUTOS
        // Y ES EXCESIVO PARA MOSTRAR NADA
        beaconManager.setBackgroundScanPeriod(1100l); // set the duration of the scan to be 1.1 seconds
        beaconManager.setBackgroundBetweenScanPeriod(60000l); // set the time between each scan to be 1 minute (60 seconds)


        // Layout para detectar iBeacons
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24"));

        // Me pongo en modo escucha
        beaconManager.bind(this);
    }


    @Override
    public void onDestroy() {
        // Quito el modo escucha
        beaconManager.unbind(this);

        super.onDestroy();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onBeaconServiceConnect() {


        final Intent intent = new Intent(this, Promocion1.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        /*
        // CUANDO ENTRAS EN UNA REGION (REGION ES EL RANGO DE UN BEACON)
        beaconManager.setMonitorNotifier(new MonitorNotifier() {

            @Override
            public void didEnterRegion(Region region) {
                Log.d(Constants.LOG_TAG, "I just saw an beacon for the first time!");
                Log.d(Constants.LOG_TAG, "Region id - " + region.getUniqueId());
                Log.d(Constants.LOG_TAG, "Sending notification.");

                sendNotification("Acabas de entrar en una region");
            }

            // CUANDO SALES DE UNA REGION
            @Override
            public void didExitRegion(Region region) {
                Log.d(Constants.LOG_TAG, "I no longer see a beacon");
                Log.d(Constants.LOG_TAG, "Region id - " + region.getUniqueId());

                sendNotification("Acabas de salir del rango region del ibeacon");
            }

            // DETERMINA EL ESTADO DE LA REGION
            @Override
            public void didDetermineStateForRegion(int state, Region region) {

            }
        });
        */




        // SI ENTRAS EN RANGO DE ALGUN BEACON.....
        beaconManager.setRangeNotifier(new RangeNotifier() {

            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                // TODO - Falta por crear un metodo que devuelva la promocion a lanzar pasandole por parametro el id del beacon

                for (Beacon beacon: beacons) {
                    if (beacon.getDistance() < 0.1) {
                        // This beacon is immediate (< 0.5 meters)
                        System.out.println("CERCA, MENOS DE 5 METROS (" + beacon.getDistance() + ") DEL BEACON (INMEDIATO)");

                        // En caso de estar en background envio una notificación que me llevara a la promocion si la abro
                        if (app.isAppIsInBackground(getApplicationContext())) {
                            sendNotification("Estas muuuuu cerca");
                        }  else {
                            // Y si no entonces mando directamente la promoción lanzando la activity correspondiente
                            // TODO - Comprobar el id del beacon para lanzar una activity u otra
                            startActivity(intent);
                        }


                    }
                    else if(beacon.getDistance() < 3.0) {
                        // This beacon is near (0.5 to 3 meters)
                        System.out.println("MEDIO, ENTRE 0.5 Y 3 METROS (" + beacon.getDistance() + ") DEL BEACON (MEDIO)");

                        if (app.isAppIsInBackground(getApplicationContext())) {
                            sendNotification("Estas muuuuu al medio");
                        }
                    }
                    else {
                        // This beacon is far (> 3 meters)
                        System.out.println("LEJOS, MAS DE 3 METROS (" + beacon.getDistance() + ") DEL BEACON (LEJOS)");

                        if (app.isAppIsInBackground(getApplicationContext())) {
                            sendNotification("Estas muuuuu lejos");
                        }
                    }
                }


                //if (region.getUniqueId() == Constants.REGION1_ID) {
                //}
            }
        });

        try {
            // String con el identificador del ibeacon de mi iphone
            // beaconManager.startMonitoringBeaconsInRegion(new Region(Constants.REGION1_ID, identifier, null, null)); // Monitorizo lo que pasa en dicha region (si entras o sales)
            beaconManager.startRangingBeaconsInRegion(new Region(Constants.REGION1_ID, identifier, null, null)); // Monitorizo la distancia al beacon que compone dicha region (distancia)
        } catch (RemoteException e) {
            Log.e(Constants.LOG_TAG, e.getMessage(), e);
        }
    }


    // Metodo que envia una notificación de mensaje pasado por parametro
    // TODO - Pasar como parametro tambien una activity a lanzar al darle a la notificacion (actualmente se lanza la MainActivity siempre)

    private void sendNotification(String message) {
        NotificationCompat.Builder builder =  new NotificationCompat.Builder(this)
                .setContentTitle("Aviso de Nuevo Beacon encontrado")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher); // TODO - Conseguir evitar el uso del import R para importar el ic_launcher

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(new Intent(this, MainActivity.class)); // TODO - Recordar que aqui se ha de lanzar la activity que se pasará como parametro
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
