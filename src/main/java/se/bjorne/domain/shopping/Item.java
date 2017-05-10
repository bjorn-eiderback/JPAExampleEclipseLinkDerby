package se.bjorne.domain.shopping;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Item {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int quantity;
    @Column(nullable = true) //Vi vill tillåta att datumet är null
    private Date dueDate;

    @ManyToOne
    private ShoppingList shoppingList;

    public Item() {
    }

    public Item(String name, ShoppingList shoppingList) {
        this(name, 1, shoppingList);
    }

    public Item(String name, int quantity, ShoppingList shoppingList) {
        this(name, quantity, null, shoppingList);
    }

    public Item(String name, int quantity, Date dueDate, ShoppingList shoppingList) {
        this.name = name;
        this.quantity = quantity;
        this.dueDate = dueDate;
        this.shoppingList = shoppingList;
    }

    public Item(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", dueDate=" + dueDate +
                ", shoppingList name=" + shoppingList.getName() +
                '}';
    }
}
