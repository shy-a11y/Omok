package client.net;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import client.Data;
import client.manager.IOManager;
import client.manager.MessageManager;
import client.manager.PlayerListManager;
import client.ui.BoardCanvas;
import client.ui.GameFrame;

// 클라이언트에서 서버로부터 받은 메시지를 처리 및 그에 따른 동작 수행

public class Resolver {

	// 초기화 메소드
	public void init(String s) {
		// 서버에서 전달된 초기화 메시지를 처리
		// 메시지 형식: "ID-닉네임"
		String str[] = s.split("-");
		String name = str[1];
		int id = Integer.parseInt(str[0]);

		// 데이터에 현재 클라이언트의 ID 및 이름 저장
		Data.myId = id;
		Data.myName = name;

		// 게임 화면 표시
		GameFrame.getInstance().showGamePanel();

		// 플레이어 목록 업데이트 요청을 서버에 전송
		IOManager.getInstance().getPs().println(Header.LIST);
	}

	// 게임 시작 메시지 처리 메소드
	public void startMessage(String s) {
		// 서버에서 전달된 게임 시작 메시지를 화면에 표시
		MessageManager.getInstance().addMessage(s);
	}

	// 게임 시작 처리 메소드
	public void start(String s) {
		// 서버에서 전달된 게임 시작 메시지를 처리하고 클라이언트의 돌 색깔 및 턴 설정

		// BLACK으로 시작하는 경우
		if (s.substring(0, 5).equals("BLACK")) {
			Data.myChess = Data.BLACK;
			Data.oppoChess = Data.WHITE;
			Data.turn = Data.BLACK;
			Data.started = true;
		}
		// WHITE로 시작하는 경우
		if (s.substring(0, 5).equals("WHITE")) {
			Data.myChess = Data.WHITE;
			Data.oppoChess = Data.BLACK;
			Data.turn = Data.BLACK;
			Data.started = true;
		}

		// 게임 화면에서 항복 버튼 텍스트 설정
		GameFrame.getInstance().getFunctionPanel().getOperationPanel().setQuitButtonText("항복");
	}

