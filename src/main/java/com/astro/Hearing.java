package com.astro;

import jfxtras.scene.control.agenda.Agenda;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a court hearing with relevant details such as case number, parties, and scheduling information.
 * Integrates with the Agenda control by providing necessary data for appointment rendering, including
 * summary, description, and appointment group for styling.
 */
public class Hearing {

    private String caseNumber; // Unique identifier for the case
    private String parties; // Names of the involved parties
    private String presidingJudge; // Name of the judge presiding over the hearing
    private LocalDate hearingDate; // Date of the hearing
    private LocalTime startTime; // Start time of the hearing
    private LocalTime endTime; // End time of the hearing
    private String courtRoom; // Assigned courtroom for the hearing
    private String typeOfHearing; // Type of hearing (e.g., Trial, Motion)
    private String status; // Current status of the hearing (e.g., Scheduled)
    private Agenda.AppointmentGroup appointmentGroup; // Styling group for Agenda integration

    /**
     * Constructs a Hearing object with all required details.
     *
     * @param caseNumber      The unique case identifier.
     * @param parties         The involved parties in the case.
     * @param presidingJudge  The judge assigned to the hearing.
     * @param hearingDate     The date of the hearing.
     * @param startTime       The start time of the hearing.
     * @param endTime         The end time of the hearing.
     * @param courtRoom       The assigned courtroom.
     * @param typeOfHearing   The type of hearing (e.g., Trial, Motion).
     * @param status          The current status of the hearing.
     * @param appointmentGroup The Agenda appointment group for styling.
     */
    public Hearing(String caseNumber, String parties, String presidingJudge,
                   LocalDate hearingDate, LocalTime startTime, LocalTime endTime,
                   String courtRoom, String typeOfHearing, String status,
                   Agenda.AppointmentGroup appointmentGroup) {
        this.caseNumber = caseNumber;
        this.parties = parties;
        this.presidingJudge = presidingJudge;
        this.hearingDate = hearingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.courtRoom = courtRoom;
        this.typeOfHearing = typeOfHearing;
        this.status = status;
        this.appointmentGroup = appointmentGroup;
    }

    // --- Getters and Setters ---

    /**
     * Gets the unique case number.
     *
     * @return The case number.
     */
    public String getCaseNumber() {
        return caseNumber;
    }

    /**
     * Sets the unique case number.
     *
     * @param caseNumber The case number to set.
     */
    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    /**
     * Gets the names of the involved parties.
     *
     * @return The parties involved.
     */
    public String getParties() {
        return parties;
    }

    /**
     * Sets the names of the involved parties.
     *
     * @param parties The parties to set.
     */
    public void setParties(String parties) {
        this.parties = parties;
    }

    /**
     * Gets the name of the presiding judge.
     *
     * @return The presiding judge's name.
     */
    public String getPresidingJudge() {
        return presidingJudge;
    }

    /**
     * Sets the name of the presiding judge.
     *
     * @param presidingJudge The judge's name to set.
     */
    public void setPresidingJudge(String presidingJudge) {
        this.presidingJudge = presidingJudge;
    }

    /**
     * Gets the date of the hearing.
     *
     * @return The hearing date.
     */
    public LocalDate getHearingDate() {
        return hearingDate;
    }

    /**
     * Sets the date of the hearing.
     *
     * @param hearingDate The hearing date to set.
     */
    public void setHearingDate(LocalDate hearingDate) {
        this.hearingDate = hearingDate;
    }

    /**
     * Gets the start time of the hearing.
     *
     * @return The hearing start time.
     */
    public LocalTime getHearingStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the hearing.
     *
     * @param startTime The start time to set.
     */
    public void setHearingStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time of the hearing.
     *
     * @return The hearing end time.
     */
    public LocalTime getHearingEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the hearing.
     *
     * @param endTime The end time to set.
     */
    public void setHearingEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the assigned courtroom.
     *
     * @return The courtroom name or identifier.
     */
    public String getCourtRoom() {
        return courtRoom;
    }

    /**
     * Sets the assigned courtroom.
     *
     * @param courtRoom The courtroom to set.
     */
    public void setCourtRoom(String courtRoom) {
        this.courtRoom = courtRoom;
    }

    /**
     * Gets the type of hearing (e.g., Trial, Motion).
     *
     * @return The type of hearing.
     */
    public String getTypeOfHearing() {
        return typeOfHearing;
    }

    /**
     * Sets the type of hearing.
     *
     * @param typeOfHearing The type of hearing to set.
     */
    public void setTypeOfHearing(String typeOfHearing) {
        this.typeOfHearing = typeOfHearing;
    }

    /**
     * Gets the current status of the hearing.
     *
     * @return The hearing status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the current status of the hearing.
     *
     * @param status The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the Agenda appointment group for styling.
     *
     * @return The appointment group.
     */
    public Agenda.AppointmentGroup getAppointmentGroup() {
        return appointmentGroup;
    }

    /**
     * Sets the Agenda appointment group for styling.
     *
     * @param appointmentGroup The appointment group to set.
     */
    public void setAppointmentGroup(Agenda.AppointmentGroup appointmentGroup) {
        this.appointmentGroup = appointmentGroup;
    }

    /**
     * Generates a summary string for display in the Agenda control.
     *
     * @return A formatted summary combining case number and hearing type.
     */
    public String getAgendaSummary() {
        return caseNumber + " (" + typeOfHearing + ")";
    }

    /**
     * Generates a detailed description for the Agenda control.
     *
     * @return A formatted description including parties, judge, courtroom, and status.
     */
    public String getAgendaDescription() {
        return "Parties: " + parties + "\nJudge: " + presidingJudge +
               "\nCourtroom: " + courtRoom + "\nStatus: " + status;
    }

    /**
     * Indicates whether the hearing can be edited.
     *
     * @return True if the hearing is editable, false otherwise.
     */
    public boolean isEditable() {
        return true; // Can be modified based on status or business logic
    }

    /**
     * Indicates whether the hearing duration can be resized.
     *
     * @return True if the hearing is resizable, false otherwise.
     */
    public boolean isResizable() {
        return true; // Can be modified based on hearing type or business logic
    }

    /**
     * Indicates whether the hearing spans an entire day.
     *
     * @return False for hearings with specific times, true for all-day hearings.
     */
    public boolean isWholeDay() {
        return false; // Specific-time hearings by default
    }
}