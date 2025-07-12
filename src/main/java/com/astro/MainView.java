package com.astro;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*; // Keep Image and ImageView
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Side;
// REMOVE THESE GLYPH-RELATED IMPORTS:
// import org.controlsfx.glyphfont.GlyphFont;
// import org.controlsfx.glyphfont.GlyphFontRegistry;
// import org.controlsfx.glyphfont.Glyph;
import javafx.scene.Node; // Keep Node

public class MainView extends Application {
    private static final int ICON_SIZE = 35;
    private static final int LARGE_ICON_SIZE = 50;
    private static final Color PRIMARY_COLOR = Color.rgb(73, 88, 181);

    @Override
    public void start(Stage primaryStage) {
        // REMOVE THIS GLYPH FONT REGISTRATION BLOCK:
        /*
        try {
            GlyphFontRegistry.register(new GlyphFont("MaterialSymbols", // A unique name for your font
                        16, // Default font size (can be overridden per icon)
                        getClass().getResourceAsStream("/lib/") // Path to your TTF
                        
            ));
        } catch (Exception e) {
            System.err.println("Failed to register Material Symbols font: " + e.getMessage());
            // Handle error, e.g., use fallback icons or log it
        }
        */

        BorderPane root = new BorderPane();
        root.setTop(createMenuBar());
        root.setCenter(createMainContent());

        Scene scene = new Scene(root, 1024, 768);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setTitle("Judiciary Management System");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    private HBox createMenuBar() {
        Label titleLabel = new Label("Judiciary Management System");
        titleLabel.setFont(Font.font("Arial", 20));
        titleLabel.setTextFill(PRIMARY_COLOR);

        HBox panel = new HBox(10);
        panel.setPadding(new Insets(10));
        panel.setAlignment(Pos.CENTER_LEFT);
        panel.setStyle("-fx-background-color: #d1dbe5; -fx-border-color: #b0b0b0; -fx-border-width: 0 0 2 0;");

        // Home icon
        ImageView homeIcon = createIconView("/lib/icon.jpeg", LARGE_ICON_SIZE);

        Label astro = new Label("AstroE-case");
        astro.setFont(Font.font("Arial", 20));
        astro.setTextFill(PRIMARY_COLOR);

        // Login icon
        ImageView loginIcon = createIconView("/lib/account.png", ICON_SIZE);

        Region spacer1 = new Region();
        Region spacer2 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        panel.getChildren().addAll(homeIcon, astro, spacer1, titleLabel, spacer2, loginIcon);
        return panel;
    }

    private BorderPane createMainContent() {
        BorderPane mainBody = new BorderPane();
        mainBody.setCenter(createSideMenu());
        return mainBody;
    }

    private TabPane createSideMenu() {
        TabPane tabPane = new TabPane();
        tabPane.setSide(Side.LEFT);
        CaseManagement caseManagement = new CaseManagement();
        CourtScheduling courtScheduling = new CourtScheduling();

        // Now passing the image path directly to addTab, which will use ImageView
        addTab(tabPane, "Dashboard", "/lib/dashboard.png", caseManagement.getView());
        addTab(tabPane, "Manage Cases", "/lib/registration.png", caseManagement.getView());
        addTab(tabPane, "Court Schedules", "/lib/workload.png", courtScheduling.getView());
        //addTab(tabPane, "past Cases", "/lib/pendingCase.png", courtScheduling.getView());
        //addTab(tabPane, "Profile", "/lib/account.png", courtScheduling.getView());
        //addTab(tabPane, "Logs", "/lib/at.png", courtScheduling.getView());
        //addTab(tabPane, "Logout", "/lib/at.png", courtScheduling.getView());

        return tabPane;
    }

    // MODIFIED addTab method to use ImageView
    private void addTab(TabPane pane, String tooltip, String iconPath, Node contentShow) { // iconPath is now truly a path
        Tab tab = new Tab();
        tab.setTooltip(new Tooltip(tooltip));

        // Create the icon using ImageView, reusing the createIconView helper
        ImageView iconImageView = createIconView(iconPath, ICON_SIZE);

        VBox content = new VBox(10); // 10px spacing between children
        content.getChildren().addAll(iconImageView, new Label(tooltip)); // Add the ImageView
        content.setAlignment(Pos.CENTER);

        tab.setGraphic(content);
        tab.setClosable(false);
        
        // The StackPane contentPane was unnecessary here as contentShow is already a Node
        // StackPane contentPane = new StackPane();
        // contentPane.setMinSize(600, 500); 
        tab.setContent(contentShow); // Set the dynamically provided content
        pane.getTabs().add(tab);
    }

   private ImageView createIconView(String path, int size) {
    try {
        // Load image from classpath
        java.net.URL imageUrl = getClass().getResource(path);
        if (imageUrl == null) {
            System.err.println("Error loading icon: " + path + " - URL is null.");
            // Return a placeholder or empty ImageView if image not found
            return new ImageView(new Image(getClass().getResourceAsStream("/lib/default.png"), size, size, true, true)); // Fallback image
        }
        Image img = new Image(imageUrl.toExternalForm(), size, size, true, true);
        return new ImageView(img);
    } catch (Exception e) {
        System.err.println("Error loading icon: " + path + " - Exception: " + e.getMessage());
        // Return a placeholder or empty ImageView on exception
        return new ImageView(new Image(getClass().getResourceAsStream("/lib/default.png"), size, size, true, true)); // Fallback image
    }
}

    public static void main(String[] args) {
        launch(args);
    }
}