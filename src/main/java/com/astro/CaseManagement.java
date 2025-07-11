package com.astro;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
 import java.time.LocalDate;


public class CaseManagement {

    private VBox caseManagementPane;
    private TableView<Case> allCasesTable;
    private TableView<Case> currentStatusTable;
    private ObservableList<Case> masterCaseList; // Holds all cases
    private FilteredList<Case> filteredCaseList; // For the search/filters

    public CaseManagement() {
        caseManagementPane = new VBox(20); // Spacing between sections
        caseManagementPane.setPadding(new Insets(20));
        caseManagementPane.setStyle("-fx-background-color: #282c34;"); // Main content background

        // Initialize master data (replace with actual data loading later)
        masterCaseList = FXCollections.observableArrayList(
            new Case("C001", "Smith vs. Jones", "Civil", "Hearing Scheduled", LocalDate.of(2025, 6, 1), "Judge Evans"),
            new Case("C002", "State vs. Johnson", "Criminal", "In Progress", LocalDate.of(2025, 5, 10), "Judge Patel"),
            new Case("C003", "Estate of Miller", "Family", "Awaiting Judgment", LocalDate.of(2025, 7, 5), "Judge Chen"),
            new Case("C004", "Doe vs. Roe", "Civil", "Filed", LocalDate.of(2025, 7, 9), "Unassigned"),
            new Case("C005", "State vs. Davis", "Criminal", "Closed", LocalDate.of(2025, 4, 15), "Judge Evans")
        );
        filteredCaseList = new FilteredList<>(masterCaseList, p -> true); // Initially show all

        // 1. Case Type Filters & Main Search
        HBox topFilterBar = createTopFilterBar();
        VBox.setMargin(topFilterBar, new Insets(0, 0, 10, 0)); // Margin below filters

        // 2. All Cases Table (Main Case List)
        Label allCasesLabel = new Label("All Cases");
        allCasesLabel.setStyle("-fx-text-fill: #e0e0e0; -fx-font-size: 16px; -fx-font-weight: bold;");
        allCasesTable = createCaseTable(filteredCaseList); // Use filtered list here

        // 3. Current Status & Related Documents Section
        HBox bottomSection = createBottomSection();

        caseManagementPane.getChildren().addAll(topFilterBar, allCasesLabel, allCasesTable, bottomSection);
    }

    private HBox createTopFilterBar() {
        HBox filterBar = new HBox(10); // Spacing between filter buttons
        filterBar.setAlignment(Pos.CENTER_LEFT);

        Button registerCaseButton = new Button("Register New Case");
        registerCaseButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        registerCaseButton.setOnAction(e -> System.out.println("Register New Case clicked!")); // Implement registration form later

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS); // Push search to right

        TextField searchField = new TextField();
        searchField.setPromptText("Search by Case ID / Name...");
        searchField.setPrefWidth(300);
        searchField.setStyle("-fx-background-color: #4a5059; -fx-text-fill: #e0e0e0; -fx-prompt-text-fill: #b0b0b0; -fx-border-radius: 5; -fx-background-radius: 5;");

