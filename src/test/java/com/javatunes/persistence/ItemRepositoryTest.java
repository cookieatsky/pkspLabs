package com.javatunes.persistence;


import com.javatunes.config.SpringConfig;
import com.javatunes.domain.ServiceCategory;
import com.javatunes.domain.ServiceItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Collection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {SpringConfig.class})
public class ItemRepositoryTest {

    @Autowired
    private PlatformTransactionManager transactionManager;
    DefaultTransactionDefinition definition = new DefaultTransactionDefinition();

    @Autowired
    ItemRepository repo;

    @Test
    public void testFindPositive() {
        Long id = 1L;

        TransactionStatus transaction = transactionManager.getTransaction(definition);
        System.out.println("\n*** Retrieving item from the database ***");

        // DONE: Get an item by id.
        ServiceItem item = repo.getOne(id);

        assertNotNull("The Service item should not be null", item);
        System.out.println(item);
        System.out.println("***\n");
        transactionManager.commit(transaction);
    }


    @Test
    public void testFindByNamePositive() {
        String name = "Thailand massage";

        TransactionStatus transaction = transactionManager.getTransaction(definition);

        Collection<ServiceItem> results = repo.findByName(name);

        assertTrue("Should find at least one item in findByName(name)", results.size()>0);
        System.out.println("Number of items found: " + results.size());
        System.out.println(results);
        transactionManager.commit(transaction);
    }


    @Test
    public void testFindByCategory() {
        ServiceCategory category = ServiceCategory.MASSAGE;

        TransactionStatus transaction = transactionManager.getTransaction(definition);

        Collection<ServiceItem> results = repo.findByCategory(category);

        assertTrue("Should find at least one item in findByCategory(category)", results.size()>0);
        System.out.println("Number of items found: " + results.size());
        System.out.println(results);
        transactionManager.commit(transaction);
    }
}
