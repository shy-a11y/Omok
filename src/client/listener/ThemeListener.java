package client.listener;

import client.manager.ThemeManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThemeListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // 테마 선택 창을 띄우는 코드
        String[] options = {"기본 테마", "커스텀 테마"};
        int choice = JOptionPane.showOptionDialog(
                null, 
                "테마를 선택하세요", 
                "테마 선택", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.INFORMATION_MESSAGE, 
                null, 
                options, 
                options[0]
        );

        // 사용자가 선택한 테마에 따라 처리
        if (choice == 0) {
            // 기본 테마 선택
            ThemeManager.setBasicTheme();
            System.out.println("기본 테마 선택됨");
        } else if (choice == 1) {
            // 커스텀 테마 선택
            ThemeManager.setCustomTheme();
            System.out.println("커스텀 테마 선택됨");
        }
    }
}
