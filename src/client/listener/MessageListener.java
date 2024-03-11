package client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.Data;
import client.manager.IOManager;
import client.manager.MessageManager;
import client.net.Header;
import client.ui.GameFrame;

// 채팅 리스너

public class MessageListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// 채팅 입력 필드에서 사용자가 입력한 메시지 가져오기
		String message = GameFrame.getInstance().getFunctionPanel().getMessagePanel().getMessageTextField().getText();

		// 상대방 ID 가져오기
		int oppoId = Data.oppoId;

		// 만약 상대방이 있다면
		if (oppoId != 0) {
			// 서버로 채팅 메시지 전송
			IOManager.getInstance().getPs().println(Header.CHAT + message + "&" + oppoId);

			// 입력 필드 초기화 및 내가 보낸 메시지 화면에 추가
			GameFrame.getInstance().getFunctionPanel().getMessagePanel().getMessageTextField().setText("");
			MessageManager.getInstance().addMessage("나: " + message);

			// 만약 상대방이 없다면
		} else {
			// 상대방이 없을 때의 처리
			MessageManager.getInstance().addMessage("상대방이 없습니다.");
			// 입력 필드의 텍스트를 전체 선택. 사용자가 다음 메시지를 입력할 때 이전 메시지가 선택된 채로 있음
			GameFrame.getInstance().getFunctionPanel().getMessagePanel().getMessageTextField().selectAll();
		}
	}

}
