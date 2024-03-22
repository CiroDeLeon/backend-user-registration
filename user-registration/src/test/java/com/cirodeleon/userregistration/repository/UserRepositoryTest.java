package com.cirodeleon.userregistration.repository;

import com.cirodeleon.userregistration.entity.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmailShouldReturnUserWhenUserExists() {
        // given
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        // more setup...
        
        entityManager.persist(user);
        entityManager.flush();

        // when
        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());

        // then
        assertThat(foundUser).isNotEmpty();
        assertThat(foundUser.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void saveShouldThrowWhenEmailIsDuplicated() {
        // given
        User user1 = new User();
        user1.setName("Test User 1");
        user1.setEmail("duplicate@example.com");
        user1.setPassword("password1");
        user1.setActive(true);
        userRepository.saveAndFlush(user1);

        User user2 = new User();
        user2.setName("Test User 2");
        user2.setEmail("duplicate@example.com");
        user2.setPassword("password2");
        user2.setActive(true);

        // when + then
        assertThatThrownBy(() -> userRepository.saveAndFlush(user2))
            .isInstanceOf(DataIntegrityViolationException.class);
    }

}
