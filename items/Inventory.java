public class Inventory {
    private List<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }
    public void addItem(Item item) {
        items.add(item);
    }
    public void removeItem(Item item) {
        items.remove(item);
    }
    public List<Item> getItems() {
        return items;
    }
    public boolean hasItem(Item item) {
        return items.contains(item);
    }
}
