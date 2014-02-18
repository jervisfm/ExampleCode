package com.cookbook.advance.rpc;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RPC extends Activity {

  IAdditionService service;
  myServiceConnection connection;

  class myServiceConnection implements ServiceConnection {

    public void onServiceConnected(ComponentName name, IBinder boundService) {
      service = IAdditionService.Stub.asInterface((IBinder) boundService);
      Toast.makeText(RPC.this, "Service connected", Toast.LENGTH_SHORT)
          .show();
    }

    public void onServiceDisconnected(ComponentName name) {
      service = null;
      Toast.makeText(RPC.this, "Service disconnected", Toast.LENGTH_SHORT)
          .show();
    }
  }

  private void initService() {
    connection = new myServiceConnection();
    Intent i = new Intent();
    i.setClassName("com.cookbook.advance.rpc", com.cookbook.advance.rpc.RPCService.class.getName());
    if(!bindService(i, connection, Context.BIND_AUTO_CREATE))
    {
        Toast.makeText(RPC.this, "Bind Service Failed", Toast.LENGTH_LONG)
        .show();
    }
  }

  private void releaseService() {
    unbindService(connection);
    connection = null;
  }

 
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    initService();

    Button buttonCalc = (Button) findViewById(R.id.buttonCalc);

    buttonCalc.setOnClickListener(new OnClickListener() {
      TextView result = (TextView) findViewById(R.id.result);
      EditText value1 = (EditText) findViewById(R.id.value1);

      public void onClick(View v) {
        int v1, res = -1;

        try {
        	v1 = Integer.parseInt(value1.getText().toString());
        	res = service.factoria(v1);
        	
        	
        } catch (RemoteException e) {
          e.printStackTrace();
        }
        result.setText(new Integer(res).toString());
      }
    });
  }

  /** Called when the activity is about to be destroyed. */
  @Override
  protected void onDestroy() {
    releaseService();
  }

}