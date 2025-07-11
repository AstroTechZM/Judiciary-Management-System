package com.astro;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Side;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import org.controlsfx.glyphfont.Glyph;
import javafx.scene.Node; // Import Node



public class MainView extends Application {
    private static final int ICON_SIZE = 25;
    private static final int LARGE_ICON_SIZE = 50;
    private static final Color PRIMARY_COLOR = Color.rgb(73, 88, 181);

    @Override
    public void start(Stage primaryStage) {
        // Register the Material Symbols font once
        try {
            GlyphFontRegistry.register(new GlyphFont("MaterialSymbols", // A unique name for your font
                        16, // Default font size (can be overridden per icon)
                        getClass().getResourceAsStream("/lib/") // Path to your TTF
                        
            ));
        } catch (Exception e) {
            System.err.println("Failed to register Material Symbols font: " + e.getMessage());
            // Handle error, e.g., use fallback icons or log it
        }

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
        ImageView loginIcon = createIconView("/lib/logIn.jpeg", ICON_SIZE);

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
        

        addTab(tabPane, "Dashboard", "/lib/dashboard.svg");
        addTab(tabPane, "Manage Cases", "/lib/registration.png");
        addTab(tabPane, "Court Schedules", "/lib/workload.png");
        addTab(tabPane, "past Cases", "/lib/pendingCase.png");
        addTab(tabPane, "Profile", "/lib/account.png");
        addTab(tabPane, "Logs", "/lib/at.png");
        addTab(tabPane, "Logout", "/lib/at.png");

        return tabPane;
    }

    private void addTab(TabPane pane, String tooltip, String iconPath) {
        Tab tab = new Tab();
        tab.setTooltip(new Tooltip(tooltip));
        // Create the icon using Glyph
    // The size() method directly controls the size and it scales perfectly.
        Node iconNode = null;
        try {
            iconNode = new Glyph("MaterialSymbols", tooltip)
                            .size(ICON_SIZE) // Use ICON_SIZE or desired size
                            .color(PRIMARY_COLOR); // Set color if desired
        } catch (Exception e) {
            System.err.println("Could not create glyph for " + tooltip + ": " + e.getMessage());
            iconNode = new Label("?"); // Fallback if glyph fails
        }

        VBox content = new VBox(10); // 10px spacing between children
        content.getChildren().addAll(iconNode, new Label(tooltip));
        content.setAlignment(Pos.CENTER);

        tab.setGraphic(content);
        tab.setClosable(false);
        // Create proper content container instead of Label
        StackPane contentPane = new StackPane();
        contentPane.setMinSize(600, 500); 
        CaseManagement caseManagement = new CaseManagement();
        tab.setContent(caseManagement.getView());
        pane.getTabs().add(tab);
    }

   private ImageView createIconView(String path, int size) {
    try {
        // Load image from classpath
        java.net.URL imageUrl = getClass().getResource(path);
        if (imageUrl == null) {
            System.err.println("Error loading icon: " + path);
            return new ImageView();
        }
        Image img = new Image(imageUrl.toExternalForm(), size, size, true, true);
        return new ImageView(img);
    } catch (Exception e) {
        System.err.println("Error loading icon: " + path);
        return new ImageView();
    }
}

    public static void main(String[] args) {
        launch(args);
    }
}