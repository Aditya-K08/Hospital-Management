package com.example.MedicalFriend.service;

import com.example.MedicalFriend.model.request.HospitalRequest;
import com.example.MedicalFriend.model.response.ApiResponseMessage;
import com.example.MedicalFriend.model.response.DoctorResponse;
import com.example.MedicalFriend.model.response.HospitalResponse;
import com.example.MedicalFriend.model.response.ReviewResponse;

import java.util.List;

public interface HospitalService {
    ApiResponseMessage createNewHospital(Long adminId, HospitalRequest hospitalRequest);

    ApiResponseMessage updateHospitalInfo(HospitalRequest hospitalRequest);

    List<HospitalResponse> getAllHospitals();

    List<DoctorResponse> getAllDoctors(Long hospitalId);

    List<ReviewResponse> getAllReviews(Long hospitalId);

    List<HospitalResponse> searchHospitalByAddress(String address);

}
