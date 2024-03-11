package client.manager;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import client.Data;


// 플레이어 목록 관리, 싱글톤 패턴

public class PlayerListManager {

	private static PlayerListManager instance = null;

	// JList 인스턴스, 플레이어 목록 표시
	private JList<String> playerList = null;

	// DefaultListModel 인스턴스, JList에 표시될 데이터를 관리
	private DefaultListModel<String> listModel = null;

	private PlayerListManager() {
		// private 생성자 👉 외부 인스턴스 생성 ❌
	}

	// 싱글톤 패턴으로, 유일한 인스턴스에 접근할 수 있는 정적 메소드
	public static PlayerListManager getInstance() {
		if (instance == null) {
			instance = new PlayerListManager();
		}
		return instance;
	}

	// JList를 반환, 필요한 경우 JList를 통해 플레이어 목록을 표시하고 관리
	public JList<String> getPlayerList() {
		if (playerList == null) {
			playerList = new JList<String>(getListModel());
			playerList.setCellRenderer(new MyListCellRenderer());
		}
		return playerList;
	}

	// JList에 표시되는 각 항목의 렌더링을 정의
	private class MyListCellRenderer extends JLabel implements ListCellRenderer<String> {

		private static final long serialVersionUID = 1L;

		public MyListCellRenderer() {
			setOpaque(true);
		}

		// 각 항목의 텍스트, 색상 등을 설정하여 JList에 표시
		// 자신의 id 및 닉네임 색상을 빨간색으로 설정
		@Override
		public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
													  boolean isSelected, boolean cellHasFocus) {
			setText(value);
			setForeground(value.equals(Data.myId + "-" + Data.myName) ? Color.RED : Color.BLACK);
			setBackground(isSelected ? Color.LIGHT_GRAY : Color.WHITE);
			return this;
		}
	}

	// DefaultListModel을 반환, JList에 표시될 데이터를 관리
	public DefaultListModel<String> getListModel() {
		if (listModel == null) {
			listModel = new DefaultListModel<String>();
		}
		return listModel;
	}

	// 플레이어 목록을 지우고 JList를 다시 그림
	public void clearList() {
		this.getListModel().clear();
		this.getPlayerList().repaint();
	}

	// 주어진 이름의 플레이어를 목록에 추가하고 JList를 다시 그림
	public void addPlayer(String name) {
		this.getListModel().addElement(name);
		this.getPlayerList().repaint();
	}

	// 주어진 이름의 플레이어를 목록에서 제거하고 JList를 다시 그림
	public void removePlayer(String name) {
		this.getListModel().removeElement(name);
		this.getPlayerList().repaint();
	}

}
