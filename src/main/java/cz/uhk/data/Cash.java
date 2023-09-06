package cz.uhk.data;

public class Cash {
    protected String type;
    protected int value;
    protected int qty;

    public Cash(String type, int value, int qty) {
        this.type = type;
        this.value = value;
        this.qty = qty;
    }

    public int getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
