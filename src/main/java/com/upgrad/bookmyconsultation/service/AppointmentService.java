package com.upgrad.bookmyconsultation.service;

import com.upgrad.bookmyconsultation.entity.Appointment;
import com.upgrad.bookmyconsultation.exception.InvalidInputException;
import com.upgrad.bookmyconsultation.exception.ResourceUnAvailableException;
import com.upgrad.bookmyconsultation.exception.SlotUnavailableException;
import com.upgrad.bookmyconsultation.repository.AppointmentRepository;
import com.upgrad.bookmyconsultation.repository.UserRepository;
import com.upgrad.bookmyconsultation.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a new appointment
    public String appointment(Appointment appointment) throws SlotUnavailableException, InvalidInputException {
        // Validate the appointment details using the validate method from ValidationUtils class
        ValidationUtils.validate(appointment);

        // Check if an appointment exists with the same doctor for the same date and time
        Appointment existingAppointment = appointmentRepository
                .findByDoctorIdAndTimeSlotAndAppointmentDate(appointment.getDoctorId(),
                        appointment.getTimeSlot(), appointment.getAppointmentDate());
        if (existingAppointment != null) {
            throw new SlotUnavailableException();
        }

        appointmentRepository.save(appointment);
        // Return the appointment id
        return appointment.getAppointmentId();
    }

    // Get an appointment by appointmentId
    public Appointment getAppointment(String appointmentId) {
        // Use the appointmentId to get the appointment details
        return Optional.ofNullable(appointmentRepository.findById(appointmentId))
                .get().orElseThrow(ResourceUnAvailableException::new);
    }

    public List<Appointment> getAppointmentsForUser(String userId) {
        return appointmentRepository.findByUserId(userId);
    }
}
