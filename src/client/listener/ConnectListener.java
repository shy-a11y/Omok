package client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.Data;
import client.manager.IOManager;
import client.net.Connecter;
import client.net.Header;
import client.net.Receiver;
import client.ui.GameFrame;

// 연결 버튼 리스너

public class ConnectListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// 만약 연결되어 있지 않다면
		if (!Data.connected) {
			// Connecter 클래스를 이용하여 서버에 연결
			new Connecter().connect();

			// 연결이 성공했다면
			if (Data.connected) {
				// 새로운 데이터 수신 쓰레드 시작
				new Thread(new Receiver()).start();

				// 사용자 이름을 입력 받아 플레이어 목록 업데이트 요청
				String name = GameFrame.getInstance().getLoginPanel().getNameTextField().getText();
				IOManager.getInstance().getPs().println(Header.INIT + name);
			}
		}
	}
}
