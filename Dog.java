public class Dog {
	
	private String name;
	private String breed;
	private int age;
	private double weight;
	private boolean isAuctioned;
	private Auction auction;
	private boolean hasOwner = false;
	private User owner;
	
	public Dog(String name, String breed, int age, double weight) {
		this.name = name.toLowerCase();
		this.breed = breed.toLowerCase().trim();
		this.age = age;
		this.weight = weight;
	}
	
	public String getName() {
		return name;
	}
	
	public String getBreed() {
		return breed;
	}
	
	public int getAge() {
		return age;
	}
	
	public void increaseAge(int numberOfYears){
		age = age + numberOfYears;
	}
	
	public int getWeight() {
		return (int) weight;
	}
	
	//Om rasen är en tax eller en daschhund är svanslängden alltid 3.7. Annars dess (ålder * vikt)/10.
	public double getTailLength() {
		if (breed.equals("tax") || breed.equals("daschhund")) {
		return 3.7;
		} else {
			return (age * weight)/10;
		}
	}
	
	public boolean getIsAuctioned() {
		return isAuctioned;
	}
	
	public void setIsAuctioned(boolean b) {
		isAuctioned = b;
	}
	
	public void setAuction(Auction auction) {
		this.auction = auction;
	}
	
	public Auction getAuction() {
		return auction;
	}
	
	public boolean getHasOwner() {
		return hasOwner;
	}
	
	public void setHasOwner(boolean b) {
		hasOwner = b;
	}
	
 	public User getOwner() {
		return owner;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	@Override
	public String toString() {
		if (hasOwner) {
			return name + " (" + breed + ", " + age + " years, " + weight + " kilo, " + this.getTailLength() + " cm tail, owned by " + owner.getName() + ")";
		}
		return name + " (" + breed + ", " + age + " years, " + weight + " kilo, " + this.getTailLength() + " cm tail" + ")";
	}
}
