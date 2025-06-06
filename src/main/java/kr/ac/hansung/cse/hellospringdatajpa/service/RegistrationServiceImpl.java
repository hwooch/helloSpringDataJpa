package kr.ac.hansung.cse.hellospringdatajpa.service;

import kr.ac.hansung.cse.hellospringdatajpa.entity.MyRole;
import kr.ac.hansung.cse.hellospringdatajpa.entity.MyUser;
import kr.ac.hansung.cse.hellospringdatajpa.repo.RoleRepository;
import kr.ac.hansung.cse.hellospringdatajpa.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public MyUser createUser(MyUser user, List<MyRole> userRoles) {
        Optional<MyUser> maybeHighest = userRepository.findTopByOrderByIdDesc();
        Integer nextId;
        if (maybeHighest.isEmpty()) {
            // 테이블이 비어 있으면 첫 사용자의 ID는 1
            nextId = 1;
        } else {
            // 이미 레코드가 있으면 “가장 큰 ID + 1”
            nextId = maybeHighest.get().getId() + 1;
        }
        // 새 User 객체에 ID를 직접 세팅
        user.setId(nextId);

        for (MyRole ur : userRoles) {
            if (roleRepository.findByRolename(ur.getRolename()).isEmpty()) {
                roleRepository.save(ur);
            }
        }

        // generate new Bcrypt hash
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        user.setRoles(userRoles);

        MyUser newUser = userRepository.save(user);

        return newUser;
    }

    public boolean checkEmailExists(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            return true;
        }

        return false;
    }

    public MyRole findByRolename(String rolename) {
        Optional<MyRole> role = roleRepository.findByRolename(rolename);
        return role.orElseGet(() -> new MyRole(rolename));
    }

}