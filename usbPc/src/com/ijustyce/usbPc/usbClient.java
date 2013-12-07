package com.ijustyce.usbPc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class usbClient {

	/**
	 * @param args
	 */
	static String order = "adb shell am start -n com.ijustyce.usb/com.ijustyce.usb.MainActivity";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			
			/*端口转发*/
			
			Runtime rt = Runtime.getRuntime();
			System.out.println(order);
			Process proc = rt.exec(order);
			InputStream stderr = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			
			Thread.sleep(3000);
			
			Runtime.getRuntime().exec("adb forward tcp:12580 tcp:10086"); // 端口转换
			startSocket();
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	private static void startSocket(){
		
		Socket socket = null;
		try
		{
			InetAddress serveraddr = null;
			serveraddr = InetAddress.getByName("127.0.0.1");
			System.out.println("TCP 1111" + "C: Connecting...");
			socket = new Socket(serveraddr, 12580);
			System.out.println("TCP 221122" + "C:RECEIVE");
			BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
			BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
			
			boolean flag = true;
			while (flag)
			{
				out.write("hello world".getBytes());
				out.flush();
				
				String currCMD = readFromSocket(in);
				if(currCMD.equals("exit")){
					
					break;
				}
				
				System.out.println(currCMD);
				
				out.write("exit".getBytes());
				out.flush();
			}
		}catch (UnknownHostException e1)
		{
			System.out.println("TCP 331133" + "ERROR:" + e1.toString());
		} catch (Exception e2)
		{
			System.out.println("TCP 441144" + "ERROR:" + e2.toString());
		} finally
		{
			try
			{
				if (socket != null)
				{
					socket.close();
					System.out.println("socket.close()");
				}
			} catch (IOException e)
			{
				System.out.println("TCP 5555" + "ERROR:" + e.toString());
			}
		}
		
	}
	
	/* 从InputStream流中读数据 */
	public static String readFromSocket(InputStream in)
	{
		int MAX_BUFFER_BYTES = 4000;
		String msg = "";
		byte[] tempbuffer = new byte[MAX_BUFFER_BYTES];
		try
		{
			int numReadedBytes = in.read(tempbuffer, 0, tempbuffer.length);
			msg = new String(tempbuffer, 0, numReadedBytes, "utf-8");

			tempbuffer = null;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return msg;
	}

}
