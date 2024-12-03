package client.net;

import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JOptionPane;

import client.Data;
import client.manager.IOManager;
import client.manager.PlayerListManager;
import client.manager.MessageManager;
import client.ui.GameFrame;

// 서버로부터 데이터 수신 및 해석하여 동작 수행 쓰레드

public class Receiver implements Runnable {

	@Override
	public void run() {
		// 입력 스트림을 가져 옴
		BufferedReader br = IOManager.getInstance().getBr();
		String s;

		// 서버와 연결되어 있는 동안 반복
		while (Data.connected) {
			try {
				// 서버로부터 명령을 수신
				s = br.readLine();

				// 수신된 명령을 콘솔에 출력
				System.out.println("[RECEIVE]" + s);

				// 명령에 따라 적절한 동작을 수행

				// 초기화 메세지 수신
				if (s.startsWith(Header.INIT)) {
					new Resolver().init(s.substring(Header.INIT.length()));
				}

				// 게임 시작 메세지 수신
				if (s.startsWith(Header.STARTMSG)) {
					new Resolver().startMessage(s.substring(Header.STARTMSG.length()));
				}

				// 게임 시작 명령 수신
				if (s.startsWith(Header.START)) {
					new Resolver().start(s.substring(Header.START.length()));
					notifyPlayers();
				}

				// 플레이어 목록 업데이트 명령 수신
				if (s.startsWith(Header.LIST)) {
					new Resolver().updateList(s.substring(Header.LIST.length()));
				}

				// 플레이어 추가 명령 수신
				if (s.startsWith(Header.ADDPLAYER)) {
					new Resolver().addList(s.substring(Header.ADDPLAYER.length()));
				}

				// 플레이어 삭제 명령 수신
				if (s.startsWith(Header.DELETEPLAYER)) {
					new Resolver().delList(s.substring(Header.DELETEPLAYER.length()));
				}

				// 돌 놓기 명령 수신
				if (s.startsWith(Header.PLAY)) {
					new Resolver().play(s.substring(Header.PLAY.length()));
				}

				// 채팅 명령 수신
				if (s.startsWith(Header.CHAT)) {
					new Resolver().chat(s.substring(Header.CHAT.length()));
				}

				// 동작 명령 수신
				if (s.startsWith(Header.OPERATION)) {
					new Resolver().operation(s.substring(Header.OPERATION.length()));
				}

				// 답장 명령 수신
				if (s.startsWith(Header.REPLY)) {
					new Resolver().reply(s.substring(Header.REPLY.length()));
				}

				// 승리 명령 수신
				if (s.startsWith(Header.WIN)) {
					new Resolver().win();
				}

				// 패배 명령 수신
				if (s.startsWith(Header.LOSE)) {
					new Resolver().lose();
				}

				// 상태 초기화 명령 수신
				if (s.startsWith(Header.RESET)) {
					// 상태 초기화
					Data.started = false;
					Data.turn = 0;
					Data.myChess = 0;
					Data.oppoChess = 0;
					Data.chessBoard = new int[15][15];
					// 추가적으로 필요한 초기화 로직
				}

			} catch (IOException e) {
				// 서버와의 연결이 끊어진 경우 데이터 초기화
				Data.last = -1;
				Data.turn = -1;
				Data.myId = 0;
				Data.oppoId = 0;
				Data.myName = null;
				Data.myChess = 0;
				Data.oppoChess = 0;
				Data.ready = false;
				Data.started = false;
				Data.connected = false;
				Data.chessBoard = new int[15][15];
				// PlayerListManager의 인스턴스를 통해 플레이어 목록 초기화
				PlayerListManager.getInstance().clearList();
				// 게임 화면의 기능 패널에서 플레이어 목록 패널의 상대 정보 레이블 업데이트
				GameFrame.getInstance().getFunctionPanel().getPlayerListPanel().getOpponentInfo().setText("현재 상대: 없음");
				// 게임 화면의 기능 패널에서 메시지 패널의 텍스트 필드 초기화
				GameFrame.getInstance().getFunctionPanel().getMessagePanel().getMessageTextField().setText("");
				// 플레이어에게 알림 창을 띄우고 로그인 화면으로 전환
				JOptionPane.showMessageDialog(GameFrame.getInstance(), "서버와의 연결이 끊어졌습니다.");
				GameFrame.getInstance().showLoginPanel();
			}
		}
	}

	public void notifyPlayers() {
		if (Data.myChess == Data.BLACK) {
			MessageManager.getInstance().addMessage("게임 시작, 돌을 놓으세요");
		} else if (Data.myChess == Data.WHITE) {
			MessageManager.getInstance().addMessage("게임 시작, 상대방이 돌을 놓을 때까지 기다리세요");
		} else {
			MessageManager.getInstance().addMessage("잘못된 상태입니다.");
		}
	}
}
