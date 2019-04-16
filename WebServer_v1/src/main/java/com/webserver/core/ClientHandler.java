package com.webserver.core;

import java.io.InputStream;
import java.net.Socket;

/**
 * 用于处理客户端请求
 * @author Administrator
 *
 */
public class ClientHandler implements Runnable{
	private Socket socket;
	public ClientHandler(Socket socket) {
		this.socket = socket;
	}
	public void run() {
		try {
			System.out.println("ClientHandler:开始处理请求");
			
		
			
			InputStream in = socket.getInputStream();
			int d = -1 ;
			while((d = in.read())!=-1) {
				System.out.print((char)d);
			}
			
			System.out.println("ClientHandler:处理完毕！");
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			//处理完毕后与客户端断开连接
			try {
				socket.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
