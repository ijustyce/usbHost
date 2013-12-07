package com.ijustyce.usb;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity{
	
	ServerSocket serverSocket = null;
	final int SERVER_PORT = 10086;
	public static Boolean mainThreadFlag = true;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new Thread()
		{
			public void run()
			{
				doListen();
			};
		}.start();
	}
	
	private void doListen()
	{
		serverSocket = null;
		try
		{
			Log.d("chl", "doListen()");
			serverSocket = new ServerSocket(SERVER_PORT);
			Log.d("chl", "doListen() 2");
			while (mainThreadFlag)
			{
				Log.d("chl", "doListen() 4");
				Socket socket = serverSocket.accept();
				Log.d("chl", "doListen() 3");
				new Thread(new transfer( socket)).start();
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
