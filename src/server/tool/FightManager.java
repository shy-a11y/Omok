package server.tool;

import java.io.IOException;
import java.io.PrintStream;

import server.net.Header;

// 오목 게임의 진행을 담당하는 오목 매니저

public class FightManager {

	// 플레이어 A가 재시작에 동의했는지 여부
	boolean restartA = false;

	// 플레이어 B가 재시작에 동의했는지 여부
	boolean restartB = false;

	// 현재 턴 👉 흑 돌 먼저 시작
	int turn = 0;

	// 흑 돌
	int BLACK = 1;

	// 백 돌
	int WHITE = -1;

	// 흑돌 플레이어 ID
	int playerA;

	// 백돌 플레이어 ID
	int playerB;

	// 플레이어 A에 대한 출력 스트림
	PrintStream psA = null;

	// 플레이어 B에 대한 출력 스트림
	PrintStream psB = null;

	// 오목 판
	int[][] chessBoard = new int[15][15];

	// 플레이어 A의 출력 스트림 반환 get 메소드
	public PrintStream getPsA() {
		// 출력 스트림이 아직 생성되지 않았을 때
		if (psA == null) {
			try {
				// HashMapManager를 통해 플레이어 A의 정보를 가져와서 소켓의 출력 스트림 생성
				psA = new PrintStream(HashMapManager.getInstance().getPlayer(playerA).socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 생성된 출력 스트림 psA 반환
		return psA;
	}

	// 플레이어 B의 출력 스트림 반환 get 메소드
	public PrintStream getPsB() {
		// 출력 스트림이 아직 생성되지 않았을 때
		if (psB == null) {
			try {
				// HashMapManager를 통해 플레이어 B의 정보를 가져와서 소켓의 출력 스트림 생성
				psB = new PrintStream(HashMapManager.getInstance().getPlayer(playerB).socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 생성된 출력 스트림 psB 반환
		return psB;
	}

	// 플레이어 A의 ID 설정 메소드
	public void setPlayerA(int playerA) {
		this.playerA = playerA;
	}

	// 플레이어 B의 ID 설정 메소드
	public void setPlayerB(int playerB) {
		this.playerB = playerB;
	}

	// 시작 메시지 전송 메소드
	public void sendStartMessage() {
		// 각 플레이어에게 게임 시작 메시지를 전송하고, 돌을 놓을 차례를 채팅으로 알려줌
		this.getPsA().println(Header.STARTMSG + "게임 시작, 돌을 놓으세요");
		this.getPsB().println(Header.STARTMSG + "게임 시작, 상대방이 돌을 놓을 때까지 기다리세요");
		// 흑 돌이 먼저 시작하므로 현재 턴을 BLACK(흑 돌)로 설정
		turn = BLACK;
	}

	// 게임 시작 메소드
	public void startPlay(int playerA, int playerB) {
		// 플레이어 A와 B에게 시작 메시지를 전송하고 돌의 색상을 설정
		this.setPlayerA(playerA);  // 플레이어 A의 ID 설정
		this.setPlayerB(playerB);  // 플레이어 B의 ID 설정
		this.getPsA().println(Header.START + "BLACK");  // 플레이어 A에게 BLACK(흑 돌) 메시지 전송
		this.getPsB().println(Header.START + "WHITE");  // 플레이어 B에게 WHITE(백 돌) 메시지 전송
	}

	// 돌 놓기 메시지 전송 메소드
	public void sendPlay(int from, int position) {
		// 플레이어 A
		if (from == playerA) {
			// 플레이어 A가 돌을 놓은 경우
			int x = position % 15;  // 돌의 x 좌표 계산
			int y = position / 15;  // 돌의 y 좌표 계산
			chessBoard[x][y] = BLACK;  // 오목 판에 흑 돌(1)을 놓음
			turn = WHITE;  // 턴을 플레이어 B(백 돌)으로 변경
			this.getPsB().println(Header.PLAY + position);  // 플레이어 B에게 돌 놓기 메시지 전송

			// 승리 확인
			if (this.checkWin(x, y, BLACK)) {
				this.getPsA().println(Header.WIN);  // 플레이어 A에게 승리 메시지 전송
				this.getPsB().println(Header.LOSE);  // 플레이어 B에게 패배 메시지 전송
				HashMapManager.getInstance().getReadys().remove(playerA);  // 플레이어 A의 준비 상태 제거
				HashMapManager.getInstance().getReadys().remove(playerB);  // 플레이어 B의 준비 상태 제거
			}
			// 플레이어 B
		} else if (from == playerB) {
			// 플레이어 B가 돌을 놓은 경우
			int x = position % 15;  // 돌의 x 좌표 계산
			int y = position / 15;  // 돌의 y 좌표 계산
			chessBoard[x][y] = WHITE;  // 오목 판에 백돌(-1)을 놓음
			turn = BLACK;  // 턴을 플레이어 A(흑돌)으로 변경
			this.getPsA().println(Header.PLAY + position);  // 플레이어 A에게 돌 놓기 메시지 전송

			// 승리 확인
			if (this.checkWin(x, y, WHITE)) {
				this.getPsB().println(Header.WIN);  // 플레이어 B에게 승리 메시지 전송
				this.getPsA().println(Header.LOSE);  // 플레이어 A에게 패배 메시지 전송
				HashMapManager.getInstance().getReadys().remove(playerA);  // 플레이어 A의 준비 상태 제거
				HashMapManager.getInstance().getReadys().remove(playerB);  // 플레이어 B의 준비 상태 제거
			}
		} else {
			// 잘못된 출처 ID로 인한 오류 처리
			MessageManager.getInstance().addMessage("돌 놓기 메시지 전송 오류: " + Header.PLAY + position);
			System.out.println("돌 놓기 메시지 전송 오류: " + Header.PLAY + position);
			System.out.println("출처 ID: " + from);
			System.out.println("플레이어 A ID: " + playerA);
			System.out.println("플레이어 B ID: " + playerB);
		}
	}

	// 승리 확인 메소드
	public boolean checkWin(int x, int y, int id) {
		// BoardChecker 클래스를 사용하여 특정 좌표에서 특정 플레이어의 승리 여부를 확인
		// check 메소드 👉 주어진 좌표에서 네 방향으로 5개의 돌이 연속으로 놓여 있는지를 확인해 승리 여부 반환
		return (new BoardChecker()).check(x, y, id, chessBoard);
	}

	// 다시 시작 요청 메소드
	public void restart(int uid) {
		// 플레이어 A의 다시 시작 요청 여부 확인
		if (uid == playerA) {
			// 다시 시작을 아직 수락하지 않았다면 수락 처리
			if (!restartA) {
				restartA = true;
			}
			// 플레이어 B의 다시 시작 요청 여부 확인
		} else {
			// 다시 시작을 아직 수락하지 않았다면 수락 처리
			if (!restartB) {
				restartB = true;
			}
		}

		// 두 플레이어가 모두 다시 시작을 눌렀을 경우
		if (restartA && restartB) {
			// 출력 스트림 초기화
			psA = null;
			psB = null;

			// 오목 판 초기화
			chessBoard = new int[15][15];

			// 플레이어 순서를 바꿔서 게임 재시작(흑 돌이 선)
			this.startPlay(playerB, playerA);

			// 다시 시작 메시지 전송
			this.sendStartMessage();

			// 다시 시작 여부 초기화
			restartA = false;
			restartB = false;
		}
	}
}