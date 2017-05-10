package se.bjorne.jpa;

import java.util.List;

import javax.persistence.*;

import se.bjorne.domain.department.Employee;
import se.bjorne.domain.department.Department;
import se.bjorne.domain.shopping.Item;
import se.bjorne.domain.shopping.ShoppingList;

/**
 * Ett initialt ganska (eller snarare mycket) fult test bara för att se att allt lirar
 */
public class VeryUgglyTest {
    private EntityManager manager;

    public VeryUgglyTest(EntityManager manager) {
        this.manager = manager;
    }

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
        EntityManager manager = factory.createEntityManager();
        VeryUgglyTest test = new VeryUgglyTest(manager);

        clear(manager);

        test1(manager, test);
        test2(manager, test);

        System.out.println(".. done");
    }

    /**
     * Vi rensar lite. I alla fall shoppinglistan.
     * (Har inte hittat något smidigt sätt att nollställa databasen annat än att ta bort hela katalogen)
     *
     * @param manager
     */
    private static void clear(EntityManager manager) {
        clearShoppingList(manager);
        //clearItems(manager); //Items ska ryka då vi tar bort shoppinglistan
    }

    private static void clearItems(EntityManager manager) {
        EntityTransaction tx = manager.getTransaction();
        List<Item> resultList = manager.createQuery("Select a From Item a", Item.class).getResultList();
        System.out.println("Items size: " + resultList.size());
        tx.begin();
        try {
            for (Item next : resultList) {
                manager.remove(next);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
    }

    private static void clearShoppingList(EntityManager manager) {
        EntityTransaction tx = manager.getTransaction();
        List<ShoppingList> resultList = manager.createQuery("Select a From ShoppingList a", ShoppingList.class).getResultList();
        System.out.println("Shopping list size: " + resultList.size());
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

    private static void test1(EntityManager manager, VeryUgglyTest test) {
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            test.createEmployees();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
        test.listEmployees();
    }

    private static void test2(EntityManager manager, VeryUgglyTest test) {
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            test.createShoppinglist();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
        test.listItems();
    }

    private void createEmployees() {
        int numOfEmployees = manager.createQuery("Select a From Employee a", Employee.class).getResultList().size();
        if (numOfEmployees == 0) {
            Department department = new Department("java");
            department.getEmployees().add(new Employee("Kalle Kula", department));
            department.getEmployees().add(new Employee("Nisse Kula", department));
            department.getEmployees().add(new Employee("Anna Kula", department));
            manager.persist(department);
        }
    }

    private void createShoppinglist() {
        ShoppingList shoppingList = new ShoppingList("Veckans affärer");
        shoppingList.getItems().add(new Item("John Holm", shoppingList));
        shoppingList.getItems().add(new Item("Lasse Holm", shoppingList));
        manager.persist(shoppingList);
        System.out.println(shoppingList);
    }

    private void listEmployees() {
        List<Employee> resultList = manager.createQuery("Select a From Employee a", Employee.class).getResultList();
        System.out.println("num of employess:" + resultList.size());
        for (Employee next : resultList) {
            System.out.println("next employee: " + next);
        }
    }

    private void listItems() {
        List<Item> resultList = manager.createQuery("Select a From Item a", Item.class).getResultList();
        System.out.println("num of items:" + resultList.size());
        for (Item next : resultList) {
            System.out.println("next item: " + next);
        }
    }
}
