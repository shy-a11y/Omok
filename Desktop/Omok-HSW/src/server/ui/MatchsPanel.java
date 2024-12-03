package server.ui;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import server.tool.HashMapManager;

// 게임 룸 GUI

public class MatchsPanel extends JPanel {

	// 직렬화를 위한 고유 식별자(serialVersionUID)를 설정
	private static final long serialVersionUID = 1L;

	// 매치 목록을 표시하는 JList와 모델
	JList<String> matchsList = null;
	DefaultListModel<String> model = null;

	// MatchsPanel 생성자
	public MatchsPanel() {
		// JList를 감싸는 JScrollPane 생성
		JScrollPane listPane = new JScrollPane();
		// 수평 스크롤바를 표시하지 않도록 설정
		listPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// 수직 스크롤바를 항상 표시하도록 설정
		listPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// JScrollPane에 JList를 추가하여 매치 목록을 표시
		listPane.setViewportView(this.getMatchsList());

		// 전체 패널 레이아웃 및 테두리 설정
		this.setLayout(new BorderLayout());
		// 전체 패널의 중앙에 JScrollPane를 추가하여 매치 목록 표시
		this.add(listPane, BorderLayout.CENTER);
		// 전체 패널에 테두리와 제목을 설정한 TitledBorder 추가
		this.setBorder(new TitledBorder(new EtchedBorder(), "매치 목록", TitledBorder.CENTER, TitledBorder.TOP));
	}

	// JList의 getter 메서드
	public JList<String> getMatchsList() {
		// JList가 아직 생성되지 않았다면 생성하고 초기 설정 수행
		if (matchsList == null) {
			// JList를 생성하고 모델 설정
			matchsList = new JList<String>(getModel());
			// 각 셀의 너비를 150으로 설정
			matchsList.setFixedCellWidth(150);
			// 표시할 행(row) 개수를 10으로 설정
			matchsList.setVisibleRowCount(10);
		}
		return matchsList;
	}

	// 모델의 get 메소드
	public DefaultListModel<String> getModel() {
		// 모델이 아직 생성되지 않았다면 생성
		if (model == null) {
			model = new DefaultListModel<String>();
		}
		return model;
	}

	// 매치 추가 메소드
	public void addMatchs(Integer uid1, Integer uid2) {
		// 모델에 uid1과 uid2를 결합하여 매치를 추가
		this.getModel().addElement(uid1 + " vs " + uid2);
	}

	// 매치 삭제 메소드
	public void removeMatchs(Integer uid) {
		// 모델에서 해당 유저의 매치를 찾아 제거
		// 주어진 uid를 이용하여 HashMapManager에서 상대방 uid를 얻어와 매치 문자열 생성
		String matchToRemove = uid + " vs " + HashMapManager.getInstance().getMatchs().get(uid);
		// 모델에서 해당 매치를 제거
		this.getModel().removeElement(matchToRemove);

		// 상대방과의 매치 문자열도 생성하여 함께 제거
		String reverseMatchToRemove = HashMapManager.getInstance().getMatchs().get(uid) + " vs " + uid;
		this.getModel().removeElement(reverseMatchToRemove);
	}
}
