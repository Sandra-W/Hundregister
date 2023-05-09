public class Bid {
	private User user;
	private int bidAmount;
	
	public Bid(User user, int bidAmount) {
		this.user = user;
		this.bidAmount = bidAmount;
	}
	
	public int getBid() {
		return bidAmount;
	}

	public User getUser() {
		return user;
	}

	@Override
	public String toString() {
		return (user.getName() + " " + bidAmount + " kr ");
	}
	
}