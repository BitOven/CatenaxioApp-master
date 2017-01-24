package com.catenaxio.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;

import com.catenaxio.ClasificacionActivity;

import java.io.File;
import java.io.IOException;

/**
 * Created by Antonio on 22/01/2017.
 */

public class DownloadFile extends AsyncTask<String, Void, Void> {

    private int id;
    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;
    private Intent intent;
    private PendingIntent pIntent;
    private Context cntx;

    /**
     *
     * @param id
     * @param mNotifyManager
     * @param mBuilder
     * @param intent
     * @param pIntent
     * @param cntx
     */
    public DownloadFile(int id, NotificationManager mNotifyManager, NotificationCompat.Builder mBuilder, Intent intent, PendingIntent pIntent, Context cntx) {
        this.id = id;
        this.mNotifyManager = mNotifyManager;
        this.mBuilder = mBuilder;
        this.intent = intent;
        this.pIntent = pIntent;
        this.cntx = cntx;
    }

    @Override
    protected Void doInBackground(String... strings) {
        String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
        String fileName = strings[1];  // -> maven.pdf
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "Download");
        folder.mkdir();
        File pdfFile = new File(folder, fileName);
        try{
            pdfFile.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        //preparo el intent de la notificacion para abrir el pdf
        intent = new Intent(Intent.ACTION_VIEW, Uri.fromFile(pdfFile));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        pIntent = PendingIntent.getActivity(cntx.getApplicationContext(), id, intent, PendingIntent.FLAG_ONE_SHOT);

        //preparo la notificacion en progreso
        mBuilder.setProgress(0, 0, true)
                .setContentIntent(pIntent);
        mNotifyManager.notify(id, mBuilder.build());

        PDFDownloader.downloadFile(fileUrl, pdfFile);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mBuilder.setContentText("Descarga completada")
                // Removes the progress bar
                .setProgress(0,0,false)
                .setAutoCancel(true);
        mNotifyManager.notify(id, mBuilder.build());

    }
}
