package server.net;

// 서버 및 클라이언트 간 통신을 위한 프로토콜 헤더

public class Header {

	// 작업 메세지

	public final static String OPERATION = "[OPER]"; // 서버와 클라이언트 간의 다양한 작업을 나타내는 헤더
	public final static String REPLY = "[REPL]"; // 응답 메시지를 나타내는 헤더
	public final static String CHALLENGE = "[CHAL]"; // 도전 메시지를 나타내는 헤더
	public final static String INIT = "[INIT]"; // 초기화 메시지를 나타내는 헤더
	public final static String GIVEUP = "[ESCA]"; // 항복 메시지를 나타내는 헤더
	public final static String QUIT = "[QUIT]"; // 종료 메시지를 나타내는 헤더
	public final static String CHAT = "[CHAT]"; // 채팅 메시지를 나타내는 헤더
	public final static String RESTART = "[REST]"; // 게임 다시 시작 메시지를 나타내는 헤더
	public final static String LIST = "[LIST]"; // 플레이어 목록 메시지를 나타내는 헤더
	public final static String PLAY = "[PLAY]"; // 돌을 놓는 메시지를 나타내는 헤더
	public final static String STARTMSG = "[SYST]"; // 게임 시작 메시지를 나타내는 헤더
	public final static String ADDPLAYER = "[ADDL]"; // 플레이어 추가 메시지를 나타내는 헤더
	public final static String DELETEPLAYER = "[DELE]"; // 플레이어 삭제 메시지를 나타내는 헤더
	public final static String START = "[STAR]"; // 게임 시작 메시지를 나타내는 헤더
	public final static String WIN = "[UWIN]"; // 승리 메시지를 나타내는 헤더
	public final static String LOSE = "[LOSE]"; // 패배 메시지를 나타내는 헤더

}
