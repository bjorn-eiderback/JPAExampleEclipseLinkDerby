package se.bjorne.domain.shopping;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


/**
 * Enkla tester.
 * Vi gör inga rena enhetstester utan testar bara JPA-funktionaliteten.
 */
public class ShoppingListTest {
    EntityManager manager;

    @Before
    public void setUp() throws IOException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
        manager = factory.createEntityManager();
        clear();
    }

    @Test
    public void testNoShoppinglists() throws Exception {
        List<ShoppingList> shoppingLists = getAllShoppingLists();
        assertTrue(shoppingLists.isEmpty());
    }

    @Test
    public void testEmptyShoppinglist() throws Exception {
        ShoppingList shoppingList = new ShoppingList("Lista 1");
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            manager.persist(shoppingList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
        List<ShoppingList> shoppingLists = getAllShoppingLists();
        assertEquals(1L, shoppingLists.size());
    }

    @Test
    public void testAddingAnItem() throws Exception {
        ShoppingList shoppingList = new ShoppingList("Lista 2");
        shoppingList.getItems().add(new Item("item 1", shoppingList));
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            manager.persist(shoppingList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
        List<ShoppingList> shoppingLists = getAllShoppingLists();
        assertEquals(1L, shoppingLists.size());
        assertEquals(1L, shoppingLists.get(0).getItems().size());
    }

    @Test
    public void testAddingSeveralItem() throws Exception {
        ShoppingList shoppingList = new ShoppingList("Lista 2");
        addSomeItemsToShoppingList(shoppingList);
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            manager.persist(shoppingList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
        List<ShoppingList> shoppingLists = getAllShoppingLists();
        assertEquals(1L, shoppingLists.size());
        assertEquals(5L, shoppingLists.get(0).getItems().size());
    }

    @Test
    public void testFinding() throws Exception {
        ShoppingList shoppingList = new ShoppingList("Lista 2");
        addSomeItemsToShoppingList(shoppingList);
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            manager.persist(shoppingList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
        List<ShoppingList> shoppingLists = getAllShoppingLists();
        assertEquals(1L, shoppingLists.size());
        Long id = shoppingLists.get(0).getId();
        ShoppingList found = manager.find(ShoppingList.class, id);
        assertEquals(id, found.getId());
    }

    @Test
    public void testRemovingItem() throws Exception {
        ShoppingList shoppingList = new ShoppingList("Lista 2");
        addSomeItemsToShoppingList(shoppingList);
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            manager.persist(shoppingList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
        EntityTransaction tx2 = manager.getTransaction();
        List<ShoppingList> shoppingLists = getAllShoppingLists();
        ShoppingList shopList = shoppingLists.get(0);
        Item item = shopList.getItems().remove(0);
        tx2.begin();
        try {
            //Variant 1: manager.remove(item);
            manager.merge(shopList);//Variant 2: men då behöver vi annotera items med orphanRemoval = true i ShoppingList
                    //Fast orphanRemoval anses samtidigt lite tveksamt
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx2.commit();
        List<ShoppingList> shoppingListsAgain = getAllShoppingLists();
        assertEquals(4L, shoppingListsAgain.get(0).getItems().size());
    }

    @Test
    public void testRemovingShoppingList() throws Exception {
        ShoppingList shoppingList = new ShoppingList("Lista 3");
        shoppingList.getItems().add(new Item("item 3.1", shoppingList));
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            manager.persist(shoppingList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
        EntityTransaction tx2 = manager.getTransaction();
        tx2.begin();
        try {
            manager.remove(shoppingList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx2.commit();
        List<ShoppingList> shoppingLists = getAllShoppingLists();
        assertTrue(shoppingLists.isEmpty());
    }

    private void addSomeItemsToShoppingList(ShoppingList shoppingList) {
        List<Item> items = shoppingList.getItems();
        for(int i = 1; i <= 5; i++) {
            items.add(new Item("item " + i, shoppingList));
        }
    }

    private List<ShoppingList> getAllShoppingLists() {
        Query query = manager.createQuery("SELECT shoplist FROM ShoppingList shoplist", ShoppingList.class);
        return query.getResultList();
    }

    private void clear() {
        clearShoppingList();
    }

    private void clearShoppingList() {
        EntityTransaction tx = manager.getTransaction();
        List<ShoppingList> resultList = getAllShoppingLists();
        tx.begin();
        try {
            for (ShoppingList next : resultList) {
                manager.remove(next);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
    }
}