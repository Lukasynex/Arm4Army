package com.bine.luk.arm4army;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ClickableViewAccessibility")
public class Main extends Activity implements SensorEventListener {
	private OpenGLSurfaceView mGLSurfaceView;
	private LukasynoRenderer mRenderer;
	
	private boolean LIGHT = false;
	
	public char global_UP = '0';
	public char global_RIGHT = '1';
	public char global_DOWN = '2';
	public char global_LEFT = '3';

	public char global_UP_DISABLE = '4';
	public char global_RIGHT_DISABLE = '5';
	public char global_DOWN_DISABLE = '6';
	public char global_LEFT_DISABLE = '7';

	public char global_INCREASE_SPEED = 'w';
	public char global_DECREASE_SPEED = 's';
	public char global_SWITCH_LIGHTS = 'a';
	public char global_SOUND_ON = 'k';
	public char global_SOUND_OFF = 'n';

	public boolean ScreenAceII = true;

	private static final int GET_DEVICE = 1;
	private Random generator = new Random();
	// status aplikacji
	private static final int DISCONNECTED = 1;
	private static final int CONNECTED = 2;
	private static final int CONNECTION_ERROR = 3;
	private static int STATE = 1;

	private BluetoothAdapter mBluetoothAdapter;
	private BluetoothDevice mDevice;
	private BluetoothSocket mSocket;
	private static final UUID uuid = UUID
			.fromString("00001101-0000-1000-8000-00805f9b34fb"); // uuid dla
																	// wymiany
																	// danych
	final Handler handler = new Handler();
	private ConnectedThread mConnectedThread;
	// sensory - nieużywany
	SensorManager sensorManager = null;
	// komunikat stanu
	private TextView mTextState;
	public void onConnectionEstablished(){
		setContentView(R.layout.render_layout);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		mGLSurfaceView = (OpenGLSurfaceView) findViewById(R.id.gl_surface_view);
		mRenderer = new LukasynoRenderer(this);
		mGLSurfaceView.setRenderer(mRenderer);
		setContentView(R.layout.activity_main);

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "Bluetooth jest niedostępny!",
					Toast.LENGTH_LONG).show();
			finish();
			return;
		}

		if (!mBluetoothAdapter.isEnabled()) {
			Toast.makeText(this, "Bluetooth jest wyłączony!", Toast.LENGTH_LONG)
					.show();
			finish();
			return;
		}

		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
				.getBondedDevices();
		if (pairedDevices.size() == 0) {
			Toast.makeText(this, "Brak sparowanych urządzeń!",
					Toast.LENGTH_LONG).show();
			finish();
			return;
		}

		mTextState = (TextView) findViewById(R.id.currentState);
	}

		private class ConnectedThread extends Thread {

		private InputStream mmInStream = null;
		private OutputStream mmOutStream = null;

		public ConnectedThread(BluetoothSocket socket) {
			try {
				mmInStream = socket.getInputStream();
				mmOutStream = socket.getOutputStream();
			} catch (IOException e) {
				handler.post(new Runnable() {
					public void run() {
						btDisconnect();
						changeState(CONNECTION_ERROR);
						mConnectedThread = null;
					}
				});
			}

		}

		public void run() {

			byte[] buffer = new byte[1024];
			int bytes;

			while (true) {
				try {
					// Read from the InputStream
					bytes = mmInStream.read(buffer);

					if (bytes > 0) {
						byte[] newbuffer = new byte[bytes];

						for (int i = 0; i < bytes; i++)
							newbuffer[i] = buffer[i];

						final String data = new String(newbuffer, "US-ASCII");
						handler.post(new Runnable() {
							public void run() {
								// mTextLog.append(data);
								// mTextLog.scrollBy(0, 1);
							}
						});
					}

				} catch (IOException e) {
					Log.e("BT", "watcher", e);
					break;
				}
			}
		}

		void write(int one) {
			if (STATE != CONNECTED)
				return;

			try {
				mmOutStream.write(one);
			} catch (IOException e) {
				handler.post(new Runnable() {
					public void run() {
						btDisconnect();
						changeState(CONNECTION_ERROR);
						mConnectedThread = null;
					}
				});
			}
		}

		void write(String str) {
			if (STATE != CONNECTED)
				return;

			try {
				mmOutStream.write(str.getBytes());
			} catch (IOException e) {

				synchronized (Main.this) {
					btDisconnect();
					changeState(CONNECTION_ERROR);
					mConnectedThread = null;
				}
			}

		}
	}

	public void onSensorChanged(SensorEvent event) {
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	private void btConnect() {
		if (mDevice == null)
			return;
		try {
			mSocket = mDevice.createRfcommSocketToServiceRecord(uuid);
			mSocket.connect();

		} catch (IOException e) {
			Log.e("BT", "point1", e);

			btDisconnect();
			changeState(CONNECTION_ERROR);
			return;
		}

		mConnectedThread = new ConnectedThread(mSocket);
		mConnectedThread.start();

		changeState(CONNECTED);
	}

	private void btConnect(String address) {
		mDevice = mBluetoothAdapter.getRemoteDevice(address);
		btConnect();
	}

	private void btDisconnect() {
		if (mSocket == null)
			return;

		if (mConnectedThread != null) {
			mConnectedThread.stop();
			mConnectedThread = null;
		}

		try {
			mSocket.close();
		} catch (IOException e) {
			Log.e("BT", "point3", e);
		}

		mSocket = null;

		changeState(DISCONNECTED);
	}

	private void changeState(int iState) {

		STATE = iState;

		switch (iState) {
		case CONNECTED:
			mTextState.setTextColor(Color.BLUE);
			mTextState.setText("Połączony z " + mDevice.getName());
			break;
		case DISCONNECTED:
			mTextState.setTextColor(Color.BLACK);
			mTextState.setText("Rozłączony");
			break;
		case CONNECTION_ERROR:
			mTextState.setTextColor(Color.RED);
			mTextState.setText("Błąd połączenia");
			break;

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.connect:
			Intent in = new Intent(this, SelectDevice.class);
			startActivityForResult(in, GET_DEVICE);
			return true;
		case R.id.disconnect:
			btDisconnect();
			mDevice = null;
			return true;
		
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case GET_DEVICE:
			if (resultCode == Activity.RESULT_OK) {
				btConnect(data.getStringExtra(SelectDevice.DEVICE_ADDRESS));
			}
			break;
		}
	}
	@Override
	public void onResume() {
		super.onResume();
		changeState(STATE);
	}
}