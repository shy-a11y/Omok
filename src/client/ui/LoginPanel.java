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

// 로그인 창 GUI

public class LoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel infoPanel = new JPanel(new GridBagLayout()); // 정보를 담은 패널
	private JPanel addressPanel = new JPanel(new FlowLayout()); // 서버 주소 입력을 담은 패널
	private JPanel namePanel = new JPanel(new FlowLayout()); // 닉네임 입력을 담은 패널

	private JLabel addressLabel = new JLabel("서버 주소:"); // 서버 주소 라벨
	private JLabel symbolLabel = new JLabel(" : "); // : 로 구분하는 라벨
	private JLabel nameLabel = new JLabel("닉네임:"); // 닉네임 라벨

	private JTextField ipTextField = null; // 서버 IP 주소를 입력하는 텍스트 필드
	private JTextField portTextField = null; // 서버 포트를 입력하는 텍스트 필드
	private JTextField nameTextField = null; // 닉네임을 입력하는 텍스트 필드

	private JButton connectButton = new JButton("연결"); // 연결 버튼 생성


	// 로그인 패널을 생성하는 생성자
	public LoginPanel() {
		// BorderLayout으로 레이아웃 설정
		this.setLayout(new BorderLayout());

		//////////////////////////////////////서버 주소 라벨///////////////////////////////////////

		// 서버 주소 라벨을 GridBagLayout으로 배치

		// 첫 번째 행(서버 주소 입력)을 위한 GridBagConstraints 설정
		GridBagConstraints bag1 = new GridBagConstraints();

        // 그리드에서의 열(Column) 위치를 나타내는 속성
        bag1.gridx = 0;

        // 그리드에서의 행(row) 위치를 나타내는 속성
        bag1.gridy = 0;

        // 그리드에서 해당 컴포넌트가 차지하는 열의 개수
        bag1.gridwidth = 1;

        // 그리드에서 해당 컴포넌트가 차지하는 행의 개수
        bag1.gridheight = 1;

        // 가중치. 컨테이너의 크기가 변경될 때 해당 컴포넌트의 크기가 얼마나 변할지를 결정
        bag1.weightx = 0;

        // 가중치. 컨테이너의 크기가 변경될 때 해당 컴포넌트의 크기가 얼마나 변할지를 결정
        bag1.weighty = 0;

        // 컴포넌트의 크기가 채워지지 않는 경우, 채울 방향 지정. BOTH는 수평 및 수직으로 채움
        bag1.fill = GridBagConstraints.BOTH;

        // 컴포넌트가 그리드 셀 내에서 어떤 위치에 배치될지를 결정하는데 사용됨
        bag1.anchor = GridBagConstraints.WEST;

		// 서버 주소 레이블을 첫 번째 행에 추가
		infoPanel.add(addressLabel, bag1);

		////////////////////////////////////////서버 주소 입력 필드///////////////////////////////////////

		// 서버 주소, 구분, 포트를 입력하는 패널을 FlowLayout으로 배치

        // 서버 주소 입력 필드를 패널에 추가
        addressPanel.add(getIpTextField());

        // 구분을 담은 레이블(Symbol Label)을 패널에 추가
        addressPanel.add(symbolLabel);

        // 포트 입력 필드를 패널에 추가
        addressPanel.add(getPortTextField());

		// 두 번째 행(서버 포트 입력)을 위한 GridBagConstraints 설정
		GridBagConstraints bag2 = new GridBagConstraints();
		bag2.gridx = 1;
		bag2.gridy = 0;
		bag2.gridwidth = 2;
		bag2.gridheight = 1;
		bag2.weightx = 0;
		bag2.weighty = 0;
		bag2.fill = GridBagConstraints.BOTH;
		bag2.anchor = GridBagConstraints.WEST;

		// 두 번째 행에 서버 주소 입력 필드, 포트 입력 필드, 구분을 담은 패널을 추가
		infoPanel.add(addressPanel, bag2);

		//////////////////////////////////////닉네임 라벨//////////////////////////////////////

		// 닉네임 라벨을 GridBagLayout으로 배치

		// 세 번째 행(닉네임 입력)을 위한 GridBagConstraints 설정
		GridBagConstraints bag3 = new GridBagConstraints();
		bag3.gridx = 0;
		bag3.gridy = 1;
		bag3.gridwidth = 1;
		bag3.gridheight = 1;
		bag3.weightx = 0;
		bag3.weighty = 0;
		bag3.fill = GridBagConstraints.BOTH;
		bag3.anchor = GridBagConstraints.WEST;

		// 세 번째 행에 닉네임 레이블을 추가
		infoPanel.add(nameLabel, bag3);

		///////////////////////////////////닉네임 입력 패널//////////////////////////////////

		// 닉네임을 입력하는 패널을 FlowLayout으로 배치

		// 닉네임 입력 필드를 담은 패널을 생성
		namePanel.add(getNameTextField());

		// 네 번째 행(닉네임 입력)을 위한 GridBagConstraints 설정
		GridBagConstraints bag4 = new GridBagConstraints();
		bag4.gridx = 1;
		bag4.gridy = 1;
		bag4.gridwidth = 2;
		bag4.gridheight = 1;
		bag4.weightx = 0;
		bag4.weighty = 0;
		bag4.fill = GridBagConstraints.BOTH;
		bag4.anchor = GridBagConstraints.WEST;

		// 네 번째 행에 정보 패널을 추가
		infoPanel.add(namePanel, bag4);

		// 전체 패널에 정보 패널을 CENTER에 추가
        this.add(infoPanel, BorderLayout.CENTER);

		// 연결 버튼에 ConnectListener 등록
        connectButton.addActionListener(new ConnectListener());

        // 전체 패널에 연결 버튼을 SOUTH에 배치
        this.add(connectButton, BorderLayout.SOUTH);
	}



	// 서버 주소를 입력하는 텍스트 필드에 대한 get 메소드
    public JTextField getIpTextField() {
        // ipTextField가 아직 생성되지 않았다면 생성하고 초기 설정
        if (ipTextField == null) {
            ipTextField = new JTextField("127.0.0.1", 15); // localhost 초기 설정
            ipTextField.setToolTipText("서버 IP 주소");
        }
        return ipTextField;
    }

	// 포트를 입력하는 텍스트 필드에 대한 get 메소드
    public JTextField getPortTextField() {
        // portTextField가 아직 생성되지 않았다면 생성하고 초기 설정
        if (portTextField == null) {
            portTextField = new JTextField("46666", 5); // 포트 초기 설정
            portTextField.setToolTipText("서버 포트");
        }
        return portTextField;
    }

	// 닉네임을 입력하는 텍스트 필드에 대한 get 메소드
    public JTextField getNameTextField() {
        // nameTextField가 아직 생성되지 않았다면 생성하고 초기 설정
        if (nameTextField == null) {
            nameTextField = new JTextField("닉네임 입력", 22); // 닉네임 초기 설정
            nameTextField.setToolTipText("닉네임");
            nameTextField.setEditable(true);
        }
        return nameTextField;
    }
}
