package com.catenaxio;
import android.content.Context;
import android.content.BroadcastReceiver;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.content.SharedPreferences;
import android.widget.Toast;
/**
 * Created by hugointegrasys on 9/10/14.
 */
public class Receiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service1 = new Intent(context, MyAlarmService.class);
        context.startService(service1);
        Log.d("mostrar","calendario");
    }
}
