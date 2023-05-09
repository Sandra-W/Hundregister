import java.util.ArrayList;
import java.util.Scanner;

public class DogRunner {
	private Scanner input = new Scanner(System.in);
	private ArrayList<Dog> dogList = new ArrayList<Dog>();
	private ArrayList<User> userList = new ArrayList<User>();
	private ArrayList<Auction> auctionList = new ArrayList<Auction>();
	private int nextAuctionNumber = 1;

	private void init() {
		System.out.println("Welcome! Program starting up.");
		System.out.println("List of commands:" 
		+ "\n-register new dog"
		+ "\n-increase age"
		+ "\n-list dogs"
		+ "\n-remove dog"
		+ "\n-register new user"
		+ "\n-list users"
		+ "\n-remove user"
		+ "\n-start auction"
		+ "\n-list auctions"
		+ "\n-list bids"
		+ "\n-make bid"
		+ "\n-close auction"
		+ "\n-exit");
	}

	//Kör det valda kommandot
	private void runCommandLoop() {
		while (true) {
			System.out.print("Choose command:");
			String command = input.nextLine();
			switch(command) {
			case "register new dog":
				registerDog();
				break;
			case "increase age":
				increaseAge();
				break;
			case "list dogs":
				listDogs();
				break;
			case "remove dog":
				removeDog();
				break;
			case "register new user":
				registerUser();
				break;
			case "list users":
				listUsers();
				break;
			case "remove user":
				removeUser();
				break;
			case "start auction":
				startAuction();
				break;
			case "list auctions":
				listAuctions();
				break;
			case "list bids":
				listBids();
				break;
			case "make bid":
				makeBid();
				break;
			case "close auction":
				closeAuction();
				break;
			case "exit":
				exitDogs();
				return;	
			default:
				System.out.println("An error occured. Make sure you entered a valid command.");
			}
			
		}
		
	}
	
	//Läser in namn, ras, ålder och vikt. Skapar hundobjekt. Lägger hundobjektet i arrayen. 
	private void registerDog() {
		String name = readName("dogs");

		String breed = readBreed();
		
		System.out.print("What is the dogs age?:");
		int age = input.nextInt();
		
		System.out.print("What is the dogs weight?:");
		double weight = input.nextDouble();
		
		dogList.add(new Dog(name, breed, age, weight));
		input.nextLine();
	}
	
	//Ökar den valda hundens ålder med 1.
	private void increaseAge() {
		String name = readName("dogs");
		Dog dog = findDog(name);
		if (dog==null) {
			return;
		}
		dog.increaseAge(1);	    
	}
	
	//Listar hundarna efter den önskade svanslängden från kortast till längst.
	private void listDogs() {
		if (dogList.isEmpty()) {
			System.out.println("Error: no dogs in register");
			return;
		}
		System.out.print("What is the shortest desired tail length?:");
		double desiredTailLength = input.nextDouble();
		
		sortDogList();
		for (Dog dog : dogList) {
			if (dog.getTailLength() >= desiredTailLength) {
				System.out.println(dog);
			}
		}
		input.nextLine();
	}
	
	//Jämför hundarnas svanslängd och sorterar dem. Om samma längd, sortera efter namn.
	private void sortDogList(){
		boolean listModified = false;
		
		for (int i = 0; i < dogList.size() - 1; i++) {
			Dog dogOne = dogList.get(i);
			Dog dogTwo = dogList.get(i + 1);
			double dogOneTailLength = dogOne.getTailLength();
			double dogTwoTailLength = dogTwo.getTailLength();
			
			if (dogTwoTailLength < dogOneTailLength) {
				dogList.set(i, dogTwo);
				dogList.set(i + 1, dogOne);
				listModified = true;
			} else {
				if (dogTwoTailLength == dogOneTailLength) {
					sortDogsByName(dogOne, dogTwo, i);
				}
			}
		}
		if (listModified) {
			sortDogList();
		}
	}
	
