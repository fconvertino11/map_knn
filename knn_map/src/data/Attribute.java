package data;
public abstract class Attribute {
    private String name;
    private int index;

    Attribute(String name, int index) {
        setName(name);
        setIndex(index);
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public void setName(String name) {
        this.name = name;
    }
}
