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

		// 메시지가 비어 있으면 처리하지 않음
		if (message.trim().isEmpty()) {
			return;
		}

		// 귓속말 처리 (/닉네임 메시지)
		if (message.startsWith("/")) {
			String[] parts = message.substring(1).split(" ", 2);
			if (parts.length == 2) {
				String targetName = parts[0];
				String whisperMessage = parts[1];
				
				// 자기 자신에게 귓속말을 보내려는 경우
				if (targetName.equals(Data.myName)) {
					MessageManager.getInstance().addMessage("자기 자신에게는 귓속말을 보낼 수 없습니다.");
				} else {
					// 귓속말 메시지 전송
					IOManager.getInstance().getPs().println(Header.CHAT + whisperMessage + "&" + targetName + "&whisper");
					MessageManager.getInstance().addMessage("(귓속말) " + targetName + "님에게: " + whisperMessage);
				}
			} else {
				MessageManager.getInstance().addMessage("귓속말 형식이 잘못되었습니다. '/닉네임 메시지' 형식으로 입력해주세요.");
			}
		} else {
			// 상대방 ID 가져오기
			int oppoId = Data.oppoId;

			// 서버로 채팅 메시지 전송
			if (oppoId != 0) {
				// 특정 상대에게 메시지 전송
				IOManager.getInstance().getPs().println(Header.CHAT + message + "&" + oppoId);
				MessageManager.getInstance().addMessage("나 (상대방): " + message);
			} else {
				// 일반 채팅 메시지 전송
				IOManager.getInstance().getPs().println(Header.CHAT + message + "&0"); // oppoId = 0으로 명시
			}
		}

		// 입력 필드 초기화
		GameFrame.getInstance().getFunctionPanel().getMessagePanel().getMessageTextField().setText("");
	}

}