	// 플레이어 목록 업데이트 메소드
	public void updateList(String s) {
		// 서버에서 전달된 플레이어 목록 업데이트 메세지를 처리하고 화면에 업데이트된 목록 표시
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					// 플레이어 목록 관리자 객체 생성
					PlayerListManager playerListManager = PlayerListManager.getInstance();

					// 플레이어 목록 초기화
					playerListManager.clearList();

					// 받아온 목록을 플레이어 목록에 추가
					String[] players = s.split("&");
					for (String player : players) {
						playerListManager.addPlayer(player);
					}
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	// 플레이어 추가 메소드
	public void addList(String s) {
		// 서버에서 전달된 플레이어 추가 메시지를 처리하고 화면에 추가된 플레이어 표시
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					PlayerListManager.getInstance().addPlayer(s);
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	// 플레이어 삭제 메소드
	public void delList(String s) {
		// 서버에서 전달된 플레이어 삭제 메시지를 처리하고 화면에서 해당 플레이어 삭제
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					PlayerListManager.getInstance().removePlayer(s);
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	// 돌 놓기 메소드
	public void play(String s) {
		// 서버에서 전달된 돌 놓기 메세지를 처리하고, 상대방 돌을 화면에 표시

		// 전달된 메세지에서 돌을 놓을 위치를 추출
		int position = Integer.parseInt(s);
		int x = position % 15;
		int y = position / 15;

		// PlayChess 클래스를 사용하여 돌을 놓는 메소드 호출
		new PlayChess().play(x, y, Data.oppoChess);
	}

	// 채팅 메소드
	public void chat(String s) {
		// 서버에서 전달된 채팅 메세지를 처리하고, 화면에 채팅 내용 표시

		// 전달된 메세지를 채팅 내용과 발신자로 분리
		String str[] = s.split("&");
		String message = str[0];
		String who = str[1];

		// MessageManager 클래스를 사용하여 채팅 메세지를 텍스트 화면에 출력
		MessageManager.getInstance().addMessage(who + "님의 채팅: " + message);
	}

	// 동작 메소드
	public void operation(String s) {
		// 서버에서 전달된 동작 메시지를 처리하고, 클라이언트의 상태를 갱신

		// 상대방이 항복한 경우
		if (s.startsWith(Header.GIVEUP)) {
			// 상대방이 항복했음을 알리는 메시지를 사용자에게 표시
			JOptionPane.showMessageDialog(GameFrame.getInstance(), "상대방이 항복했습니다. 새로운 상대를 선택하세요.");

			// 클라이언트 상태 갱신
			Data.ready = false;
			Data.started = false;

			// 게임 화면의 동작 패널에서 나가기 버튼 텍스트 업데이트
			GameFrame.getInstance().getFunctionPanel().getOperationPanel().setQuitButtonText("나가기");
		}

		// 상대방이 퇴장한 경우
		if (s.startsWith(Header.QUIT)) {
			// 상대방이 퇴장했음을 알리는 메시지를 사용자에게 표시
			JOptionPane.showMessageDialog(GameFrame.getInstance(), "상대방이 퇴장했습니다. 새로운 상대를 선택하세요.");

			// 클라이언트 상태 갱신
			Data.ready = false;
			Data.started = false;

			// 게임 화면의 동작 패널에서 나가기 버튼 텍스트 업데이트
			GameFrame.getInstance().getFunctionPanel().getOperationPanel().setQuitButtonText("나가기");
		}

		// 도전 받음
		if (s.startsWith(Header.CHALLENGE)) {
			// 도전 받은 플레이어의 정보를 파싱하여 처리
			s = s.substring(Header.CHALLENGE.length());
			String[] str = s.split("-");
			int targetId = Integer.parseInt(str[0]);

			// 도전 수락 여부를 사용자에게 확인
			int value = JOptionPane.showConfirmDialog(GameFrame.getInstance(),
					"플레이어 “" + s + "”님이 당신에게 도전하였습니다. 수락하시겠습니까?", "도전 받음",
					JOptionPane.YES_NO_OPTION);

			// 도전 수락
			if (value == JOptionPane.YES_OPTION) {
				// 서버에 도전 수락 메시지 전송
				IOManager.getInstance().getPs().println(Header.REPLY + Header.CHALLENGE + targetId + "&YES");
				JOptionPane.showMessageDialog(GameFrame.getInstance(),
						"도전을 수락하셨습니다. “준비” 버튼을 눌러 게임을 시작하세요.");
				Data.oppoId = targetId;
				GameFrame.getInstance().getFunctionPanel().getPlayerListPanel().getOpponentInfo()
						.setText("현재 상대: " + s);
				MessageManager.getInstance().addMessage("도전을 수락하셨습니다. “준비” 버튼을 눌러 게임을 시작하세요.");
			}
			// 도전 거절
			else {
				// 서버에 도전 거절 메시지 전송
				IOManager.getInstance().getPs().println(Header.REPLY + Header.CHALLENGE + targetId + "&NO");
				MessageManager.getInstance().addMessage("도전을 거절하셨습니다.");
			}
		}
	}

	// 도전 처리 메소드
	public void reply(String s) {
		// 서버에서 전달된 도전 메시지를 처리하고, 도전에 대한 응답 처리

		// 도전에 대한 응답 여부 확인
		if (s.startsWith(Header.CHALLENGE)) {
			// 헤더를 제외한 도전 메시지 파싱
			s = s.substring(Header.CHALLENGE.length());
			// 도전에 대한 응답 정보를 "&" 으로 분리
			String str[] = s.split("&");
			// 도전을 받은 상대방의 정보
			String challenged = str[0];
			// 응답 결과 (YES 또는 NO)
			String result = str[1];

			// 상대방이 도전을 수락한 경우
			if (result.equals("YES")) {
				// 도전을 수락한 상대방의 ID 추출
				int uid = Integer.parseInt(challenged.split("-")[0]);

				// 도전 수락 메시지 팝업
				JOptionPane.showMessageDialog(GameFrame.getInstance(),
						"상대방이 도전을 수락했습니다. “준비” 버튼을 눌러 게임을 시작하세요.");

				// 상대방 ID 설정
				Data.oppoId = uid;

				// 상대방 상태 표시줄 정보 설정
				GameFrame.getInstance().getFunctionPanel().getPlayerListPanel().getOpponentInfo()
						.setText("현재 상대: " + challenged);

				// 게임 채팅 창에 도전 수락 메시지 추가
				MessageManager.getInstance().addMessage("상대방이 도전을 수락했습니다. “준비” 버튼을 눌러 게임을 시작하세요.");
			}
			// 상대방이 도전을 거절한 경우
			else if (result.equals("NO")) {
				// 도전 거절 메시지 팝업
				JOptionPane.showMessageDialog(GameFrame.getInstance(),
						"플레이어 “" + challenged + "”님이 도전을 거절했습니다.");
			}
		}
	}

	// 승리 처리 메소드
	public void win() {
		// 승리 메세지를 플레이어에게 알림
		JOptionPane.showMessageDialog(GameFrame.getInstance(), "당신이 이겼습니다!");

		// 메세지 창에 승리 메세지 추가
		MessageManager.getInstance().addMessage("당신이 이겼습니다!");

		// 데이터 초기화
		Data.last = -1;
		Data.oppoId = 0;
		Data.myChess = 0;
		Data.oppoChess = 0;
		Data.ready = false;
		Data.started = false;
		Data.chessBoard = new int[15][15];

		// 턴 및 게임 진행 여부 초기화
		Data.turn = 0;
		Data.ready = false;
		Data.started = false;

		// 나가기 버튼 텍스트 설정
		GameFrame.getInstance().getFunctionPanel().getOperationPanel().setQuitButtonText("나가기");
	}

	// 패배 처리 메소드
	public void lose() {
		// 패배 메세지를 플레이어에게 알림
		JOptionPane.showMessageDialog(GameFrame.getInstance(), "당신이 졌습니다!");

		// 메세지 창에 패배 메세지 추가
		MessageManager.getInstance().addMessage("당신이 졌습니다!");

		// 턴 및 게임 진행 여부 초기화
		Data.turn = 0;
		Data.ready = false;
		Data.started = false;

		// 나가기 버튼 텍스트 설정
		GameFrame.getInstance().getFunctionPanel().getOperationPanel().setQuitButtonText("나가기");
	}

}