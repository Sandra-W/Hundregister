import java.util.ArrayList;

public class User {
	
	private ArrayList<Dog> ownedDogList = new ArrayList<Dog>();
	private String name;
	
	public User(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void addDog(Dog dog) {
		ownedDogList.add(dog);
	}
		
	public void removeDog(Dog dog) {
		ownedDogList.remove(dog);
	}
	
	public ArrayList<Dog> getOwnedDogList() {
		return ownedDogList;
	}
	
	@Override
	public String toString() {
		String ownedDogListAsString = "";
		for (Dog dog : ownedDogList) {
			ownedDogListAsString += "(" + dog.getName() + ") ";
		}
		return name + " [ " + ownedDogListAsString + "]";
	}
	
}