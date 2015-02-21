package com.bine.luk.arm4army;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class OpenGLSurfaceView extends GLSurfaceView {
	private final float TOUCH_SCALE_FACTOR = 180.0f / 320;

	private LukasynoRenderer mRenderer;

	// Offsets for touch events
	private float mPreviousX;
	private float mPreviousY;

	public OpenGLSurfaceView(Context context) {
		super(context);
	}

	public OpenGLSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

//	@Override
//	public boolean onTouchEvent(MotionEvent e) {
//		if (e != null) {
//			float x = e.getX();
//			float y = e.getY();
//
//			switch (e.getAction()) {
//			case MotionEvent.ACTION_MOVE:
//
//				float dx = x - mPreviousX;
//				float dy = y - mPreviousY;
//
//				if (y > this.getHeight() / 2) {
//					dx = dx * -1;
//				}
//				if (x < this.getWidth() / 2) {
//					dy = dy * -1;
//				}
//
//				if (Math.abs(dx) > Math.abs(3 * dy))
//
//					mRenderer.setAngleZ(mRenderer.getAngleZ()
//							+ ((-dx) * TOUCH_SCALE_FACTOR));
//				else if (Math.abs(dy) > Math.abs(3 * dx))
//					mRenderer.setAngleY(mRenderer.getAngleY()
//							+ ((dy) * TOUCH_SCALE_FACTOR));
//			}
//
//			mPreviousX = x;
//			mPreviousY = y;
//			return true;
//		} else {
//			return super.onTouchEvent(e);
//		}
//	}

	// Hides superclass method.
	public void setRenderer(LukasynoRenderer renderer) {
		mRenderer = renderer;
		super.setRenderer(renderer);
	}

}
