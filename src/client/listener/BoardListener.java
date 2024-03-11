package client.listener;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import client.Data;
import client.manager.MessageManager;
import client.net.PlayChess;
import client.ui.BoardCanvas;

// 오목 판 리스너

public class BoardListener extends MouseAdapter {

	@Override
	public void mousePressed(MouseEvent e) {
		// 이벤트가 발생한 캔버스를 가져옴
		BoardCanvas canvas = (BoardCanvas) e.getSource();

		// 서버와의 연결 확인
		if (Data.connected) {
			// 상대방 선택 확인
			if (Data.oppoId != 0) {
				// 상대방과의 게임 준비 확인
				if (Data.ready) {
					// 게임이 시작되었는지 확인
					if (Data.started) {
						// 내 차례인지 확인
						if (Data.turn == Data.myChess) {
							// 마우스 클릭 위치가 오목 판 범위 안에 있는지 확인
							if (e.getX() < canvas.getMapWidth() - 6 && e.getY() < canvas.getHeight() - 7) {
								// 클릭한 위치를 오목 보드 상의 좌표로 변환
								int x = e.getX() / 35;
								int y = e.getY() / 35;
								// 해당 위치에 이미 돌이 없는지 확인
								if (Data.chessBoard[x][y] == 0) {
									// 해당 위치에 돌을 놓고 서버에 플레이 메시지 전송
									new PlayChess().play(x, y, Data.myChess);
								} else {
									MessageManager.getInstance().addMessage("이곳에는 놓을 수 없습니다.");
								}
							}
						} else if (Data.turn == Data.oppoChess) {
							// 상대방의 차례일 때의 메시지
							MessageManager.getInstance().addMessage("당신의 차례가 아닙니다.");
						}
					} else {
						// 게임 시작 대기 중일 때의 메시지
						MessageManager.getInstance().addMessage("상대방이 준비하기를 기다리는 중입니다.");
					}
				} else {
					// 게임 시작 전인 경우의 메시지
					MessageManager.getInstance().addMessage("게임을 시작하려면 먼저 준비하십시오.");
				}
			} else {
				// 상대방 선택 전인 경우의 메시지
				MessageManager.getInstance().addMessage("먼저 상대방을 선택하십시오.");
			}
		}
	}

	// 마우스가 컴포넌트에 들어왔을 때 호출되는 이벤트
	@Override
	public void mouseEntered(MouseEvent e) {
		// 이벤트가 발생한 컴포넌트를 BoardCanvas로 캐스팅하여 가져옴
		BoardCanvas canvas = (BoardCanvas) e.getSource();
		// 커서를 손가락 모양으로 변경
		canvas.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	// 마우스가 컴포넌트에서 나갔을 때 호출되는 이벤트 핸들러
	@Override
	public void mouseExited(MouseEvent e) {
		// 이벤트가 발생한 컴포넌트를 BoardCanvas로 캐스팅하여 가져옴
		BoardCanvas canvas = (BoardCanvas) e.getSource();
		// 커서를 기본 화살표 모양으로 변경
		canvas.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

}
