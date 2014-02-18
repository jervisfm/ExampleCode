package com.cookbook.advance.rpc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;


public class RPCService extends Service {
  
	IAdditionService.Stub mBinder;
  @Override
  public void onCreate() {
    super.onCreate();
    mBinder = new IAdditionService.Stub() {
        public int factoria(int value1) throws RemoteException {
            int result=1;
            for(int i=1; i<=value1; i++){
            	result*=i;
            }
            return result;
          }
    };
  }
  
  @Override
  public IBinder onBind(Intent intent) {
    return mBinder;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }
}
