package com.app.milestone.service;

import com.app.milestone.domain.LoginDTO;
import com.app.milestone.entity.People;
import com.app.milestone.entity.User;
import com.app.milestone.repository.PeopleRepository;
import com.app.milestone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PeopleRepository peopleRepository;
    private final LoginDTO loginDTO;

    //    로그인
    public Long login(LoginDTO loginDTO) {
        String encodingUserPassword = Base64.getEncoder().encodeToString(loginDTO.getUserPassword().getBytes());
        return userRepository.findByUserEmailAndUserPassword(loginDTO.getUserEmail(), encodingUserPassword);
    }

    //    전체 회원 (보육원 + 일반)
    public List<User> userList(Pageable pageable) {
        return userRepository.findByCreatedDate(pageable);
    }

    /*황지수*/
    public Long checkEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail).get().getUserId();
    }
    /*/황지수*/

    /**
     * 비밀번호 일치 확인
     **/
    public boolean checkPassword(Long userId, String userPassword) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        String realPassword = user.getUserPassword();
        userPassword = Base64.getEncoder().encodeToString(userPassword.getBytes());
        log.info("진짜 비밀번호 : " + realPassword);
        log.info("입력 비밀번호 : " + userPassword);
        boolean matches = (userPassword.equals(realPassword));
        return matches;
    }

    public boolean typeCheck(Long userId){
        Optional<People> people = peopleRepository.findById(userId);
        if(people.isPresent()){
            return true;
        }
        return false;
    }

    //마이페이지 회원탈퇴시 회원삭제
    public void saveReasonAnddeleteUserID(Long userId) {
        userRepository.deleteById(userId);
    }

}
