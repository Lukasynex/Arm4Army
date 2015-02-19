package com.bine.luk.arm4army;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	private OpenGLSurfaceView mGLSurfaceView;
	private LukasynoRenderer mRenderer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		
		
		mGLSurfaceView = (OpenGLSurfaceView) findViewById(R.id.gl_surface_view);
		mRenderer = new LukasynoRenderer(this);
		mGLSurfaceView.setRenderer(mRenderer);
	}
}
