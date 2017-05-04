package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Socket socket = null;
		try {
			//1.Socket
			socket = new Socket();
		
			//2.Server connection
			socket.connect(new InetSocketAddress("192.168.1.27", 4000));
			
			//3.IO stream 받아오기
			BufferedReader br =
					new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			PrintWriter pw =
					new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true); //true 하면 자동으로 flush(buffer비움)
			
			while(true) {
				System.out.print("input>>");
				String message = scanner.nextLine();
				if("exit".equals(message)) {
					break;
				}
				
				//메세지 보내기
				pw.println(message);
				
				//echo 메세지 읽어오기
				String echoMessage = br.readLine();
				if(echoMessage == null) {
					System.out.println("[Client] 연결이 끊어짐(Server가 연결을 정상 종료)");
					break;
				}
				
				//출력
				System.out.println("<<" + echoMessage);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
			try {
				if(socket != null && socket.isClosed() == false)
					socket.close(); //socket만 종료하면 모두 close 됨
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
