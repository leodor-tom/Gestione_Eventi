package TommasoEleodori.Gestione_Eventi.security;

import TommasoEleodori.Gestione_Eventi.config.EmailSender;
import TommasoEleodori.Gestione_Eventi.exceptions.BadRequestException;
import TommasoEleodori.Gestione_Eventi.exceptions.NotFoundException;
import TommasoEleodori.Gestione_Eventi.exceptions.UnauthorizedException;
import TommasoEleodori.Gestione_Eventi.security.dto.LoginDto;
import TommasoEleodori.Gestione_Eventi.users.User;
import TommasoEleodori.Gestione_Eventi.users.UserRepository;
import TommasoEleodori.Gestione_Eventi.users.UserService;
import TommasoEleodori.Gestione_Eventi.users.dto.UserDTO;
import TommasoEleodori.Gestione_Eventi.users.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UserService us;
    @Autowired
    private JWTTools tools;
    @Autowired
    private UserRepository ur;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private EmailSender emailsdr;

    public String authenticateUser(LoginDto body) throws NotFoundException {
        User user = us.findByEmail(body.email());
        if (bcrypt.matches(body.password(), user.getPassword())) {
            return tools.createToken(user);
        } else {
            throw new UnauthorizedException("Login failed. please check your username and password and try again");
        }
    }

    public UUID save(UserDTO body) throws BadRequestException, IOException {
        ur.findByEmailIgnoreCase(body.email()).ifPresent(user -> {
            throw new BadRequestException("The email: " + user.getEmail() + "it's already used");
        });
        User user = new User();
        user.setName(body.name());
        user.setSurname(body.surname());
        user.setEmail(body.email());
        user.setPassword(body.password());
        user.setAvatar("http://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());
        user.setRole(Role.USER);
        ur.save(user);
        emailsdr.sendRegistrationEmail(user.getEmail(), user.getName());
        return user.getId();
    }
}
