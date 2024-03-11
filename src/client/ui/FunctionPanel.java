package client.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

// 기능 GUI

public class FunctionPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// 플레이어 목록 패널
	PlayerListPanel playerListPanel = null;
	// 메시지 패널
	MessagePanel messagePanel = null;
	// 조작 패널
	OperationPanel operationPanel = null;

	// 생성자
	FunctionPanel() {
		// 레이아웃을 BorderLayout으로 설정
		this.setLayout(new BorderLayout());

		// 플레이어 목록 패널을 NORTH 위치에 추가
		this.add(getPlayerListPanel(), BorderLayout.NORTH);
		// 메시지 패널을 CENTER 위치에 추가
		this.add(getMessagePanel(), BorderLayout.CENTER);
		// 조작 패널을 SOUTH 위치에 추가
		this.add(getOperationPanel(), BorderLayout.SOUTH);
	}

	// 플레이어 목록 패널을 반환하는 메소드
	public PlayerListPanel getPlayerListPanel() {
		// 패널이 생성되지 않았을 경우, 새로운 PlayerListPanel 생성
		if (playerListPanel == null) {
			playerListPanel = new PlayerListPanel();
		}
		return playerListPanel;
	}

	// 메세지 패널을 반환하는 메소드
	public MessagePanel getMessagePanel() {
		// 패널이 생성되지 않았을 경우, 새로운 MessagePanel 생성
		if (messagePanel == null) {
			messagePanel = new MessagePanel();
		}
		return messagePanel;
	}

	// 조작 패널을 반환하는 메소드
	public OperationPanel getOperationPanel() {
		// 패널이 생성되지 않았을 경우, 새로운 OperationPanel 생성
		if (operationPanel == null) {
			operationPanel = new OperationPanel();
		}
		return operationPanel;
	}
}