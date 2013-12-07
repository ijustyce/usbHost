package com.ijustyce.usb;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import android.util.Log;

public class transfer implements Runnable {

	private Socket client;

	public transfer(Socket client) {

		this.client = client;
	}

	@Override
	public void run() {

		BufferedOutputStream out;
		BufferedInputStream in;
		try {
			/* PC端发来的数据msg */
			String currCMD = "";
			out = new BufferedOutputStream(client.getOutputStream());
			in = new BufferedInputStream(client.getInputStream());
			while (true) {
				try {
					if (!client.isConnected()) {
						break;
					}

					/* 读操作命令 */
					currCMD = readCMDFromSocket(in);
					
					if(currCMD.equals("exit")){
						
						out.write("exit".getBytes());
						out.flush();
						break;
					}
					
					Log.i("msg", currCMD);

					/* 向pc端发送东西 */
					
					out.write("service receive OK".getBytes());
					out.flush();
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (client != null) {
					client.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/* 读取命令 */
	public String readCMDFromSocket(InputStream in) {
		int MAX_BUFFER_BYTES = 2048;
		String msg = "";
		byte[] tempbuffer = new byte[MAX_BUFFER_BYTES];
		try {
			int numReadedBytes = in.read(tempbuffer, 0, tempbuffer.length);
			msg = new String(tempbuffer, 0, numReadedBytes, "utf-8");
			tempbuffer = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}
}
