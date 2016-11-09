import java.io.Serializable;
import java.util.Calendar;

public class Periodical extends LoanableItem implements Serializable,
		Matchable<String> {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a book with the given id, title, and author name
	 * 
	 * @param title
	 *            book title
	 * @param author
	 *            author name
	 * @param id
	 *            book id
	 */
	public Periodical(String title, String id) {
		super(title, id);
	}

	/**
	 * Marks the book as issued to a member
	 * 
	 * @param member
	 *            the borrower
	 * @return true iff the book could be issued. True currently
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
	 * @return The member who had borrowed the book
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
	 *            the Visitor that will process the Periodical object
	 */
	@Override
	public void accept(LoanableItemVisitor visitor) {
		visitor.visit(this);
	}
}
