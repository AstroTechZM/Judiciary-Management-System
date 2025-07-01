import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainView {
    private static final int ICON_SIZE = 25;
    private static final int LARGE_ICON_SIZE = 50;
    private static final Color PRIMARY_COLOR = new Color(73, 88, 181);

    public MainView() {
        JFrame frame = createMainFrame();
        frame.add(createMenuBar());
        frame.add(createMainContent());

        frame.setVisible(true);
    }

    private JFrame createMainFrame() {
        JFrame frame = new JFrame("Judiciary Management System");
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        //frame.setSize(1024, 768); // More reasonable default size
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(800, 600)); // Set minimum size
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    private JPanel createMenuBar() {
        JLabel titleLabel = new JLabel("Judiciary Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(PRIMARY_COLOR);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(new Color(209, 219, 229));
        panel.setBorder(BorderFactory.createRaisedBevelBorder());

        // Home icon
        JLabel homeIcon = createIconLabel("../lib/icon.jpeg", LARGE_ICON_SIZE);

        JLabel astro = new JLabel("AstroE-case");
        astro.setFont(new Font("Arial", Font.BOLD, 20));
        astro.setForeground(PRIMARY_COLOR);
        
        // Login icon
        JLabel loginIcon = createIconLabel("../lib/logIn.jpeg", ICON_SIZE);
        loginIcon.setBorder(new EmptyBorder(0, 10, 0, 10));

        panel.add(homeIcon);
        panel.add(astro);
        panel.add(Box.createHorizontalGlue());
        panel.add(titleLabel);
        panel.add(Box.createHorizontalGlue());
        panel.add(loginIcon);

        return panel;
    }

    private JComponent createMainContent() {
        JPanel mainBody = new JPanel(new BorderLayout());
        mainBody.add(createSideMenu(), BorderLayout.CENTER);
        return mainBody;
    }

    private JTabbedPane createSideMenu() {
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 14));

        // Add tabs with icons
        addTab(tabbedPane, "Dashboard", "../lib/home.png", new Dashboard());
        addTab(tabbedPane, "Manage Cases", "../lib/home.png", new CaseManagement());
        addTab(tabbedPane, "Court Schedules", "../lib/home.png", new CourtScheduling());
        addTab(tabbedPane, "Documents", "../lib/home.png", new DocumentManagement());
        addTab(tabbedPane, "E-Filings", "../lib/home.png", new Efillings());
        addTab(tabbedPane, "Profile", "../lib/home.png", new Profile());
        addTab(tabbedPane, "Staff", "../lib/home.png", new StaffAllocation());
        addTab(tabbedPane, "Tasks", "../lib/home.png", new Tasks());
        addTab(tabbedPane, "Search", "../lib/home.png", new Search());
        addTab(tabbedPane, "Reports", "../lib/home.png", new Reports());
        addTab(tabbedPane, "Logs", "../lib/home.png", new Logs());
        addTab(tabbedPane, "Settings", "../lib/home.png", new Settings());
        addTab(tabbedPane, "Help", "../lib/home.png", new Help());
        addTab(tabbedPane, "Logout", "../lib/home.png", new Logout());

        return tabbedPane;
    }

    private void addTab(JTabbedPane pane, String tooltip, String iconPath, JComponent component) {
        ImageIcon icon = createIcon(iconPath, ICON_SIZE);
        pane.addTab("", icon, component, tooltip);
    }

    private JLabel createIconLabel(String path, int size) {
        return new JLabel(createIcon(path, size));
    }

    private ImageIcon createIcon(String path, int size) {
        try {
            ImageIcon icon = new ImageIcon(path);
            Image scaledImage = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (Exception e) {
            System.err.println("Error loading icon: " + path);
            return new ImageIcon(); // Return empty icon if there's an error
        }
    }
}