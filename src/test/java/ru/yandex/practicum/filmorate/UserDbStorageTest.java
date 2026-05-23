/*package ru.yandex.practicum.filmorate.dao.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.Rollback;
import ru.yandex.practicum.filmorate.dto.UserCreateRequest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(UserDbStorage.class)
@Transactional
@Rollback
class UserDbStorageTest {

    @Autowired
    private UserDbStorage storage;

    private UserCreateRequest createRequest;

    @BeforeEach
    void setUp() {
        createRequest = new UserCreateRequest();
        createRequest.setEmail("test@mail.com");
        createRequest.setLogin("testlogin");
        createRequest.setName("Test Name");
        createRequest.setBirthday(LocalDate.of(2000, 1, 1));
    }

    @Test
    void shouldAddUser() {
        User user = storage.addUser(createRequest);

        assertNotNull(user.getId());
        assertEquals("test@mail.com", user.getEmail());
        assertEquals("testlogin", user.getLogin());
    }

    @Test
    void shouldGetUserById() {
        User created = storage.addUser(createRequest);

        Optional<User> found = storage.getUserById(created.getId());

        assertTrue(found.isPresent());
        assertEquals(created.getId(), found.get().getId());
    }

    @Test
    void shouldUpdateUser() {
        User created = storage.addUser(createRequest);

        created.setName("Updated Name");

        storage.updateUser(created);

        Optional<User> fromDb = storage.getUserById(created.getId());

        assertTrue(fromDb.isPresent());
        assertEquals("Updated Name", fromDb.get().getName());
    }

    @Test
    void shouldDeleteUser() {
        User user = storage.addUser(createRequest);

        storage.deleteUser(user.getId());

        Optional<User> found = storage.getUserById(user.getId());

        assertTrue(found.isEmpty());
    }

    @Test
    void shouldGetAllUsers() {
        storage.addUser(createRequest);

        UserCreateRequest second = new UserCreateRequest();
        second.setEmail("test2@mail.com");
        second.setLogin("login2");
        second.setName("User2");
        second.setBirthday(LocalDate.of(1999, 1, 1));

        storage.addUser(second);

        List<User> users = (List<User>) storage.getUsers();

        assertEquals(2, users.size());
    }

    @Test
    void shouldAddAndGetFriends() {
        User u1 = storage.addUser(createRequest);

        UserCreateRequest friend = new UserCreateRequest();
        friend.setEmail("friend@mail.com");
        friend.setLogin("friend");
        friend.setName("Friend");
        friend.setBirthday(LocalDate.of(1999, 1, 1));

        User u2 = storage.addUser(friend);

        storage.addFriend(u1.getId(), u2.getId());

        var friends = storage.getAllFriends(u1.getId());

        assertEquals(1, friends.size());
        assertEquals(u2.getId(), friends.iterator().next().getId());
    }

    @Test
    void shouldDetectMutualFriends() {
        User u1 = storage.addUser(createRequest);

        UserCreateRequest u2Req = new UserCreateRequest();
        u2Req.setEmail("u2@mail.com");
        u2Req.setLogin("u2");
        u2Req.setName("U2");
        u2Req.setBirthday(LocalDate.of(1999, 1, 1));
        User u2 = storage.addUser(u2Req);

        UserCreateRequest u3Req = new UserCreateRequest();
        u3Req.setEmail("u3@mail.com");
        u3Req.setLogin("u3");
        u3Req.setName("U3");
        u3Req.setBirthday(LocalDate.of(1998, 1, 1));
        User u3 = storage.addUser(u3Req);

        storage.addFriend(u1.getId(), u2.getId());
        storage.addFriend(u3.getId(), u2.getId());

        var mutual = storage.checkMutualFriends(u1.getId(), u3.getId());

        assertEquals(1, mutual.size());
        assertEquals(u2.getId(), mutual.iterator().next().getId());
    }
}*/