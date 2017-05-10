package se.bjorne.domain.shopping;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.IOException;
import java.util.List;


/**
 * Bara för att vi ska kunna se hur databasen ser ut (så inga egentliga tester...)
 */
public class GetShoppingListTest {
    EntityManager manager;

    @Before
    public void setUp() throws IOException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
        manager = factory.createEntityManager();
    }

    @Test
    public void printGetAllShoppingLists() throws Exception {
        List<ShoppingList> shoppingLists = getAllShoppingLists();
        for (ShoppingList shoppingList : shoppingLists) {
            System.out.println(shoppingList);
        }
    }

    private List<ShoppingList> getAllShoppingLists() {
        Query query = manager.createQuery("SELECT shoplist FROM ShoppingList shoplist", ShoppingList.class);
        return query .getResultList();
    }

}