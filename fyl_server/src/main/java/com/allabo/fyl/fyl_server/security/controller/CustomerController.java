package com.allabo.fyl.fyl_server.security.controller;

import com.allabo.fyl.fyl_server.exception.AddException;
import com.allabo.fyl.fyl_server.exception.FindException;
import com.allabo.fyl.fyl_server.security.service.CustomerService;
import com.allabo.fyl.fyl_server.security.vo.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class CustomerController {
    private final CustomerService service;



    @GetMapping(value="/iddupchk/{id}", produces = "text/html;charset=UTF-8")
    @CrossOrigin(
            origins = "http://localhost:5173"
    )
    public ResponseEntity<String> idDupchk(@PathVariable("id") String id) {
        try {
            Customer c = service.showMyInfo(id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 아이디입니다");
        } catch (FindException e) {
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping(value = "/signup" )
    public ResponseEntity<Void> signup(@RequestBody Customer c) throws AddException,RuntimeException {
        service.signup(c);
        return ResponseEntity.ok().build();
    }


    @GetMapping(value="/logout")
    @CrossOrigin(
            origins = "http://localhost:5173",
            allowCredentials = "true"
    )
    public ResponseEntity<String> logout(HttpSession session){
        session.invalidate();
        return ResponseEntity.ok().build();
    }
}
