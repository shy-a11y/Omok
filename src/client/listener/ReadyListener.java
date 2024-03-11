package client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import client.Data;
import client.manager.IOManager;
import client.manager.MessageManager;
import client.net.Header;
import client.ui.GameFrame;

// 준비 버튼 리스너

public class ReadyListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// 연결 상태 확인
		if (Data.connected) {
			// 상대방이 선택된 경우
			if (Data.oppoId != 0) {
				// 아직 준비가 되지 않은 경우
				if (!Data.ready) {
					// 메시지 패널에 알림 메세지 추가
					MessageManager.getInstance().addMessage("잠시 기다려주세요");

					// 서버에 준비 상태 전송
					IOManager.getInstance().getPs().println(Header.OPERATION + Header.START);

					// 클라이언트의 준비 상태를 true로 설정
					Data.ready = true;
				} else {
					// 이미 준비된 경우 알림 메세지 추가
					MessageManager.getInstance().addMessage("이미 준비되었습니다.");
				}
			} else {
				// 상대를 선택하지 않은 경우 알림 팝업
				JOptionPane.showMessageDialog(GameFrame.getInstance(), "먼저 상대를 선택하세요.");
			}
		}
	}
}