package com.example.MedicalFriend.service;

import com.example.MedicalFriend.exception.GlobalException;
import com.example.MedicalFriend.model.request.AdminRequest;
import com.example.MedicalFriend.model.response.AdminResponse;
import com.example.MedicalFriend.model.response.ApiResponseMessage;

public interface AdminService {
    AdminResponse createNewAdmin(AdminRequest adminRequest) throws GlobalException;

    ApiResponseMessage updateAdminNamePassword(AdminRequest adminRequest);

}
