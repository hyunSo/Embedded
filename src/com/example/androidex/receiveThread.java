package com.example.androidex;

import java.io.IOException;
import java.io.InputStream;

import android.bluetooth.BluetoothSocket;
import android.widget.Toast;

import com.example.androidex.dataCenter;

public class receiveThread extends Thread {
	private final BluetoothSocket mmSocket;
	private final InputStream mmInStream;

	public receiveThread(BluetoothSocket socket) {
		mmSocket = socket;
		InputStream tmpIn = null;

		// BluetoothSocketÀÇ inputstream °ú outputstreamÀ» ŸòŽÂŽÙ.
		try {
			tmpIn = socket.getInputStream();
		} catch (IOException e) {
		}

		mmInStream = tmpIn;
	}

	public void run() {
		byte[] buffer = new byte[50];
		while(true){
/*			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
*/		// Keep listening to the InputStream while connected
			try {
				// InputStreamÀž·ÎºÎÅÍ °ªÀ» ¹ÞŽÂ ÀÐŽÂ ºÎºÐ(°ªÀ» ¹ÞŽÂŽÙ)
				mmInStream.read(buffer);
				String tmp = new String(buffer);
				if (buffer[0] == '1'){
					
				}
				else if (buffer[0] == '2'){
					
				}
				else if (buffer[0] == '3'){
					
				}
				else if (buffer[0] == '4'){
					
				}
				else if (buffer[0] == '5'){
					
				}
				else if (buffer[0] == '6'){
					
				}
				else if (buffer[0] == '0'){
					dataCenter.setExitflag(true);
				}
				
				dataCenter.control();
				} catch (IOException e) {
					dataCenter.getLight();
					
			}
/*
			try {
				// InputStreamÀž·ÎºÎÅÍ °ªÀ» ¹ÞŽÂ ÀÐŽÂ ºÎºÐ(°ªÀ» ¹ÞŽÂŽÙ)
				mmInStream.read(buffer);
				String tmp = new String(buffer);
				dataCenter.set_string(tmp);
				String[] Str = tmp.split("\n");
				String[] token = Str[1].split("-|\r|\n");

				if (token.length == 4){
					dataCenter.setVolume(Integer.parseInt(token[0]));
					dataCenter.setLight(Integer.parseInt(token[1]));
					dataCenter.setSwipe(Integer.parseInt(token[2]));
					dataCenter.setNoise(Integer.parseInt(token[3]));
					
					dataCenter.control();
				}
				else
					dataCenter.getLight();
				
				} catch (IOException e) {
					dataCenter.getLight();
					
			}
			*/
		}
	}
}
