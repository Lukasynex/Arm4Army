package com.bine.luk.arm4army;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private OpenGLSurfaceView mGLSurfaceView;
	private LukasynoRenderer mRenderer;
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
	final Handler handler = new Handler();
//	private ConnectedThread mConnectedThread;

	private TextView mTextState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//TODO:
		// naprawić wykrzaczającą się aplikację!!!
		//tzn: wyjebać wszystko, wrzucić wszystko z apki CarController
		//potem próbować dodać layout i klasy openGL ESowe
	}

}
