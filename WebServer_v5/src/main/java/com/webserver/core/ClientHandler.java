package com.webserver.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
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
				//将该资源以标准的HTTP响应格式发送给客户端
				
				OutputStream out = socket.getOutputStream();
				
				//1发送状态行
				String line = "HTTP/1.1 200 OK";
				out.write(line.getBytes("ISO8859-1"));
				out.write(13);//written CR
				out.write(10);//written LF
				
				//2发送响应头
				line = "Content-Type:text/html";
				out.write(line.getBytes("ISO8859-1"));
				out.write(13);//written CR
				out.write(10);//written LF
				
				//告知浏览器响应正文的数据长度（字节）
				line = "Content-Length:" + file.length();
				out.write(line.getBytes("ISO8859-1"));
				out.write(13);//written CR
				out.write(10);//written LF
				
				//单独发送CRLF表示响应头发送
				out.write(13);//written CR
				out.write(10);//written LF
				
				//3.发送响应正文
				//将用户请求的文件数据作为正文发送给客户端
				FileInputStream fis = new FileInputStream(file);
				int len = -1 ;
				byte data[] = new byte[1024*10];
				while((len = fis.read(data))!=-1) {
					out.write(data,0,len);
				}
			}else {
				System.out.println("ClientHandler:资源不存在！");
				
				File file404 = new File("webapps/root/404.html");
				
				OutputStream out = socket.getOutputStream();
				
				//1发送状态行
				String line = "HTTP/1.1 404 NOT FOUND";
				out.write(line.getBytes("ISO8859-1"));
				out.write(13);//written CR
				out.write(10);//written LF
				
				//2发送响应头
				line = "Content-Type:text/html";
				out.write(line.getBytes("ISO8859-1"));
				out.write(13);//written CR
				out.write(10);//written LF
				
				//告知浏览器响应正文的数据长度（字节）
				line = "Content-Length:" + file404.length();
				out.write(line.getBytes("ISO8859-1"));
				out.write(13);//written CR
				out.write(10);//written LF
				
				//单独发送CRLF表示响应头发送
				out.write(13);//written CR
				out.write(10);//written LF
				//3.发送响应正文
				//将用户请求的文件数据作为正文发送给客户端
				FileInputStream fis = new FileInputStream(file404);
				int len = -1 ;
				byte data[] = new byte[1024*10];
				while((len = fis.read(data))!=-1) {
					out.write(data,0,len);
				}
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
