package client.manager;

import javax.swing.*;

public class ThemeManager {
    private static String themePrefix = "images/";  // 기본 테마 경로

    // 기본 테마 설정
    public static void setBasicTheme() {
        themePrefix = "images/";  // 기본 테마 폴더
        System.out.println("기본 테마 적용됨");
        // 필요 시 UI 요소를 업데이트하거나 repaint 등을 호출
    }

    // 커스텀 테마 설정
    public static void setCustomTheme() {
        themePrefix = "images_custom/";  // 커스텀 테마 폴더
        System.out.println("커스텀 테마 적용됨");
        // 필요 시 UI 요소를 업데이트하거나 repaint 등을 호출
    }

    // 이미지 경로 가져오기
    public static String getThemeImagePath(String imageName) {
        return themePrefix + imageName;
    }
}
