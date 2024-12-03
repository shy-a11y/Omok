package client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;
import javax.swing.JOptionPane;

import client.Data;
import client.manager.IOManager;
import client.manager.MessageManager;
import client.manager.PlayerListManager;
import client.net.Header;
import client.ui.GameFrame;

// 도전 버튼 리스너

public class ChallengeListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// 플레이어 목록을 가져옴
		JList<String> list = PlayerListManager.getInstance().getPlayerList();

		// 상대방이 없을 때
		if (Data.oppoId == 0) {
			// 선택된 항목이 있는지 확인
			if (!list.isSelectionEmpty()) {
				// 선택된 항목에서 플레이어의 ID를 추출
				String s = list.getSelectedValue().split("-")[0];
				int targetId = Integer.parseInt(s);

				// 자신이 선택한 상대가 아닌 경우
				if (targetId != Data.myId) {
					// 대기 메시지를 추가하고 도전 메시지를 서버로 전송
					MessageManager.getInstance().addMessage("상대방이 도전을 수락할 때까지 기다리는 중입니다.");
					IOManager.getInstance().getPs().println(Header.OPERATION + Header.CHALLENGE + targetId);
				} else {
					// 자신과 도전은 할 수 없다는 경고 메시지 출력
					JOptionPane.showMessageDialog(GameFrame.getInstance(), "자신과 도전은 할 수 없습니다.");
				}
			} else {
				// 선택된 상대가 없을 때의 경고 메시지 출력
				JOptionPane.showMessageDialog(GameFrame.getInstance(), "아무 상대도 선택되지 않았습니다.");
			}
		} else {
			// 이미 상대가 있는 경우의 메시지 출력
			MessageManager.getInstance().addMessage("이미 상대가 있습니다.");
		}
	}
}