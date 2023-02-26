package com.ltp.gradesubmission.service;

import java.util.Base64;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ltp.gradesubmission.entity.User;
import com.ltp.gradesubmission.exception.EntityNotFoundException;
import com.ltp.gradesubmission.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return unwrapUser(user, id);
    }

    @Override
    public User saveUser(User user) {
        // pre nego sto se user snimi u bazu uzima se password, enkodira se i takav enkodiran se snima u bazu
        // za enkodiranje se koristi bean BCryptPasswordEncoder koji smo kreirali u entry point klasi GradeSubmissionApplication
        // ovo je one-way hash - moze da se enkodira ali ne moze da se dekodira
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // @Override
    // public User getUser(String username) {
    //     Optional<User> user = userRepository.findByUsername(username);
    //     if(user.isPresent()) return user.get();
    //     // posto se ovaj user trazi po username ne prosledjuje se id pa se ovde prosledjuje 404 kastovan u Long
    //     else throw new EntityNotFoundException(404L, User.class); 
    // }

    @Override
    public User getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        // posto se ovaj user trazi po username ne prosledjuje se id pa se ovde prosledjuje 404 kastovan u Long
        return unwrapUser(user, 404L);
    }

    static User unwrapUser(Optional<User> entity, Long id) {
        if(entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, User.class);
    }
}
