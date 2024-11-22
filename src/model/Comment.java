/**
 * 이다창(20241115)
 * Comment 모델 클래스
 * 각 댓글의 ID, 게시글 ID, 작성자 ID, 내용 등을 저장하는 클래스
 * 댓글 데이터를 구조화,관리
 */
package model;

public class Comment {
    private int id;
    private int postId;
    private String userId;
    private String content;

    public Comment(int id, int postId, String userId, String content) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
    }

    public int getId() { return id; }
    public int getPostId() { return postId; }
    public String getUserId() { return userId; }
    public String getContent() { return content; }
}
