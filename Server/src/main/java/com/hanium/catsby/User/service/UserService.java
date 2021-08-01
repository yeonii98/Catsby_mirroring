package com.hanium.catsby.User.service;

import com.hanium.catsby.User.domain.Users;
import com.hanium.catsby.User.repository.UserRepository;
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
        user.setNickname(nickname);
        user.setAddress(address);
    }
}

