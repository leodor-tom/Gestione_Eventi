package TommasoEleodori.Gestione_Eventi.events;

import TommasoEleodori.Gestione_Eventi.config.EmailSender;
import TommasoEleodori.Gestione_Eventi.events.dto.EventDTO;
import TommasoEleodori.Gestione_Eventi.exceptions.BadRequestException;
import TommasoEleodori.Gestione_Eventi.exceptions.NotFoundException;
import TommasoEleodori.Gestione_Eventi.security.JWTTools;
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
public class EventService {
    @Autowired
    private EventRepository er;
    @Autowired
    private EmailSender emailsdr;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private JWTTools tools;

    public Page<Event> getEvents(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return er.findAll(pageable);
    }

    public Event findById(UUID id) throws NotFoundException {
        return er.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public UUID save(EventDTO body, String email, String name) throws BadRequestException, IOException {
        er.findByNameIgnoreCase(body.name()).ifPresent(event -> {
            throw new BadRequestException("The name of the event it's already used");
        });
        Event event = new Event();
        event.setName(body.name());
        event.setDescription(body.descriptions());
        event.setDate(body.date());
        event.setSeats(body.seats());
        event.setVenue(body.venue());
        er.save(event);
        emailsdr.sendNewEventCreated(email, name, String.valueOf(event.getId()));
        return event.getId();
    }

    public Event findByIdAndUpdate(UUID id, EventDTO body) throws NotFoundException {
        Event found = this.findById(id);
        found.setName(body.name());
        found.setDescription(body.descriptions());
        found.setVenue(body.venue());
        found.setDate(body.date());
        found.setSeats(body.seats());
        return er.save(found);
    }

    public String uploadPoster(UUID id, MultipartFile file) throws NotFoundException, IOException {
        Event found = this.findById(id);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setPoster(url);
        er.save(found);
        return found.getPoster();
    }

    public void findByIdAndDelete(UUID id, String email, String name) throws NotFoundException, IOException {
        Event found = this.findById(id);
        emailsdr.sendEventDeleted(email, name, String.valueOf(found.getId()));
        er.delete(found);
    }
}



