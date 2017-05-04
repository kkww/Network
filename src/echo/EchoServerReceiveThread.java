package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServerReceiveThread extends Thread {
	private Socket socket;
	
	public EchoServerReceiveThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try{
			//4.연결됨
			InetSocketAddress remoteSocketAddress =
					(InetSocketAddress) socket.getRemoteSocketAddress();
			//socket address = ip + port
			String remoteAddress = remoteSocketAddress.getAddress().getHostAddress();
			int remotPort = remoteSocketAddress.getPort();
			System.out.println("[Server] Connection is success.\nclient IP:PORT is " + remoteAddress + ":" + remotPort);
			
			//5.IOStream 받아오기
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
				
				System.out.println("[Server] receive msg : " + message);
				pw.println(message);
			}
		} catch (SocketException e) {
			System.out.println("[Server] 연결 끊어짐(Client가 비정상 종료됨)");
		} catch (IOException e) { //통신 error
			e.printStackTrace();
		} finally {
			try {
				if(socket != null && socket.isClosed() == false)
					socket.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

}
