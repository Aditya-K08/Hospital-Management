package com.example.MedicalFriend.controller;

import com.example.MedicalFriend.exception.GlobalException;
import com.example.MedicalFriend.model.request.AdminRequest;
import com.example.MedicalFriend.model.request.HospitalRequest;
import com.example.MedicalFriend.model.response.AdminResponse;
import com.example.MedicalFriend.model.response.ApiResponseMessage;
import com.example.MedicalFriend.service.AdminService;
import com.example.MedicalFriend.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/create-admin")
    public ResponseEntity<AdminResponse> createNewPatient(@RequestBody AdminRequest adminRequest) throws GlobalException {
        AdminResponse adminResponse = adminService.createNewAdmin(adminRequest);
        return ResponseEntity.status(HttpStatus.OK).body(adminResponse);
    }

    @PutMapping("/update-admin")
    public ResponseEntity<ApiResponseMessage> updatePatientNamePassword(@RequestBody AdminRequest adminRequest) {
        ApiResponseMessage responseMessage = adminService.updateAdminNamePassword(adminRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PostMapping("/{adminId}/create-hospital")
    public ResponseEntity<ApiResponseMessage> createNewHospital(@PathVariable Long adminId, @RequestBody HospitalRequest hospitalRequest) {
        ApiResponseMessage responseMessage = hospitalService.createNewHospital(adminId, hospitalRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PutMapping("/update-hospital")
    public ResponseEntity<ApiResponseMessage> updateHospitalInfo(@RequestBody HospitalRequest hospitalRequest) {
        ApiResponseMessage responseMessage = hospitalService.updateHospitalInfo(hospitalRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

}
