package com.example.taskspring.utilsTests;

import com.example.taskspring.utils.PasswordGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordGeneratorTests {

    @Test
    public void generatePasswordWithValidLengthTest(){
        String password = PasswordGenerator.generatePassword(10);
        assertEquals(password.length(), 10);
    }

    @Test
    public void doNotGeneratePasswordWithLengthLessThan0Test(){
        assertThrows(IllegalArgumentException.class, () -> PasswordGenerator.generatePassword(-1));
    }
}
