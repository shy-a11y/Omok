/**
 * 이다니엘창민 (20241115)
 * PostPanel 클래스
 * 게시글 목록을 화면에 표시하는 패널 구현
 * 게시글의 작성자, 내용, 좋아요 버튼, 댓글 패널 등을 포함하여 메인 화면의 중심 요소로 사용
 */


package view;
import javax.swing.*;


import model.Post;
import service.PostService;

import java.awt.*;
import java.util.List;

public class PostPanel extends JPanel {
	private static final long serialVersionUID = 1L;


    public PostPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        loadPosts();
    }

    private void loadPosts() {
        List<Post> posts = PostService.getPosts();// PostService에서 글 데이터 가져오기
        if (posts == null || posts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "게시글을 불러오는 데 실패했습니다. 다시 시도해주세요.", "경고", JOptionPane.WARNING_MESSAGE); // 예외처리 로직
            return ;
        }	//게시물이 로드 가 안되었을때 경고문
        for (Post post : posts) {
            add(createPostComponent(post));
        }
    }

    private JPanel createPostComponent(Post post) {
        JPanel postPanel = new JPanel(new BorderLayout());
        JLabel userIdLabel = new JLabel("작성자: " + post.getPostId());
        JTextArea contentArea = new JTextArea(post.getText());
        contentArea.setEditable(false);

        // 좋아요 버튼과 댓글 섹션 추가
        JButton likeButton = new JButton("좋아요 (" + post.getNumOfLikes() + ")");
        likeButton.addActionListener(e -> PostService.likePost(post.getPostId()));

        CommentPanel commentPanel = new CommentPanel(post.getPostId());

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(likeButton);
        bottomPanel.add(commentPanel);

        postPanel.add(userIdLabel, BorderLayout.NORTH);
        postPanel.add(contentArea, BorderLayout.CENTER);
        postPanel.add(bottomPanel, BorderLayout.SOUTH);

        return postPanel;
    }
}
