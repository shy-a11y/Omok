/**
 * 이다창(20241115)
 * CommentPanel 클래스
 * 댓글을 표시하고 새로운 댓글을 작성할 수 있는 패널
 * 기존 댓글을 로드, 새 댓글을 추가하는 입력창을 포함 > 데베랑 통신하는지는 확인  x
 */

package view;
import javax.swing.*;

import model.Comment;
import service.PostService;

import java.awt.*;
import java.util.List;

public class CommentPanel extends JPanel {
	private static final long serialVersionUID = 1L;

    private int postId;

    public CommentPanel(int postId) {
        this.postId = postId;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        loadComments();
        add(createCommentInput());
    }

    private void loadComments() {
        List<Comment> comments = PostService.getComments(postId);
        for (Comment comment : comments) {
            add(new JLabel(comment.getUserId() + ": " + comment.getContent()));
        }
    }

    private JPanel createCommentInput() {
        JPanel inputPanel = new JPanel(new BorderLayout());
        JTextField commentField = new JTextField();
        JButton submitButton = new JButton("댓글 작성");

        submitButton.addActionListener(e -> {
            String content = commentField.getText();
            if (!content.isEmpty()) {
                PostService.addComment(postId, "User1", content); // 기본 사용자 ID
                commentField.setText("");
                removeAll();
                loadComments();
                add(createCommentInput());
                revalidate();
                repaint();
            }
        });

        inputPanel.add(commentField, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.EAST);
        return inputPanel;
    }
}
