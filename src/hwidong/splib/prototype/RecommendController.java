package hwidong.splib.prototype;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.splib.SplibController;

public class RecommendController {

	private Activity activity;
	private SplibController spController;
	private RecommendHandler handler;
	
	public static final int MODE_PRIVATE = 0;
	public static final int MODE_PUBLIC = 1;
	
	ArrayAdapter<String> adWord;
	AutoCompleteTextView view;
	
	private boolean isSearchHidden = true;
	
	public RecommendController(Activity activity, SplibController spController) {
		this.activity = activity;
		this.spController = spController;
		
		handler = new RecommendHandler(this);
	}
	
	static class RecommendHandler extends Handler {		
		private RecommendController controller;
		public static final int WHAT_PRIVATE = 0;
		public static final int WHAT_PUBLIC = 1;

		public RecommendHandler(RecommendController controller) {
			this.controller = controller;
		}
		
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT_PRIVATE:
				controller.setContent(MODE_PRIVATE);
				break;
			case WHAT_PUBLIC:
				controller.setContent(MODE_PUBLIC);
				break;
			}
			
			controller.spController.setSPCheckDone(false);
		}
	};
	
	public void start(AutoCompleteTextView view) {
		if (isSearchHidden) {
			this.view = view;
			spController.checkSP();
			spController.sendMsgStart(handler);
			isSearchHidden = false;
		}
		else {
			view.setVisibility(View.INVISIBLE);
			isSearchHidden = true;
		}
	}
			
	public void setContent(int mode) {
		view.setVisibility(View.VISIBLE);
		
		switch(mode) {
			case MODE_PRIVATE:
				adWord = new ArrayAdapter<String>(activity,						
			            android.R.layout.simple_dropdown_item_1line, 
			            activity.getResources().getStringArray(R.array.recommend_array_private));
				break;
			case MODE_PUBLIC:
				adWord = new ArrayAdapter<String>(activity,						
			            android.R.layout.simple_dropdown_item_1line, 
			            activity.getResources().getStringArray(R.array.recommend_array_public));
				break;
		}
		
		view.setAdapter(adWord);		
	}	
}
