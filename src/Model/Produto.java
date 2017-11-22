package Model;

import java.util.Date;

public class Produto {
	String name, category,description;
	double sellPrice,costPrice;
	int amount,id;
	Date validity;
	String validityy;
	

	public Produto(){
		
	}
	
	public Produto(String name, String category, String description, double sellPrice, double costPrice,
			int amount, Date validity) {
		super();
		this.name = name;
		this.category = category;
		this.description = description;
		this.sellPrice = sellPrice;
		this.costPrice = costPrice;
		this.amount = amount;
		this.validity = validity;
	}
	
	public Produto(int id, String name, String category, String description, double sellPrice, double costPrice,
			int amount, Date validity) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.description = description;
		this.sellPrice = sellPrice;
		this.costPrice = costPrice;
		this.amount = amount;
		this.validity = validity;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(double costPrice) {
		this.costPrice = costPrice;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getValidity() {
		return validity;
	}

	public void setValidity(Date validity) {
		this.validity = validity;
	}



	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getValidityy() {
		return validityy;
	}

	public void setValidityy(String validityy) {
		this.validityy = validityy;
	}

}
