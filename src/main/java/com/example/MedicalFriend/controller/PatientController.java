package com.example.MedicalFriend.controller;

import com.example.MedicalFriend.exception.GlobalException;
import com.example.MedicalFriend.model.request.AppointmentRequest;
import com.example.MedicalFriend.model.request.PatientRequest;
import com.example.MedicalFriend.model.request.ReviewRequest;
import com.example.MedicalFriend.model.response.ApiResponseMessage;
import com.example.MedicalFriend.model.response.AppointmentResponse;
import com.example.MedicalFriend.model.response.PatientResponse;
import com.example.MedicalFriend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/create-patient")
    public ResponseEntity<PatientResponse> createNewPatient(@RequestBody PatientRequest patientRequest) throws GlobalException {
        PatientResponse patientResponse = patientService.createNewPatient(patientRequest);
        return ResponseEntity.status(HttpStatus.OK).body(patientResponse);
    }

    @PutMapping("/update-patient")
    public ResponseEntity<ApiResponseMessage> updatePatientNamePassword(@RequestBody PatientRequest patientRequest) {
        ApiResponseMessage responseMessage = patientService.updatePatientNamePassword(patientRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @GetMapping("/{patientId}/get-all-appointments")
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments(@PathVariable Long patientId) {
        List<AppointmentResponse> appointmentResponses = patientService.getAllAppointments(patientId);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentResponses);
    }


    @DeleteMapping("/{patientId}/delete-appointment/{appointmentId}")
    public ResponseEntity<ApiResponseMessage> deleteAppointmentById(@PathVariable Long patientId, @PathVariable Long appointmentId) {
        ApiResponseMessage responseMessage = patientService.deleteAppointmentById(patientId, appointmentId);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PostMapping("/{patientId}/create-appointment/{doctorId}")
    public ResponseEntity<ApiResponseMessage> deleteAppointmentById(@PathVariable Long patientId, @PathVariable Long doctorId, @RequestBody AppointmentRequest appointmentRequest) {
        ApiResponseMessage responseMessage = patientService.createNewAppointment(patientId, doctorId, appointmentRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PostMapping("/{patientId}/create-review/{hospitalId}")
    public ResponseEntity<ApiResponseMessage> createReview(@PathVariable Long patientId, @PathVariable Long hospitalId, @RequestBody ReviewRequest reviewRequest) {
        ApiResponseMessage responseMessage = patientService.createReview(patientId, hospitalId, reviewRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @DeleteMapping("/{patientId}/delete-review/{reviewId}")
    public ResponseEntity<ApiResponseMessage> deleteReview(@PathVariable Long patientId, @PathVariable Long reviewId) {
        ApiResponseMessage responseMessage = patientService.deleteReview(patientId, reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }


}
