package com.karthik.example.calender.service_recivers;

import com.karthik.example.calender.R;
import com.karthik.example.calender.EventActivity;
import com.karthik.example.calender.utilites.Utils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;

public class MyAlarmReciver extends BroadcastReceiver {
static int INDEX=0;
	@SuppressLint("NewApi") @Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
      //Log.d("Testing App ","Alram On "+ intent.getStringExtra("time"));
       /*Toast.makeText(context, "Alram On "+ intent.getStringExtra("time"),
    		   Toast.LENGTH_SHORT).show();*/
       String tilte= intent.getStringExtra("title");
       String msg=intent.getStringExtra("msg");
       
       ++INDEX;
       Intent intentAct=new Intent(context,EventActivity.class);
       intentAct.putExtra("show", true);
       intentAct.putExtra(Utils.ROWID ,intent.getIntExtra(Utils.ROWID, -1));
       
       PendingIntent pendingIntent=PendingIntent.getActivity(context, INDEX, intentAct, 0);
			
		Notification notification=new Notification.Builder(context)
		.setContentTitle(""+ tilte )
		.setContentText(" "+ msg +" !")
		.setSmallIcon(R.drawable.icon)		
		.setAutoCancel(true)
		.setContentIntent(pendingIntent)
		.setPriority(Notification.PRIORITY_HIGH)
		.setSound(RingtoneManager.getActualDefaultRingtoneUri(context,RingtoneManager.TYPE_NOTIFICATION))
		.build();		
		NotificationManager notiMgr=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		notification.flags= Notification.FLAG_AUTO_CANCEL;
		
		notiMgr.notify(INDEX, notification);
	}

}
