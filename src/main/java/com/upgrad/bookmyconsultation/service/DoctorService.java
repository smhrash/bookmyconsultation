package com.upgrad.bookmyconsultation.service;

import com.upgrad.bookmyconsultation.entity.Address;
import com.upgrad.bookmyconsultation.entity.Doctor;
import com.upgrad.bookmyconsultation.enums.Speciality;
import com.upgrad.bookmyconsultation.exception.InvalidInputException;
import com.upgrad.bookmyconsultation.exception.ResourceUnAvailableException;
import com.upgrad.bookmyconsultation.model.TimeSlot;
import com.upgrad.bookmyconsultation.repository.AddressRepository;
import com.upgrad.bookmyconsultation.repository.AppointmentRepository;
import com.upgrad.bookmyconsultation.repository.DoctorRepository;
import com.upgrad.bookmyconsultation.util.ValidationUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.Cacheable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
public class DoctorService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AddressRepository addressRepository;

    public Doctor register(Doctor doctor) throws InvalidInputException {
        // Validate the doctor details
        ValidationUtils.validate(doctor);

        if (doctor.getAddress() == null) throw new InvalidInputException(List.of("Address"));
        doctor.setId(UUID.randomUUID().toString());

        if (doctor.getSpeciality() == null) {
            doctor.setSpeciality(Speciality.GENERAL_PHYSICIAN);
        }

        // Create an Address object, initialize it with address details from the doctor object
        Address address = doctor.getAddress();

        // Save the address object to the database and store the response
        address.setId(doctor.getId());

        // Set the address in the doctor object with the response
        doctor.setAddress(addressRepository.save(address));

        // Save the doctor object to the database
        doctorRepository.save(doctor);

        return doctor;
    }

    public Doctor getDoctor(String id) {

        return Optional.ofNullable(doctorRepository.findById(id)).get()
                .orElseThrow(ResourceUnAvailableException::new);
    }

    public List<Doctor> getAllDoctorsWithFilters(String speciality) {

        if (speciality != null && !speciality.isEmpty()) {
            return doctorRepository.findBySpecialityOrderByRatingDesc(Speciality.valueOf(speciality));
        }
        return getActiveDoctorsSortedByRating();
    }

    @Cacheable(value = "doctorListByRating")
    private List<Doctor> getActiveDoctorsSortedByRating() {
        log.info("Fetching doctor list from the database");
        return doctorRepository.findAllByOrderByRatingDesc()
                .stream()
                .limit(20)
                .collect(Collectors.toList());
    }

    public TimeSlot getTimeSlots(String doctorId, String date) {

        TimeSlot timeSlot = new TimeSlot(doctorId, date);
        timeSlot.setTimeSlot(timeSlot.getTimeSlot()
                .stream()
                .filter(slot -> {
                    return appointmentRepository
                            .findByDoctorIdAndTimeSlotAndAppointmentDate(timeSlot.getDoctorId(), slot, timeSlot.getAvailableDate()) == null;
                }).collect(Collectors.toList()));

        return timeSlot;

    }
}
