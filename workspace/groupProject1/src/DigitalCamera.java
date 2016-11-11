import java.io.Serializable;
import java.util.Calendar;

public class DigitalCamera extends LoanableItem implements Serializable,
		Matchable<String> {

	private String brand;

	protected DigitalCamera(String id, String brand) {
		super(id);
		this.brand = brand;
	}

	/**
	 * Marks the digital camera as issued to a member
	 * 
	 * @param member
	 *            the borrower
	 * @return true iff the digital camera could be issued. True currently
	 */
	@Override
	public boolean issue(Member member) {
		if (super.issue(member)) {
			dueDate.add(Calendar.HOUR, 3);
			return true;
		}
		return false;
	}

	/**
	 * Marks the book as returned
	 * 
	 * @return The member who had borrowed the digital camera
	 */
	@Override
	public Member returnItem() {
		if (borrowedBy == null) {
			return null;
		} else {
			Member borrower = borrowedBy;
			borrowedBy = null;
			return borrower;
		}
	}

	/**
	 * Implements the Matchable interface
	 * 
	 * @param key
	 *            the id of the digital camera
	 */
	@Override
	public boolean matches(String key) {
		return getId().equals(key);
	}

	/**
	 * Implements the accept method of the Visitor pattern.
	 * 
	 * @param visitor
	 *            the Visitor that will process the DigitalCamera object
	 */
	@Override
	public void accept(LoanableItemVisitor visitor) {
		visitor.visit(this);
	}

	public String getBrand() {
		return this.brand;
	}
}
