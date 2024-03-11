package client.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import client.listener.ChallengeListener;
import client.manager.PlayerListManager;

// 플레이어 목록 GUI

public class PlayerListPanel extends JPanel {
	// 해당 클래스의 첫 번째 버전
	private static final long serialVersionUID = 1L;

	// 현재 상대 정보를 표시하는 라벨
	private JLabel opponentInfoLabel = null;

	// 플레이어 목록을 표시하는 패널과 하단 패널
	private JPanel playerBodyPanel = new JPanel(); // 목록
	private JPanel playerBottomPanel = new JPanel(new BorderLayout()); // 하단 패널

	// 목록 스크롤을 가진 JScrollPane과 도전 하기 버튼
	private JScrollPane listPane = new JScrollPane(); // 목록 스크롤
	private JButton challengeButton = new JButton("도전 하기"); // 도전 하기 버튼

	// PlayerListPanel 생성자
	PlayerListPanel() {
		// 목록 스크롤 설정
		listPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		listPane.setViewportView(PlayerListManager.getInstance().getPlayerList());
		playerBodyPanel.add(listPane);

		// 도전 버튼에 ChallengeListener 등록
		challengeButton.addActionListener(new ChallengeListener());

		// 하단 패널 설정
		playerBottomPanel.add(getOpponentInfo(), BorderLayout.CENTER);
		playerBottomPanel.add(challengeButton, BorderLayout.SOUTH);

		// 플레이어 목록의 크기와 표시 개수 설정
		PlayerListManager.getInstance().getPlayerList().setFixedCellWidth(210);
		PlayerListManager.getInstance().getPlayerList().setVisibleRowCount(7);

		// 전체 패널 레이아웃 및 테두리 설정
		this.setLayout(new BorderLayout());
		this.setBorder(new TitledBorder(new EtchedBorder(), "플레이어 목록", TitledBorder.CENTER, TitledBorder.TOP));
		this.add(playerBodyPanel, BorderLayout.CENTER);
		this.add(playerBottomPanel, BorderLayout.SOUTH);
	}

	// 상대 정보 라벨에 대한 get 메소드
	public JLabel getOpponentInfo() {
		// opponentInfoLabel이 아직 생성되지 않았다면 생성
		if (opponentInfoLabel == null) {
			opponentInfoLabel = new JLabel("현재 상대: 없음");
		}
		return opponentInfoLabel;
	}
}
