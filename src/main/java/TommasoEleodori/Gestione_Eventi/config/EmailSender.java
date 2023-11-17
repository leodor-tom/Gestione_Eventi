package TommasoEleodori.Gestione_Eventi.config;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EmailSender {
    private final String apikey;
    private final String sender;

    public EmailSender(@Value("${sendgrid.apikey}") String apikey,
                       @Value("${sendgrid.sender}") String sender) {
        this.apikey = apikey;
        this.sender = sender;
    }

    public void sendRegistrationEmail(String recipient, String name) throws IOException {
        Email from = new Email(sender);
        String subject = "Registration successful";
        Email to = new Email(recipient);
        Content content = new Content("text/plain", "Welcome aboard " + name);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apikey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sg.api(request);
    }

    public void sendUpdateAccountEmail(String recipient, String name) throws IOException {
        Email from = new Email(sender);
        String subject = "Account Updated";
        Email to = new Email(recipient);
        Content content = new Content("text/plain", "Hi " + name + ", your account has been updated successfully");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apikey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sg.api(request);
    }

    public void sendDeletedAccountEmail(String recipient, String name) throws IOException {
        Email from = new Email(sender);
        String subject = "Account Deleted";
        Email to = new Email(recipient);
        Content content = new Content("text/plain", "Hi " + name + ", your account has been deleted successfully");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apikey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sg.api(request);
    }

    public void sendNewEventCreated(String recipient, String name, String eventId) throws IOException {
        Email from = new Email(sender);
        String subject = "New Event";
        Email to = new Email(recipient);
        Content content = new Content("text/plain", "Hi " + name + ", the event: " + eventId + " has been created successfully");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apikey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sg.api(request);
    }

    public void sendEventDeleted(String recipient, String name, String eventId) throws IOException {
        Email from = new Email(sender);
        String subject = "Event Deleted";
        Email to = new Email(recipient);
        Content content = new Content("text/plain", "Hi " + name + ", the event: " + eventId + " has been deleted successfully");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apikey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sg.api(request);
    }

    public void sendEventParticipation(String recipient, String name, String eventName) throws IOException {
        Email from = new Email(sender);
        String subject = "New Participation";
        Email to = new Email(recipient);
        Content content = new Content("text/plain", "Hi " + name + ", Your reservation for the " + eventName + " has been successfully saved! We look forward to welcoming you to the event.");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apikey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sg.api(request);
    }

    public void sendEventParticipationDeleted(String recipient, String name, String eventName) throws IOException {
        Email from = new Email(sender);
        String subject = "Participation Deleted";
        Email to = new Email(recipient);
        Content content = new Content("text/plain", "Hi " + name + ", Your reservation for the " + eventName + " has been successfully deleted!");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apikey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sg.api(request);
    }

}
