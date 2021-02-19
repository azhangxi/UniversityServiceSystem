package testmodel;

import model.Admin;
import model.Courses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Main;

import static model.UsersType.ADMIN;
import static org.junit.jupiter.api.Assertions.*;

public class TestAdmin {
    Admin admin;

    @BeforeEach
    public void runBefore() {
        Main.initializeTheSystem();
        admin = new Admin("TestA1", "TestA1", "TestA1", "Test.",0);
    }

    @Test
    public void testEquals() {

        assertTrue(admin.equals(admin));
        assertFalse(admin.equals(null));
        assertFalse(admin.equals(new Courses()));

        Admin admin0 = new Admin("TestA1", "TestA1", "TestA1", "Test.",0);
        Admin admin1 = new Admin("1", "TestA1", "TestA1", "Test.",0);
        Admin admin2 = new Admin("TestA1", "1", "TestA1", "Test.",0);
        Admin admin3 = new Admin("TestA1", "TestA1", "1", "Test.",0);
        Admin admin4 = new Admin("TestA1", "TestA1", "TestA1", "1.",0);

        assertTrue(admin.equals(admin0));
        assertFalse(admin.equals(admin1));
        assertFalse(admin.equals(admin2));
        assertFalse(admin.equals(admin3));
        assertFalse(admin.equals(admin4));
    }

    @Test
    public void testGetUserName() {
        assertEquals("TestA1", admin.getUserName());
    }

    @Test
    public void testGetPassWord() {
        assertEquals("TestA1", admin.getPassWord());
    }

    @Test
    public void testGetName() {
        assertEquals("TestA1", admin.getName());
    }

    @Test
    public void testSetPassWord() {
        admin.setPassWord("TestSP");
        assertEquals("TestSP", admin.getPassWord());
    }

    @Test
    public void testSetName() {
        admin.setName("TestSP");
        assertEquals("TestSP", admin.getName());
    }

    @Test
    public void testHashcode() {
        Admin admin1 = admin;
        assertEquals(admin.hashCode(), admin1.hashCode());
    }

    @Test
    public void testGetMyType() {
        assertEquals(ADMIN, admin.getMyType());
    }
}
