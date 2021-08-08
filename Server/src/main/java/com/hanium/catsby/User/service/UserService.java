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
    public Long savaUser(Users user) {
        user.setCreateDate();
        userRepository.save(user);
        return user.getId();
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
        user.setUpdateDate();
        user.setNickname(nickname);
        user.setAddress(address);
    }

    @Transactional
    public void updateFcmToken(Long id, String token) {
        Users user = userRepository.findUser(id);
        user.setFcmToken(token);
    }
}

