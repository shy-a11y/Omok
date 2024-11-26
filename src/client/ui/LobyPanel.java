package client.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.listener.ConnectListener;

public class LobyPanel extends JPanel{
    private static final long serialVersionUID = 1L;
    // 플레이어 목록 패널
    PlayerListPanel playerListPanel = null;
    // 메시지 패널
    MessagePanel messagePanel = null;

    // 생성자
    LobyPanel() {
        // 레이아웃을 BorderLayout으로 설정
        this.setLayout(new BorderLayout());

        // 플레이어 목록 패널을 NORTH 위치에 추가
        this.add(getPlayerListPanel(), BorderLayout.NORTH);
        // 메시지 패널을 CENTER 위치에 추가
        this.add(getMessagePanel(), BorderLayout.CENTER);

    }

    // 플레이어 목록 패널을 반환하는 메소드
    public PlayerListPanel getPlayerListPanel() {
        // 패널이 생성되지 않았을 경우, 새로운 PlayerListPanel 생성
        if (playerListPanel == null) {
            playerListPanel = new PlayerListPanel();
        }
        return playerListPanel;
    }

    // 메세지 패널을 반환하는 메소드
    public MessagePanel getMessagePanel() {
        // 패널이 생성되지 않았을 경우, 새로운 MessagePanel 생성
        if (messagePanel == null) {
            messagePanel = new MessagePanel();
        }
        return messagePanel;
    }

}
