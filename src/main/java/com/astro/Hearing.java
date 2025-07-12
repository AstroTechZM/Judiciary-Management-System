package com.astro;

import jfxtras.scene.control.agenda.Agenda; // Still need this import for AppointmentGroup
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime; // Used for converting to/from Agenda.AppointmentImplLocal

public class Hearing {

    private String caseNumber;
    private String parties;
    private String presidingJudge;
    private LocalDate hearingDate;
    private LocalTime startTime; // Renamed to internalStartTime to avoid conflicts if needed
    private LocalTime endTime;   // Renamed to internalEndTime to avoid conflicts if needed
    private String courtRoom;
    private String typeOfHearing;
    private String status;
    private Agenda.AppointmentGroup appointmentGroup; // Still part of your Hearing model

    // Constructor with all fields including Agenda.AppointmentGroup
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

    // --- Getters and Setters for your Hearing specific properties ---
    public String getCaseNumber() { return caseNumber; }
    public void setCaseNumber(String caseNumber) { this.caseNumber = caseNumber; }
    public String getParties() { return parties; }
    public void setParties(String parties) { this.parties = parties; }
    public String getPresidingJudge() { return presidingJudge; }
    public void setPresidingJudge(String presidingJudge) { this.presidingJudge = presidingJudge; }
    public LocalDate getHearingDate() { return hearingDate; }
    public void setHearingDate(LocalDate hearingDate) { this.hearingDate = hearingDate; }

    // Renamed to avoid any potential conflict with Agenda.Appointment interface methods
    public LocalTime getHearingStartTime() { return startTime; }
    public void setHearingStartTime(LocalTime startTime) { this.startTime = startTime; }

    // Renamed to avoid any potential conflict with Agenda.Appointment interface methods
    public LocalTime getHearingEndTime() { return endTime; }
    public void setHearingEndTime(LocalTime endTime) { this.endTime = endTime; }

    public String getCourtRoom() { return courtRoom; }
    public void setCourtRoom(String courtRoom) { this.courtRoom = courtRoom; }
    public String getTypeOfHearing() { return typeOfHearing; }
    public void setTypeOfHearing(String typeOfHearing) { this.typeOfHearing = typeOfHearing; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Getter and Setter for AppointmentGroup
    public Agenda.AppointmentGroup getAppointmentGroup() {
        return appointmentGroup;
    }
    public void setAppointmentGroup(Agenda.AppointmentGroup appointmentGroup) {
        this.appointmentGroup = appointmentGroup;
    }

    // Helper methods to generate summary and description for Agenda
    public String getAgendaSummary() {
        return caseNumber + " (" + typeOfHearing + ")";
    }

    public String getAgendaDescription() {
        return "Parties: " + parties + "\nJudge: " + presidingJudge + "\nCourtroom: " + courtRoom + "\nStatus: " + status;
    }

    // You might also want boolean flags for editable/resizable if your Hearing model supports it
    public boolean isEditable() { return true; } // Or based on status
    public boolean isResizable() { return true; } // Or based on type
    public boolean isWholeDay() { return false; } // For specific-time hearings
}