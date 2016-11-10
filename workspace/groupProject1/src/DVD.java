import java.io.Serializable;
import java.util.Calendar;

public class DVD extends LoanableItem implements Serializable,
Matchable<String> {
/**
 * Creates a DVD with the given title and ID
 * @param title
 * @param id
 */
	protected DVD(String title, String id) {
		super(title, id);
		
	}
	/**
	 * Marks the DVD as issued to a member
	 * 
	 * @param member
	 *            the borrower
	 * @return true iff the DVD could be issued. True currently
	 */
	@Override
	public boolean issue(Member member) {
		if (super.issue(member)) {
			dueDate.add(Calendar.WEEK_OF_YEAR, 1);
			return true;
		}
		return false;
	}

	/**
	 * Marks the book as returned
	 * 
	 * @return The member who had borrowed the DVD
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
	 *            the id of the book
	 */
	@Override
	public boolean matches(String key) {
		return getId().equals(key);
	}

	/**
	 * Implements the accept method of the Visitor pattern.
	 * 
	 * @param visitor
	 *            the Visitor that will process the DVD object
	 */
	@Override
	public void accept(LoanableItemVisitor visitor) {
		visitor.visit(this);
	}
}


