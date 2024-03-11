package client.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

// 오목 판 GUI

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JPanel gameBody = new JPanel(); // 오목 판을 담을 패널

	private BoardCanvas boardCanvas = null; // 오목 판을 그리고 이미지를 처리하는 객체

	// 생성자
	GamePanel() {
		// 게임 바디에 오목 판을 추가
		gameBody.add(getBoardCanvas());

		// 전체 레이아웃을 BorderLayout으로 설정하고 게임 바디를 CENTER에 추가
		this.setLayout(new BorderLayout());
		this.add(gameBody, BorderLayout.CENTER);
	}

	// 오목 판 그리기 및 이미지를 처리하는 객체를 반환하는 메소드
	public BoardCanvas getBoardCanvas() {
		// boardCanvas가 생성되지 않았을 경우, 새로운 BoardCanvas 생성
		if (boardCanvas == null) {
			boardCanvas = new BoardCanvas();
		}
		return boardCanvas;
	}
}
