package com.example.saitynai.controller;

import com.example.saitynai.exception.UserNotFoundException;
import com.example.saitynai.model.User;
import com.example.saitynai.model.role.Role;
import com.example.saitynai.model.role.RoleEnum;
import com.example.saitynai.payload.request.SignupRequest;
import com.example.saitynai.payload.response.MessageResponse;
import com.example.saitynai.repository.RoleRepository;
import com.example.saitynai.repository.UserRepository;
import com.example.saitynai.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RoleRepository roleRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = new ArrayList<>(userRepository.findAll());
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        UserDetailsImpl userDetails = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal());
        long userId = userDetails.getId();
        if (userId == id && userRepository.findById(userId).isPresent()) {
            return new ResponseEntity<>(userRepository.getOne(userId), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        User user = new User(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        UserDetailsImpl userDetails = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal());
        long userId = userDetails.getId();
        Optional<User> userData = userRepository.findById(userId);
        if (userId == id && userData.isPresent()) {
            User _user = userData.get();
            _user.setUsername(user.getUsername());
            _user.setPassword(encoder.encode(user.getPassword()));
            Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            if (_user.getRoles().contains(adminRole)) {
                _user.setRoles(user.getRoles());
            } else {
                Set<Role> roles = new HashSet<>();
                Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
                _user.setRoles(roles);
            }
            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable("id") long id) {
        UserDetailsImpl userDetails = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal());
        long userId = userDetails.getId();
        if (userId == id && userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}