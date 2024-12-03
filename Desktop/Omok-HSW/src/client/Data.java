package client;

// 전역 stactic 데이터 관리

public final class Data {

	// 마지막으로 놓인 돌의 위치를 저장하는 변수 솔정
	public static int last = -1;

	// 현재 턴을 나타내는 변수 (0: 흑돌, 1: 백돌) 설정
	public static int turn = 0;

	// 나의 ID와 상대방 ID를 저장하는 변수 설정
	public static int myId = 0;
	public static int oppoId = 0;

	// 나의 닉네임을 저장하는 변수 설종
	public static String myName = null;

	// 돌의 색상을 나타내는 변수 (BLACK: 흑돌, WHITE: 백돌) 설정
	public static int BLACK = 1;
	public static int WHITE = -1;

	// 나의 돌 색상과 상대방의 돌 색상을 저장하는 변수 설정
	public static int myChess = 0;
	public static int oppoChess = 0;

	// 서버와의 연결 여부, 게임 준비 여부, 게임 시작 여부를 저장하는 변수 설정
	public static boolean connected = false;
	public static boolean ready = false;
	public static boolean started = false;

	// 오목 게임의 판을 나타내는 2차원 배열 설정
	public static int[][] chessBoard = new int[15][15];


}
