package server.tool;

import javax.swing.JTextArea;

// 채팅 관리자

public class MessageManager {

	// 싱글톤 패턴
	private static MessageManager instance = null;

	// 채팅 메시지를 표시하는 JTextArea 객체
	private JTextArea messageArea = null;

	private MessageManager() {
		// private 생성자 👉 외부 인스턴스 생성 ❌
	}

	// 싱글톤 패턴 사용
	// 하나의 인스턴스만 생성할 수 있도록 하는 get 메소드
	public static MessageManager getInstance() {
		// 인스턴스가 없을 경우에만 생성
		// 이미 인스턴스가 존재하면 기존 인스턴스를 반환
		if (instance == null) {
			instance = new MessageManager();
		}
		return instance;
	}

	// JTextArea 객체를 반환하는 get 메소드
	public JTextArea getMessageArea() {
		// JTextArea가 아직 생성되지 않았다면 생성하고 초기 설정 수행
		if (messageArea == null) {
			messageArea = new JTextArea("", 10, 33);
			messageArea.setEditable(false); // 편집 불가능
		}
		return messageArea;
	}

	// 메시지를 JTextArea에 추가하는 메소드
	public void addMessage(String s) {
		// 새로운 메시지를 현재 메시지에 추가하고 줄바꿈
		messageArea.append("• " + s + System.lineSeparator());
	}

}
