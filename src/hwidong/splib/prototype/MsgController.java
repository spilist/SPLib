package hwidong.splib.prototype;

import com.splib.SplibController;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

public class MsgController {

	private Activity activity;	
	private SplibController spController;
	private MsgHandler handler;
	
	public static final int MODE_PRIVATE = 0;
	public static final int MODE_PUBLIC = 1;
	
	private AlertDialog ad;
	private AlertDialog.Builder adBuilder;						
	
	public MsgController(Activity activity, SplibController spController) {
		this.activity = activity;
		this.spController = spController;
		
		handler = new MsgHandler(this);		
	}
	
	static class MsgHandler extends Handler {		
		private MsgController controller;
		public static final int WHAT_PRIVATE = 0;
		public static final int WHAT_PUBLIC = 1;

		public MsgHandler(MsgController controller) {
			this.controller = controller;
		}
		
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT_PRIVATE:
				controller.showText(MODE_PRIVATE);
				break;
			case WHAT_PUBLIC:
				controller.showText(MODE_PUBLIC);
				break;
			}
			
			controller.spController.setSPCheckDone(false);
		}
	};
	
	public void start() {
		spController.getSP((Handler)handler); 
	}
	
	public void showText(int mode) {
		adBuilder = new AlertDialog.Builder(this.activity);				
		switch(mode) {
			case MODE_PRIVATE:
				adBuilder.setTitle(activity.getString(R.string.msg_private_title));
				adBuilder.setMessage(activity.getString(R.string.msg_private_contents));
				adBuilder.setIcon(
						activity.getResources().getDrawable(R.drawable.msg_icon));
				break;
			case MODE_PUBLIC:
				adBuilder.setMessage(activity.getString(R.string.msg_public_contents));
				break;
		}
		
		ad = adBuilder.create();
		ad.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {  
		    @Override  
		    public void onClick(DialogInterface dialog, int which) {  
		        dialog.dismiss();                      
		    }
		});
		
		ad.show();
	}
}