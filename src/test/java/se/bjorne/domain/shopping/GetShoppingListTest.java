package se.bjorne.domain.shopping;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


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