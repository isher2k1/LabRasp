package sample;

public class Item {
    public int id;
    public String headline ;
    public String author;

    Item(int id, String headline, String author) {
        this.id = id;
        this.headline = headline;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", headline='" + headline + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
