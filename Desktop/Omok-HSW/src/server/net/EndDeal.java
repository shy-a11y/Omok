package server.net;

import server.tool.HashMapManager;
import server.tool.MessageManager;

// 클라이언트 종료 처리

public class EndDeal {

	// 클라이언트 종료 메소드
	public void clientOff(int uid) {
		// HashMapManager 인스턴스를 가져옴
		HashMapManager manager = HashMapManager.getInstance();

		// 이미 매치되어 있는지 확인
		if (manager.getMatchs().containsKey(uid)) {
			// 상대방 ID 가져오기
			int oppoId = manager.getMatchs().get(uid);

			// 이미 준비 상태인 경우 준비 취소
			if (manager.getReadys().contains(uid)) {
				// 자신의 준비 상태를 취소
				manager.getReadys().remove(uid);
			}
			if (manager.getReadys().contains(oppoId)) {
				// 상대방의 준비 상태를 취소
				manager.getReadys().remove(oppoId);
			}

			// FightManager에 등록된 경우 처리
			if (manager.getFightManagers().containsKey(uid)) {
				// 상대방도 FightManager에 등록된 경우
				if (manager.getFightManagers().containsKey(oppoId)) {
					// FightManager 제거
					manager.getFightManagers().remove(oppoId);
				}
				// 나가는 플레이어의 FightManager 제거
				manager.getFightManagers().remove(uid);
			}

			// 매치 제거
			manager.removeMatchs(uid);
		}

		// 현재 매칭 중인지 확인
		if (manager.getMatching().containsKey(uid)) {
			// 매칭 제거
			manager.getMatching().remove(uid);
		}

		// 채팅에 종료 메시지 추가
		MessageManager.getInstance().addMessage("플레이어 " + uid + " 종료");

		// 클라이언트 제거 명령 전송
		new Action().removeClient(uid);

		// 플레이어 제거
		manager.removePlayer(uid);
	}
}
