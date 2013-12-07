package hwidong.splib.prototype;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.splib.SplibController;

public class SPLib_MainActivity extends Activity{
	
	private Button btnShowSearch, btnShowMsg, btnShowGallery;
	private SplibController spController;
	private MsgController msgController;
	private RecommendController reController;	
	
	AutoCompleteTextView searchView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		spController = new SplibController(this);
		reController = new RecommendController(this, spController);		
		msgController = new MsgController(this, spController);
		
		setContentView(R.layout.activity_main);

		searchView = (AutoCompleteTextView) findViewById(R.id.searchView);
		searchView.setOnTouchListener(touchListener);
		
		btnShowSearch = (Button) findViewById(R.id.btn_showSearch);
		btnShowMsg = (Button) findViewById(R.id.btn_showMessage);
		btnShowGallery = (Button) findViewById(R.id.btn_showGallery);
		
		btnShowMsg.setOnClickListener(btnClickListener);
		btnShowSearch.setOnClickListener(btnClickListener);
		btnShowGallery.setOnClickListener(btnClickListener);
	}
	
	private OnTouchListener touchListener = new View.OnTouchListener() {		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			searchView.showDropDown();
			return false;
		}
	};
	
	private OnClickListener btnClickListener = new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			switch(v.getId()) {
				case R.id.btn_showSearch:					
					reController.start(searchView);
					break;
				case R.id.btn_showMessage:
					msgController.start();
					break;
				case R.id.btn_showGallery:
					//msgController.start();
					break;
			}			
		}
	};

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SplibController.SP_REQUEST) {	        	        	
	        	spController.setSP(data.getBooleanExtra("SP", false));
				spController.setSPCheckDone(true);	        	
	        }
	    }
	}
}
