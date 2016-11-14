import java.util.Calendar;

/**
 * Class to represent a single Reserved Book object
 * 
 * @author Group
 *
 */
public class ReservedBook extends Book {

	private int hourlyFine = 1;

	/**
	 * Constructor for creating a Reserved Book object
	 * 
	 * @param title
	 * @param author
	 * @param id
	 */
	public ReservedBook(String title, String author, String id) {
		super(title, author, id);

	}

	/**
	 * A method to calculate a fine incurred by a member
	 */

	@Override
	public double calculateFine() {
		double hours = System.currentTimeMillis() / 3600000;
		double hourDue = getDueDate().getTimeInMillis() / 3600000;
		int difference = (int) Math.ceil(hours - hourDue);
		if (difference > 0) {
			return difference * hourlyFine;
		}
		return 0;
	}

	/**
	 * Method to return a message if the member tries to place a hold on a
	 * reserved book which isn't allowed
	 */
	@Override
	public void placeHold(Hold hold) {
		String result = "Unable to place a hold on a reserved item.";
		System.out.println(result);
	}

	/**
	 * Method to check if a member can get a reserved book It checks to see if
	 * they already have a reserved book or a digital camera borrowed to them
	 * (this makes them ineligible to get a reserved book)
	 */
	@Override
	public boolean issue(Member member) {
		if (member.hasReservedBook() || member.hasCamera()) {
			return false;
		}
		if (super.issue(member)) {
			dueDate.add(Calendar.HOUR, 2);
			return true;
		}
		return false;
	}

}
