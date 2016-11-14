import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author matthew
 * 
 */
public abstract class LoanableItem implements Serializable, Matchable<String> {
	private String title;
	private String id;
	protected Member borrowedBy;
	protected Calendar dueDate;
	private List<Hold> holds;

	public boolean matches(String other) {
		return (id.equals(other));
	}

	// getters for all fields
	// other fields and methods

	protected LoanableItem(String title, String id) {
		this.title = title;
		this.id = id;
		holds = new LinkedList();
	}

	protected LoanableItem(String id) {
		this.id = id;
	}

	/**
	 * Issue an item to a member
	 * 
	 * @param member
	 * @return
	 */
	protected boolean issue(Member member) {
		borrowedBy = member;
		dueDate = Calendar.getInstance();
		dueDate.setTimeInMillis(System.currentTimeMillis());
		return true;
	}

	/**
	 * Get the borrower of an item
	 * 
	 * @return
	 */
	protected Member getBorrower() {
		return borrowedBy;
	}

	/**
	 * Add a hold to an item
	 * 
	 * @param hold
	 */
	public void placeHold(Hold hold) {
		holds.add(hold);
	}

	/**
	 * return the next hold on the item
	 * 
	 * @return
	 */
	public Hold getNextHold() {
		return holds.get(0);
	}

	/**
	 * remove hold from item
	 * 
	 * @param id2
	 * @return
	 */
	public boolean removeHold(String id2) {
		try {
			holds.remove(id2);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * renews an item for a member
	 * 
	 * @param member
	 * @return true is successful, else false.
	 */
	public boolean renew(Member member) {
		return this.issue(member);
	}

	/**
	 * returns the holds on an item.
	 * 
	 * @return Iterator<hold>
	 */
	public Iterator<Hold> getHolds() {
		return holds.iterator();
	}

	/**
	 * Checks if an item has a hold
	 * 
	 * @return true if item has a hold, else false.
	 */
	public boolean hasHold() {
		if (holds.iterator().hasNext())
			return true;
		else
			return false;
	}

	/**
	 * Returns an item, removes from both the member and item object.
	 * 
	 * @return member that the item was returned for.
	 */
	public Member returnItem() {
		Member member = this.getBorrower();
		if (member == null) {
			return null;
		}
		member.returnItem(this);
		borrowedBy = null;
		return member;
	}

	/**
	 * returns the title of an item
	 * 
	 * @return String title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * returns the id of an item
	 * 
	 * @return String id
	 */
	public String getId() {
		return id;
	}

	/**
	 * returns the due date of an object.
	 * 
	 * @return String due date.
	 */
	public Calendar getDueDate() {
		return dueDate;
	}

	public boolean isBorrowed() {
		if (this.borrowedBy == null) {
			return false;
		}
		return true;
	}

	public String toString() {
		return "title: " + title + " id: " + id + " borrowed by " + borrowedBy;
	}

	public boolean isOverdue() {
		return System.currentTimeMillis() > dueDate.getTimeInMillis();
	}

	public int setDueDate(Calendar dueDate2) {
		dueDate = dueDate2;
		return Library.OPERATION_COMPLETED;

	}

	public double calculateFine() {
		int dueDate = (int) (this.dueDate.getTimeInMillis() / 86400000);
		int currentDay = (int) (System.currentTimeMillis() / 86400000);
		int difference = currentDay - dueDate;
		double fine = 0;
		if (difference > 0) {
			fine = .10 + (difference - 1) * .05;
		}

		return fine;
	}

	public void accept(LoanableItemVisitor visitor) {
		visitor.visit(this);

	}
}
