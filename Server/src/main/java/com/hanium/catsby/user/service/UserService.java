package com.hanium.catsby.user.service;

import com.hanium.catsby.user.domain.Users;
import com.hanium.catsby.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void savaUser(String uid, String email, String fcmToken) {
        Users user = new Users();
        user.setUid(uid);
        user.setNickname(email.split("@")[0]);
        user.setFcmToken(fcmToken);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<Users> findUsers() {
        return userRepository.findAllUser();
    }

    @Transactional(readOnly = true)
    public Users findUser(Long userId) {
        return userRepository.findUser(userId);
    }

    @Transactional
    public void update(Long id, String nickname, String address) {
        Users user = userRepository.findUser(id);
        user.setNickname(nickname);
        user.setAddress(address);
    }

    @Transactional(readOnly = true)
    public Long findUserByUid(String uid){
        Users user = userRepository.findUserByUid(uid);
        return user.getId();
    }

    @Transactional(readOnly = true)
    public Users findUsersByUid(String uid){
        Users user = userRepository.findUserByUid(uid);
        return user;
    }

    @Transactional
    public void updateFcmToken(String uid, String token) {
        Users user = userRepository.findUserByUid(uid);
        user.setFcmToken(token);
    }


}