	//Sorterar hundar efter namn vid samma svanslängd.
	private boolean sortDogsByName(Dog dogOne, Dog dogTwo, int i) {
		String dogOneName = dogOne.getName();
		String dogTwoName = dogTwo.getName();
		for (int a = 0; a < dogOneName.length(); a++) {
			
			if (dogTwoName.length() == a) {
				dogList.set(i, dogTwo);
				dogList.set(i + 1, dogOne);
				return true;
			}
			if (dogTwoName.charAt(a) == dogOneName.charAt(a)) {
				continue;
			}
			if (dogTwoName.charAt(a) < dogOneName.charAt(a)) {
				dogList.set(i, dogTwo);
				dogList.set(i + 1, dogOne);
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	//Hittar hundobjektet med det givna namnet, tar bort det från samtliga samlingar i programmet. 
	private void removeDog() {
		String name = readName("dogs");		
		Dog dog = findDog(name);
		if (dog==null) {
			return;
		}
		if (dog.getIsAuctioned()) {
			auctionList.remove(dog.getAuction());
		}
		if (dog.getHasOwner()) {
			dog.getOwner().removeDog(dog);
		}
		dogList.remove(dog);
		System.out.println("The dog with the given name is deleted.");
	}
	
	//Läser in namn. Lägger till det i listan. 
	private void registerUser() {
		String name = readName("users");
		userList.add(new User(name));
	}
	
	//Returnerar en lista med registrerade användare. 
	private void listUsers() {
		if (userList.isEmpty()) {
			System.out.println("Error: no users in register");
			return;
		}
		for (User user : userList) {
			System.out.println(user);
		}
	}
	
	//Hittar användarobjektet med det givna namnet, tar bort det från samtliga samlingar i programmet.
	private void removeUser() {
		String name = readName("users");
		User user = findUser(name);
		if (user==null) {
			return;
		}
		
		for (Auction auction : auctionList) {
			auction.removeBidsBy(user);
		}
		
		for (Dog dog : user.getOwnedDogList()) {
			dogList.remove(dog);
		}
		
		userList.remove(user);
	}
	
	//Väljer hundobjekt med det givna namnet. Kollar om hunden redan auktioneras.
	//Kollar om hunden redan har ägare. Kör createAuction() med valt hundobjekt.
	private void startAuction() {
		String name = readName("dogs");
		if (dogList.isEmpty()) {
			System.out.println("Error: no dogs in register");
			return;
		}
		Dog dog = findDog(name);
		if (dog==null) {
		    return;
		}
		if (dog.getIsAuctioned()) {
			System.out.println("Error: this dog is already up for auction");
			return;
		}
		if (dog.getHasOwner()) {
			System.out.println("Error: this dog already has an owner");
			return;
		}
		createAuction(dog);
	}
	
	//Skapar auktionsobjekt med valda hundobjekt från startAuction(). Påbörjar uppräkning av auktionsnummer med nextAuctionNumber. 
	//Lägger auktionsobjektet i actionList listan. Sätter hundobjektet status till auktionerad, och assignerar hunden till en auktion.
	private void createAuction(Dog dog) {
		Auction auction = new Auction(dog, nextAuctionNumber);
		System.out.println("Auction started #" + nextAuctionNumber);
		nextAuctionNumber++;
		auctionList.add(auction);
		dog.setIsAuctioned(true);
		dog.setAuction(auction);
	}
	
	//Skriver ut aktiva auktioner och de topp 3 buden. 
 	private void listAuctions() {
		if (auctionList.isEmpty()) {
			System.out.println("Error: no auctions in progress");
			return;
		}
		for (Auction auction : auctionList) {
			Bid[] bidList = auction.getTopThreeBids();
			System.out.print("Auction #" + auction.getAuctionNumber() + ": " + auction.getAuctionedDogName() + ". Top bids: [ ");
			for (Bid bid : bidList) {
				if (bid!=null) {
					System.out.print(bid);
				}
			}
			System.out.println("]");
		}
	}

	//Skriver ut alla bud gjorda på valt hundobjekt. Ger felmeddelande om hunden ej auktioneras, eller om inga bud finns.
	private void listBids() {
		String name = readName("dogs");
		Dog dog = findDog(name);
		if (dog==null) {
			return;
		}
		if (!dog.getIsAuctioned()) {
			System.out.println("Error: this dog is not up for auction");
			return;
		}
		ArrayList<Bid> bidList = dog.getAuction().getBidList();
		if (bidList.isEmpty()) {
			System.out.println("No bids registred yet for this auction");
			return;
		}
		System.out.println("Here are the bids for this auction");
		for (Bid bid : bidList) {
			System.out.println(bid);
		}
	}
	
	//Skapar ett bud på vald hund med vald användare
	private void makeBid() {
		String userName = readName("users");
		User user = findUser(userName);
		if (user==null) {
			return;
		}
		if (dogList.isEmpty()) {
			System.out.println("Error: no dogs in register");
		}
		String dogName = readName("dogs");
		Dog dog = findDog(dogName);
		if (dog==null) {
			return;
		}
		if (!dog.getIsAuctioned()) {
			System.out.println("Error: this dog is not up for auction");
			return;
		}
		int bidAmount = readBidAmount(dog.getAuction().getCurrentBid());
		removePreviousBid(user, dog);
		dog.getAuction().saveBid(new Bid(user, bidAmount));
	}
	
	//Läser av budet, kontrollerar summan.
	private int readBidAmount(int currentBid) {
		while (true) {
			System.out.print("Amount to bid (min " + (currentBid + 1) + "): ");
			int bidAmount = input.nextInt();
			if (bidAmount > currentBid) {
				input.nextLine();
				return bidAmount;
			}
			System.out.println("Error: too low bid!");
		}
	}
	
	//Tar bort föregående bud.
	private void removePreviousBid(User user, Dog dog) {
		ArrayList<Bid> bidList = dog.getAuction().getBidList();
		for (Bid bid : bidList) {
			if (bid.getUser().getName().equals(user.getName())) {
				bidList.remove(bid);
				return;
			}
		}
	}
	
	//Kontrollerar avslutningen av auktionen, och tar sedan bort auktionen. 
	private void closeAuction() {
		String name = readName("dogs");
		Dog dog = findDog(name);
		if (dog == null) {
			return;
		}
		if (!dog.getIsAuctioned()) {
			System.out.println("Error: this dog is not up for auction");
			return;
		}
		Auction auction = dog.getAuction();
		Bid topBid = auction.getTopBid();
		if (topBid==null) {
			System.out.println("No bids were made for " + dog.getName() + ".");
			return;
		}
		deleteAuction(dog, topBid);
	}

	//Registrerar hund till användare med högst bud, tar bort hund från auktionen.
	private void deleteAuction(Dog dog, Bid topBid) {
		User user = topBid.getUser();
		dog.setIsAuctioned(false);
		user.addDog(dog);
		dog.setHasOwner(true);
		dog.setOwner(user);
		
		auctionList.remove(dog.getAuction());
		System.out.print("The auction is closed. ");
		System.out.println("The winning bid was " + topBid.getBid() + " kr and was made by " + user.getName() + ".");
	}
	
	//Läser namn
	private String readName(String entity) {
		System.out.print("What is the " + entity + " name?:");
		String name = validateFormat("name");
		return name;
	}
	
	//Läser ras
	private String readBreed() {
		System.out.print("What is the dogs breed?:");
		String breed = validateFormat("breed");
		return breed;
	}
	
	//Formatterar inmatningen. Kollar om inmatningsfält är tomt, ger felmeddelande.                                                                   
 	private String validateFormat(String nameOrBreed) {
		String name;
		name = input.nextLine().toLowerCase().trim();
		if (name.isEmpty()) {
			System.out.println("Error: the " + nameOrBreed + " can not be empty");
			name = validateFormat(nameOrBreed);
		}
		return name;
	}
	
	//Söker efter hundobjektet med det givna namnet i listan och returnerar det om det finns
 	private Dog findDog(String name) {
		for (Dog dog : dogList) {
			if (dog.getName().equals(name)) {
				return dog;
			}
		}
		System.out.println("Error: no such dog");
		return null;
	}
	
	//Söker efter användarobjektet med det givna namnet i listan och returnerar det om det finns
	private User findUser(String name) {
		for (User user : userList) {
			if (user.getName().equals(name)) {
				return user;
			}
		}
		System.out.println("Error: no such user");
		return null;
	}
	
	//Avslutar programmet
	private void exitDogs() {
		System.out.println("Program closing down.");
	}
	
	//Initierar programmet
	private void run() {
		init();
		runCommandLoop();
	}
	
 	public static void main(String[] args) {
		new DogRunner().run();
	}
}