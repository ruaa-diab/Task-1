package org.example;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {

    private Admin admin;
    private ManagingSubscribers managingSubscribers;

    @BeforeEach
    public void setUp() {
        // Create fresh admin for each test
        admin = new Admin("ADMIN001", "John Doe");
        managingSubscribers = ManagingSubscribers.getInstance();
    }

    @AfterEach
    public void tearDown() {
       //CHECK
        managingSubscribers = ManagingSubscribers.getInstance();
        EventHistory.getInstance().getHistoryEvents().clear();
    }

    // Test constructors
    @Test
    public void testDefaultConstructor() {
        Admin defaultAdmin = new Admin();
        assertNotNull(defaultAdmin);
        assertNull(defaultAdmin.getAdiminId());
        assertNull(defaultAdmin.getName());
    }

    @Test
    public void testParameterizedConstructor() {
        Admin testAdmin = new Admin("ADMIN123", "Jane Smith");
        assertEquals("ADMIN123", testAdmin.getAdiminId());
        assertEquals("Jane Smith", testAdmin.getName());
    }

    // Test getters and setters
    @Test
    public void testGettersAndSetters() {
        admin.setAdiminId("ADMIN999");
        admin.setName("Updated Name");

        assertEquals("ADMIN999", admin.getAdiminId());
        assertEquals("Updated Name", admin.getName());
    }


    @Test
    public void testCreateTaskBasic() {

        assertDoesNotThrow(() -> {
            admin.creatTask("Test Task", true);
        });
    }

    @Test
    public void testCreateTaskWithUrgentFlag() {
        assertDoesNotThrow(() -> {
            admin.creatTask("Urgent Task", true);
        });

        assertDoesNotThrow(() -> {
            admin.creatTask("Non-Urgent Task", false);
        });
    }


    @Test
    public void testCreateTaskWithNullTitle() {
        // This assumes you added null checking to creatTask method
        assertThrows(IllegalArgumentException.class, () -> {
            admin.creatTask(null, true);
        });
    }

    @Test
    public void testCreateTaskWithEmptyTitle() {

        assertThrows(IllegalArgumentException.class, () -> {
            admin.creatTask("", true);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            admin.creatTask("   ", false); // whitespace only
        });
    }

    // Test task ID generation (counter behavior)
    @Test
    public void testTaskIdGeneration() {
        // Create multiple tasks and verify they get different IDs
        // Note: This is tricky because Task creation happens inside creatTask
        // You might need to modify your code to make this testable

        admin.creatTask("Task 1", false);
        admin.creatTask("Task 2", true);

        // Since we can't directly access the created tasks,
        // we verify the method doesn't throw exceptions
        // In a real scenario, you'd want to return the created task
        // or have a way to verify the task was created with correct ID

        assertDoesNotThrow(() -> {
            admin.creatTask("Task 3", false);
        });
    }

    // Test integration with ManagingSubscribers
    @Test
    public void testTaskPublishing() {
        // Create a subscriber to verify the task gets published
        User testUser = new User("Test User","1234577");
        Subscriber subscriber = managingSubscribers.Subscribe(testUser, EventType.NEW_TASK);

        // Create task - should trigger publishing
        admin.creatTask("Published Task", true);

        // Verify that the event was recorded in history
        EventHistory history = EventHistory.getInstance();
        assertFalse(history.getHistoryEvents().isEmpty());
    }

    // Test constructor parameter validation (if you added it)
    @Test
    public void testConstructorWithNullParameters() {
        // Test if your constructor handles null values properly
        assertDoesNotThrow(() -> {
            new Admin(null, "Valid Name");
        });

        assertDoesNotThrow(() -> {
            new Admin("ADMIN001", null);
        });
    }

    // Test setter parameter validation (if you added it)
    @Test
    public void testSetterWithNullValues() {
        assertDoesNotThrow(() -> {
            admin.setAdiminId(null);
        });

        assertDoesNotThrow(() -> {
            admin.setName(null);
        });
    }



    @Test
    public void testTaskCreationWithSpecialCharacters() {
        assertDoesNotThrow(() -> {
            admin.creatTask("Task with special chars: !@#$%^&*()", true);
        });

        assertDoesNotThrow(() -> {
            admin.creatTask("Task with unicode: 你好世界", false);
        });
    }
}