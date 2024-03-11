package server.ui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import server.tool.MessageManager;

// 텍스트 메세제 GUI

public class MessagePanel extends JPanel {

	// 직렬화를 위한 고유 식별자(serialVersionUID)를 설정
	private static final long serialVersionUID = 1L;

	private JScrollPane messagePane = new JScrollPane();

	// MessagePanel 생성자
	public MessagePanel() {
		// 수평 스크롤바를 표시하지 않도록 설정
		messagePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// 수직 스크롤바를 항상 표시하도록 설정
		messagePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// JScrollPane에 MessageManager에서 가져온 MessageArea를 추가
		messagePane.getViewport().add(MessageManager.getInstance().getMessageArea());
		// 전체 패널에 JScrollPane를 추가
		this.add(messagePane);
	}

}
