package client.manager;

import javax.swing.JTextArea;

// 채팅 텍스트 영역 관리, 싱글톤 패턴

public class MessageManager {

	// 유일한 인스턴스를 저장할 정적 변수
	private static MessageManager instance = null;

	// 채팅 창에 사용될 JTextArea
	private JTextArea messageArea = null;

	private MessageManager() {
		// private 생성자 👉 외부 인스턴스 생성 ❌
	}

	// 인스턴스에 접근할 수 있는 정적 메소드
	public static MessageManager getInstance() {
		if (instance == null) {
			instance = new MessageManager();
		}
		return instance;
	}

	// JTextArea를 반환, 채팅 창에 필요한 텍스트 영역 설정
	public JTextArea getMessageArea() {
		if (messageArea == null) {
			messageArea = new JTextArea(12, 19);
			messageArea.setEditable(false);  // 읽기만 가능
			messageArea.setLineWrap(true);   // 자동 줄 바꿈
		}
		return messageArea;
	}

	// 채팅 창에 메세지 표시
	public void addMessage(String message) {
		getMessageArea().append("•" + message + System.lineSeparator());
	}
}
