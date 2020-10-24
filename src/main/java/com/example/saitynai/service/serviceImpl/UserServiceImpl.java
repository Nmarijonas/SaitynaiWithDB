//package com.example.saitynai.service.serviceImpl;
//
//import com.example.saitynai.model.User;
//import com.example.saitynai.repository.UserRepository;
//import com.example.saitynai.service.UserService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Transactional
//@Service(value = "userService")
//public class UserServiceImpl implements UserDetailsService, UserService {
//
//    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//        User user = userRepository.findUserByName(userId);
//        if(user == null){
//            log.error("Invalid username or password.");
//            throw new UsernameNotFoundException("Invalid username or password.");
//        }
//        int grantedAuthorities = getAuthorities(user);
//        Set authorities = new HashSet();
//        authorities.add(grantedAuthorities);
//        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),authorities);
//    }
//
//    private int getAuthorities(User user) {
//        int roleByUserId = user.getRole();
////        final int authority = roleByUserId.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toString().toUpperCase())).collect(Collectors.toSet());
//        return roleByUserId;
//    }
//
//    public List<User> findAll() {
//        List<User> users = new ArrayList<>();
//        userRepository.findAll(); //.iterator().forEachRemaining(user -> users.add(user.toUserDto()));
//        return users;
//    }
//
//    @Override
//    public User findOne(long id) {
//        return userRepository.findById(id).get();
//    }
//
//    @Override
//    public void delete(long id) {
//        userRepository.deleteById(id);
//    }
//
//    @Override
//    public User save(User user) {
//        User userWithDuplicateUsername = userRepository.findUserByName(user.getName());
//        if(userWithDuplicateUsername != null && user.getIdUsers() != userWithDuplicateUsername.getIdUsers()) {
//            log.error(String.format("Duplicate username %", user.getName()));
//            throw new RuntimeException("Duplicate username.");
//        }
////        User userWithDuplicateEmail = userDao.findByEmail(userDto.getEmail());
////        if(userWithDuplicateEmail != null && userDto.getId() != userWithDuplicateEmail.getId()) {
////            log.error(String.format("Duplicate email %", userDto.getEmail()));
////            throw new RuntimeException("Duplicate email.");
////        }
//        User newUser = new User();
//        newUser.setName(user.getName());
//        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
//        newUser.setRole(user.getRole());
//        userRepository.save(user);
//        return user;
//    }
//}
