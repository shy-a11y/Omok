package server.tool;

import java.net.Socket;

// 플레이어 정보

public class Player {

	public Socket socket;       // 플레이어와의 통신을 위한 소켓

	public int oppoId;          // 상대 플레이어 ID
	public int myId;            // 자신의 플레이어 ID
	public boolean ready;       // 게임 시작 준비 여부
	public boolean started;     // 게임 시작 여부
	public String name;         // 플레이어의 이름

	FightManager fightManager;  // 오목 게임의 진행을 담당하는 매니저

	// 생성자: 플레이어의 ID와 소켓을 받아 초기화
	public Player(int id, Socket s) {
		this.myId = id;
		this.socket = s;
	}

	// 상대 플레이어 ID 반환
	public int getOppoId() {
		return oppoId;
	}

	// 나의 플레이어 ID 반환
	public int getMyId() {
		return myId;
	}

	// 게임 시작 준비 여부 반환
	public boolean isReady() {
		return ready;
	}

	// 게임 시작 여부 반환
	public boolean isStarted() {
		return started;
	}

	// 플레이어의 이름 반환
	public String getName() {
		return name;
	}

	// 소켓 반환
	public Socket getS() {
		return socket;
	}

	// 오목 게임 매니저 반환
	public FightManager getFightManager() {
		return fightManager;
	}

	// 상대 플레이어의 ID 설정
	public void setOppoId(int oppoId) {
		this.oppoId = oppoId;
	}

	// 게임 시작 준비 여부 설정
	public void setReady(boolean ready) {
		this.ready = ready;
	}

	// 게임 시작 여부 설정
	public void setStarted(boolean started) {
		this.started = started;
	}

	// 플레이어의 이름 설정
	public void setName(String name) {
		this.name = name;
	}

	// 오목 게임 매니저 설정
	public void setFightManager(FightManager fightManager) {
		this.fightManager = fightManager;
	}

}
