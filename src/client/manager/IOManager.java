package client.manager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;


// 입출력 스트림 싱글톤 패턴 관리

public class IOManager {

	// 유일한 인스턴스를 저장할 정적 변수
	private static IOManager instance = null;

	// 입력 스트림을 나타내는 BufferedReader 변수
	private BufferedReader br = null;

	// 출력 스트림을 나타내는 PrintStream 변수
	private PrintStream ps = null;

	private IOManager() {
		// private 생성자 👉 외부 인스턴스 생성 ❌
	}

	// 인스턴스에 접근할 수 있는 정적 메소드
	public static IOManager getInstance() {
		if (instance == null) {
			instance = new IOManager();
		}
		return instance;
	}

	// 출력 스트림을 반환하는 메소드
	public PrintStream getPs() {
		return ps;
	}

	// 입력 스트림을 반환하는 메소드
	public BufferedReader getBr() {
		return br;
	}

	// 출력 스트림을 설정하는 메소드
	public void setPs(OutputStream os) {
		ps = new PrintStream(os);
	}

	// 입력 스트림을 설정하는 메소드
	public void setBr(InputStreamReader isr) {
		br = new BufferedReader(isr);
	}
}
