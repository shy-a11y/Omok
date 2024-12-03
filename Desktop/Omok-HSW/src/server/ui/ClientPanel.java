package server.ui;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

// 유저 목록 GUI

public class ClientPanel extends JPanel {

	// 직렬화를 위한 고유 식별자(serialVersionUID)를 설정
	private static final long serialVersionUID = 1L;

	// 클라이언트 목록을 나타내는 JList
	JList<Integer> clientList = null;

	// JList의 모델을 담당하는 DefaultListModel
	DefaultListModel<Integer> model = null;

	// 클라이언트 목록을 반환하는 get 메소드
	public JList<Integer> getClientList() {
		// 클라이언트 목록이 아직 생성되지 않았다면
		if (clientList == null) {
			// JList에 사용될 모델을 가져와서 새로운 JList를 생성
			clientList = new JList<Integer>(this.getModel());

			// 각 셀의 고정된 너비를 150으로 설정
			clientList.setFixedCellWidth(150);

			// 한 번에 표시되는 행의 수를 10으로 설정
			clientList.setVisibleRowCount(10);
		}

		// 생성된 또는 기존의 클라이언트 목록을 반환
		return clientList;
	}

	// JList 모델을 반환하는 get 메소드
	public DefaultListModel<Integer> getModel() {
		// 모델이 아직 생성되지 않았다면 DefaultListModel을 생성하여 할당
		if (model == null) {
			model = new DefaultListModel<Integer>();
		}

		// 생성된 또는 기존의 모델을 반환
		return model;
	}

	// 생성자
	public ClientPanel() {
		// JScrollPane을 생성
		JScrollPane listPane = new JScrollPane();

		// 수평 스크롤바를 비활성화
		listPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		// 수직 스크롤바를 항상 활성화
		listPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// JScrollPane에 클라이언트 목록을 보여주는 JList를설정
		listPane.setViewportView(this.getClientList());

		// 패널의 레이아웃을 BorderLayout으로 설정
		this.setLayout(new BorderLayout());

		// BorderLayout의 CENTER에 JScrollPane을 추가
		this.add(listPane, BorderLayout.CENTER);

		// 패널의 테두리를 설정
		this.setBorder(new TitledBorder(new EtchedBorder(), "유저 목록", TitledBorder.CENTER, TitledBorder.TOP));
	}

	// 클라이언트를 목록에 추가하는 메소드
	public void addClient(int uid) {
		// 모델에 새로운 클라이언트 ID를 추가
		this.getModel().addElement(uid);
	}

	// 클라이언트를 목록에서 제거하는 메소드
	public void removeClient(int uid) {
		// 모델에서 해당 클라이언트 ID를 제거
		this.getModel().removeElement(uid);
	}
}
