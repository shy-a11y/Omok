package server.tool;


// 돌 연결 확인

public class BoardChecker {

	// 4 방향으로 체크하기 위한 방향 배열
	private final int dirx[] = {1, 0, 1, 1};
	private final int diry[] = {0, 1, 1, -1};

	// 돌 연결을 확인 메소드
	public boolean check(int x, int y, int id, int[][] chessBoard) {
		for (int d = 0; d < 4; d++) {
			int count = 0;

			// 현재 위치 (x, y)부터 시작하여 dirx[d]와 diry[d] 방향으로 5개의 돌을 체크
			for (int i = 0; i < 5; i++) {
				int xx = x + dirx[d] * i, yy = y + diry[d] * i;

				// 오목 판 안에 있고, 돌의 색이 현재 플레이어의 돌과 일치하면 count 증가
				if (isInsideBoard(xx, yy) && chessBoard[xx][yy] == id) {
					count++;
				} else {
					break;
				}
			}

			// 현재 위치에서 반대 방향으로 이동하여 (x - dirx[d], y - diry[d])부터 시작하여 4개의 돌을 체크
			// 위를 포함하여 총 9개의 돌을 체크 함
			for (int i = 1; i < 5; i++) {
				int xx = x - dirx[d] * i, yy = y - diry[d] * i;

				// 오목 판 안에 있고, 돌의 색이 현재 플레이어의 돌과 일치하면 count 증가
				if (isInsideBoard(xx, yy) && chessBoard[xx][yy] == id) {
					count++;
				} else {
					break;
				}
			}

			// 5개 이상 연결되면 true 반환 👉 승리
			if (count >= 5) {
				return true;
			}
		}
		// 5개 미만으로 연결된 경우 false 반환 👉 게임 계속 진행
		return false;
	}

	// 오목 판 안에 있는지 여부 확인하는 메서드
	private boolean isInsideBoard(int x, int y) {
		return x >= 0 && x < 15 && y >= 0 && y < 15;
	}
}
