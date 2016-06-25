package ssm.bluetooth;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OptionActivity extends Activity{
	private Button btnStop, btnRestart;
	private static int destination;

	
	static void setDestination(int destination){
		OptionActivity.destination = destination;
	}
	static int getDestination(){
		return OptionActivity.destination;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option);
		
		btnRestart = (Button)findViewById(R.id.buttonRestart);
		btnStop = (Button)findViewById(R.id.buttonStop);
		
		btnRestart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//OptionActivity.setDestination(B);
				String outputData = "C";
				try {
					MainActivity.outputdataStream.writeUTF(outputData);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				
			}
		});
		
		btnStop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String outputData = "D";
				try {
					MainActivity.outputdataStream.writeUTF(outputData);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				
			}
		});
	}	
}
