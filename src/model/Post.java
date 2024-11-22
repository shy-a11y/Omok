/**
 * 이다창(20241115)
 * Post 모델 클래스
 * 게시글의 기본 정보를 저장하는 클래스입니다.
 * 게시글 ID, 작성자 ID, 내용, 좋아요 수 등을 관리하며, Post_twit과 함께 사용됩니다.
 */

package model;

public class Post {
    private int postId;
    private String text;
    private String writterId;
    private int numOfLikes;

    // 생성자
    public Post(int postId, String text, String writterId, int numOfLikes) {
        this.postId = postId;
        this.text = text;
        this.writterId = writterId;
        this.numOfLikes = numOfLikes;
    }

    // Getter 메서드들
    public int getPostId() {
        return postId;
    }

    public String getText() {
        return text;
    }

    public String getWritterId() {
        return writterId;
    }

    public int getNumOfLikes() {
        return numOfLikes;
    }
}

	