package com.poshtech.android.waller.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Punit Chhajer on 05-06-2016.
 */
public class SyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static WallerSyncAdapter sWallerSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("SyncService", "onCreate - SyncService");
        synchronized (sSyncAdapterLock) {
            if (sWallerSyncAdapter == null) {
                sWallerSyncAdapter = new WallerSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return sWallerSyncAdapter.getSyncAdapterBinder();
    }
}
