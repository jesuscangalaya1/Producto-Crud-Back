package com.tutorialjwtsecurity.jwt.security.emailpassword.controller;

import com.tutorialjwtsecurity.jwt.security.dto.Mensaje;
import com.tutorialjwtsecurity.jwt.security.emailpassword.dto.ChangePasswordDTO;
import com.tutorialjwtsecurity.jwt.security.emailpassword.dto.EmailValuesDTO;
import com.tutorialjwtsecurity.jwt.security.emailpassword.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/email-password")
@CrossOrigin
public class EmailController {

    @Autowired
    EmailService emailService;


    @PostMapping("/send-email")
    public ResponseEntity<Mensaje> sendEmailTemplate(@RequestBody EmailValuesDTO dto) {
        return ResponseEntity.ok(emailService.sendEmailTemplate(dto));
    }

    @PostMapping("/change-password")
    public ResponseEntity<Mensaje> changePassword(@Valid @RequestBody ChangePasswordDTO dto) {
        return ResponseEntity.ok(emailService.changePassword(dto));
    }

}
