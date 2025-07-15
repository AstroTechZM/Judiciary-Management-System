package com.astro;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * A customizable card component for JavaFX applications, extending VBox.
 * Provides a structured layout with a title bar and content area, featuring
 * customizable styling, including background color, shadow effects, and rounded corners.
 */
public class Card extends VBox {

    private final Label titleLabel; // Label for the card's title
    private final VBox contentPane; // Container for the card's main content

    /**
     * Default constructor that initializes a card with an empty title.
     * Sets up the card's structure and applies default styling.
     */
    public Card() {
        this(""); // Delegate to parameterized constructor with empty title
    }

    /**
     * Parameterized constructor that initializes a card with a specified title.
     * Configures the title bar, content area, and default visual styling.
     *
     * @param title The title text to display at the top of the card.
     */
    public Card(String title) {
        // Initialize title label with specified text and styling
        this.titleLabel = new Label(title);
        this.titleLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        this.titleLabel.setTextFill(Color.web("#FFFFFF"));

        // Configure title bar container
        HBox titleBox = new HBox(this.titleLabel);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        titleBox.setPadding(new Insets(10, 15, 5, 15));

        // Initialize content area container
        this.contentPane = new VBox();
        this.contentPane.setPadding(new Insets(10, 15, 15, 15));
        this.contentPane.setSpacing(10);

        // Add title bar and content area to the card
        this.getChildren().addAll(titleBox, contentPane);

        // Apply default card styling
        this.setStyle(
            "-fx-background-color: #2b2e35;" +
            "-fx-background-radius: 8;" +
            "-fx-border-radius: 8;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 0);"
        );
        this.setPadding(Insets.EMPTY);
        VBox.setVgrow(this.contentPane, Priority.ALWAYS); // Ensure content area expands vertically
    }

    /**
     * Updates the title text of the card.
     *
     * @param title The new title text to set.
     */
    public void setTitle(String title) {
        this.titleLabel.setText(title);
    }

    /**
     * Retrieves the current title text of the card.
     *
     * @return The current title text.
     */
    public String getTitle() {
        return this.titleLabel.getText();
    }

    /**
     * Replaces the card's content with the specified node.
     * Clears any existing content before adding the new node.
     *
     * @param content The node to set as the card's content. If null, clears the content area.
     */
    public void setCardContent(Node content) {
        this.contentPane.getChildren().clear();
        if (content != null) {
            this.contentPane.getChildren().add(content);
        }
    }

    /**
     * Adds a node to the card's content area without clearing existing content.
     *
     * @param content The node to append to the content area. Ignored if null.
     */
    public void addCardContent(Node content) {
        if (content != null) {
            this.contentPane.getChildren().add(content);
        }
    }

    /**
     * Updates the font size of the card's title while preserving the font family and weight.
     *
     * @param size The new font size for the title.
     */
    public void setTitleFontSize(double size) {
        this.titleLabel.setFont(Font.font(
            this.titleLabel.getFont().getFamily(),
            FontWeight.BOLD,
            size
        ));
    }

    /**
     * Sets the text color of the card's title.
     *
     * @param color The color to apply to the title text.
     */
    public void setTitleTextColor(Color color) {
        this.titleLabel.setTextFill(color);
    }

    /**
     * Updates the background color of the card while preserving other styles.
     *
     * @param color The CSS color string (e.g., "#3e4450") to set as the background color.
     */
    public void setCardBackgroundColor(String color) {
        String currentStyle = this.getStyle();
        // Remove existing background color to avoid style conflicts
        String newStyle = currentStyle
            .replaceAll("-fx-background-color: #[0-9a-fA-F]{6};", "")
            .replaceAll("-fx-background-color: [^;]+;", "");
        this.setStyle(newStyle + "-fx-background-color: " + color + ";");
    }

    /**
     * Applies a new CSS style string to the card, overwriting all previous styles.
     * Use cautiously, as this may remove default border and shadow effects.
     *
     * @param style The CSS style string to apply to the card.
     */
    public void setCardStyle(String style) {
        this.setStyle(style);
    }
}