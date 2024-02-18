package com.example.MedicalFriend.service;

import com.example.MedicalFriend.exception.GlobalException;
import com.example.MedicalFriend.model.request.AppointmentRequest;
import com.example.MedicalFriend.model.request.PatientRequest;
import com.example.MedicalFriend.model.request.ReviewRequest;
import com.example.MedicalFriend.model.response.ApiResponseMessage;
import com.example.MedicalFriend.model.response.AppointmentResponse;
import com.example.MedicalFriend.model.response.PatientResponse;

import java.util.List;

public interface PatientService {

    PatientResponse createNewPatient(PatientRequest patientRequest) throws GlobalException;

    ApiResponseMessage updatePatientNamePassword(PatientRequest patientRequest);

    ApiResponseMessage createNewAppointment(Long patientId, Long doctorId, AppointmentRequest appointmentRequest);

    List<AppointmentResponse> getAllAppointments(Long patientId);

    ApiResponseMessage deleteAppointmentById(Long patientId, Long appointmentId);


    ApiResponseMessage createReview(Long patientId, Long hospitalId, ReviewRequest reviewRequest);

    ApiResponseMessage deleteReview(Long patientId, Long reviewId);


}
