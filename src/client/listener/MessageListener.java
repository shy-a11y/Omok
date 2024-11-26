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

		// 메시지가 비어 있으면 처리하지 않음
		if (message.trim().isEmpty()) {
			return;
		}

		// 서버로 채팅 메시지 전송
		if (oppoId != 0) {
			// 특정 상대에게 메시지 전송
			IOManager.getInstance().getPs().println(Header.CHAT + message + "&" + oppoId);
			MessageManager.getInstance().addMessage("나 (상대방): " + message);
		} else {
			// 전체 채팅 메시지 전송
			IOManager.getInstance().getPs().println(Header.CHAT + message + "&0"); // oppoId = 0으로 명시
			

		}

		// 입력 필드 초기화
		GameFrame.getInstance().getFunctionPanel().getMessagePanel().getMessageTextField().setText("");
	}

	}
