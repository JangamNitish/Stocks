package pojo;

public class StocksPojo {
	int price,Quantity;
	String symbol;
	public int getPrice() {
		return price;
	}
	public int getQuantity() {
		return Quantity;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public StocksPojo(int price, int quantity, String symbol) {
		super();
		this.price = price;
		Quantity = quantity;
		this.symbol = symbol;
	}

	
}
