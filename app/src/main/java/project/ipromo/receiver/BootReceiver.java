package project.ipromo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import project.ipromo.service.iPromoBeaconService;


// Este receiver se activa cuando se arranca el movil ya que se lo especifico en el manifest
// y este a su vez lanza el servicio de escaneo de beacons
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, iPromoBeaconService.class));
    }
}
