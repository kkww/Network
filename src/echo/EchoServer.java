package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	public static void main(String[] args) {
		try {
			//1.Server socket
			ServerSocket serverSocket =
					new ServerSocket();
			
			//2.Binding(socket address = IP address + port)
			serverSocket.bind(
					new InetSocketAddress("192.168.1.27", 4000)); //InetSocket : IP와 Port로 binding
			
			//3.Accept(연결 요청 기다림)
			Socket socket = serverSocket.accept(); //block 됨
			//Xshell에서 telnet 연결하면, (Xshell 실행 -> [~]$telnet IP주소 PORT번호 -> Enter
			System.out.println("[Server] 연결됨");
			
			try{
				//4.연결됨
				/*InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				
				BufferedReader br =
						new BufferedReader(new InputStreamReader(is, "utf-8"));
				PrintWriter pw =
						new PrintWriter(new OutputStreamWriter(os, "utf-8"), true); //true 하면 자동으로 flush(buffer비움)
				 */
				BufferedReader br =
						new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
				PrintWriter pw =
						new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true); //true 하면 자동으로 flush(buffer비움)
				
				while(true) {
					String message = br.readLine();
					
					if(message == null) {
						System.out.println("[Server] 연결 끊어짐(Client가 정상 종료됨)"); //Ctrl+Alt+] 누름 -> [~]$disconnect 입력 -> 연결 완전히 끊김
						break;
					}
					
					System.out.println("[Server] " + message);
					pw.println(message);
				}
			} catch (IOException e) { //통신 error
				e.printStackTrace();
			}
		} catch (IOException e) { //server socket error
			e.printStackTrace();
		}
	}

}
