package client.net;

// 클라이언트 통신 메세지 정의

public class Header {

	// 게임 운영 메시지
	public final static String OPERATION = "[OPER]";

	// 서버 응답 메시지
	public final static String REPLY = "[REPL]";

	// 도전 관련 메시지
	public final static String CHALLENGE = "[CHAL]";

	// 초기화 메시지
	public final static String INIT = "[INIT]";

	// 기권 메시지
	public final static String GIVEUP = "[ESCA]";

	// 나가기 메시지
	public final static String QUIT = "[QUIT]";

	// 채팅 메시지
	public final static String CHAT = "[CHAT]";

	// 재시작 메시지
	public final static String RESTART = "[REST]";

	// 플레이어 목록 메시지
	public final static String LIST = "[LIST]";

	// 돌 놓기 메시지
	public final static String PLAY = "[PLAY]";

	// 시작 메시지
	public final static String STARTMSG = "[SYST]";

	// 플레이어 추가 메시지
	public final static String ADDPLAYER = "[ADDL]";

	// 플레이어 삭제 메시지
	public final static String DELETEPLAYER = "[DELE]";

	// 게임 시작 메시지
	public final static String START = "[STAR]";

	// 승리 메시지
	public final static String WIN = "[UWIN]";

	// 패배 메시지
	public final static String LOSE = "[LOSE]";

	// 연결 종료 메시지
	public final static String DISCONNECT = "[DISC]";

}
