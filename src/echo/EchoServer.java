package echo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	private final static int PORT = 4000;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			//1.Server socket
			serverSocket = new ServerSocket();
			
			//2.Binding(socket address = IP address + port)
			InetAddress inetAddress = InetAddress.getLocalHost();
			String localhost = inetAddress.getHostAddress();
			serverSocket.bind(
					new InetSocketAddress(localhost, PORT)); //InetSocket : IP와 Port로 binding
			System.out.println("[Server] binding " + localhost + ":" + PORT);
			
			while(true) {
				//3.Accept(연결 요청 기다림)
				Socket socket = serverSocket.accept(); //block 됨
				//Xshell에서 telnet 연결하면, (Xshell 실행 -> [~]$telnet IP주소 PORT번호 -> Enter
				
				Thread thread = new EchoServerReceiveThread(socket);
				thread.start();
			}
		} catch (IOException e) { //server socket error
			e.printStackTrace();
		} finally {
			try {
				if(serverSocket != null && serverSocket.isClosed() == false)
					serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
