package sample;

import java.util.ArrayList;

public class Category {
    public int code;
    public String name;
    private final ArrayList<Item> items = new ArrayList<>();

    Category(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item addItemByID(int id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).id == id) {
                return items.get(i);
            }
        }
        return null;
    }
    public Item findItemById(int id){
        for (Item item : items) {
            if (item.id == id) {
                return item;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return  System.lineSeparator() + "Category{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", items=" + items +
                '}' ;
    }
}
