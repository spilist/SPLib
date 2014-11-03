package hwidong.splib.prototype;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.splib.SplibController;

public class ImgController extends BaseAdapter{
	
	private SplibController spController;
	private ImgHandler handler;
	
	public static final int MODE_PRIVATE = 0;
	public static final int MODE_PUBLIC = 1;
	
	private Context context;
	private boolean isGalleryHidden = true;
	private GridView view;
	private Integer[] mThumbIds;
	
	public ImgController(Context context, SplibController spController) {
		this.context = context;
		this.spController = spController;
		
		handler = new ImgHandler(this);		
	}
	
	public int getCount() {
        return mThumbIds.length;
    }
	
	public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
	
	static class ImgHandler extends Handler {		
		private ImgController controller;
		public static final int WHAT_PRIVATE = 0;
		public static final int WHAT_PUBLIC = 1;

		public ImgHandler(ImgController controller) {
			this.controller = controller;
		}
		
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT_PRIVATE:
				controller.setImage(MODE_PRIVATE);
				break;
			case WHAT_PUBLIC:
				controller.setImage(MODE_PUBLIC);
				break;
			}
			
			controller.spController.setSPCheckDone(false);
		}
	};
	
	public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }
	
	public void start(GridView view) {
		if (isGalleryHidden) {			
			this.view = view;
			spController.getSP((Handler)handler); 
			isGalleryHidden = false;
		}
		else {
			view.setVisibility(View.INVISIBLE);
			isGalleryHidden = true;
		}		
	}
	
	private void setImage(int mode) {
		switch(mode) {
		case MODE_PRIVATE:
			mThumbIds = new Integer[] {
					R.drawable.pri_0, R.drawable.pri_1,
					R.drawable.pub_0, R.drawable.pub_1,
					R.drawable.pri_2, R.drawable.pri_3,
					R.drawable.pub_2, R.drawable.pub_3,
					R.drawable.pri_4, R.drawable.pri_5,
					R.drawable.pub_4, R.drawable.pub_5,
					R.drawable.pri_6, R.drawable.pri_7,
					R.drawable.pub_6, R.drawable.pub_7}; 		
			break;
		case MODE_PUBLIC:
			mThumbIds = new Integer[] {
					R.drawable.pub_0, R.drawable.pub_1,
					R.drawable.pub_2, R.drawable.pub_3,
					R.drawable.pub_4, R.drawable.pub_5,
					R.drawable.pub_6, R.drawable.pub_7,
					R.drawable.pub_0, R.drawable.pub_1,
					R.drawable.pub_2, R.drawable.pub_3,
					R.drawable.pub_4, R.drawable.pub_5,
					R.drawable.pub_6, R.drawable.pub_7};
			break;
		}
		view.setAdapter(this);		
		view.setVisibility(View.VISIBLE);
	}
}
