package com.catenaxio.daos;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.BaseAdapter;

import com.catenaxio.beans.Clasificacion;
import com.catenaxio.interfaces.conexion.ConexionDB;
import com.catenaxio.interfaces.daos.ClasificacionDAOInterfaz;
import com.catenaxio.managers.ConnectionManager;
import com.catenaxio.utils.Constantes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Antonio on 03/03/2017.
 */

public class ClasificacionDAOFirebase implements ClasificacionDAOInterfaz {

    private Clasificacion clasificacion;

    public ClasificacionDAOFirebase( Clasificacion clasificacion) {

        this.clasificacion = clasificacion;

    }

    @Override
    public void downloadClasificacion() {
        descargarImagen();
    }

    private void descargarImagen(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference stRef = storage.getReference().child(Constantes.FRBS_CLASIFICACION)
                .child(Constantes.FRBS_CLASIFICACION.toLowerCase()+clasificacion.getTemporada().toLowerCase()+Constantes.PNG_EXTENSION);
        final long ONE_MEGABYTE = 1024 * 1024;
        stRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new MiSuccessListener(clasificacion)).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(this.getClass().getSimpleName(),e.getLocalizedMessage());
            }
        });
    }

    //descarga y setea la imagen descargada
    private class MiSuccessListener implements OnSuccessListener {

        Clasificacion clasif;

        public MiSuccessListener(Clasificacion clasificacion){
            super();
            clasif=clasificacion;
        }

        @Override
        public void onSuccess(Object o) {
            clasif.setImagen((byte[])o);
        }
    }

}
