package server.net;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import server.tool.HashMapManager;
import server.tool.MessageManager;

// readLine으로 클라이언트로부터 수신된 명령을 해석하고 처리

public class Resolver {

	// 소켓, 플레이어 ID, 수신된 메시지를 저장하는 변수
	Socket socket;
	int uid;
	String readLine;

	// readLine 클라이언트로부터 수신된 명령을 해석하고 처리하는 메소드
	public void resolve(int _uid, Socket s, String _readLine) {
		// 전달받은 소켓, 플레이어 ID, 메시지를 클래스 변수에 저장
		this.socket = s;
		this.uid = _uid;
		this.readLine = _readLine;

		try {
			// 클라이언트로부터의 출력 스트림을 얻어옴
			PrintStream ps = new PrintStream(socket.getOutputStream());

			// 목록 갱신 명령 처리
			if (readLine.startsWith(Header.LIST)) {
				new Action().getList(socket); // 목록 갱신을 위한 명령을 Action 클래스의 getList 메소드에 전달
			}

			// 응답 명령 처리
			if (readLine.startsWith(Header.REPLY)) {
				// Header.REPLY 이후의 문자열을 추출
				String str = readLine.substring(Header.REPLY.length());
				// 도전 응답 명령 처리
				if (str.startsWith(Header.CHALLENGE)) {
					// Header.CHALLENGE 이후의 문자열을 추출하고, replyChallenge 메소드 호출
					new Action().replyChallenge(uid, str.substring(Header.CHALLENGE.length()));
				}
			}

			// 돌 놓기 명령 처리
			if (readLine.startsWith(Header.PLAY)) {
				// Header.PLAY 이후의 문자열을 추출
				String str = readLine.substring(Header.PLAY.length());

				// 추출한 문자열을 정수로 변환하여 position 변수에 저장
				int position = Integer.parseInt(str);

				// playChess 메소드 호출
				new Action().playChess(uid, position);
			}

			// 채팅 명령 처리
			if (readLine.startsWith(Header.CHAT)) {
				// Header.CHAT 이후의 문자열을 추출
				String str = readLine.substring(Header.CHAT.length());

				// sendMessage 메소드 호출
				new Action().sendMessage(uid, str);
			}

			// 동작 명령 처리
			if (readLine.startsWith(Header.OPERATION)) {
				// Header.OPERATION 이후의 문자열을 추출
				String str = readLine.substring(Header.OPERATION.length());

				// 플레이어 도전 명령 처리
				if (str.startsWith(Header.CHALLENGE)) {
					str = str.substring(Header.CHALLENGE.length());
					// Header.CHALLENGE 이후의 문자열을 추출하고, 정수로 변환하여 target 변수에 저장
					int target = Integer.parseInt(str);
					// 도전 명령을 Action 클래스의 sendChallenge 메소드에 전달
					new Action().sendChallenge(uid, target);
					// 매칭 정보를 갱신
					HashMapManager.getInstance().getMatching().put(uid, target);
				}

				// 준비 명령 처리
				if (str.startsWith(Header.START)) {
					// 준비 명령을 Action 클래스의 ready 메소드에 전달
					new Action().ready(uid);
				}

				// 재시작 명령 처리
				if (str.startsWith(Header.RESTART)) {
					// 재시작 명령을 Action 클래스의 restart 메소드에 전달
					new Action().restart(uid);
				}

				// 나가기 명령 처리
				if (str.startsWith(Header.QUIT)) {
					// Header.QUIT 이후의 문자열을 추출하고, 정수로 변환하여 oppoId 변수에 저장
					int oppoId = Integer.parseInt(str.substring(Header.QUIT.length()));
					// 나가기 명령을 Action 클래스의 quit 메소드에 전달
					new Action().quit(uid, oppoId);
				}

				// 포기 명령 처리
				if (str.startsWith(Header.GIVEUP)) {
					// Header.GIVEUP 이후의 문자열을 추출하고, 정수로 변환하여 oppoId 변수에 저장
					int oppoId = Integer.parseInt(str.substring(Header.GIVEUP.length()));
					// 포기 명령을 Action 클래스의 giveUp 메소드에 전달
					new Action().giveUp(uid, oppoId);
				}
			}

			// 초기 정보 명령 처리
			if (readLine.startsWith(Header.INIT)) {
				// Header.INIT 이후의 문자열을 추출하여 플레이어의 이름으로 설정
				HashMapManager.getInstance().getPlayer(uid).setName(readLine.substring(Header.INIT.length()));
				// 새로운 클라이언트 추가를 Action 클래스의 newClient 메소드에 전달
				new Action().newClient(uid);
				// 초기 정보 응답 전송
				ps.println(Header.INIT + uid + "-" + readLine.substring(Header.INIT.length()));
			}

		} catch (IOException e) {
			// 예외 발생 시 메시지 출력
			MessageManager.getInstance().addMessage("출력 스트림 가져오기 에러");
			e.printStackTrace();
		}
	}

}
