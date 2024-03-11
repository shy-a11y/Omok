package client.net;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import client.Data;
import client.manager.IOManager;
import client.manager.MessageManager;
import client.ui.GameFrame;


// 서버 연결, 해제 담당

public class Connecter {

	private Socket socket = null; // 서버와의 Socket 연결 객체

	// 서버에 연결하는 메소드
	public void connect() {
		try {
			// 사용자로부터 서버 IP 및 포트를 입력받는다
			String ipStr = GameFrame.getInstance().getLoginPanel().getIpTextField().getText();
			String portStr = GameFrame.getInstance().getLoginPanel().getPortTextField().getText();

			int portValue = Integer.parseInt(portStr);

			// Socket을 이용하여 서버에 연결
			socket = new Socket(ipStr, portValue);

			// 입력 및 출력 스트림 설정
			IOManager.getInstance().setBr(new InputStreamReader(socket.getInputStream()));
			IOManager.getInstance().setPs(socket.getOutputStream());

			// 연결 상태를 변경하고 메세지를 추가
			Data.connected = true;
			MessageManager.getInstance().addMessage("서버에 연결되었습니다");

		} catch (UnknownHostException e) {
			// 서버를 찾을 수 없는 경우
			JOptionPane.showMessageDialog(GameFrame.getInstance(), "서버를 찾을 수 없습니다");
		} catch (IOException e) {
			// 서버 연결 중 오류가 발생한 경우
			JOptionPane.showMessageDialog(GameFrame.getInstance(), "서버 연결 중 오류가 발생했습니다");
		}
	}

	// 서버 연결을 해제하는 메소드
	public void disconnect() {
		// 소켓이 연결되어 있다면 연결 해제
		if (socket != null && socket.isConnected()) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}