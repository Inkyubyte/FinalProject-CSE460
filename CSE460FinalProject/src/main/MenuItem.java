package main;

public class MenuItem {
	private int itemID;
	private String itemName;
	private double price;
	private String category;
	private String imagePath;
	
	public MenuItem(int itemID, String itemName, double price, String category, String imagePath) {
		super();
		this.itemID = itemID;
		this.itemName = itemName;
		this.price = price;
		this.category = category;
		this.imagePath = imagePath;
	}
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public String getCategory() {
		return category;
	}
	
	public int getItemID() {
		return itemID;
	}
	
}
