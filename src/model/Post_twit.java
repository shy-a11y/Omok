/**
 * 이다창(20241115)
 * Post_twit 모델 클래스
 * 게시글의 ID, 작성자 ID, 게시글 내용, 좋아요 수 등을 저장하는 클래스
 * 게시글 데이터를 구조화, 관리
 */

package model;

public class Post_twit {
    private int id;
    private String userId;
    private String content;
    private int likeCount;

    public Post_twit(int id, String userId, String content, int likeCount) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.likeCount = likeCount;
    }

    public int getId() { return id; }
    public String getUserId() { return userId; }
    public String getContent() { return content; }
    public int getLikeCount() { return likeCount; }
}
