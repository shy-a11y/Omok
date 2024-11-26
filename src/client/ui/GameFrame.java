package client.ui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import client.net.Connecter;

// 게임 창 GUI

public class GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static GameFrame instance = null; // 싱글톤 패턴

	// 로그인 창
	LoginPanel loginPanel = null;
	// 게임 창
	GamePanel gamePanel = null;
	// 기능 창
	FunctionPanel functionPanel = null;
	// 로비창
	LobyPanel lobyPanel = null;

	// 생성자
	private GameFrame() {
		// JFrame의 제목을 "오목"으로 설정
		super("오목");
	}

	// 싱글톤 인스턴스를 반환하는 메소드
	public static GameFrame getInstance() {
		// 인스턴스가 생성되지 않았을 경우, 새로운 GameFrame 인스턴스 생성
		if (instance == null) {
			instance = new GameFrame();
		}
		return instance;
	}


	// 로그인 창 표시
	public void showLoginPanel() {
		// 현재 창을 비활성화하고 게임 패널을 제거
		this.setVisible(false);
		this.remove(getGamePanel());

		// 레이아웃을 BorderLayout으로 설정하고 로그인 패널을 중앙에 추가
		this.setLayout(new BorderLayout());
		this.add(getLoginPanel(), BorderLayout.CENTER);

		// 창 크기를 자동으로 맞추고 화면 중앙에 표시
		this.pack();
		this.setLocationRelativeTo(null);
		// 창 크기 조절 불가능하게 설정
		this.setResizable(false);
		// 창 닫을 때 연결 종료 및 창 닫힘 이벤트 처리
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// 연결 종료
				new Connecter().disconnect();
				super.windowClosed(e);
			}
		});
		// 로그인 창을 화면에 표시
		this.setVisible(true);
	}

	// 게임 창을 표시하는 메소드
	public void showGamePanel() {
		// 현재 창을 비활성화하고 로그인 패널을 제거
		this.setVisible(false);
		this.remove(getLobyPanel());

		// 레이아웃을 BorderLayout으로 설정하고 게임 패널을 CENTER, 기능 패널을 EAST에 추가
		this.setLayout(new BorderLayout());
		this.add(getGamePanel(), BorderLayout.CENTER);
		this.add(getFunctionPanel(), BorderLayout.EAST);

		// 창 크기를 자동으로 맞추고 화면 중앙에 표시
		this.pack();
		this.setLocationRelativeTo(null);
		// 창 크기 조절 불가능
		this.setResizable(false);
		// 창 닫을 때 연결 종료 및 창 닫힘 이벤트 처리
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// 연결 종료
				new Connecter().disconnect();
				super.windowClosed(e);
			}
		});
		// 게임 창의 나가기 버튼 텍스트 설정
		this.getFunctionPanel().getOperationPanel().setQuitButtonText("나가기");
		// 게임 창을 화면에 표시
		this.setVisible(true);
	}

	// 로비 창을 표시하는 메소드
	public void showLobyPanel() {
		// 현재 창을 비활성화하고 로그인 패널을 제거
		this.setVisible(false);
		this.remove(getLoginPanel());
		this.remove(getGamePanel());

		// 레이아웃을 BorderLayout으로 설정하고 게임 패널을 CENTER, 기능 패널을 EAST에 추가
		this.setLayout(new BorderLayout());
		//this.add(getLobyPanel(), BorderLayout.CENTER);
		this.add(getFunctionPanel(), BorderLayout.EAST);

		// 창 크기를 자동으로 맞추고 화면 중앙에 표시
		this.pack();
		this.setLocationRelativeTo(null);
		// 창 크기 조절 불가능
		this.setResizable(false);
		// 창 닫을 때 연결 종료 및 창 닫힘 이벤트 처리
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// 연결 종료
				new Connecter().disconnect();
				super.windowClosed(e);
			}
		});
		// 게임 창의 나가기 버튼 텍스트 설정
		this.getFunctionPanel().getOperationPanel().setQuitButtonText("나가기");
		// 게임 창을 화면에 표시
		this.setVisible(true);
	}

	// 로그인 패널을 반환하는 메소드
	public LoginPanel getLoginPanel() {
		// 패널이 생성되지 않았을 경우, 새로운 LoginPanel 생성
		if (loginPanel == null) {
			loginPanel = new LoginPanel();
		}
		return loginPanel;
	}

	// 게임 패널을 반환하는 메소드
	public GamePanel getGamePanel() {
		// 패널이 생성되지 않았을 경우, 새로운 GamePanel 생성
		if (gamePanel == null) {
			gamePanel = new GamePanel();
		}
		return gamePanel;
	}

	// 로비 패널을 반환하는 메소드
	public LobyPanel getLobyPanel() {
		// 패널이 생성되지 않았을 경우, 새로운 GamePanel 생성
		if (lobyPanel == null) {
			lobyPanel = new LobyPanel();
		}
		return lobyPanel;
	}

	// 기능 패널을 반환하는 메소드
	public FunctionPanel getFunctionPanel() {
		// 패널이 생성되지 않았을 경우, 새로운 FunctionPanel 생성
		if (functionPanel == null) {
			functionPanel = new FunctionPanel();
		}
		return functionPanel;
	}
}