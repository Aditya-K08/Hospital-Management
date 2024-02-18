package com.example.MedicalFriend.service.impl;

import com.example.MedicalFriend.entity.*;
import com.example.MedicalFriend.exception.GlobalException;
import com.example.MedicalFriend.helper.Helper;
import com.example.MedicalFriend.model.request.AppointmentRequest;
import com.example.MedicalFriend.model.request.PatientRequest;
import com.example.MedicalFriend.model.request.ReviewRequest;
import com.example.MedicalFriend.model.response.ApiResponseMessage;
import com.example.MedicalFriend.model.response.AppointmentResponse;
import com.example.MedicalFriend.model.response.PatientResponse;
import com.example.MedicalFriend.repository.*;
import com.example.MedicalFriend.service.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PatientResponse createNewPatient(PatientRequest patientRequest) throws GlobalException {

        PatientEntity patient = patientRepository.findByEmail(patientRequest.getEmail()).orElse(null);

        if (patient != null) {
            throw new GlobalException("Patient Is Already Present!!", HttpStatus.OK);
        }
        patient = PatientEntity
                .builder()
                .name(patientRequest.getName())
                .address(patientRequest.getAddress())
                .email(patientRequest.getEmail())
                .appointments(new ArrayList<>())
                .dateOfBirth(patientRequest.getDateOfBirth())
                .password(patientRequest.getPassword())
                .contactNumber(patientRequest.getContactNumber())
                .build();

        patientRepository.save(patient);

        return Helper.getPatientResponseFromPatientEntity(patient);
    }

    @Override
    public ApiResponseMessage updatePatientNamePassword(PatientRequest patientRequest) {

        PatientEntity patient = patientRepository.findByEmail(patientRequest.getEmail()).orElse(null);

        assert patient != null;
        patient.setName(patientRequest.getName());
        patient.setPassword(patientRequest.getPassword());
        patient.setAddress(patientRequest.getAddress());
        patient.setContactNumber(patient.getContactNumber());
        patient.setDateOfBirth(patient.getDateOfBirth());

        patientRepository.save(patient);

        return ApiResponseMessage
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Name Password Updated Successfully!!")
                .build();
    }

    @Override
    public ApiResponseMessage createNewAppointment(Long patientId, Long doctorId, AppointmentRequest appointmentRequest) {

        PatientEntity patient = patientRepository.findById(patientId).orElse(null);
        DoctorEntity doctor = doctorRepository.findById(doctorId).orElse(null);

        if (doctor == null) {
            return ApiResponseMessage
                    .builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("Doctor Not Found!!")
                    .build();
        }

        AppointmentEntity appointment = AppointmentEntity
                .builder()
                .appointmentDate(appointmentRequest.getAppointmentDate())
                .patient(patient)
                .status("PENDING")
                .doctor(doctor)
                .appointmentTime(appointmentRequest.getAppointmentTime())
                .description(appointmentRequest.getDescription())
                .build();

        assert patient != null;
        patient.getAppointments().add(appointment);
        patientRepository.save(patient);

        return ApiResponseMessage
                .builder()
                .httpStatus(HttpStatus.CREATED)
                .message("Appointment Created Successfully!!")
                .build();
    }

    @Override
    public List<AppointmentResponse> getAllAppointments(Long patientId) {
        PatientEntity patient = patientRepository.findById(patientId).orElse(null);
        assert patient != null;
        return patient.getAppointments().stream().map(Helper::getAppointmentResponseFromAppointmentEntity).toList();
    }

    @Override
    public ApiResponseMessage deleteAppointmentById(Long patientId, Long appointmentId) {

        PatientEntity patient = patientRepository.findById(patientId).orElse(null);
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId).orElse(null);

        if (appointment == null) {
            return ApiResponseMessage
                    .builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("Appointment Not Found !!")
                    .build();
        }
        assert patient != null;
        patient.getAppointments().remove(appointment);
        patientRepository.save(patient);
        return ApiResponseMessage
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Appointment Deleted Successfully!!")
                .build();
    }

    @Override
    public ApiResponseMessage createReview(Long patientId, Long hospitalId, ReviewRequest reviewRequest) {

        HospitalEntity hospital = hospitalRepository.findById(hospitalId).orElse(null);

        if (hospital == null) {
            return ApiResponseMessage
                    .builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("Hospital Not Found!!")
                    .build();
        }

        PatientEntity patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) {
            return ApiResponseMessage
                    .builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("Patient Not Found!!")
                    .build();
        }

        ReviewEntity review = ReviewEntity
                .builder()
                .star(reviewRequest.getStar())
                .hospital(hospital)
                .patient(patient)
                .description(reviewRequest.getDescription())
                .build();
        reviewRepository.save(review);

        return ApiResponseMessage
                .builder()
                .httpStatus(HttpStatus.CREATED)
                .message("Review Created Successfully!!")
                .build();
    }

    @Override
    public ApiResponseMessage deleteReview(Long patientId, Long reviewId) {

        PatientEntity patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) {
            return ApiResponseMessage
                    .builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("Patient Not Found!!")
                    .build();
        }

        ReviewEntity review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null && Objects.equals(review.getPatient().getPatientId(), patient.getPatientId())) {
            reviewRepository.delete(review);
            return ApiResponseMessage
                    .builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Review Deleted Successfully!!")
                    .build();
        }
        return ApiResponseMessage
                .builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message("Review Not Found!!")
                .build();


    }

}
