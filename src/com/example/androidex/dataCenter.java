package com.example.androidex;

public class dataCenter {
	static int bytes=0;
	static int volume=0;
	static int light=0;
	static int swipe=0;
	static int noise=0;
	static int noiseflag=0;
	static int swipeflag=0;
	static int volumeflag=0;
	static int lightflag=0;
	static boolean exitflag = false;
	static String recieved;
	
	static void control(){
		if (lightflag >= 10)
				exitflag = true;
	}
	public static int getVolume() {
		return volume;
	}
	public static void setVolume(int v) {
		dataCenter.volume = v;
	}
	public static int getLight() {
		return light;
	}
	public static void setLight(int l) {
		if (l <= 500)
			lightflag++;
		else lightflag = 0;
		dataCenter.light = l;
	}
	public static int getSwipe() {
		return swipe;
	}
	public static void setSwipe(int s) {
		dataCenter.swipe = s;
	}
	public static int getNoise() {
		return noise;
	}
	public static void setNoise(int n) {
		dataCenter.noise = n;
	}
	public static int getNoiseflag() {
		return noiseflag;
	}
	public static int getSwipeflag() {
		return swipeflag;
	}
	public static int getVolumeflag() {
		return volumeflag;
	}
	public static int getLightflag() {
		return lightflag;
	}	
	
	static int get_bytes(){
		return bytes;
	}
	static String get_string(){
		return recieved;
	}	
	static void set_bytes(int b){
		bytes = b;
	}
	static void set_string(String s){
		recieved = s;
	}
	public static int getBytes() {
		return bytes;
	}
	public static void setBytes(int bytes) {
		dataCenter.bytes = bytes;
	}
	public static boolean getExit() {
		return exitflag;
	}
	public static void setExitflag(boolean f) {
		exitflag = f;
	}
	
}
