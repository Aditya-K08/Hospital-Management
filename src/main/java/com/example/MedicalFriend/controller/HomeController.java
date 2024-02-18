package com.example.MedicalFriend.controller;

import com.example.MedicalFriend.exception.GlobalException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {


    @RequestMapping("/")
    @Operation(hidden = true)
    public void homePage(HttpServletResponse httpServletResponse) throws GlobalException {
        try {
            httpServletResponse.sendRedirect("/swagger-ui.html");
        } catch (Exception exception) {
            throw new GlobalException("Page Not Found Exception!", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "OK !!!";
    }

}
