package pw.react.backend.reactbackend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pw.react.backend.reactbackend.Controllers.UserAlreadyExistsException;
import pw.react.backend.reactbackend.Controllers.UserNotFoundException;
import pw.react.backend.reactbackend.Controllers.UsersController;
import pw.react.backend.reactbackend.Models.User;
import pw.react.backend.reactbackend.Repositories.*;
import pw.react.backend.reactbackend.Service.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("it")
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
class UsersControllerTests {
    private UsersController usersController;

    @Spy
    @InjectMocks
    private UsersService usersService;

    @Mock
    private UsersRepository usersRepository;

    private static User [] users = {
            new User().setAllData("testowyjan", "Jan", "Testowy", LocalDate.of(1998, 9, 23), true),
            new User().setAllData("przykladowaanna", "Anna", "Przykladowa", LocalDate.of(1976, 3, 27), true),
            new User().setAllData("testowykarol", "Karol", "Test", LocalDate.of(1945, 5, 29), false),
            new User().setAllData("teeest1", "Test", "Testowy1", LocalDate.of(1934, 4, 15), false),
            new User().setAllData("haloktoto", "Halo", "Ktoto", LocalDate.of(1967, 3, 25), true),
            new User().setAllData("tabaluga1", "Taba", "Luga1", LocalDate.of(1985, 6, 18), true)
    };

    @Before
    public void before() {
        usersController = new UsersController(usersService);
    }

    @After
    public void after() {
        usersController = null;
    }

    @Test
    public void test_GetAllUsers() throws Exception {
        given(usersRepository.findAll()).willReturn((List<User>) Arrays.asList(users));

        // when
        ResponseEntity<List<User>> response = usersController.getAllUsers();

        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).hasSize(users.length);
        then(response.getBody()).containsExactly(users); }

    @Test
    public void test_GetUserFromId() {
        // given
        long id = users[users.length - 1].getId();
        List<User> responseUsers = Arrays.stream(users).filter(user -> user.getId().equals(id)).collect(Collectors.toList());
        given(usersRepository.findById((int) id)).willReturn((User) responseUsers);

        // when
        ResponseEntity<User> response = usersController.getUser(id);

        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(response);
    }

    @Test(expected = UserNotFoundException.class)
    public void test_InvalidLogin_ReturnError() {
        // given
        String login = "teeeeeest";
        given(usersRepository.findByLogin(login)).willReturn(null);

        when(usersController.getAllUsers(login)).
                thenThrow(UserNotFoundException.class);
    }

    @Test
    public void test_UserId_ReturnUser() {
        // given
        Long id = users[users.length - 3].getId();
        User responseUser = Arrays.stream(users).filter(user -> user.getId() == id).findFirst().get();
        given(usersRepository.findById(Math.toIntExact(id))).willReturn(responseUser);

        // when
        ResponseEntity<User> response = usersController.getUser(Math.toIntExact(id));

        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualToComparingFieldByField(responseUser);
    }

    @Test(expected = UserNotFoundException.class)
    public void test_InvalidId_ReturnError() {
        // given
        int id = 10000000;
        given(usersRepository.findById(id)).willReturn(null);

        when(usersController.getUser(id)).
                thenThrow(UserNotFoundException.class);
    }


    @Test
    public void test_CreateNewUser() {
        // given
        User user = new User().setAllData("halocentralo", "Maniek", "Mamut", LocalDate.of(1980,5,6),false);
        given(usersRepository.findByLogin("halocentralo")).willReturn(null);
        given(usersRepository.save(user)).willReturn(user);

        // when
        ResponseEntity<User> response = usersController.createUser(user);

        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualToComparingFieldByField(user);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void test_CreateUserWithExisitingLOgin_ReturnError() {
        // given
        User user = new User().setAllData(users[5].getLogin(), "Sid", "Leniwiec", LocalDate.of(1990,7,26),true);
        given(usersRepository.findByLogin(users[5].getLogin())).willReturn(Collections.singletonList(users[5]));

        when(usersController.createUser(user)).
                thenThrow(UserAlreadyExistsException.class);
    }

    @Test
    public void test_UpdateUser() {
        // given
        User updatedUser = new User().setAllData("login", users[2].getFirstName(), "test",users[2].getBirthDate(), false);
        long id = users[2].getId();
        updatedUser.setId(id);
        given(usersRepository.findById((int) id)).willReturn(users[2]);
        given(usersRepository.save(users[2])).willReturn(users[2]);

        // when
        ResponseEntity<User> response = usersController.updateUser((int) id, updatedUser);

        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualToComparingFieldByField(updatedUser);
    }

    @Test
    public void test_DeleteUser() {
        // given
        long id = users[4].getId();
        given(usersRepository.findById((int) id)).willReturn(users[4]);

        // when
        ResponseEntity<Map<String, Boolean>> response = usersController.deleteUser((int) id);

        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).containsExactly(new AbstractMap.SimpleEntry<>("deleted", Boolean.TRUE));
    }

}