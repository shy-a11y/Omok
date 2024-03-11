package client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import client.Data;
import client.manager.IOManager;
import client.manager.MessageManager;
import client.net.Header;
import client.ui.BoardCanvas;
import client.ui.GameFrame;


// 나가기/항복 버튼 리스너

public class QuitListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// 상대방이 있을 때만 동작
		if (Data.oppoId != 0) {
			// 게임이 시작되었는지 확인
			if (Data.started) {
				// 항복 여부 YES/NO로 확인
				int value = JOptionPane.showConfirmDialog(GameFrame.getInstance(), "게임이 아직 끝나지 않았습니다. 항복하시겠습니까?", "항복",
						JOptionPane.YES_NO_OPTION);
				// YES 선택
				if (value == JOptionPane.YES_OPTION) {
					// 항복 처리
					JOptionPane.showMessageDialog(GameFrame.getInstance(), "당신은 항복했습니다!");
					// 항복했음을 서버에 알림
					IOManager.getInstance().getPs().println(Header.OPERATION + Header.GIVEUP + Data.oppoId);

					// 데이터 초기화
					Data.last = -1;
					Data.oppoId = 0;
					Data.myChess = 0;
					Data.oppoChess = 0;
					Data.ready = false;
					Data.started = false;
					Data.chessBoard = new int[15][15];

					// UI 업데이트
					GameFrame.getInstance().getFunctionPanel().getPlayerListPanel().getOpponentInfo().setText("현재 상대: 없음");

					// 오목 판 다시 그리기
					BoardCanvas boardCanvas = GameFrame.getInstance().getGamePanel().getBoardCanvas();
					boardCanvas.paintBoardImage();
					boardCanvas.repaint();

					// 새로운 상대 선택 안내 메시지 추가
					MessageManager.getInstance().addMessage("새로운 상대를 선택할 수 있습니다");
				}
			} else{
				// 게임 시작 전에 상대방이 퇴장 했을 때 서버에 알림
				IOManager.getInstance().getPs().println(Header.OPERATION + Header.QUIT + Data.oppoId);

				// 데이터 초기화
				Data.last = -1;
				Data.oppoId = 0;
				Data.myChess = 0;
				Data.oppoChess = 0;
				Data.ready = false;
				Data.started = false;
				Data.chessBoard = new int[15][15];

				// UI 업데이트
				GameFrame.getInstance().getFunctionPanel().getPlayerListPanel().getOpponentInfo().setText("현재 상대: 없음");

				// 오목 판 다시 그리기
				BoardCanvas boardCanvas = GameFrame.getInstance().getGamePanel().getBoardCanvas();
				boardCanvas.paintBoardImage();
				boardCanvas.repaint();

				// 새로운 상대 선택 안내 메시지 추가
				MessageManager.getInstance().addMessage("새로운 상대를 선택할 수 있습니다");
			}
		}
	}
}