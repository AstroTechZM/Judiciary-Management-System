package com.astro;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Side;
import javafx.scene.Node;

/**
 * Main application class for the Judiciary Management System, providing the primary user interface.
 * Configures a responsive layout with a top menu bar and a side tabbed navigation menu,
 * integrating various system components such as dashboard, case management, and court scheduling.
 */
public class MainView extends Application {

    private static final int ICON_SIZE = 35; // Standard size for tab icons
    private static final int LARGE_ICON_SIZE = 50; // Size for prominent header icons
    private static final Color PRIMARY_COLOR = Color.rgb(73, 88, 181); // Primary color for branding

    /**
     * Initializes and displays the primary application window.
     *
     * @param primaryStage The main stage for the JavaFX application.
     */
    @Override
    public void start(Stage primaryStage) {
        // Create the root layout
        BorderPane root = new BorderPane();
        root.setTop(createMenuBar());
        root.setCenter(createMainContent());

        // Configure the main scene with stylesheet
        Scene scene = new Scene(root, 1024, 768);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        // Set up the primary stage
        primaryStage.setTitle("Judiciary Management System");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    /**
     * Creates the top menu bar with branding, title, and navigation icons.
     *
     * @return An HBox containing the menu bar components.
     */
    private HBox createMenuBar() {
        // Configure the title label
        Label titleLabel = new Label("Judiciary Management System");
        titleLabel.setFont(Font.font("Arial", 20));
        titleLabel.setTextFill(PRIMARY_COLOR);

        // Create the menu bar container
        HBox panel = new HBox(10);
        panel.setPadding(new Insets(10));
        panel.setAlignment(Pos.CENTER_LEFT);
        panel.setStyle(
            "-fx-background-color: #d1dbe5;" +
            "-fx-border-color: #b0b0b0;" +
            "-fx-border-width: 0 0 2 0;"
        );

        // Add branding and icons
        ImageView homeIcon = createIconView("/lib/icon.jpeg", LARGE_ICON_SIZE);
        Label astroLabel = new Label("AstroE-case");
        astroLabel.setFont(Font.font("Arial", 20));
        astroLabel.setTextFill(PRIMARY_COLOR);
        ImageView loginIcon = createIconView("/lib/account.png", ICON_SIZE);

        // Add spacers for layout flexibility
        Region spacer1 = new Region();
        Region spacer2 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        // Assemble the menu bar
        panel.getChildren().addAll(homeIcon, astroLabel, spacer1, titleLabel, spacer2, loginIcon);
        return panel;
    }

    /**
     * Creates the main content area with a side tabbed navigation menu.
     *
     * @return A BorderPane containing the main content layout.
     */
    private BorderPane createMainContent() {
        BorderPane mainBody = new BorderPane();
        mainBody.setCenter(createSideMenu());
        return mainBody;
    }

    /**
     * Creates a side tabbed menu with navigation options for different system views.
     *
     * @return A TabPane configured with tabs for various system components.
     */
    private TabPane createSideMenu() {
        TabPane tabPane = new TabPane();
        tabPane.setSide(Side.LEFT);

        // Initialize component views
        Dashboard dashboard = new Dashboard();
        CaseManagement caseManagement = new CaseManagement();
        CourtScheduling courtScheduling = new CourtScheduling();

        // Add tabs with icons and content
        addTab(tabPane, "Dashboard", "/lib/dashboard.png", dashboard.getView());
        addTab(tabPane, "Manage Cases", "/lib/registration.png", caseManagement.getView());
        addTab(tabPane, "Court Schedules", "/lib/workload.png", courtScheduling.getView());

        return tabPane;
    }

    /**
     * Adds a tab to the specified TabPane with an icon, tooltip, and content.
     *
     * @param pane The TabPane to which the tab will be added.
     * @param tooltip The tooltip text for the tab.
     * @param iconPath The classpath path to the tab's icon image.
     * @param contentShow The content node to display when the tab is selected.
     */
    private void addTab(TabPane pane, String tooltip, String iconPath, Node contentShow) {
        Tab tab = new Tab();
        tab.setTooltip(new Tooltip(tooltip));

        // Create tab graphic with icon and label
        ImageView iconImageView = createIconView(iconPath, ICON_SIZE);
        VBox content = new VBox(10);
        content.getChildren().addAll(iconImageView, new Label(tooltip));
        content.setAlignment(Pos.CENTER);

        // Configure tab properties
        tab.setGraphic(content);
        tab.setClosable(false);
        tab.setContent(contentShow);

        // Add the tab to the TabPane
        pane.getTabs().add(tab);
    }

    /**
     * Creates an ImageView for an icon with error handling and a fallback image.
     *
     * @param path The classpath path to the icon image.
     * @param size The desired size (width and height) for the icon.
     * @return An ImageView containing the loaded or fallback image.
     */
    private ImageView createIconView(String path, int size) {
        try {
            java.net.URL imageUrl = getClass().getResource(path);
            if (imageUrl == null) {
                System.err.println("Error loading icon: " + path + " - URL is null.");
                return createFallbackIconView(size);
            }
            Image img = new Image(imageUrl.toExternalForm(), size, size, true, true);
            return new ImageView(img);
        } catch (Exception e) {
            System.err.println("Error loading icon: " + path + " - Exception: " + e.getMessage());
            return createFallbackIconView(size);
        }
    }

    /**
     * Creates a fallback ImageView for when an icon cannot be loaded.
     *
     * @param size The desired size (width and height) for the fallback icon.
     * @return An ImageView containing the fallback image.
     */
    private ImageView createFallbackIconView(int size) {
        return new ImageView(new Image(
            getClass().getResourceAsStream("/lib/default.png"),
            size, size, true, true
        ));
    }

    /**
     * Main entry point for the JavaFX application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}