        // Implement search filtering
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredCaseList.setPredicate(caseItem -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Display all cases if search field is empty
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (caseItem.getCaseNumber().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter by case number
                } else if (caseItem.getCaseTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter by case title
                } else if (caseItem.getCaseType().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter by case type
                }
                return false; // Does not match
            });
        });

        filterBar.getChildren().addAll(registerCaseButton, spacer, searchField);
        return filterBar;
    }

    private TableView<Case> createCaseTable(ObservableList<Case> data) {
        TableView<Case> table = new TableView<>();
        table.setItems(data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Make columns fill width

        TableColumn<Case, String> numberCol = new TableColumn<>("Case Number");
        numberCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCaseNumber()));
        numberCol.setPrefWidth(100);

        TableColumn<Case, String> titleCol = new TableColumn<>("Case Title");
        titleCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCaseTitle()));
        titleCol.setPrefWidth(250);

        TableColumn<Case, String> typeCol = new TableColumn<>("Case Type");
        typeCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCaseType()));
        typeCol.setPrefWidth(100);

        TableColumn<Case, String> statusCol = new TableColumn<>("Current Status");
        statusCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCurrentStatus()));
        statusCol.setPrefWidth(150);

        TableColumn<Case, LocalDate> filingDateCol = new TableColumn<>("Filing Date");
        filingDateCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getFilingDate()));
        filingDateCol.setPrefWidth(120);

        TableColumn<Case, String> judgeCol = new TableColumn<>("Assigned Judge");
        judgeCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAssignedJudge()));
        judgeCol.setPrefWidth(150);

        // Action Column (for Clerk/Judge)
        TableColumn<Case, Void> actionsCol = new TableColumn<>("Actions");
        actionsCol.setPrefWidth(80);
        actionsCol.setCellFactory(param -> new TableCell<Case, Void>() {
            private final Button btn = new Button("View/Edit");
            {
                btn.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-padding: 3 8; -fx-font-size: 10px; -fx-border-radius: 3; -fx-background-radius: 3;");
                btn.setOnAction(event -> {
                    Case data = getTableView().getItems().get(getIndex());
                    System.out.println("View/Edit: " + data.getCaseNumber());
                    // Logic to open Case Details Page
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });


        table.getColumns().addAll(numberCol, titleCol, typeCol, statusCol, filingDateCol, judgeCol, actionsCol);

        // Styling for the table itself
        table.setStyle("-fx-control-inner-background: #3e4450; " +
                       "-fx-background-color: #3e4450; " +
                       "-fx-table-cell-border-color: #282c34; " +
                       "-fx-text-fill: #e0e0e0;");
        table.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        // Right side: Related Documents and Notes
        /*VBox relatedDocsNotesBox = new VBox(10);
        relatedDocsNotesBox.setPrefWidth(350); // Fixed width for this column
        relatedDocsNotesBox.setStyle("-fx-background-color: #3e4450; -fx-padding: 15; -fx-border-radius: 8; -fx-background-radius: 8;");

        Label relatedDocsLabel = new Label("Related Documents");
        relatedDocsLabel.setStyle("-fx-text-fill: #e0e0e0; -fx-font-size: 16px; -fx-font-weight: bold;");

        ListView<String> documentList = new ListView<>(FXCollections.observableArrayList("Complaint Document.pdf", "Evidence_1.jpg", "Court" Order.docheets().add(getClass().getResource("/table-styles.css").toExternalForm()); // Custom CSS for table rows/headers*/
        return table;
    }

    private HBox createBottomSection() {
        HBox bottomSection = new HBox(20);
        bottomSection.setAlignment(Pos.TOP_CENTER);
        HBox.setHgrow(bottomSection, Priority.ALWAYS); // Make it take available horizontal space

        // Left side: Current Status Table (could be "My Cases" or a filtered view)
        VBox currentStatusBox = new VBox(10);
        Label currentStatusLabel = new Label("Current Status Cases");
        currentStatusLabel.setStyle("-fx-text-fill: #e0e0e0; -fx-font-size: 16px; -fx-font-weight: bold;");

        // Example: Filtered to show only "In Progress" or "Hearing Scheduled"
        ObservableList<Case> activeCases = FXCollections.observableArrayList(
            masterCaseList.filtered(c -> c.getCurrentStatus().equals("In Progress") || c.getCurrentStatus().equals("Hearing Scheduled"))
        );
        currentStatusTable = createCaseTable(activeCases); // Reuse table creation
        currentStatusTable.setPrefHeight(200); // Limit height for this section
        currentStatusBox.getChildren().addAll(currentStatusLabel, currentStatusTable);
        HBox.setHgrow(currentStatusBox, Priority.ALWAYS);


        // Right side: Related Documents and Notes
        VBox relatedDocsNotesBox = new VBox(10);
        relatedDocsNotesBox.setPrefWidth(350); // Fixed width for this column
        relatedDocsNotesBox.setStyle("-fx-background-color: #3e4450; -fx-padding: 15; -fx-border-radius: 8; -fx-background-radius: 8;");

        Label relatedDocsLabel = new Label("Related Documents");
        relatedDocsLabel.setStyle("-fx-text-fill: #e0e0e0; -fx-font-size: 16px; -fx-font-weight: bold;");

        ListView<String> documentList = new ListView<>(FXCollections.observableArrayList("Complaint Document.pdf", "Evidence_1.jpg", "Court Order.docx"));
        documentList.setPrefHeight(120);
        documentList.setStyle("-fx-background-color: #4a5059; -fx-control-inner-background: #4a5059; -fx-text-fill: #e0e0e0;"); // Listview background
        documentList.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    setTextFill(javafx.scene.paint.Color.valueOf("#e0e0e0")); // Item text color
                }
            }
        });


        Label notesLabel = new Label("Internal Notes");
        notesLabel.setStyle("-fx-text-fill: #e0e0e0; -fx-font-size: 16px; -fx-font-weight: bold;");
        VBox.setMargin(notesLabel, new Insets(15, 0, 0, 0)); // Margin above notes

        TextArea notesArea = new TextArea("Case notes will appear here...");
        notesArea.setPrefHeight(80);
        notesArea.setWrapText(true);
        notesArea.setStyle("-fx-control-inner-background: #4a5059; -fx-text-fill: #e0e0e0; -fx-prompt-text-fill: #b0b0b0; -fx-border-radius: 5; -fx-background-radius: 5;");


        relatedDocsNotesBox.getChildren().addAll(relatedDocsLabel, documentList, notesLabel, notesArea);


        bottomSection.getChildren().addAll(currentStatusBox, relatedDocsNotesBox);
        return bottomSection;
    }

    public VBox getView() {
        return caseManagementPane;
    }
}