//package com.example.saitynai.controller;
//
//import com.example.saitynai.service.AuthenticationFacadeService;
//import com.example.saitynai.service.UserService;
//import com.example.saitynai.exception.RecipeNotFoundException;
//import com.example.saitynai.exception.UserNotFoundException;
//import com.example.saitynai.model.Recipe;
//import com.example.saitynai.model.User;
//import com.example.saitynai.repository.UserRepository;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.logging.Logger;
//
//@RestController
//@RequestMapping("/users")
//class UserController {
//    private static final Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);
//
//    @Autowired
//    private UserRepository userRepository;
//
//    public static final String SUCCESS = "success";
//    public static final String ROLE_ADMIN = "ROLE_ADMIN";
//    public static final String ROLE_USER = "ROLE_USER";
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private AuthenticationFacadeService authenticationFacadeService;
//
//    @Secured({ROLE_ADMIN})
//    @GetMapping
//    public ResponseEntity<List<User>> listUser(){
//        logger.info(String.format("received request to list user %s", authenticationFacadeService.getAuthentication().getPrincipal()));
//        List<User> users = new ArrayList<User>();
//        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
//    }
//
////    @GetMapping("/users")
////    public List<User> getAllUsers() {
////        return userRepository.findAll();
////    }
////    public ResponseEntity<List<User>> getAllUsers() {
////        try {
////            List<User> users = new ArrayList<User>();
////
////            userRepository.findAll().forEach(users::add);
////            return new ResponseEntity<>(users, HttpStatus.OK);
////        } catch (Exception e) {
////            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
////        }
////    }
//
////    @GetMapping("/users/{id}")
////    public User getUserByID(@PathVariable Long id) {
////        Optional<User> optUser = userRepository.findById(id);
////        if(optUser.isPresent()) {
////            return optUser.get();
////        }else {
////            throw new UserNotFoundException(id);
////        }
////    }
//////    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
//////        Optional<User> userData = userRepository.findById(id);
//////
//////        return userData.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).
//////                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//////    }
////
////    @PostMapping("/users")
////    public ResponseEntity<User> createUser(@RequestBody User user) {
////        try {
////            User _user = userRepository
////                    .save(new User(user.getName(), user.getPassword(),user.getRole()));
////            return new ResponseEntity<>(_user, HttpStatus.CREATED);
////        } catch (Exception e) {
////            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
////        }
////    }
////
////    @PutMapping("/users/{id}")
////    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
////        Optional<User> userData = userRepository.findById(id);
////
////        if (userData.isPresent()) {
////            User _user = userData.get();
////            _user.setName(user.getName());
////            _user.setPassword(user.getPassword());
////            _user.setRole(user.getRole());
////            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
////        } else {
////            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////        }
////    }
////
////    @DeleteMapping("/users/{id}")
////    public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable("id") long id) {
////        try {
////            userRepository.deleteById(id);
////            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
////        } catch (Exception e) {
////            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
////        }
////    }
//}