package com.hanium.catsby.bowl.service;

import com.hanium.catsby.bowl.domain.Bowl;
import com.hanium.catsby.bowl.domain.BowlFeed;
import com.hanium.catsby.bowl.domain.BowlUser;
import com.hanium.catsby.bowl.domain.dto.BowlDetailDto;
import com.hanium.catsby.bowl.domain.dto.BowlDto;
import com.hanium.catsby.bowl.domain.dto.BowlFeedDto;
import com.hanium.catsby.bowl.repository.BowlFeedRepository;
import com.hanium.catsby.bowl.repository.BowlRepository;
import com.hanium.catsby.bowl.repository.BowlUserRepository;
import com.hanium.catsby.notification.exception.DuplicateBowlInfoException;
import com.hanium.catsby.user.domain.Users;
import com.hanium.catsby.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BowlService {

    private final BowlRepository bowlRepository;
    private final BowlUserRepository bowlUserRepository;
    private final UserRepository userRepository;
    private final BowlFeedRepository bowlFeedRepository;

    @Transactional
    public Long enroll(Bowl bowl) throws DuplicateBowlInfoException {
        isDuplicatedBowlInfo(bowl.getInfo());
        bowlRepository.save(bowl);
        return bowl.getId();
    }

    @Transactional(readOnly = true)
    public List<Bowl> findAllBowls(){
        return bowlRepository.findAllBowl();
    }

    @Transactional(readOnly = true)
    public List<Bowl> findUserBowls(String userId){
        Users user = userRepository.findUserByUid(userId);
        return bowlRepository.findBowlByUsers(user.getId());
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
        bowl.setAddress(address);
        bowl.setImage(image);
    }

    @Transactional
    public void delete(Long id) {
       bowlRepository.deleteById(id);
    }

    @Transactional
    public Long saveBowlUser(String  uid, String bowlInfo, double latitude, double longitude)  {
        Users user = userRepository.findUserByUid(uid);
        Bowl bowl = bowlRepository.findByBowlInfo(bowlInfo).get(0);
        bowl.setLatitude(latitude);
        bowl.setLongitude(longitude);

        bowlUserRepository.save(new BowlUser(bowl, user));

        return bowl.getId();
    }

    @Transactional
    public void saveBowlFeed(String uid, Long bowlId) {
        Users user = userRepository.findUserByUid(uid);
        Bowl bowl = bowlRepository.findBowl(bowlId);

        BowlFeed feed = new BowlFeed();
        feed.setUser(user);
        feed.setBowl(bowl);
        bowlFeedRepository.save(feed);

        updateLastFeeding(bowlId);
    }

    @Transactional
    public void updateLastFeeding(Long bowlId) {
        Bowl bowl = bowlRepository.findBowl(bowlId);
        bowl.setLastFeeding(LocalDateTime.now());
    }

    @Transactional
    public void updateBowlImage(Long bowlId, String uid, String image) {
        Users user = userRepository.findUserByUid(uid);
        BowlUser bu = bowlUserRepository.findByBowlIdAndUserId(bowlId, user.getId());
        bu.setImage(image);
    }

    public List<BowlFeedDto> findBowlFeed(Long bowlId) {
        return bowlFeedRepository.findByBowlId(bowlId, Sort.by(Sort.Direction.DESC, "id")).stream().map((bf) -> new BowlFeedDto(bf)).collect(Collectors.toList());
    }

    public BowlDto getBowl(Long bowlId, String uid){
        Bowl bowl = bowlRepository.findBowl(bowlId);
        Users user = userRepository.findUserByUid(uid);
        BowlUser bu = bowlUserRepository.findByBowlIdAndUserId(bowlId, user.getId());

        BowlDto bowlDto = new BowlDto();
        bowlDto.setId(bowl.getId());
        bowlDto.setName(bowl.getName());
        bowlDto.setImage(bu.getImage());
        bowlDto.setAddress(bowl.getAddress());

        return bowlDto;
    }

    public BowlDetailDto getBowlDetail(Long bowlId, String uid) {
        Bowl bowl = bowlRepository.findBowl(bowlId);
        Users user = userRepository.findUserByUid(uid);
        BowlUser bu = bowlUserRepository.findByBowlIdAndUserId(bowlId, user.getId());

        List<BowlFeedDto> feeds = bowlFeedRepository.findByBowlId(bowlId, Sort.by(Sort.Direction.DESC, "id"))
                .stream().map((bf) -> new BowlFeedDto(bf)).collect(Collectors.toList());

        BowlDetailDto detail = new BowlDetailDto();
        detail.setId(bowl.getId());
        detail.setName(bowl.getName());
        detail.setLatitude(bowl.getLatitude());
        detail.setLongitude(bowl.getLongitude());
        detail.setFeed(feeds);
        detail.setAddress(bowl.getAddress());
        return detail;
    }

    public void isDuplicatedBowlInfo(String bowlIfo) throws DuplicateBowlInfoException {
        List<Bowl> bowl = bowlRepository.findByBowlInfo(bowlIfo);
        if (bowl.size() > 0) {
            throw new DuplicateBowlInfoException("중복 되었습니다.");
        }
    }
}
