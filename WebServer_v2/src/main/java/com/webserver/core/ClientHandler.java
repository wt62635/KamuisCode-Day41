package com.webserver.core;

import java.io.File;
import java.net.Socket;

import com.webserver.http.HttpRequest;

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
			//1.解析请求
			HttpRequest request = new HttpRequest(socket);
			
			//2.处理请求
			/*
			 * 2.1通过request获取url,用来得知用户请求的资源的路径
			 * 2.2从webapps目录下根据该资源路径找到对应资源
			 * 2.3判断该资源时是否真实存在
			 * 2.4存在则响应该资源
			 * 2.5不存在则响应404页面
			 */
			// myweb/index.html
			String path = request.getUrl();
			//通过路径找到webapps目录下对应资源。
			File file = new File("webapps" + path);
			//判断用户请求的资源是否真实存在
			if(file.exists()) {
				System.out.println("ClientHandler:资源已找到！");
			}else {
				System.out.println("ClientHandler:资源不存在！");
			}
			
			//3.发送响应
			
//			InputStream in = socket.getInputStream();
//			int d = -1 ;
//			while((d = in.read())!=-1) {
//				System.out.print((char)d);
//			}
			
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
