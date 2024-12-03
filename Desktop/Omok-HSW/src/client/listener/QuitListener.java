package client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

import client.Data;
import client.manager.IOManager;
import client.manager.MessageManager;
import client.net.Header;
import client.ui.GameFrame;

// 나가기/항복 버튼 리스너
public class QuitListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// 상대방이 있을 때
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
					cleanupAndExit();
				}
			} else {
				// 게임 시작 전에 상대방이 있을 때
				int value = JOptionPane.showConfirmDialog(GameFrame.getInstance(), "게임을 종료하시겠습니까?", "종료",
						JOptionPane.YES_NO_OPTION);
				if (value == JOptionPane.YES_OPTION) {
					// 서버에 퇴장 알림
					IOManager.getInstance().getPs().println(Header.OPERATION + Header.QUIT + Data.oppoId);
					cleanupAndExit();
				}
			}
		} else {
			// 상대방이 없을 때
			int value = JOptionPane.showConfirmDialog(GameFrame.getInstance(), "게임을 종료하시겠습니까?", "종료",
					JOptionPane.YES_NO_OPTION);
			if (value == JOptionPane.YES_OPTION) {
				cleanupAndExit();
			}
		}
	}

	// 정리 및 종료 메소드
	public void cleanupAndExit() {
		try {
			// 서버에 종료 메시지 전송 (연결이 아직 살아있다면)
			if (Data.connected && IOManager.getInstance().getPs() != null) {
				// 상대방이 있을 경우 상대방에게도 알림
				if (Data.oppoId != 0) {
					IOManager.getInstance().getPs().println(Header.OPERATION + Header.QUIT + Data.oppoId);
				}
				// 서버에 연결 종료 메시지 전송
				IOManager.getInstance().getPs().println(Header.OPERATION + Header.DISCONNECT);
			}

			// 서버와의 연결 종료
			if (IOManager.getInstance().getPs() != null) {
				IOManager.getInstance().getPs().close();
			}
			if (IOManager.getInstance().getBr() != null) {
				IOManager.getInstance().getBr().close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// 데이터 초기화
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

		// 프로그램 종료
		System.exit(0);
	}
}