package main;

public class Order {
	private int orderID;
	private MenuItem[] orderItems;
	private String name;
	
	public Order(int orderID, MenuItem[] orderItems, String name) {
		super();
		this.orderID = orderID;
		this.orderItems = orderItems;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOrderID() {
		return orderID;
	}
	public MenuItem[] getOrderItems() {
		return orderItems;
	}
	
	
	
}
