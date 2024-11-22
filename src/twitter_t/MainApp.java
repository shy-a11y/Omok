
/*이다니엘창민(20241115)
//GUI 전반을 전부 정의하는 함수
//swing 으로 구현하였음
//크게 왼쪽 중간 오른쪽 패널 구현
// 왼쪽에는 사이드바 구현 >클릭불가
// 중간에는 포스트들 구현> 스크롤까지 구현> 좋아요 버튼 동작 정의 x
// 오른쪽에는 트랜드 프레임 구현 > 알고리즘도 생각 X 그냥 만들어만 둠.
*/

package twitter_t;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Post;
import service.PostService;

public class MainApp extends JFrame {

    public MainApp() {
        setTitle("Twitter Clone");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        // 전체 레이아웃 설정 (BorderLayout)
        setLayout(new BorderLayout());

        // 왼쪽 사이드바 패널 생성
        JPanel sidebarPanel = createSidebarPanel();
        add(sidebarPanel, BorderLayout.WEST);

        // 중앙 메인 패널 생성
        JPanel mainPanel = createMainPanel();
        add(mainPanel, BorderLayout.CENTER);

        // 오른쪽 사이드바 패널 생성
        JPanel trendsPanel = createTrendsPanel();
        add(trendsPanel, BorderLayout.EAST);

        setVisible(true);
    }

    // 왼쪽 사이드바 패널 생성 //버튼들은 만들어두었는데, 동작 정의는 X
    private JPanel createSidebarPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        // 메뉴 아이콘과 트윗 버튼 추가
        String[] menuItems = {"Home", "Explore", "Notifications", "Messages", "Bookmarks", "Lists", "Profile", "More"};
        for (String item : menuItems) {
            JButton button = new JButton(item);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setPreferredSize(new Dimension(150, 50));
            panel.add(button);
            panel.add(Box.createVerticalStrut(10));
        }

        JButton tweetButton = new JButton("Tweet");
        tweetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        tweetButton.setPreferredSize(new Dimension(150, 50));
        panel.add(tweetButton);

        return panel;
    }

    // 중앙 메인 패널 생성
    private JPanel createMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // 트윗 작성 패널 추가
        JPanel tweetPanel = new JPanel();
        tweetPanel.setLayout(new BorderLayout());
        JTextField tweetField = new JTextField("What's happening?");
        JButton postButton = new JButton("Tweet");

        tweetPanel.add(tweetField, BorderLayout.CENTER);
        tweetPanel.add(postButton, BorderLayout.EAST);
        panel.add(tweetPanel, BorderLayout.NORTH);

        // 타임라인 패널 추가 (DB에서 데이터를 로드하여 표시)_스트롤 가능
        JPanel timelinePanel = new JPanel();
        timelinePanel.setLayout(new BoxLayout(timelinePanel, BoxLayout.Y_AXIS));

        // 실제 DB에서 데이터 로드
        List<Post> posts = PostService.getPosts();
        for (Post post : posts) {
            JPanel postPanel = new JPanel();
            postPanel.setLayout(new BorderLayout());
            postPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel userLabel = new JLabel("User ID: " + post.getWritterId());
            JTextArea contentArea = new JTextArea(post.getText());
            contentArea.setLineWrap(true);
            contentArea.setWrapStyleWord(true);
            contentArea.setEditable(false);

            JPanel postActions = new JPanel();
            postActions.setLayout(new FlowLayout(FlowLayout.LEFT));
            postActions.add(new JLabel("♡ " + post.getNumOfLikes())); //라벨 클릭시 동작은 구현 X
            postActions.add(new JLabel("↻")); //라벨 클릭시 동작은 구현 X
            postActions.add(new JLabel("💬")); //라벨 클릭시 동작은 구현 X

            postPanel.add(userLabel, BorderLayout.NORTH);
            postPanel.add(contentArea, BorderLayout.CENTER);
            postPanel.add(postActions, BorderLayout.SOUTH);

            timelinePanel.add(postPanel);
            timelinePanel.add(Box.createVerticalStrut(10)); // 트윗 간격
        }

        JScrollPane timelineScrollPane = new JScrollPane(timelinePanel); //스크롤 구현
        panel.add(timelineScrollPane, BorderLayout.CENTER);

        return panel;
    }

    // 오른쪽 트렌드 패널 생성(패널은 만들어 두었는데, 아닌거 같으면 제거 가능)
    private JPanel createTrendsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(300, 700));
        panel.setBackground(Color.WHITE);

        // 검색창 추가(일단 버튼만 만들어둠. 수틀리면 제거 가능)
        JTextField searchField = new JTextField("Search Twitter");
        panel.add(searchField, BorderLayout.NORTH);

        // 트렌드 목록 패널(그냥 기본 트위터 ui에 있어서 벳겨옴. 이후에 제거 가능)
        JPanel trendsListPanel = new JPanel();
        trendsListPanel.setLayout(new BoxLayout(trendsListPanel, BoxLayout.Y_AXIS));

        // 예시 트렌드 추가
        for (int i = 0; i < 5; i++) {
            JPanel trendPanel = new JPanel();
            trendPanel.setLayout(new BorderLayout());
            trendPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            JLabel trendLabel = new JLabel("#LoremIpsum");
            JLabel tweetCountLabel = new JLabel("29,300 Tweets");

            trendPanel.add(trendLabel, BorderLayout.NORTH);
            trendPanel.add(tweetCountLabel, BorderLayout.SOUTH);

            trendsListPanel.add(trendPanel);
            trendsListPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane trendsScrollPane = new JScrollPane(trendsListPanel);
        panel.add(trendsScrollPane, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        new MainApp();
    }
}
