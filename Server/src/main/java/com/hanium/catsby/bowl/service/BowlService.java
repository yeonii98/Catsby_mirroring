package com.hanium.catsby.bowl.service;

import com.hanium.catsby.bowl.domain.Bowl;
import com.hanium.catsby.bowl.repository.BowlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BowlService {

    private final BowlRepository bowlRepository;

    @Transactional
    public Long enroll(Bowl bowl){
        bowl.setCreateDate();
        bowlRepository.save(bowl);
        return bowl.getId();
    }

    @Transactional(readOnly = true)
    public List<Bowl> findAllBowls(){
        return bowlRepository.findAllBowl();
    }

    @Transactional(readOnly = true)
    public Bowl findOne(Long id){
        return bowlRepository.findBowl(id);
    }

    @Transactional
    public void update(Long id, String name, String info, String address, byte[] image) {
        Bowl bowl = bowlRepository.findBowl(id);
        bowl.setName(name);
        bowl.setInfo(info);
        bowl.setUpdateDate(LocalDateTime.now());
        bowl.setAddress(address);
        bowl.setImage(image);
    }

    @Transactional
    public void delete(Long id) {
       bowlRepository.deleteById(id);
    }
}
