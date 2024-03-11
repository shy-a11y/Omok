package server.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import server.tool.HashMapManager;
import server.tool.MessageManager;
import server.tool.Player;

// 서버 쓰레드

public class ServerThread implements Runnable {

	// 클라이언트와의 통신을 위한 소켓
	Socket socket = null;

	// 생성자
	public ServerThread(Socket s) {
		// 소켓 초기화
		this.socket = s;
		// 새로운 플레이어 객체 생성 및 해당 플레이어를 HashMapManager에 추가
		Player player = new Player(this.hashCode(), s);
		HashMapManager.getInstance().addPlayer(this.hashCode(), player);
	}

	// 쓰레드 실행
	@Override
	public void run() {
		// 클라이언트와의 연결 상태를 나타내는 변수
		boolean connected = true;
		// 클라이언트로부터 수신된 명령을 저장하는 변수
		String s;

		// 새로운 플레이어의 접속을 MessageManager에 추가
		MessageManager.getInstance().addMessage("플레이어 " + this.hashCode() + " 접속");

		// 클라이언트와의 연결이 유지되는 동안 반복
		while (connected) {
			try {
				// 클라이언트로부터 명령을 수신하기 위한 BufferedReader 생성
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				// 클라이언트로부터 명령을 읽음
				s = br.readLine();

				// 수신된 명령을 콘솔에 출력
				System.out.println("[수신 FROM " + this.hashCode() + "]" + s);

				// 명령을 해석하고 처리하는 Resolver 클래스의 resolve 메서드 호출
				new Resolver().resolve(this.hashCode(), socket, s);

			} catch (IOException e) {
				// 클라이언트 접속이 종료된 경우
				connected = false;
				// 콘솔에 클라이언트의 종료 메시지 출력
				System.out.println(this.hashCode() + "님이 접속 종료했습니다.");

				// 클라이언트 종료 처리를 담당하는 EndDeal 클래스의 clientOff 메소드 호출
				new EndDeal().clientOff(this.hashCode());
			}
		}
	}
}
