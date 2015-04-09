package com.my.demo.coffee;

public class Order {

	private String drinker;
	private String size;
	private long coffeeShopId;
	private String[] selectedOptions;
	private DrinkType type;
	private String id = "1";
	
	public String getId() {
		return id;
	}
	
	public String getDrinker() {
		return drinker;
	}
	
	public void setDrinker(String drinker) {
		this.drinker = drinker;
	}
	
	public String getSize() {
		return size;
	}
	
	public void setSize(String size) {
		this.size = size;
	}
	
	public long getCoffeeShopId() {
		return coffeeShopId;
	}
	
	public void setCoffeeShopId(long coffeeShopId) {
		this.coffeeShopId = coffeeShopId;
	}
	
	public String[] getSelectedOptions() {
		return selectedOptions;
	}
	
	public void setSelectedOptions(String[] selectedOptions) {
		this.selectedOptions = selectedOptions;
	}
	
	public DrinkType getType() {
		return type;
	}
	
	public void setType(DrinkType type) {
		this.type = type;
	}
	
}
