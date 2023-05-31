package test.service;

import dao.PersistException;
import entity.auxiliary.Participants;
import entity.Chat;
import entity.Message;
import entity.Role;
import entity.User;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import service.ChatService;
import service.UserService;

import java.sql.Date;
import java.util.List;

@Test(groups = {"service"})
public class MessageServiceTest extends ServiceTest<Message> {

    private UserService userService;
    private ChatService chatService;
    private User testUser;
    private Chat testChat;

    @BeforeClass(groups = {"service"})
    @Override
    public void init() throws PersistException {
        super.init();
        service = factory.getService(Message.class);
        userService = factory.getService(User.class);
        chatService = factory.getService(Chat.class);
        testUser = new User();
        testUser.setUsername("johndoe");
        testUser.setPasswordHash("password");
        testUser = userService.persist(testUser);
        testChat = new Chat();
        testChat.setName("Test Chat");
        testChat.setParticipants(new Participants());
        testChat.getParticipants().addUser(testUser, new Role("User"));
        testChat = chatService.persist(testChat);
    }

    @AfterTest(groups = {"service"})
    public void tearDown() throws PersistException {
        userService.delete(testUser);
        chatService.delete(testChat);
    }

    @Override
    public void getByIdTest() throws PersistException {
        Message message = new Message();
        message.setSender(testUser);
        message.setChat(testChat);
        message.setContent("Test message");
        message.setDate(new Date(2003,11,20));
        message = (Message) service.persist(message);
        Message retrievedMessage = (Message) service.getById(message.getId());
        service.delete(message);
        assertNotNull(retrievedMessage);
    }

    @Override
    public void getAllTest() throws PersistException {
        Message message1 = new Message();
        message1.setSender(testUser);
        message1.setChat(testChat);
        message1.setContent("Test message 1");
        message1.setDate(new Date(System.currentTimeMillis()));
        message1 = (Message) service.persist(message1);
        Message message2 = new Message();
        message2.setSender(testUser);
        message2.setChat(testChat);
        message2.setContent("Test message 2");
        message2.setDate(new Date(System.currentTimeMillis()));
        message2 = (Message) service.persist(message2);
        List<Message> messages = (List<Message>) service.getAll();
        service.delete(message1);
        service.delete(message2);
        assertEquals(2, messages.size());
        assertTrue(messages.contains(message1));
        assertTrue(messages.contains(message2));
    }

    @Override
    public void persistTest() throws PersistException {
        Message message = new Message();
        message.setSender(testUser);
        message.setChat(testChat);
        message.setContent("Test message");
        message.setDate(new Date(System.currentTimeMillis()));
        message = (Message) service.persist(message);
        Message retrievedMessage = (Message) service.getById(message.getId());
        service.delete(message);
        assertEquals(message, retrievedMessage);

    }

    @Override
    public void deleteTest() throws PersistException {
        Message message = new Message();
        message.setSender(testUser);
        message.setChat(testChat);
        message.setContent("Test message");
        message.setDate(new Date(System.currentTimeMillis()));
        message = (Message) service.persist(message);
        service.delete(message);
        Message retrievedMessage = (Message) service.getById(message.getId());
        assertNull(retrievedMessage);
    }

    @Override
    public void updateTest() throws PersistException {
        Message message = new Message();
        message.setSender(testUser);
        message.setChat(testChat);
        message.setContent("Test message");
        message.setDate(new Date(System.currentTimeMillis()));
        message = (Message) service.persist(message);
        message.setContent("Updated message");
        service.update(message);
        Message retrievedMessage = (Message) service.getById(message.getId());
        service.delete(message);
        assertEquals(retrievedMessage.getContent(), "Updated message");
    }
}
