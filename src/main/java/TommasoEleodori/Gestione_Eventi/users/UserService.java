package TommasoEleodori.Gestione_Eventi.users;

import TommasoEleodori.Gestione_Eventi.config.EmailSender;
import TommasoEleodori.Gestione_Eventi.exceptions.NotFoundException;
import TommasoEleodori.Gestione_Eventi.users.dto.RoleDTO;
import TommasoEleodori.Gestione_Eventi.users.dto.UserUpdateDTO;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userepo;
    @Autowired
    private EmailSender emailsdr;

    @Autowired
    private Cloudinary cloudinary;

    public Page<User> getUsers(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return userepo.findAll(pageable);
    }

    public User findById(UUID id) throws NotFoundException {
        return userepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User findByIdAndUpdate(UUID id, UserUpdateDTO body) throws NotFoundException, IOException {
        User found = this.findById(id);
        found.setName(body.name());
        found.setSurname(body.surname());
        userepo.save(found);
        emailsdr.sendUpdateAccountEmail(found.getEmail(), found.getName());
        return found;
    }

    public User findByIdAndPatchRole(UUID id, RoleDTO body) throws NotFoundException, IOException {
        User user = this.findById(id);
        user.setRole(body.role());
        userepo.save(user);
        emailsdr.sendUpdateAccountEmail(user.getEmail(), user.getName());
        return user;
    }

    public String uploadPicture(UUID id, MultipartFile file) throws NotFoundException, IOException {
        User found = this.findById(id);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setAvatar(url);
        userepo.save(found);
        return found.getAvatar();
    }

    public void findByIdAndDelete(UUID id) throws NotFoundException, IOException {
        User found = this.findById(id);
        emailsdr.sendDeletedAccountEmail(found.getEmail(), found.getName());
        userepo.delete(found);
    }
}