package com.example.MedicalFriend.service;

import com.example.MedicalFriend.exception.GlobalException;
import com.example.MedicalFriend.model.request.DoctorRequest;
import com.example.MedicalFriend.model.response.ApiResponseMessage;
import com.example.MedicalFriend.model.response.AppointmentResponse;
import com.example.MedicalFriend.model.response.DoctorResponse;

import java.util.List;

public interface DoctorService {

    DoctorResponse createNewDoctor(DoctorRequest doctorRequest, Long hospitalId) throws GlobalException;

    ApiResponseMessage updateDoctorNamePassword(DoctorRequest doctorRequest);

    ApiResponseMessage updateStatusOfAppointment(Long doctorId, Long appointmentId);

    List<AppointmentResponse> getAllAppointments(Long doctorId);

    List<DoctorResponse> searchDoctorByName(String name);
}
