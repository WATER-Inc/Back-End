package test.service;

import dao.PersistException;
import dao.pool.ConnectionPool;
import entity.auxiliary.Participants;
import entity.Chat;
import entity.Message;
import entity.Role;
import entity.User;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import service.ChatService;
import service.Service;
import service.ServiceFactory;
import service.UserService;

import java.sql.Date;
import java.util.List;

@Test(groups = {"service"})
public class MessageServiceTest extends ServiceTest<Message> {

    private UserService userService;
    private ChatService chatService;
    private User testUser;
    private Chat testChat;

    protected Service service;
    protected static final ServiceFactory factory = new ServiceFactory();

    public static final String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/water?useUnicode=true&characterEncoding=UTF-8";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "20November3;5.-65@1234";
    public static final int DB_POOL_START_SIZE = 10;
    public static final int DB_POOL_MAX_SIZE = 1000;
    public static final int DB_POOL_CHECK_CONNECTION_TIMEOUT = 0;
    @BeforeClass(groups = {"service"})
    public void init() throws PersistException {
        ConnectionPool.getInstance().init(DB_DRIVER_CLASS, DB_URL, DB_USER, DB_PASSWORD, DB_POOL_START_SIZE, DB_POOL_MAX_SIZE, DB_POOL_CHECK_CONNECTION_TIMEOUT);
        service = factory.getService(Message.class);
        userService = factory.getService(User.class);
        chatService = factory.getService(Chat.class);
        testUser = new User();
        testUser.setUsername("johndoesergwe");
        testUser.setPasswordHash("password");
        testUser = userService.persist(testUser);
        testChat = new Chat();
        testChat.setName("Test Chatdfbw");
        testChat.setParticipants(new Participants());
        testChat.getParticipants().addUser(testUser, new Role("User"));
        testChat = chatService.persist(testChat);
    }

    @AfterTest(groups = {"service"})
    public void tearDown() throws PersistException {
        userService.delete(testUser);
        chatService.delete(testChat);
    }
    @Test(groups = {"service"})
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
    @Test(groups = {"service"})
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
    @Test(groups = {"service"})
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
    @Test(groups = {"service"})
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
    @Test(groups = {"service"})
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
