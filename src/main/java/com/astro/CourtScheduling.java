package com.astro;

import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import jfxtras.scene.control.agenda.Agenda.AppointmentGroup;
import jfxtras.scene.control.agenda.Agenda.AppointmentImplLocal;
import jfxtras.scene.control.agenda.AgendaSkinSwitcher;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.util.Callback;

public class CourtScheduling {

    private BorderPane mainLayout;
    private Agenda agenda;
    private ObservableList<Hearing> allHearings;

    // Appointment groups remain unchanged
    public static final Agenda.AppointmentGroup HEARING_GROUP_TRIAL = new Agenda.AppointmentGroupImpl().withStyleClass("hearing-trial");
    public static final Agenda.AppointmentGroup HEARING_GROUP_MOTION = new Agenda.AppointmentGroupImpl().withStyleClass("hearing-motion");
    public static final Agenda.AppointmentGroup HEARING_GROUP_STATUS = new Agenda.AppointmentGroupImpl().withStyleClass("hearing-status");
    public static final Agenda.AppointmentGroup HEARING_GROUP_DEFAULT = new Agenda.AppointmentGroupImpl().withStyleClass("hearing-default");

    public CourtScheduling() {
        mainLayout = new BorderPane();
        agenda = new Agenda();
        allHearings = FXCollections.observableArrayList();

        // Dummy data remains unchanged
        allHearings.add(new Hearing(
            "Case001", "Plaintiff A vs Defendant B", "Judge Smith",
            LocalDate.of(2025, 7, 15), LocalTime.of(9, 0), LocalTime.of(10, 0),
            "Courtroom 1", "Trial", "Scheduled", HEARING_GROUP_TRIAL
        ));
        allHearings.add(new Hearing(
            "Case002", "Applicant C vs Respondent D", "Judge Jones",
            LocalDate.of(2025, 7, 15), LocalTime.of(10, 30), LocalTime.of(11, 30),
            "Courtroom 2", "Motion", "Scheduled", HEARING_GROUP_MOTION
        ));
        allHearings.add(new Hearing(
            "Case003", "State vs Accused E", "Judge Brown",
            LocalDate.of(2025, 7, 16), LocalTime.of(14, 0), LocalTime.of(15, 0),
            "Courtroom 1", "Status Conference", "Scheduled", HEARING_GROUP_STATUS
        ));
        allHearings.add(new Hearing(
            "Case004", "Party F vs Party G", "Judge Green",
            LocalDate.of(2025, 7, 17), LocalTime.of(9, 30), LocalTime.of(10, 0),
            "Courtroom 3", "Trial", "Scheduled", HEARING_GROUP_TRIAL
        ));

        // FIX 1: Use direct property assignment instead of builder methods
        List<Appointment> appointments = new ArrayList<>();
        for (Hearing h : allHearings) {
            AppointmentImplLocal appt = new AppointmentImplLocal();
            appt.setStartLocalDateTime(LocalDateTime.of(h.getHearingDate(), h.getHearingStartTime()));
            appt.setEndLocalDateTime(LocalDateTime.of(h.getHearingDate(), h.getHearingEndTime()));
            appt.setSummary(h.getAgendaSummary());
            appt.setDescription(h.getAgendaDescription());
            appt.setLocation(h.getCourtRoom());
            appt.setAppointmentGroup(h.getAppointmentGroup());
            
            // These properties might not be needed for basic functionality
            // appt.setWholeDay(h.isWholeDay());
            // appt.setEditable(h.isEditable());
            // appt.setResizable(h.isResizable());
            
            appointments.add(appt);
        }

        // FIX 2: Clear any existing appointments before adding new ones
        agenda.appointments().clear();
        agenda.appointments().addAll(appointments);

        // New appointment callback
        agenda.setNewAppointmentCallback(new Callback<Agenda.LocalDateTimeRange, Agenda.Appointment>() {
            @Override
            public Agenda.Appointment call(Agenda.LocalDateTimeRange localDateTimeRange) {
                System.out.println("New appointment requested from " + localDateTimeRange.getStartLocalDateTime() + " to " + localDateTimeRange.getEndLocalDateTime());
                return null;
            }
        });

        // Appointment click handler
        agenda.setActionCallback(new Callback<Agenda.Appointment, Void>() {
            @Override
            public Void call(Agenda.Appointment appointment) {
                System.out.println("Appointment clicked: Summary: " + appointment.getSummary() + ", Location: " + appointment.getLocation());
                return null;
            }
        });

        // Layout setup
        VBox agendaContainer = new VBox();
        agendaContainer.setStyle("-fx-background-color: #3e4450;");
        agendaContainer.getChildren().add(agenda);
        VBox.setMargin(agenda, new Insets(10));

        AgendaSkinSwitcher switcher = new AgendaSkinSwitcher(agenda);
        switcher.setPadding(new Insets(0, 10, 10, 10));
        mainLayout.setTop(switcher);
        mainLayout.setCenter(agendaContainer);
    }

    public BorderPane getView() {
        return mainLayout;
    }
}