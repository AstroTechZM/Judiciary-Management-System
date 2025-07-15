package com.astro;

import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import jfxtras.scene.control.agenda.Agenda.AppointmentGroup;
import jfxtras.scene.control.agenda.Agenda.AppointmentImplLocal;
import jfxtras.scene.control.agenda.AgendaSkinSwitcher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * A JavaFX component for managing and displaying court hearing schedules using the Agenda control.
 * Provides functionality to initialize, display, and interact with court hearings, with support for
 * different hearing types, customizable styling, and user interaction callbacks.
 */
public class CourtScheduling {

    private final BorderPane mainLayout; // Primary layout container for the scheduling view
    private final Agenda agenda; // Agenda control for displaying hearings
    private final ObservableList<Hearing> allHearings; // Collection of all scheduled hearings

    // Predefined appointment groups for different hearing types
    public static final Agenda.AppointmentGroup HEARING_GROUP_TRIAL = new Agenda.AppointmentGroupImpl().withStyleClass("hearing-trial");
    public static final Agenda.AppointmentGroup HEARING_GROUP_MOTION = new Agenda.AppointmentGroupImpl().withStyleClass("hearing-motion");
    public static final Agenda.AppointmentGroup HEARING_GROUP_STATUS = new Agenda.AppointmentGroupImpl().withStyleClass("hearing-status");
    public static final Agenda.AppointmentGroup HEARING_GROUP_DEFAULT = new Agenda.AppointmentGroupImpl().withStyleClass("hearing-default");

    /**
     * Constructs a CourtScheduling component, initializing the layout, agenda, and sample hearings.
     * Configures event handlers for user interactions and applies default styling.
     */
    public CourtScheduling() {
        // Initialize primary layout and agenda components
        mainLayout = new BorderPane();
        agenda = new Agenda();
        allHearings = FXCollections.observableArrayList();

        // Populate with sample hearing data
        initializeSampleHearings();

        // Convert hearings to Agenda appointments
        List<Appointment> appointments = createAppointmentsFromHearings();
        agenda.appointments().clear(); // Ensure no duplicate appointments
        agenda.appointments().addAll(appointments);

        // Configure callback for creating new appointments
        setupNewAppointmentCallback();

        // Configure callback for handling appointment clicks
        setupActionCallback();

        // Set up the layout with agenda and skin switcher
        configureLayout();
    }

    /**
     * Initializes the sample hearing data for demonstration purposes.
     */
    private void initializeSampleHearings() {
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
    }

    /**
     * Converts the list of hearings into Agenda appointments for display.
     *
     * @return A list of configured Appointment objects.
     */
    private List<Appointment> createAppointmentsFromHearings() {
        List<Appointment> appointments = new ArrayList<>();
        for (Hearing hearing : allHearings) {
            AppointmentImplLocal appointment = new AppointmentImplLocal();
            appointment.setStartLocalDateTime(LocalDateTime.of(hearing.getHearingDate(), hearing.getHearingStartTime()));
            appointment.setEndLocalDateTime(LocalDateTime.of(hearing.getHearingDate(), hearing.getHearingEndTime()));
            appointment.setSummary(hearing.getAgendaSummary());
            appointment.setDescription(hearing.getAgendaDescription());
            appointment.setLocation(hearing.getCourtRoom());
            appointment.setAppointmentGroup(hearing.getAppointmentGroup());
            appointments.add(appointment);
        }
        return appointments;
    }

    /**
     * Configures the callback for handling new appointment creation requests.
     */
    private void setupNewAppointmentCallback() {
        agenda.setNewAppointmentCallback((Agenda.LocalDateTimeRange range) -> {
            System.out.println("New appointment requested from " + range.getStartLocalDateTime() + " to " + range.getEndLocalDateTime());
            return null; // Return null to prevent default appointment creation
        });
    }

    /**
     * Configures the callback for handling clicks on existing appointments.
     */
    private void setupActionCallback() {
        agenda.setActionCallback((Agenda.Appointment appointment) -> {
            System.out.println("Appointment clicked: Summary: " + appointment.getSummary() + ", Location: " + appointment.getLocation());
            return null; // No return value required
        });
    }

    /**
     * Configures the layout, including the agenda container and skin switcher.
     */
    private void configureLayout() {
        // Set up agenda container with styling
        VBox agendaContainer = new VBox();
        agendaContainer.setStyle("-fx-background-color: #3e4450;");
        agendaContainer.getChildren().add(agenda);
        VBox.setMargin(agenda, new Insets(10));

        // Add skin switcher for agenda view options
        AgendaSkinSwitcher switcher = new AgendaSkinSwitcher(agenda);
        switcher.setPadding(new Insets(0, 10, 10, 10));

        // Assemble the main layout
        mainLayout.setTop(switcher);
        mainLayout.setCenter(agendaContainer);
    }

    /**
     * Returns the main layout of the court scheduling component for integration into a JavaFX application.
     *
     * @return The BorderPane containing the scheduling view.
     */
    public BorderPane getView() {
        return mainLayout;
    }
}