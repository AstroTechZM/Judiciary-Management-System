package com.astro;
import java.time.LocalDate;

public class Case {
    private final String caseNumber;
    private final String caseTitle;
    private final String caseType;
    private final String currentStatus;
    private final LocalDate filingDate;
    private final String assignedJudge;

    public Case(String caseNumber, String caseTitle, String caseType, String currentStatus, LocalDate filingDate, String assignedJudge) {
        this.caseNumber = caseNumber;
        this.caseTitle = caseTitle;
        this.caseType = caseType;
        this.currentStatus = currentStatus;
        this.filingDate = filingDate;
        this.assignedJudge = assignedJudge;
    }

    // Getters for TableView PropertyValueFactory
    public String getCaseNumber() { return caseNumber; }
    public String getCaseTitle() { return caseTitle; }
    public String getCaseType() { return caseType; }
    public String getCurrentStatus() { return currentStatus; }
    public LocalDate getFilingDate() { return filingDate; }
    public String getAssignedJudge() { return assignedJudge; }
}
// -----------------------------------------------------------------------------
