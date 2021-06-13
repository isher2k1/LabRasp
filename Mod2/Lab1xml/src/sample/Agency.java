package sample;

import java.util.ArrayList;

public class Agency {
    private ArrayList<Category> categories = new ArrayList<>();

    public void addCategory(int code, String name) {
        categories.add(new Category(code, name));
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public Category getCategory(int code) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).code == code) {
                return categories.get(i);
            }
        }
        return null;
    }

    public ArrayList<Category> getCategories(){
        return categories;
    }

    public int countCategories() {
        return categories.size();
    }

    public void deleteCategory(int code) throws Exception {
        Category categoryToDelete = getCategory(code);
        if (categoryToDelete == null) {
            throw new Exception("Cotegory doesnt exist");
        }
        categories.remove(categoryToDelete);
    }

    public void addItem(int code, String from, String to, int aircompanyCode) throws Exception {
        Category category = getCategory(code);
        if (category == null) {
            throw new Exception("Category doesnt exist");
        }
        ArrayList<Item> items = category.getItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).id == code) {
                throw new Exception("This news already exists");
            }
        }
        Item item = new Item(code, from, to);
        category.addItem(item);
    }
}
