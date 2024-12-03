package client.ui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import client.listener.MessageListener;
import client.manager.MessageManager;

// 채팅 GUI

public class MessagePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// 메시지를 표시하는 패널과 컴포넌트들을 관리하는 여러 패널들을 선언
	private JPanel messagePanel = new JPanel(new BorderLayout());
	private JPanel messageBodyPanel = new JPanel();
	private JPanel messageBottomPanel = new JPanel();

	// 메시지 스크롤을 담당하는 JScrollPane
	private JScrollPane messagePane = new JScrollPane();
	// 메시지를 입력하는 JTextField
	private JTextField messageTextField = null; // 텍스트 상자

	// 메시지 입력 필드에 대한 get 메소드
	public JTextField getMessageTextField() {
		// messageTextField가 아직 생성되지 않았다면 생성
		if (messageTextField == null) {
			messageTextField = new JTextField(20);
		}
		return messageTextField;
	}

	// MessagePanel 생성자
	MessagePanel() {
		this.setLayout(new BorderLayout());

		// 패널의 외곽선과 제목을 설정
		this.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "채팅", TitledBorder.CENTER, TitledBorder.TOP));

		// 스크롤바가 항상 아래로 이동하도록 설정
		messagePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// 스크롤 가능한 패널에 메시지 영역 추가
		messagePane.setViewportView(MessageManager.getInstance().getMessageArea());

		// JScrollPane이 자동으로 스크롤하도록 설정. 최신 버전은 화살표 함수 사용해야함
		messagePane.getVerticalScrollBar().addAdjustmentListener(
				e -> e.getAdjustable().setValue(e.getAdjustable().getMaximum()));

		// 메시지를 표시하는 패널에 메시지 영역 패널 추가
		messageBodyPanel.add(messagePane);
		messagePanel.add(messageBodyPanel, BorderLayout.CENTER);

		// 메시지 입력 필드에 ActionListener 추가
		getMessageTextField().addActionListener(new MessageListener());
		messageBottomPanel.add(getMessageTextField());
		messagePanel.add(messageBottomPanel, BorderLayout.SOUTH);

		// 전체 패널에 채팅 패널 추가
		this.add(messagePanel);
	}
}
