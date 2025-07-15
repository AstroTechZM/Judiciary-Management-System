package com.astro;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * Dashboard view for the judiciary management system.
 * Displays key metrics and quick actions in a responsive grid layout.
 * Contains multiple dashboard cards showing real-time case data,
 * notifications, and system analytics.
 */
public class Dashboard {

    // Main layout container using GridPane for responsive card arrangement
    private GridPane mainLayout;

    /**
     * Constructs the dashboard with all UI components.
     * Initializes the layout and creates four primary dashboard cards:
     * 1. New Cases Today - Displays current case intake
     * 2. Cases by Type - Visualizes case distribution
     * 3. Notifications - Shows system alerts and updates
     * 4. Quick Actions - Provides access to common tasks
     */
    public Dashboard() {
        // Initialize main grid layout with spacing and styling
        mainLayout = new GridPane();
        mainLayout.setPadding(new Insets(20));
        mainLayout.setHgap(20);
        mainLayout.setVgap(20);
        mainLayout.setStyle("-fx-background-color: #1a1e24;");  // Dark theme background

        // Card 1: New Cases Today -------------------------------------------------
        Card newCasesCard = new Card("New Cases Today");
        Label casesCount = new Label("15");
        casesCount.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: #00FFC2;");
        HBox countBox = new HBox(casesCount);
        countBox.setAlignment(Pos.CENTER);
        newCasesCard.setCardContent(countBox);
        mainLayout.add(newCasesCard, 0, 0);  // Position at column 0, row 0

        // Card 2: Case Type Distribution ------------------------------------------
        Card casesByTypeCard = new Card("Cases by Type");
        
        // Initialize bar chart with case type categories
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setPrefHeight(200);
        barChart.setLegendVisible(false);
        barChart.setStyle("-fx-bar-fill: #1E90FF;");  // Custom bar color
        
        // Populate chart with sample data
        XYChart.Series<String, Number> caseSeries = new XYChart.Series<>();
        caseSeries.getData().add(new XYChart.Data<>("Civil", 50));
        caseSeries.getData().add(new XYChart.Data<>("Criminal", 80));
        caseSeries.getData().add(new XYChart.Data<>("Family", 30));
        barChart.getData().add(caseSeries);
        
        casesByTypeCard.setCardContent(barChart);
        mainLayout.add(casesByTypeCard, 1, 0);  // Position at column 1, row 0

        // Card 3: System Notifications --------------------------------------------
        Card notificationCard = new Card("Notifications");
        VBox notificationContent = new VBox(5);  // Vertical container with 5px spacing
        
        // Notification items
        Label notification1 = new Label("• Case #2025-0123: Hearing adjourned.");
        Label notification2 = new Label("• New filing: Plaintiff Smith vs. Jones.");
        Label notification3 = new Label("• Deadline approaching for Case #2024-5678.");
        
        // Apply consistent text styling
        String notificationStyle = "-fx-text-fill: #DDDDDD;";
        notification1.setStyle(notificationStyle);
        notification2.setStyle(notificationStyle);
        notification3.setStyle(notificationStyle);
        
        // View All button
        Button viewAllButton = new Button("View All");
        viewAllButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        HBox buttonBox = new HBox(viewAllButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        
        // Assemble notification content
        notificationContent.getChildren().addAll(notification1, notification2, notification3, buttonBox);
        notificationCard.setCardContent(notificationContent);
        mainLayout.add(notificationCard, 0, 1);  // Position at column 0, row 1

        // Card 4: Quick Task Access -----------------------------------------------
        Card quickActionsCard = new Card("Quick Actions");
        VBox actionButtons = new VBox(10);  // Vertical container with 10px spacing
        
        // Action buttons
        Button assignCaseBtn = new Button("Assign Case");
        Button generateReportBtn = new Button("Generate Report");
        Button manageUsersBtn = new Button("Manage Users");
        
        // Unified button styling
        String buttonStyle = "-fx-background-color: #4CAF50; " +  // Green background
                            "-fx-text-fill: white; " +
                            "-fx-font-weight: bold; " +
                            "-fx-pref-width: 150px;";
        
        assignCaseBtn.setStyle(buttonStyle);
        generateReportBtn.setStyle(buttonStyle);
        manageUsersBtn.setStyle(buttonStyle);
        
        actionButtons.getChildren().addAll(assignCaseBtn, generateReportBtn, manageUsersBtn);
        quickActionsCard.setCardContent(actionButtons);
        mainLayout.add(quickActionsCard, 1, 1);  // Position at column 1, row 1
    }

    /**
     * Provides the root node of the dashboard view.
     * @return The GridPane containing all dashboard components,
     *         ready for integration into the main application layout.
     */
    public Node getView() {
        return mainLayout;
    }
}