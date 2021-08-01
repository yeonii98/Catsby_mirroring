package com.hanium.catsby.User.service;

import com.hanium.catsby.User.domain.User;
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
    public Long savaUser(User user) {
        userRepository.save(user);
        return user.getId();
    }

    @Transactional(readOnly = true)
    public List<User> findUsers() {
        return userRepository.findAllUser();
    }

    @Transactional(readOnly = true)
    public User findUser(Long userId) {
        return userRepository.findUser(userId);
    }

    @Transactional
    public void update(Long id, String nickname, String address) {
        User user = userRepository.findUser(id);
        user.setNickname(nickname);
        user.setAddress(address);
    }
}

