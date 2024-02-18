package com.example.MedicalFriend.service.impl;

import com.example.MedicalFriend.entity.AppointmentEntity;
import com.example.MedicalFriend.entity.DoctorEntity;
import com.example.MedicalFriend.entity.HospitalEntity;
import com.example.MedicalFriend.exception.GlobalException;
import com.example.MedicalFriend.helper.Helper;
import com.example.MedicalFriend.model.request.DoctorRequest;
import com.example.MedicalFriend.model.response.ApiResponseMessage;
import com.example.MedicalFriend.model.response.AppointmentResponse;
import com.example.MedicalFriend.model.response.DoctorResponse;
import com.example.MedicalFriend.repository.AppointmentRepository;
import com.example.MedicalFriend.repository.DoctorRepository;
import com.example.MedicalFriend.repository.HospitalRepository;
import com.example.MedicalFriend.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public DoctorResponse createNewDoctor(DoctorRequest doctorRequest, Long hospitalId) throws GlobalException {

        DoctorEntity doctor = doctorRepository.findByEmail(doctorRequest.getEmail()).orElse(null);
        HospitalEntity hospital = hospitalRepository.findById(hospitalId).orElse(null);

        if (hospital == null) {
            throw new GlobalException("Hospital Not Found!!", HttpStatus.NOT_FOUND);
        }
        if (doctor != null) {
            throw new GlobalException("Doctor Is Already Present!!", HttpStatus.NOT_FOUND);
        }

        doctor = DoctorEntity
                .builder()
                .email(doctorRequest.getEmail())
                .name(doctorRequest.getName())
                .description(doctorRequest.getDescription())
                .experience(doctorRequest.getExperience())
                .hospital(hospital)
                .contactNumber(doctorRequest.getContactNumber())
                .password(doctorRequest.getPassword())
                .specialization(doctorRequest.getSpecialization())
                .qualification(doctorRequest.getQualification())
                .appointments(new ArrayList<>())
                .build();

        doctorRepository.save(doctor);

        return Helper.getDoctorResponseFromDoctorEntity(doctor);

    }

    @Override
    public ApiResponseMessage updateDoctorNamePassword(DoctorRequest doctorRequest) {

        DoctorEntity doctor = doctorRepository.findByEmail(doctorRequest.getEmail()).orElse(null);

        if (doctor == null) {
            return ApiResponseMessage
                    .builder()
                    .httpStatus(HttpStatus.CREATED)
                    .message("Doctor Not Found!!")
                    .build();
        }

        doctor.setName(doctorRequest.getName());
        doctor.setPassword(doctorRequest.getPassword());
        doctor.setExperience(doctorRequest.getExperience());
        doctor.setContactNumber(doctorRequest.getContactNumber());
        doctor.setExperience(doctorRequest.getExperience());
        doctor.setQualification(doctorRequest.getQualification());
        doctor.setSpecialization(doctorRequest.getSpecialization());

        doctorRepository.save(doctor);
        return ApiResponseMessage
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Doctor Updated Successfully!!")
                .build();


    }

    @Override
    public ApiResponseMessage updateStatusOfAppointment(Long doctorId, Long appointmentId) {

        DoctorEntity doctor = doctorRepository.findById(doctorId).orElse(null);

        AppointmentEntity appointment = appointmentRepository.findById(appointmentId).orElse(null);

        if (appointment == null) {
            return ApiResponseMessage
                    .builder()
                    .httpStatus(HttpStatus.CREATED)
                    .message("Appointment Not Found!!")
                    .build();
        }

        appointment.setStatus("COMPLETED");
        appointmentRepository.save(appointment);

        return ApiResponseMessage
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Doctor Updated Successfully!!")
                .build();
    }

    @Override
    public List<AppointmentResponse> getAllAppointments(Long doctorId) {
        DoctorEntity doctor = doctorRepository.findById(doctorId).orElse(null);
        if (doctor == null)
            return new ArrayList<>();
        List<AppointmentEntity> appointmentEntityList = doctor.getAppointments();
        return appointmentEntityList.stream().map(Helper::getAppointmentResponseFromAppointmentEntity).collect(Collectors.toList());
    }

    @Override
    public List<DoctorResponse> searchDoctorByName(String name) {
        List<DoctorEntity> doctorEntityList = doctorRepository.searchByDoctorName(name);

        return doctorEntityList.stream().map(Helper::getDoctorResponseFromDoctorEntity).collect(Collectors.toList());

    }
}
