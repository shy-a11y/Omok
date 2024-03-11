package client.net;

import java.io.PrintStream;

import client.Data;
import client.manager.IOManager;
import client.manager.MessageManager;
import client.ui.BoardCanvas;
import client.ui.GameFrame;

// 돌 놓기 동작 처리

public class PlayChess {

	// 돌을 놓는 메소드
	public void play(int x, int y, int chess) {
		// 출력 스트림을 가져 옴
		PrintStream ps = IOManager.getInstance().getPs();

		// 돌 위치 계산
		int position = 15 * y + x;
		// 마지막에 놓은 위치를 업데이트
		Data.last = position;

		// 내 턴에 돌을 놓는 경우
		if (chess == Data.myChess) {
			// 내 돌을 놓은 위치에 표시
			Data.chessBoard[x][y] = Data.myChess;

			// 오목 판을 다시 그리고 화면을 갱신
			BoardCanvas mapCanvas = GameFrame.getInstance().getGamePanel().getBoardCanvas();
			mapCanvas.paintBoardImage();
			mapCanvas.repaint();

			// 상대방 턴으로 변경
			Data.turn = Data.oppoChess;

			// 서버로 놓은 돌의 위치를 전송
			ps.println(Header.PLAY + position);

			// 상대방 턴인 걸 알려 주는 메세지
			MessageManager.getInstance().addMessage("상대방 턴 입니다. 기다려주세요.");
		}

		// 상대방 턴에 돌을 놓는 경우
		if (chess == Data.oppoChess) {
			// 상대방 돌을 놓은 위치에 표시
			Data.chessBoard[x][y] = Data.oppoChess;

			// 오목 판을 다시 그리고 화면을 갱신
			BoardCanvas mapCanvas = GameFrame.getInstance().getGamePanel().getBoardCanvas();
			mapCanvas.paintBoardImage();
			mapCanvas.repaint();

			// 내 턴으로 변경
			Data.turn = Data.myChess;

			// 자신의 턴인 걸 알려 주는 메세지
			MessageManager.getInstance().addMessage("내 턴 입니다. 착수하세요.");
		}
	}
}