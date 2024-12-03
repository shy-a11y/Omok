package client.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import client.listener.QuitListener;
import client.listener.ReadyListener;
import client.listener.RestartListener;

// 여러 버튼 GUI

public class OperationPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// 동작 패널 및 버튼들을 선언
	private JPanel operationPanel = new JPanel(new BorderLayout());
	private JButton quitButton = new JButton("나가기"); // 나가기 버튼
	private JButton restartButton = new JButton("다시 시작"); // 다시 시작 버튼
	private JButton readyButton = new JButton("준비"); // 준비 버튼

	// OperationPanel 생성자
	OperationPanel() {
		// 각 버튼에 대한 Listener 등록
		quitButton.addActionListener(new QuitListener());
		restartButton.addActionListener(new RestartListener());
		readyButton.addActionListener(new ReadyListener());

		// 버튼들을 동작 패널에 추가
		operationPanel.add(quitButton, BorderLayout.WEST);
		operationPanel.add(restartButton, BorderLayout.CENTER);
		operationPanel.add(readyButton, BorderLayout.EAST);

		// 전체 패널에 동작 패널 추가
		this.add(operationPanel);
	}

	// 나가기 버튼의 텍스트를 설정하는 set 메소드
	public void setQuitButtonText(String s) {
		quitButton.setText(s);
	}
}
