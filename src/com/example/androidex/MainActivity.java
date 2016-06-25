package com.example.androidex;

import com.example.androidex.R;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
		MediaPlayer mp;
		LinearLayout linear;
    TextView nameText;
    ImageButton prev, next, pause;
    
    int songID = 0, initSongID;
    boolean pauseFlag = true;
    final int SONGCNT = 5;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linear = (LinearLayout) findViewById(R.id.container);
        nameText = (TextView) findViewById(R.id.musicName);
        prev = (ImageButton) findViewById(R.id.prevButton);
        next = (ImageButton) findViewById(R.id.nextButton);
        pause = (ImageButton) findViewById(R.id.pauseButton);
        
        prev.setOnClickListener(this);
        next.setOnClickListener(this);
        pause.setOnClickListener(this);
        
        mp = MediaPlayer.create(this, R.raw._1);
        initSongID = R.raw._1;
    }
    
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.nextButton:	//create buttons
					nextSong(1);
	        break;
		case R.id.prevButton:	//create buttons
					nextSong(-1);
	        break;
		case R.id.pauseButton:	//create buttons
					pause();
					break;
		default:
					break;
		}
	}	
	
	public void pause(){
				if (pauseFlag)	mp.start();
				else						mp.pause();
				pauseFlag = !pauseFlag;
	}
	
	public void nextSong(int i){
				if (i == 1)	songID =  (++songID) % 5;
				else if (songID == 0) songID = SONGCNT - 1;
				else									--songID;
				
				mp.stop();
				mp = MediaPlayer.create(this, initSongID + songID);
				mp.start();
	}
	
	@Override
	public void onBackPressed() {
		 mp.stop();
	   Intent intent = new Intent(Intent.ACTION_MAIN);
	   intent.addCategory(Intent.CATEGORY_HOME);
	   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	   startActivity(intent);
	 }

}
