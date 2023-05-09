import java.util.ArrayList;

public class Auction {
	private ArrayList<Bid> bidList = new ArrayList<Bid>();
	private Dog dog;
	private int auctionNumber;
	private int currentBid = 0;
	
	public Auction(Dog dog, int auctionNumber){
		this.dog = dog;
		this.auctionNumber = auctionNumber;
	}
	
	public void saveBid(Bid bid) {
		bidList.add(0, bid);
		currentBid = bid.getBid();
	}
		
	public ArrayList<Bid> getBidList() {
		return bidList;
	}
	
	public Bid getTopBid() {
		if (bidList.size() == 0){
			return null;
		}
		return bidList.get(0);
	}
	
	//Returnerar topp 3 buden. 
	public Bid[] getTopThreeBids() {
		Bid[] bidList = {null, null, null};
		for (int i = 0; i < 3; i++) {
			if (this.bidList.size() == i) {
				break;
			}
			bidList[i] = this.bidList.get(i);
		}
		return bidList;
	}
	
	public String getAuctionedDogName() {
		return dog.getName();
	}

	public int getAuctionNumber() {
		return auctionNumber;
	}

	public int getCurrentBid() {
		return currentBid;
	}

	//Tar bort alla bud av en användare. 
	public void removeBidsBy(User user) {
		ArrayList<Bid> bidToRemove = new ArrayList<Bid>();
		for (int i = 0; i < bidList.size(); i++) {
			if (bidList.get(i).getUser().equals(user)) {
				bidToRemove.add(bidList.get(i));
			}
		}
		for (Bid bid : bidToRemove) {
			bidList.remove(bid);
		}
	}
}