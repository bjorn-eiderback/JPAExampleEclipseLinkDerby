package com.mastertheboss.jpa;

import java.util.List;

import javax.persistence.*;

import com.mastertheboss.domain.Employee;
import com.mastertheboss.domain.Department;
import com.mastertheboss.domain.Item;
import com.mastertheboss.domain.ShoppingList;

public class JpaTest {
	private EntityManager manager;

	public JpaTest(EntityManager manager) {
		this.manager = manager;
	}

	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
		EntityManager manager = factory.createEntityManager();
		JpaTest test = new JpaTest(manager);

		clear(manager);

		test1(manager, test);
		test2(manager, test);

		System.out.println(".. done");
	}

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

	private static void test1(EntityManager manager, JpaTest test) {
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

	private static void test2(EntityManager manager, JpaTest test) {
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
			manager.persist(department);
			manager.persist(new Employee("Jakab Gipsz",department));
			manager.persist(new Employee("Captain Nemo",department));
		}
	}

	private void createShoppinglist() {
		int numOfItems = manager.createQuery("Select a From ShoppingList a", ShoppingList.class).getResultList().size();
		//if (numOfItems == 0) {
			ShoppingList shoppingList = new ShoppingList("Veckans affärer");
			shoppingList.getItems().add(new Item("John Holm", shoppingList));
			shoppingList.getItems().add(new Item("Lasse Holm", shoppingList));
			manager.persist(shoppingList);
		//}
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
