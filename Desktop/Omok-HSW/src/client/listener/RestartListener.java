package client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.Data;
import client.manager.IOManager;
import client.manager.MessageManager;
import client.net.Header;
import client.ui.BoardCanvas;
import client.ui.GameFrame;

// 재시작 버튼 리스너

public class RestartListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// 연결 상태 확인
		if (Data.connected) {
			// 게임이 시작되지 않은 경우
			if (!Data.started) {
				// 데이터 초기화
				Data.last = -1;
				Data.turn = 0;
				Data.chessBoard = new int[15][15];

				// 돌 색깔 초기화
				Data.myChess = 0;
				Data.oppoChess = 0;

				// 오목 판 다시 그리기
				BoardCanvas boardCanvas = GameFrame.getInstance().getGamePanel().getBoardCanvas();
				boardCanvas.paintBoardImage();
				boardCanvas.repaint();

				// 서버에 재시작 메세지 전송
				IOManager.getInstance().getPs().println(Header.OPERATION + Header.RESTART);
			} else {
				// 게임이 시작된 경우 알림 메세지 추가
				MessageManager.getInstance().addMessage("게임이 아직 끝나지 않았습니다.");
			}
		}
	}
}
