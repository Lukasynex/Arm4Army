package com.bine.luk.arm4army;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {
	private OpenGLSurfaceView mGLSurfaceView;
	private LukasynoRenderer mRenderer;// = new LukasynoRenderer(this);
	private final int SEEKBAR_HEIGHT = 50;
	private final float ARM_RANGE = 1.8f;

	// private static final int GET_DEVICE = 1;
	// private Random generator = new Random();
	// // status aplikacji
	// private static final int DISCONNECTED = 1;
	// private static final int CONNECTED = 2;
	// private static final int CONNECTION_ERROR = 3;
	// private static int STATE = 1;
	//
	// private BluetoothAdapter mBluetoothAdapter;
	// private BluetoothDevice mDevice;
	// private BluetoothSocket mSocket;
	// private static final UUID uuid = UUID
	// .fromString("00001101-0000-1000-8000-00805f9b34fb"); // uuid dla
	// final Handler handler = new Handler();
	// // private ConnectedThread mConnectedThread;
	//
	// private TextView mTextState;

	
	//
	SeekBar bar1,bar2;
	LinearLayout renderLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mGLSurfaceView = new OpenGLSurfaceView(this);
		mRenderer = new LukasynoRenderer(this);
		mGLSurfaceView.setRenderer(mRenderer);
		bar1 = new SeekBar(this);
		bar2 = new SeekBar(this);
		bar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				mRenderer.setAngleZ(ARM_RANGE * progress);
			}});
		bar2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				mRenderer.setAngleY(ARM_RANGE* progress);
			}});

		final DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		
		
		renderLayout = new LinearLayout(this);
		renderLayout.setOrientation(LinearLayout.VERTICAL);

		LayoutParams rules = new LayoutParams(displayMetrics.widthPixels, 3*displayMetrics.heightPixels/5);
		LayoutParams rules4seekBar = new LayoutParams(displayMetrics.widthPixels, displayMetrics.heightPixels/6);

		renderLayout.addView(mGLSurfaceView, rules);
		renderLayout.addView(bar1,rules4seekBar);
		renderLayout.addView(bar2);
		this.setContentView(renderLayout);

		// this.setContentView(R.id.framelay);
		// setContentView(R.layout.render_layout);
		// setContentView(mGLSurfaceView);
		// TODO:
		// naprawić wykrzaczającą się aplikację!!!
		// tzn: wyjebać wszystko, wrzucić wszystko z apki CarController
		// potem próbować dodać layout i klasy openGL ESowe
	}
}
