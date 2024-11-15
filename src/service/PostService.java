/**
 * PostService 클래스
 * 데이터베이스와 상호작용하여 게시글 데이터를 가져오고, 추가 및 수정하는 서비스를 제공합니다.
 * 글 조회, 좋아요 기능, 댓글 추가 등의 기능을 포함합니다.
 */
package service;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import model.Comment;
import model.Post;
import twitter_t.DatabaseConnection;

public class PostService {



    public static List<Post> getPosts() { // 포스트를 가져오는 함수.
        List<Post> posts = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {       
            ResultSet rs = stmt.executeQuery("SELECT * FROM posts");
            
            while (rs.next()) {
                int post_id = rs.getInt("post_id");
                String text = rs.getString("text");
                String writter_id = rs.getString("writter_id");
                int num_of_likes = rs.getInt("num_of_likes");
                posts.add(new Post(post_id, text, writter_id, num_of_likes));
            }

        } catch (SQLException e) {
            // SQLException 발생 시 상세 원인 분석
            int errorCode = e.getErrorCode();
            String sqlState = e.getSQLState();

            if ("08S01".equals(sqlState)) {
                System.err.println("네트워크 오류로 인해 데이터베이스 연결이 끊어졌습니다.");
            } else if ("42000".equals(sqlState) || errorCode == 1064) {
                System.err.println("SQL 문법 오류: 쿼리를 확인해 주세요.");
            } else if ("42S02".equals(sqlState) || errorCode == 1146) {
                System.err.println("테이블을 찾을 수 없습니다. 테이블 이름을 확인해 주세요.");
            } else {
                System.err.println("데이터베이스 오류: " + e.getMessage());
            }
            
            // 스택 트레이스 출력 (디버깅 용도)
            e.printStackTrace();
        }
        
        return posts;
    }
    public static void likePost(int postId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("UPDATE posts SET like_count = like_count + 1 WHERE id = ?")) {
            pstmt.setInt(1, postId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static List<Comment> getComments(int post_id) {
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM comment WHERE post_id = ?")) {
            pstmt.setInt(1, post_id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("post_id");
                String userId = rs.getString("user_id");
                String content = rs.getString("text");
                comments.add(new Comment(id, post_id, userId, content));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }
    
    public static void addComment(int post_id, String user_id, String text) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO comment (post_id, user_id, text) VALUES (?, ?, ?)")) {
            pstmt.setInt(1, post_id);
            pstmt.setString(2, user_id);
            pstmt.setString(3, text);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



    
