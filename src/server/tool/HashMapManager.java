package server.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import server.ui.ServerFrame;

// HashMap 매니저

public class HashMapManager {

	// 싱글톤 패턴 HashMapManager
	private static HashMapManager instance = null;

	// 플레이어 관리를 위한 HashMap
	// Integer : 플레이어의 ID
	// Player : 플레이어 정보
	private HashMap<Integer, Player> players = null;

	// 게임 중인 플레이어들의 ID를 나타내는 HashMap
	// 두 플레이어의 ID를 저장하여 게임 진행 상태를 유지
	private HashMap<Integer, Integer> matchs = null;

	// 매칭 중인 플레이어들의 ID를 나타내는 HashMap
	// 두 플레이어의 ID를 저장하여 매칭 상태를 유지
	private HashMap<Integer, Integer> matching = null;

	// 오목 게임의 진행을 담당하는 FightManager 객체를 관리하기 위한 HashMap
	// Integer : 플레이어의 ID
	// FightManager : 해당 플레이어들 간의 게임 상태를 유지
	private HashMap<Integer, FightManager> fightManagers = null;

	// 게임 준비 상태를 나타내는 HashSet
	// 플레이어의 ID를 저장하여 게임 시작을 기다리는 플레이어들을 관리
	private HashSet<Integer> readys = null;

	private HashMapManager() {
		// private 생성자 👉 외부 인스턴스 생성 ❌
	}

	// HashMapManager의 인스턴스를 반환하는 정적 get 메소드
	public static HashMapManager getInstance() {
		// 인스턴스가 아직 생성되지 않았다면 새로운 인스턴스 생성
		if (instance == null) {
			instance = new HashMapManager();
		}
		return instance;
	}

	// FightManager 객체를 관리하는 HashMap을 반환하는 get 메소드
	public HashMap<Integer, FightManager> getFightManagers() {
		// HashMap이 아직 생성되지 않았다면 새로운 HashMap 생성
		if (fightManagers == null) {
			fightManagers = new HashMap<Integer, FightManager>();
		}
		return fightManagers;
	}

	// 게임 준비 상태를 나타내는 HashSet을 반환하는 get 메소드
	public HashSet<Integer> getReadys() {
		// HashSet이 아직 생성되지 않았다면 새로운 HashSet 생성
		if (readys == null) {
			readys = new HashSet<Integer>();
		}
		return readys;
	}

	// 매칭 중인 플레이어들을 관리하는 HashMap을 반환하는 get 메소드
	public HashMap<Integer, Integer> getMatching() {
		// HashMap이 아직 생성되지 않았다면 새로운 HashMap 생성
		if (matching == null) {
			matching = new HashMap<Integer, Integer>();
		}
		return matching;
	}

	// 매칭된 두 플레이어를 추가
	public void addMatchs(Integer uid1, Integer uid2) {
		// 매칭 정보를 HashMap에 추가
		getMatchs().put(uid1, uid2);
		// 서버 업데이트: 매치 목록 패널에 추가
		ServerFrame.getInstance().getMatchsPanel().addMatchs(uid1, uid2);
	}

	// 매칭된 두 플레이어를 제거하는 메소듣
	public void removeMatchs(Integer uid) {
		// 서버 업데이트: 매치 목록 패널에서 제거
		ServerFrame.getInstance().getMatchsPanel().removeMatchs(uid);
		// 매칭 정보를 HashMap에서 제거
		getMatchs().remove(uid);
	}
	public List<Player> getAllPlayers() {
		return new ArrayList<>(players.values());
	}

	// 게임 중인 플레이어들의 ID를 나타내는 HashMap을 반환하는 get 메소드
	public HashMap<Integer, Integer> getMatchs() {
		// HashMap이 아직 생성되지 않았다면 새로운 HashMap 생성
		if (matchs == null) {
			matchs = new HashMap<Integer, Integer>();
		}
		return matchs;
	}

	// 주어진 플레이어 ID에 해당하는 플레이어의 이름을 반환하는 메서드
	public String getName(Integer uid) {
		return getPlayer(uid).name;
	}

	// 플레이어를 추가하는 메소드
	public void addPlayer(Integer uid, Player player) {
		// 플레이어 정보를 HashMap에 추가
		getPlayers().put(uid, player);
		// 서버 업데이트 : 클라이언트 목록 패널에 플레이어 추가
		ServerFrame.getInstance().getClientPanel().addClient(uid);
	}

	// 주어진 플레이어 ID에 해당하는 플레이어를 제거하는 메소드
	public void removePlayer(Integer uid) {
		// 플레이어 정보를 HashMap에서 제거
		getPlayers().remove(uid);
		// 서버 업데이트 : 클라이언트 목록 패널에서 플레이어 제거
		ServerFrame.getInstance().getClientPanel().removeClient(uid);
	}

	// 주어진 플레이어 ID에 해당하는 플레이어 객체를 반환하는 get 메소드
	public Player getPlayer(Integer uid) {
		return getPlayers().get(uid);
	}

	// 플레이어 정보를 관리하는 HashMap을 반환하는 get 메소드
	public HashMap<Integer, Player> getPlayers() {
		// HashMap이 아직 생성되지 않았다면 새로운 HashMap 생성
		if (players == null) {
			players = new HashMap<Integer, Player>();
		}
		return players;
	}

}
