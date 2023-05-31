package test.service;

import dao.PersistException;
import entity.auxiliary.ChatLink;
import entity.auxiliary.Participants;
import dao.mysql.MySqlDaoFactory;
import entity.Chat;
import entity.Role;
import entity.User;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import service.ChatService;
import service.RoleService;
import service.UserService;

import java.util.List;

@Test(groups = {"service"})
public class ChatServiceTest extends ServiceTest<Chat> {

    private UserService userService;
    private RoleService roleService;
    private User testUser;
    private Role testRole;

    @BeforeClass(groups = {"service"})
    @Override
    public void init() throws PersistException {
        super.init();
        service = factory.getService(Chat.class);
        userService = factory.getService(User.class);
        roleService = factory.getService(Role.class);
        testUser = new User();
        testUser.setUsername("johndoe");
        testUser.setPasswordHash("password");
        testUser = userService.persist(testUser);
        testRole = new Role();
        testRole.setTitle("Test Role");
        testRole = roleService.persist(testRole);
    }

    @AfterTest(groups = {"service"})
    public void tearDown() throws PersistException {
        userService.delete(testUser);
        roleService.delete(testRole);
    }
    @Test(groups = {"service"})
    @Override
    public void getByIdTest() throws PersistException {
        Chat chat = new Chat();
        chat.setName("Test Chat");
        chat.setParticipants(new Participants());
        chat.getParticipants().addUser(testUser, testRole);
        chat = (Chat) service.persist(chat);
        Chat retrievedChat = (Chat) service.getById(chat.getId());
        service.delete(chat);
        assertEquals(chat, retrievedChat);
    }
    @Test(groups = {"service"})
    @Override
    public void getAllTest() throws PersistException {
        Chat chat1 = new Chat();
        chat1.setName("Test Chat 1");
        chat1.setParticipants(new Participants());
        chat1.getParticipants().addUser(testUser, testRole);
        chat1 = (Chat) service.persist(chat1);
        Chat chat2 = new Chat();
        chat2.setName("Test Chat 2");
        chat2.setParticipants(new Participants());
        chat2.getParticipants().addUser(testUser, testRole);
        chat2 = (Chat) service.persist(chat2);
        List<Chat> chatList = (List<Chat>) service.getAll();
        service.delete(chat1);
        service.delete(chat2);
        assertEquals(2, chatList.size());
        assertTrue(chatList.contains(chat1));
        assertTrue(chatList.contains(chat2));
    }
    @Test(groups = {"service"})
    @Override
    public void persistTest() throws PersistException {
        Chat chat = new Chat();
        chat.setName("Test Chat");
        chat.setParticipants(new Participants());
        chat.getParticipants().addUser(testUser, testRole);
        Chat persistedChat = (Chat) service.persist(chat);
        assertNotNull(persistedChat.getId());
        Chat retrievedChat = (Chat) service.getById(persistedChat.getId());
        service.delete(persistedChat);
        assertEquals(persistedChat, retrievedChat);
    }
    @Test(groups = {"service"})
    @Override
    public void deleteTest() throws PersistException {
        Chat chat = new Chat();
        chat.setName("Test Chat");
        chat.setParticipants(new Participants());
        chat.getParticipants().addUser(testUser, testRole);
        chat = (Chat) service.persist(chat);
        service.delete(chat);
        assertNull(service.getById(chat.getId()));
    }
    @Test(groups = {"service"})
    @Override
    public void updateTest() throws PersistException {
        Chat chat = new Chat();
        chat.setName("Test Chat");
        chat.setParticipants(new Participants());
        chat.getParticipants().addUser(testUser, testRole);
        chat = (Chat) service.persist(chat);
        chat.setName("Updated Test Chat");
        service.update(chat);
        Chat retrievedChat = (Chat) service.getById(chat.getId());
        service.delete(chat);
        assertEquals(chat, retrievedChat);
    }

    @Test(groups = {"service"})
    public void getByUserTest() throws PersistException {
        Chat chat1 = new Chat();
        chat1.setName("Test Chat 1");
        chat1.setParticipants(new Participants());
        chat1.getParticipants().addUser(testUser, testRole);
        chat1 = (Chat) service.persist(chat1);
        Chat chat2 = new Chat();
        chat2.setName("Test Chat 2");
        chat2.setParticipants(new Participants());
        chat2.getParticipants().addUser(testUser, testRole);
        chat2 = (Chat) service.persist(chat2);
        List<Chat> chatList = ((ChatService) service).getByUser(testUser);
        service.delete(chat1);
        service.delete(chat2);
        assertEquals(2, chatList.size());
        assertTrue(chatList.contains(chat1));
        assertTrue(chatList.contains(chat2));
    }

    @Test(groups = {"service"})
    public void addUserTest() throws PersistException {
        Chat chat = new Chat();
        chat.setName("Test Chat");
        chat.setParticipants(new Participants());
        chat.getParticipants().addUser(testUser, testRole);
        chat = (Chat) service.persist(chat);
        User user = new User();
        user.setUsername("janedoe");
        user.setPasswordHash("password");
        user = userService.persist(user);
        ((ChatService) service).addUser(chat, user, testRole);
        Chat retrievedChat = (Chat) service.getById(chat.getId());
        MySqlDaoFactory mySqlDaoFactory = new MySqlDaoFactory();
        List<ChatLink> chatLinks = mySqlDaoFactory.getDao(mySqlDaoFactory.getConnection(), ChatLink.class).getAll();
        userService.delete(user);
        service.delete(chat);
        assertEquals(2, retrievedChat.getParticipants().getUsers().size());
        assertTrue(retrievedChat.getParticipants().getUsers().contains(testUser));
        assertTrue(retrievedChat.getParticipants().getUsers().contains(user));
        assertEquals(2, retrievedChat.getParticipants().getRoles().size());
        assertTrue(retrievedChat.getParticipants().getRoles().contains(testRole));
        assertTrue(retrievedChat.getParticipants().getRoles().contains(testRole));
        assertEquals(2, chatLinks.size());
        assertEquals(chat.getId(), chatLinks.get(0).getChatId());
        assertEquals(testUser.getId(), chatLinks.get(0).getUserId());
        assertEquals(testRole.getId(), chatLinks.get(0).getRoleId());
        assertEquals(chat.getId(), chatLinks.get(1).getChatId());
        assertEquals(user.getId(), chatLinks.get(1).getUserId());
        assertEquals(testRole.getId(), chatLinks.get(1).getRoleId());
    }

}