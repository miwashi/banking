package net.miwashi.banking;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test of CRUD functionality for Account.
 */
public class AccountRepositoryTest {

    AccountRepository accountRepository = new AccountRepository();

    @BeforeClass
    public static void setUpClass(){
        AccountRepository.createTable();
    }

    @Test
    public void shouldCreateAccount() throws Exception {
        String holder = "Mikael Wallin";
        Account account = accountRepository.create(new Account(holder));
        assertEquals("Holder must be correct!", holder, account.getHolder());
        assertTrue("Account ID must not be zero!", account.getId()>0);
    }

    @Test
    public void shouldReadAccount() throws Exception {
        Account original = accountRepository.create(new Account("Mikael Wallin"));
        Account inDatabase = accountRepository.findById(original.getId());
        assertNotNull(inDatabase);
        assertEquals(original.getHolder(), inDatabase.getHolder());
        assertEquals(original.getId(), inDatabase.getId());
    }
    @Test
    public void shouldUpdateAccount() throws Exception {
        fail("Not yet implemented");
    }

    @Test
    public void shouldDeleteAccount() throws Exception {
        fail("Not yet implemented");
    }
}
