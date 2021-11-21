package com.example.demo.controller;

import com.example.demo.dto.request.SignInForm;
import com.example.demo.dto.request.SignUpForm;
import com.example.demo.dto.response.JwtResponse;
import com.example.demo.dto.response.ResponseMessage;
import com.example.demo.model.Role;
import com.example.demo.model.RoleName;
import com.example.demo.model.User;
import com.example.demo.security.jwt.JwtProvider;
import com.example.demo.security.userprincal.UserPrinciple;
import com.example.demo.service.impl.RoleServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/signUp")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm){
        if(userService.existsByUsername(signUpForm.getUsername())){
            return new ResponseEntity<>(new ResponseMessage("The username exited! please try again !"), HttpStatus.OK);
        }
        if(userService.existsByEmail(signUpForm.getEmail())){
            return new ResponseEntity<>(new ResponseMessage("The email exited! try again !"), HttpStatus.OK);
        }

        User user=new User(signUpForm.getName(), signUpForm.getUsername(), signUpForm.getEmail(), passwordEncoder.encode(signUpForm.getPassword()));
        Set<String> strRole=signUpForm.getRoles();
        Set<Role> roles= new HashSet<>();
        strRole.forEach(role->{
            switch (role){
                case "admin":
                    Role admimRole=roleService.findByName(RoleName.ADMIN).orElseThrow(
                            ()-> new RuntimeException("Role not found ")
                    );
                    roles.add(admimRole);
                    break;

                case "pm" :
                    Role pmRole= roleService.findByName(RoleName.PM).orElseThrow(
                            ()->new RuntimeException("Role not found ")
                    );
                    roles.add(pmRole);
                    break;
                default:
                    Role userRole =roleService.findByName(RoleName.USER).orElseThrow(
                            ()-> new RuntimeException(" Role not found! ")
                    );
                    roles.add(userRole);
                    break;
            }
        });

        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(
                new ResponseMessage(" Create user sucess "),
                HttpStatus.OK
        );
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> login(@Valid @RequestBody SignInForm signInForm){
        Authentication authentication =authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUsername(),signInForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token= jwtProvider.createToke(authentication);
        UserPrinciple userPrinciple=(UserPrinciple) authentication.getPrincipal();
        JwtResponse jwtResponse=new JwtResponse();
        jwtResponse.setName(userPrinciple.getUsername());
        jwtResponse.setToken(token);
        jwtResponse.setRoles(userPrinciple.getAuthorities());

        return ResponseEntity.ok(jwtResponse);
    }





}
