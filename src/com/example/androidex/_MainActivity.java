package ssm.bluetooth;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {//implements OnClickListener {
	// Debugging
	private static final String TAG = "Main";

	// Intent request code
	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;

	// Layout
	private Button btn_Connect;
	private TextView txt_Result;

	private Button btnDestinationA, btnDestinationB;
	private final int A = 0, B = 1; 

	private BluetoothService btService = null;

	final String ev3Address = "00:16:53:47:F3:97";
	BluetoothAdapter localAdapter;
	BluetoothSocket socketEv3;
	boolean success=false;

	public static OutputStream mmOutputStream;
	public static InputStream	mmInputStream;

	
	public static DataInputStream inputdataStream;
	public static DataOutputStream outputdataStream;
	

	//Enables Bluetooth if not enabled
	public void enableBT(){
		localAdapter=BluetoothAdapter.getDefaultAdapter();
		//If Bluetooth not enable then do it
		if(localAdapter.isEnabled()==false){
			localAdapter.enable();
			while(!(localAdapter.isEnabled())){

			}
		}

	}

	//connect to both NXTs
	public  boolean connectToEV3(){

		//get the BluetoothDevice of the NXT
		BluetoothDevice ev3Device = localAdapter.getRemoteDevice(ev3Address);
		//try to connect to the nxt
		try {
			socketEv3 = ev3Device.createRfcommSocketToServiceRecord(UUID
					.fromString("00001101-0000-1000-8000-00805F9B34FB"));

			socketEv3.connect();
			
			mmOutputStream = socketEv3.getOutputStream();
            mmInputStream = socketEv3.getInputStream();

            inputdataStream = new DataInputStream(mmInputStream);
            outputdataStream = new DataOutputStream(mmOutputStream);
            
			success = true;

		} catch (IOException e) {
			Log.d("Bluetooth","Err: Device not found or cannot connect");
			success=false;

		}
		return success;

	}



	private final Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(TAG, "onCreate");

		setContentView(R.layout.main);




		/** Main Layout **/
		btn_Connect = (Button) findViewById(R.id.btn_connect);
		txt_Result = (TextView) findViewById(R.id.txt_result);

		btn_Connect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(btService.getDeviceState()) {
					// 블루투스가 지원 가능한 기기일 때
					//btService.enableBluetooth();
					//
					try{
						enableBT();
						while(connectToEV3());
						//sendBtn.setText("????");
					}
					catch(Exception e){
						e.printStackTrace();
					}

				} else {
					finish();
				}
			}
		});

		btnDestinationA = (Button)findViewById(R.id.buttonDestA);
		btnDestinationB = (Button)findViewById(R.id.buttonDestB);

		// BluetoothService 클래스 생성
		if(btService == null) {
			btService = new BluetoothService(this, mHandler);
		}

		btnDestinationA.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				OptionActivity.setDestination(A);
				String outputData = "A";
				try {
					outputdataStream.writeUTF(outputData);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				Intent intent = new Intent(MainActivity.this,OptionActivity.class);
				startActivity(intent);
			}
		});

		btnDestinationB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//btnDestinationB.setText("clicked");
				//OptionActivity.setDestination(B);
				//Intent intent = new Intent(MainActivity.this,OptionActivity.class);
				//startActivity(intent);
				OptionActivity.setDestination(B);
				String outputData = "B";
				try {
					outputdataStream.writeUTF(outputData);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				Intent intent = new Intent(MainActivity.this,OptionActivity.class);
				startActivity(intent);
			}
		});


	}


	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "onActivityResult " + resultCode);

		switch (requestCode) {

		/** 추가된 부분 시작 **/
		case REQUEST_CONNECT_DEVICE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				btService.getDeviceInfo(data);
			}
			break;
			/** 추가된 부분 끝 **/
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				// Next Step
				btService.scanDevice();
			} else {

				Log.d(TAG, "Bluetooth is not enabled");
			}
			break;
		}
	}

	/*
	void openBT() throws IOException {
		                //SerialPortService ID
		mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);    
		mmSocket.connect();
		mmOutputStream = mmSocket.getOutputStream();
		mmInputStream = mmSocket.getInputStream();
		beginListenForData();
		myLabel.setText("Bluetooth Opened");
	}



	void beginListenForData() {
		final Handler handler = new Handler(); 
		final byte delimiter = 10; //This is the ASCII code for a newline character

		stopWorker = false;
		readBufferPosition = 0;
		readBuffer = new byte[1024];
		workerThread = new Thread(new Runnable() {
			public void run() {
				while(!Thread.currentThread().isInterrupted() && !stopWorker) {
					try {
						int bytesAvailable = mmInputStream.available();            
						if(bytesAvailable > 0) {
							byte[] packetBytes = new byte[bytesAvailable];
							mmInputStream.read(packetBytes);
							for(int i=0;i<bytesAvailable;i++) {
								byte b = packetBytes[i];
								if(b == delimiter) {
									byte[] encodedBytes = new byte[readBufferPosition];
									System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
									final String data = new String(encodedBytes, "US-ASCII");
									readBufferPosition = 0;

									handler.post(new Runnable() {
										public void run() {
											myLabel.setText(data);
										}
									});
								}else {
									readBuffer[readBufferPosition++] = b;
								}
							}
						}
					}catch (IOException e) {
						stopWorker = true;
					}
				}
			}
		});

		workerThread.start();
	}

	void sendData() throws IOException {
		String msg = myTextbox.getText().toString();
		msg += "\n";
		//mmOutputStream.write(msg.getBytes());
		mmOutputStream.write('A');
		myLabel.setText("Data Sent");
	}
	 */
}
