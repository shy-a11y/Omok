package server.ui;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import server.net.ServerThread;
import server.tool.MessageManager;

// 서버 GUI

public class ServerFrame extends JFrame {

	// 직렬화를 위한 고유 식별자(serialVersionUID)를 설정
	private static final long serialVersionUID = 1L;

	// ServerFrame의 인스턴스를 유일하게 관리하기 위한 변수
	private static ServerFrame instance = null;

	// 서버 소켓 객체 선언
	ServerSocket serversocket = null;

	// 클라이언트 목록을 표시하는 패널
	private ClientPanel clientPanel = null;

	// 매치 목록을 표시하는 패널
	private MatchsPanel matchsPanel = null;

	// 메시지를 표시하는 패널
	private MessagePanel messagePanel = null;

	// ServerFrame의 생성자
	private ServerFrame() {
		super("오목 서버"); // 프레임의 타이틀 설정

		// 전체 레이아웃을 BorderLayout으로 설정
		this.setLayout(new BorderLayout());

		// 매치 목록 패널을 EAST에 추가
		this.add(getMatchsPanel(), BorderLayout.EAST);

		// 클라이언트 목록 패널을 WEST에 추가
		this.add(getClientPanel(), BorderLayout.WEST);

		// 메시지 패널을 SOUTH에 추가
		this.add(getMessagePanel(), BorderLayout.SOUTH);

		// 프레임을 적절한 크기로 조절
		this.pack();

		// 창 크기 조절 ❌
		this.setResizable(false);

		// 프레임을 화면 중앙에 배치
		this.setLocationRelativeTo(null);

		// 닫기 버튼 클릭 시 프로그램 종료
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 프레임을 화면에 표시
		this.setVisible(true);
	}

	// 서버를 시작하고 클라이언트의 연결을 대기하는 메소드
	public void launchFrame() {
		try {
			// 서버 소켓을 생성하고 46666번 포트로 열기
			serversocket = new ServerSocket(46666);

			// 서버가 시작되었다는 메시지를 메시지 패널에 추가
			MessageManager.getInstance().addMessage("서버가 시작되었습니다.");

		} catch (IOException e) {
			// 포트가 이미 사용 중일 경우 에러 메시지를 표시하고 프로그램 종료
			JOptionPane.showMessageDialog(this, "포트가 이미 사용 중입니다. 포트를 확인해주세요.");
			System.exit(0);
		}

		// 클라이언트 연결을 계속해서 대기하는 무한 루프
		while (true) {
			try {
				// 클라이언트의 연결을 수락하고 소켓을 생성
				Socket socket = serversocket.accept();

				// 새로운 스레드에서 클라이언트 연결을 처리하는 ServerThread 객체를 생성
				Thread thread = new Thread(new ServerThread(socket));

				// 스레드 시작
				thread.start();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public ClientPanel getClientPanel() {
		if (clientPanel == null) {
			clientPanel = new ClientPanel();
		}
		return clientPanel;
	}

	public MatchsPanel getMatchsPanel() {
		if (matchsPanel == null) {
			matchsPanel = new MatchsPanel();
		}
		return matchsPanel;
	}

	public MessagePanel getMessagePanel() {
		if (messagePanel == null) {
			messagePanel = new MessagePanel();
		}
		return messagePanel;
	}

	public static ServerFrame getInstance() {
		if (instance == null) {
			instance = new ServerFrame();
		}
		return instance;
	}

}
