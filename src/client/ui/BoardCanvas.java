package client.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import client.Data;
import client.listener.BoardListener;
import client.manager.ThemeManager;

// 오목 판 GUI
public class BoardCanvas extends Canvas {

    private static final long serialVersionUID = 1L;
    private final int MAP_WIDTH = 531; // 가로
    private final int MAP_HEIGHT = 531; // 세로

    // 이미지 캐시를 저장할 BufferedImage 객체 생성
    BufferedImage chessBoardImage = new BufferedImage(MAP_WIDTH, MAP_HEIGHT, ColorSpace.TYPE_RGB);
    // Graphics2D 객체 생성 및 BufferedImage에 그래픽을 그리기 위한 설정
    Graphics2D g = chessBoardImage.createGraphics();

    public BoardCanvas() {
        this.paintBoardImage();
        this.setSize(MAP_WIDTH, MAP_HEIGHT);
        this.addMouseListener(new BoardListener());
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(chessBoardImage, 0, 0, null);
    }

    // 오목 판 이미지를 그리는 메소드
    public void paintBoardImage() {
        this.paintBackground();
        this.paintChess();
    }

    // 오목 판 배경을 그리는 메소드
    public void paintBackground() {
        try {
            // 현재 테마에 맞는 오목판 이미지를 로드
            BufferedImage background = ImageIO.read(this.getClass().getResource(ThemeManager.getThemeImagePath("map.png")));
            g.drawImage(background, 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 오목판에 돌을 그리는 메소드
    public void paintChess() {
        BufferedImage black = null;
        BufferedImage white = null;

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (Data.chessBoard[i][j] == Data.BLACK) {
                    try {
                        black = ImageIO.read(this.getClass().getResource(ThemeManager.getThemeImagePath("black.png")));
                        if (15 * j + i == Data.last) {
                            black = ImageIO.read(this.getClass().getResource(ThemeManager.getThemeImagePath("black2.png")));
                        }
                        g.drawImage(black, i * 35 + 4, j * 35 + 4, null);
                    } catch (IOException e) {
                        g.fillOval(i * 35 + 4, j * 35 + 4, 33, 33);
                        e.printStackTrace();
                    }
                } else if (Data.chessBoard[i][j] == Data.WHITE) {
                    try {
                        white = ImageIO.read(this.getClass().getResource(ThemeManager.getThemeImagePath("white.png")));
                        if (15 * j + i == Data.last) {
                            white = ImageIO.read(this.getClass().getResource(ThemeManager.getThemeImagePath("white2.png")));
                        }
                        g.drawImage(white, i * 35 + 4, j * 35 + 4, null);
                    } catch (IOException e) {
                        g.setColor(Color.white);
                        g.fillOval(i * 35 + 4, j * 35 + 4, 33, 33);
                        g.setColor(Color.black);
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
