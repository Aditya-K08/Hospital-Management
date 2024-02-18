package com.example.MedicalFriend.service.impl;

import com.example.MedicalFriend.entity.AdminEntity;
import com.example.MedicalFriend.entity.DoctorEntity;
import com.example.MedicalFriend.entity.HospitalEntity;
import com.example.MedicalFriend.entity.ReviewEntity;
import com.example.MedicalFriend.helper.Helper;
import com.example.MedicalFriend.model.request.HospitalRequest;
import com.example.MedicalFriend.model.response.ApiResponseMessage;
import com.example.MedicalFriend.model.response.DoctorResponse;
import com.example.MedicalFriend.model.response.HospitalResponse;
import com.example.MedicalFriend.model.response.ReviewResponse;
import com.example.MedicalFriend.repository.AdminRepository;
import com.example.MedicalFriend.repository.HospitalRepository;
import com.example.MedicalFriend.repository.ReviewRepository;
import com.example.MedicalFriend.service.HospitalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ApiResponseMessage createNewHospital(Long adminId, HospitalRequest hospitalRequest) {

        HospitalEntity hospital = hospitalRepository.findByEmail(hospitalRequest.getEmail()).orElse(null);

        AdminEntity admin = adminRepository.findById(adminId).orElse(null);

        if (hospital != null) {
            return ApiResponseMessage
                    .builder()
                    .httpStatus(HttpStatus.CREATED)
                    .message("Hospital Is Already Present!!")
                    .build();
        }

        hospital = Helper.getHospitalEntityFromHospitalRequest(hospitalRequest, admin);
        hospitalRepository.save(hospital);

        return ApiResponseMessage
                .builder()
                .httpStatus(HttpStatus.CREATED)
                .message("Hospital Created Successfully!!")
                .build();

    }

    @Override
    public ApiResponseMessage updateHospitalInfo(HospitalRequest hospitalRequest) {

        HospitalEntity hospital = hospitalRepository.findByEmail(hospitalRequest.getEmail()).orElse(null);

        if (hospital == null) {
            return ApiResponseMessage
                    .builder()
                    .httpStatus(HttpStatus.CREATED)
                    .message("Hospital Not Found!!")
                    .build();
        }
        hospital.setAddress(hospitalRequest.getAddress());
        hospital.setCapacity(hospitalRequest.getCapacity());
        hospital.setName(hospitalRequest.getName());
        hospital.setCity(hospitalRequest.getCity());
        hospital.setState(hospitalRequest.getState());
        hospital.setZipCode(hospitalRequest.getZipCode());
        hospital.setContactNumber(hospitalRequest.getContactNumber());
        hospital.setDescription(hospitalRequest.getDescription());
        hospital.setEstablishedDate(hospitalRequest.getEstablishedDate());

        hospitalRepository.save(hospital);

        return ApiResponseMessage
                .builder()
                .httpStatus(HttpStatus.CREATED)
                .message("Hospital Updated Successfully!!")
                .build();

    }

    @Override
    public List<HospitalResponse> getAllHospitals() {

        List<HospitalEntity> hospitalEntityList = hospitalRepository.findAll();

        return hospitalEntityList.stream().map(Helper::getHospitalResponseFromHospitalEntity).collect(Collectors.toList());
    }

    @Override
    public List<DoctorResponse> getAllDoctors(Long hospitalId) {

        HospitalEntity hospital = hospitalRepository.findById(hospitalId).orElse(null);
        if (hospital == null) {
            return new ArrayList<>();
        }

        List<DoctorEntity> doctorEntityList = hospital.getDoctors();

        return doctorEntityList.stream().map(Helper::getDoctorResponseFromDoctorEntity).collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponse> getAllReviews(Long hospitalId) {
        HospitalEntity hospital = hospitalRepository.findById(hospitalId).orElse(null);
        if (hospital == null) {
            return new ArrayList<>();
        }

        List<ReviewEntity> reviewEntityList = reviewRepository.findByHospitalId(hospitalId);

        return reviewEntityList.stream().map(Helper::getReviewResponseFromReviewEntity).collect(Collectors.toList());
    }

    @Override
    public List<HospitalResponse> searchHospitalByAddress(String address) {
        List<HospitalEntity> hospitalEntityList = hospitalRepository.searchByAddress(address);

        return hospitalEntityList.stream().map(Helper::getHospitalResponseFromHospitalEntity).collect(Collectors.toList());
    }
}
