package main;

import java.util.List;

public class Order {
	private int orderID;
	private int customerID;
	private List<MenuItem> orderItems;
	private String name;
	private String status;
	
	public Order(int orderID, int customerID, List<MenuItem> orderItems, String name, String status) {
		this.orderID = orderID;
		this.customerID = customerID;
		this.orderItems = orderItems;
		this.name = name;
		this.status = status;
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
	public List<MenuItem> getOrderItems() {
		return orderItems;
	}
	public String getStatus() {
		return status;
	}
	
	
}
