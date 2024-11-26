package server.net;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import server.tool.FightManager;
import server.tool.HashMapManager;
import server.tool.Player;

// 클라이언트 액션 치리

public class Action {

	// 싱글톤 매니저 객체
	HashMapManager manager = HashMapManager.getInstance();

	// 신규 클라이언트에 대한 처리 메소드
	public void newClient(int uid) {
		// 등록된 플레이어 목록을 가져오기
		HashMap<Integer, Player> players = manager.getPlayers();

		// 플레이어 목록에서 모든 플레이어에 대한 컬렉션을 가져오기
		Collection<Player> c = players.values();

		// 플레이어 목록 컬렉션에 대한 iterator 생성
		Iterator<Player> i = c.iterator();

		// 플레이어 목록을 돌며 각 플레이어의 소켓 정보를 이용해 새로운 클라이언트 정보 전송
		while (i.hasNext()) {
			// 현재 플레이어의 소켓 가져오기
			Socket s = i.next().socket;

			try {
				// 소켓의 출력 스트림 생성
				PrintStream ps = new PrintStream(s.getOutputStream());

				// 새로 추가된 클라이언트의 ID와 이름 가져오기
				String name = manager.getName(uid);

				// 헤더와 함께 새로운 클라이언트의 정보를 전송
				ps.println(Header.ADDPLAYER + uid + "-" + name);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 클라이언트 제더에 대한 처리 메소드
	public void removeClient(int uid) {
		// 플레이어 ID 집합 가져오기
		Set<Integer> playerIds = manager.getPlayers().keySet();

		// 플레이어 ID 집합에 대한 iterator 생성
		Iterator<Integer> iterator = playerIds.iterator();

		// 플레이어 목록을 돌며 각 플레이어에게 클라이언트 제거 메시지 전송
		while (iterator.hasNext()) {
			// 현재 플레이어의 ID 가져오기
			int playerId = iterator.next();

			// 현재 플레이어의 소켓 정보 가져오기
			Socket socket = manager.getPlayer(playerId).socket;

			try {
				// 소켓의 출력 스트림 생성
				PrintStream printStream = new PrintStream(socket.getOutputStream());

				// 삭제된 클라이언트의 ID와 이름 가져오기
				String removedClientId = String.valueOf(uid);
				String removedClientName = manager.getPlayer(uid).name;

				// 헤더와 함께 클라이언트 삭제 메시지를 전송
				printStream.println(Header.DELETEPLAYER + removedClientId + "-" + removedClientName);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 클라이언트에게 등록된 모든 플레이어 목록을 전송하는 get 메소드
	public void getList(Socket s) {
		String list = null;

		try {
			// 소켓의 출력 스트림 생성
			PrintStream ps = new PrintStream(s.getOutputStream());

			// 등록된 모든 플레이어의 ID 집합 가져오기
			Set<Integer> playerIds = manager.getPlayers().keySet();

			// 플레이어 ID 집합에 대한 iterator 생성
			Iterator<Integer> iterator = playerIds.iterator();

			// 플레이어 목록을 돌면서 각 플레이어의 ID와 이름을 문자열로 구성하여 목록에 추가
			while (iterator.hasNext()) {
				// 현재 플레이어의 ID 가져오기
				int playerId = iterator.next();

				// 현재 플레이어의 이름 가져오기
				String playerName = manager.getPlayer(playerId).name;

				// 목록에 현재 플레이어의 ID와 이름 추가
				if (list == null) {
					// 목록이 비어있을 때, 초기화
					list = playerId + "-" + playerName + "&";
				} else {
					// 목록이 이미 값이 있을 때, 끝에 추가
					list = list + playerId + "-" + playerName + "&";
				}
			}

			// 헤더와 함께 플레이어 목록을 클라이언트에게 전송
			ps.println(Header.LIST + list);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 채팅 메시지 전송 메소드
	public void sendMessage(int uid, String readLine) {
		try {
			// 입력된 문자열을 "&"를 기준으로 분리
			String[] s = readLine.split("&");
			String message = s[0];
			int targetId = (s.length > 1) ? Integer.parseInt(s[1]) : 0; // targetId가 없으면 전체 채팅으로 간주

			if (targetId == 0) {
				// 전체 채팅 처리: 모든 연결된 플레이어에게 메시지를 전송
				for (Player player : manager.getAllPlayers()) {
					Socket socket = player.socket;
					PrintStream printStream = new PrintStream(socket.getOutputStream());
					printStream.println(Header.CHAT + message + "&" + uid + "-" + HashMapManager.getInstance().getName(uid));
				}
			} else {
				// 특정 대상에게 메시지 전송
				Socket socket = manager.getPlayer(targetId).socket;
				PrintStream printStream = new PrintStream(socket.getOutputStream());
				printStream.println(Header.CHAT + message + "&" + uid + "-" + HashMapManager.getInstance().getName(uid));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	// 도전 메시지 전송 메소드
	public void sendChallenge(int uid, int target) {
		// HashMapManager의 플레이어 목록을 가져옴
		HashMap<Integer, Player> players = manager.getPlayers();

		// 도전을 보내는 플레이어의 이름을 가져옴
		String name = HashMapManager.getInstance().getName(uid);

		// 도전을 받는 플레이어의 소켓을 가져옴
		Socket s = players.get(target).socket;

		try {
			// 소켓으로부터 출력 스트림을 얻어옴
			PrintStream ps = new PrintStream(s.getOutputStream());

			// 도전 메시지 생성 및 전송
			// Header.OPERATION: 도전 메시지가 특정 작업임을 나타냄
			// Header.CHALLENGE: 실제 도전을 나타냄
			// uid: 도전을 보내는 플레이어의 ID
			// name: 도전을 보내는 플레이어의 이름
			ps.println(Header.OPERATION + Header.CHALLENGE + uid + "-" + name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 재시작 요청 전송 메소드
	public void restart(int uid) {
		// 만약 해당 ID를 가진 플레이어가 오목 매니저 목록에 존재한다면
		if (manager.getFightManagers().containsKey(uid)) {
			// 해당 플레이어의 오목 매니저의 restart 메소드 호출
			manager.getFightManagers().get(uid).restart(uid);
		}
	}

	// 도전 요청 응답 메소드
	public void replyChallenge(int uid, String readLine) {
		// 전달받은 문자열을 "&"으로 분리
		String[] s = readLine.split("&");
		// 도전자의 ID 추출
		int challengerId = Integer.parseInt(s[0]);
		// 응답 선택 (YES 또는 NO)
		String choose = s[1];
		// 도전자의 소켓
		Socket socket = manager.getPlayer(challengerId).socket;

		try {
			// 도전자에 대한 출력 스트림
			PrintStream challengerPs = new PrintStream(socket.getOutputStream());

			// 선택에 따라 다른 응답 전송
			if (choose.equals("YES")) {
				// 도전 응답 메시지 생성 및 전송
				// Header.REPLY: 응답 메시지 헤더로 이를 통해 클라이언트에서 응답 메시지임을 구분
				// Header.CHALLENGE: 도전 메시지 헤더로 이를 통해 도전 메시지임을 구분
				// uid: 응답을 보내는 플레이어의 ID
				// manager.getName(uid): 응답을 보내는 플레이어의 이름
				// "&YES": 응답이 YES인 경우를 나타내는 구분자
				challengerPs.println(Header.REPLY + Header.CHALLENGE + uid + "-" + manager.getName(uid) + "&YES");
				// 매칭 중인 목록에서 도전자 제거
				manager.getMatching().remove(challengerId);
				// 매치 목록에 새로운 매치 추가
				manager.addMatchs(challengerId, uid);
			} else {
				// 응답이 NO인 경우 도전자에게 거절한 응답 전송
				challengerPs.println(Header.REPLY + Header.CHALLENGE + uid + "-" + manager.getName(uid) + "&NO");
				// 매칭 중인 목록에서 도전자 제거
				manager.getMatching().remove(challengerId);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 플레이어의 준비 상태를 설정하고 게임 시작 여부를 처리하는 메소드
	public void ready(int uid) {
		int oppoId = 0; // 상대방의 ID 초기화

		// 플레이어를 레디 상태로 설정
		manager.getReadys().add(uid);


		// 현재 플레이어의 ID가 매치 리스트에 있으면
		if (manager.getMatchs().containsKey(uid)) {
			// 상대방 ID 가져오기
			oppoId = manager.getMatchs().get(uid);
			// 현재 플레이어의 ID가 매치 리스트에 없으면
		} else {
			// 매치 리스트에서 상대방 ID를 찾아옴
			Set<Integer> s = manager.getMatchs().keySet(); // 매치 리스트의 모든 키(플레이어 ID)를 가져옴
			Iterator<Integer> i = s.iterator(); // Iterator를 사용하여 매치 리스트를 돈다

			// 상대방 ID를 찾을 때까지 반복
			while (i.hasNext()) {
				int id = i.next(); // 현재 돌고 있는 중인 플레이어 ID를 가져옴
				if (manager.getMatchs().get(id) == uid) {
					// 현재 플레이어 ID가 상대방 ID를 가지는 경우
					oppoId = id; // 상대방 ID로 설정하고 반복문 종료
				}
			}
		}

		// 두 플레이어가 모두 준비되면 게임 시작
		if (manager.getReadys().contains(oppoId)) {
			// 등록된 fightManager 가져오기
			FightManager publicManager = manager.getFightManagers().get(oppoId);

			// 현재 플레이어의 ID(uid)를 키로하여 FightManager 맵에 공유된 FightManager(publicManager) 저장
			manager.getFightManagers().put(uid, publicManager);

			// 공유된 FightManager를 이용하여 현재 플레이어(uid)와 상대방(oppoId) 간의 게임 시작 처리
			publicManager.startPlay(uid, oppoId);

			// 게임이 시작되었음을 두 플레이어에게 알리기 위해 시작 메시지를 전송
			publicManager.sendStartMessage();

		} else {
			// 상대방이 준비되지 않은 경우 새로운 fightManager를 생성하여 등록
			manager.getFightManagers().put(uid, new FightManager());
		}
	}

	// 돌 놓기 메소드
	// from: 돌을 놓는 플레이어의 ID
	// position: 돌을 놓는 위치
	public void playChess(int from, int position) {
		// from을 키로하여 해당 플레이어의 FightManager를 가져와서 해당 FightManager의 sendPlay 메소드 호출
		manager.getFightManagers().get(from).sendPlay(from, position);
	}

	// 나가기 메소드
	public void quit(int uid, int oppoId) {
		// HashMapManager 인스턴스를 가져옴
		HashMapManager manager = HashMapManager.getInstance();

		// 상대방 플레이어의 소켓을 가져옴
		Socket opponentSocket = manager.getPlayer(oppoId).socket;

		try {
			// 나가기 메세지를 전송하기 위해 출력 스트림 생성
			PrintStream opponentPrintStream = new PrintStream(opponentSocket.getOutputStream());
			// 나가기 메시지 전송
			opponentPrintStream.println(Header.OPERATION + Header.QUIT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 준비 상태 취소
		manager.getReadys().remove(uid);
		manager.getReadys().remove(oppoId);

		// 매치 제거
		if (manager.getMatchs().containsKey(uid)) {
			// 나가는 플레이어가 매치 리스트에 있으면 해당 정보 제거
			manager.removeMatchs(uid);
		} else {
			// 나가는 플레이어가 매치 리스트에 없으면 상대방의 정보 제거
			manager.removeMatchs(oppoId);
		}

		// fightManager 제거
		manager.getFightManagers().remove(uid);
		manager.getFightManagers().remove(oppoId);
	}

	// 항복 메소드
	public void giveUp(int uid, int oppoId) {
		// HashMapManager 인스턴스를 가져옴
		HashMapManager manager = HashMapManager.getInstance();
		// 상대방에게 항복을 알리기 위해 oppoId로 상대방의 소켓을 얻어옴
		Socket s = manager.getPlayer(oppoId).socket;
		try {
			// 항복 메시지를 전송하기 위해 출력 스트림 생성
			PrintStream ps = new PrintStream(s.getOutputStream());
			// 항복 메시지 전송
			ps.println(Header.OPERATION + Header.GIVEUP);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 준비 상태 취소
		manager.getReadys().remove(uid);
		manager.getReadys().remove(oppoId);

		// 매치 제거
		if (manager.getMatchs().containsKey(uid)) {
			// 나가는 플레이어가 매치 리스트에 있으면 해당 정보 제거
			manager.removeMatchs(uid);
		} else {
			// 나가는 플레이어가 매치 리스트에 없으면 상대방의 정보 제거
			manager.removeMatchs(oppoId);
		}
		// fightManager 제거
		manager.getFightManagers().remove(uid);
		manager.getFightManagers().remove(oppoId);
	}

}
