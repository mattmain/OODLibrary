
/**
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010

 * Redistribution and use with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - the use is for academic purpose only
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Neither the name of Brahma Dathan or Sarnath Ramnath
 *     may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * The authors do not make any claims regarding the correctness of the code in this module
 * and are not responsible for any loss or damage resulting from its use.  
 */
import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Implementation for a single member of the library
 * 
 * @author Brahma Dathan
 * 
 */
public class Member implements Serializable, Matchable<String> {
	private static final long serialVersionUID = 1L;
	private String name;
	private String address;
	private String phone;
	private String id;
	private static final String MEMBER_STRING = "M";
	private List<LoanableItem> itemsBorrowed = new LinkedList<LoanableItem>();
	private List<Hold> booksOnHold = new LinkedList<Hold>();
	private List<Transaction> transactions = new LinkedList<Transaction>();
	private double fine = 0;

	/**
	 * Creates a single member
	 * 
	 * @param name
	 *            name of the member
	 * @param address
	 *            address of the member
	 * @param phone
	 *            phone number of the member
	 */
	public Member(String name, String address, String phone) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		id = MEMBER_STRING + (MemberIdServer.instance()).getId();
	}

	/**
	 * Stores the book as issued to the member
	 * 
	 * @param book
	 *            the book to be issued
	 * @return true iff the book could be marked as issued. always true
	 *         currently
	 */
	public boolean issue(LoanableItem item) {
		if (itemsBorrowed.add(item)) {
			System.out.println("Item Tittle Issued:" + item.getTitle());
			transactions.add(new Transaction("Item issued ", item.getTitle()));
			return true;
		}
		return false;
	}

	/**
	 * Marks the book as not issued to the member
	 * 
	 * @param book
	 *            the book to be returned
	 * @return true iff the book could be marked as marked as returned
	 * 
	 *         public boolean returnBook(Book book) { if
	 *         (itemsBorrowed.remove(book)) { transactions .add(new
	 *         Transaction("Book returned ", book.getTitle())); return true; }
	 *         return false; }
	 */
	public boolean returnBook(Book book) {
		if (itemsBorrowed.remove(book)) {
			transactions.add(new Transaction("Book returned ", book.getTitle()));
			return true;
		}
		return false;
	}


	/**
	 * Marks the book as renewed
	 * 
	 * @param book
	 *            the book to be renewed
	 * @return true iff the book could be renewed
	 */
	public boolean renew(LoanableItem item) {

		for (ListIterator<LoanableItem> iterator = itemsBorrowed.listIterator(); iterator.hasNext();) {
			Book aBook = (Book) iterator.next();
			String id = aBook.getId();
			if (id.equals(item.getId())) {
				transactions.add(new Transaction("Book renewed ", item.getTitle()));
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets an iterator to the issued books
	 * 
	 * @return Iterator to the collection of issued books
	 */
	public Iterator<LoanableItem> getBooksIssued() {
		return (itemsBorrowed.listIterator());
	}

	/**
	 * Gets an iterator to the holds
	 * 
	 * @return Iterator to the collection of holds
	 */
	public Iterator<Hold> getHolds() {
		return (booksOnHold.listIterator());
	}

	/**
	 * Checks if the member had holds on any books
	 * 
	 * @return true iff the member has no holds
	 */
	public boolean hasHolds() {
		if (booksOnHold.size() == 0) {
			return false;

		}
		return true;
	}

	/**
	 * Places a hold for the book
	 * 
	 * @param hold
	 *            the book to be placed a hold
	 */
	public void placeHold(Hold hold) {
		transactions.add(new Transaction("Hold Placed ", hold.getBook().getTitle()));
		booksOnHold.add(hold);
	}

	/**
	 * Removes a hold
	 * 
	 * @param bookId
	 *            the book id for removing a hold
	 * @return true iff the hold could be removed
	 */
	public boolean removeHold(String bookId) {
		for (ListIterator<Hold> iterator = booksOnHold.listIterator(); iterator.hasNext();) {
			Hold hold = iterator.next();
			String id = hold.getBook().getId();
			if (id.equals(bookId)) {
				transactions.add(new Transaction("Hold Removed ", hold.getBook().getTitle()));
				iterator.remove();
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets an iterator to a collection of selected ransactions
	 * 
	 * @param date
	 *            the date for which the transactions have to be retrieved
	 * @return the iterator to the collection
	 */
	public Iterator<Transaction> getTransactions(Calendar date) {
		List<Transaction> result = new LinkedList<Transaction>();
		for (Iterator<Transaction> iterator = transactions.iterator(); iterator.hasNext();) {
			Transaction transaction = iterator.next();
			if (transaction.onDate(date)) {
				result.add(transaction);
			}
		}
		return (result.iterator());
	}

	/**
	 * Getter for name
	 * 
	 * @return member name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for phone number
	 * 
	 * @return phone number
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Getter for address
	 * 
	 * @return member address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Getter for id
	 * 
	 * @return member id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Getter for the list of holds a member may have
	 * 
	 * @return the list of holds
	 */
	public List<Hold> getListOfHolds() {
		return booksOnHold;
	}

	/**
	 * Getter for the list of books borrowed
	 * 
	 * @return the list of items borrowed by a member
	 */
	public List<LoanableItem> getListOfBooks() {
		return itemsBorrowed;
	}

	/**
	 * Setter for name
	 * 
	 * @param newName
	 *            member's new name
	 */
	public void setName(String newName) {
		name = newName;
	}

	/**
	 * Setter for address
	 * 
	 * @param newName
	 *            member's new address
	 */
	public void setAddress(String newAddress) {
		address = newAddress;
	}

	/**
	 * Setter for phone
	 * 
	 * @param newName
	 *            member's new phone
	 */
	public void setPhone(String newPhone) {
		phone = newPhone;
	}

	/**
	 * Checks whether the member is equal to the one with the given id
	 * 
	 * @param id
	 *            of the member who should be compared
	 * @return true iff the member ids match
	 */
	public boolean equals(String id) {
		return this.id.equals(id);
	}

	/**
	 * String form of the member
	 * 
	 */
	@Override
	public String toString() {
		String string = "Member name " + name + " address " + address + " id " + id + "phone " + phone;
		string += " borrowed: [";
		for (Iterator<LoanableItem> iterator = itemsBorrowed.iterator(); iterator.hasNext();) {
			LoanableItem item = iterator.next();
			if (item instanceof Book || item instanceof DVD || item instanceof Periodical) {
				string += " " + item.getTitle();
			}
			if (item instanceof DigitalCamera) {
				DigitalCamera camera = (DigitalCamera) item;
				string += " " + camera.getBrand();
			}
			if (item instanceof Laptop) {
				Laptop laptop = (Laptop) item;
				string += " " + laptop.getBrand();
			}
		}

		string += "] holds: [";
		for (Iterator<Hold> iterator = booksOnHold.iterator(); iterator.hasNext();) {
			Hold hold = iterator.next();
			string += " " + hold.getBook().getTitle();
		}
		string += "] transactions: [";
		for (Iterator<Transaction> iterator = transactions.iterator(); iterator.hasNext();) {
			string += iterator.next();
		}
		string += "]";
		return string;
	}

	/**
	 * To implement the Matchable interface
	 * 
	 * @param key
	 *            the member id
	 */
	// @Override
	public boolean matches(String key) {
		return id.equals(key);
	}

	/**
	 * gets the items that have been issued to a member
	 * 
	 * @return Iterator <LoanableItem>
	 */
	public Iterator<LoanableItem> getLoanableItemsIssued() {
		return itemsBorrowed.iterator();
	}

	/**
	 * return a checked out item
	 * 
	 * @param loanableItem
	 * @return true is successful, else false.
	 */
	public boolean returnItem(LoanableItem loanableItem) {
		fine += loanableItem.calculateFine();
		// loanableItem.returnItem();
		itemsBorrowed.remove(loanableItem);
		return true;
	}

	/**
	 * Getter for getting the list of books issued with holds
	 * 
	 * @return the list of books issued with holds
	 */
	public Iterator<LoanableItem> getBooksIssuedWithHolds() {
		List<LoanableItem> booksWithHolds = new LinkedList<LoanableItem>();
		Iterator<LoanableItem> iterator = itemsBorrowed.iterator();

		while (iterator.hasNext()) {

			LoanableItem item = iterator.next();

			if (item.hasHold()) {
				booksWithHolds.add(item);
			}
		}

		return (booksWithHolds.iterator());

	}

	/**
	 * Method to see if a member has fines
	 * 
	 * @return true iff the member has fines associated with their membership
	 */
	public boolean hasFines() {
		if (fine > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Method to check if a member is removable from the library system
	 * 
	 * @param member
	 * @return true iff the member has no fines and/or no holds placed
	 */
	public double getFines() {
		return fine;
	}

	public boolean isRemovable(Member member) {

		if (member.hasHolds() || member.hasFines()) {
			return false;

		}
		return true;

	}

	/**
	 * Method to see if the member has a reserved book
	 * 
	 * @return true iff the member has a reserved book
	 */
	public boolean hasReservedBook() {
		Iterator<LoanableItem> borrowedItems = itemsBorrowed.iterator();
		while (borrowedItems.hasNext()) {
			if (borrowedItems.next() instanceof ReservedBook) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method to see if the member has a digital camera borrowed
	 * 
	 * @return true iff the member has a camera borrowed
	 */
	public boolean hasCamera() {
		Iterator<LoanableItem> borrowedItems = itemsBorrowed.iterator();
		while (borrowedItems.hasNext()) {
			if (borrowedItems.next() instanceof DigitalCamera) {
				return true;
			}
		}
		return false;
	}

	public double payFines(double amount) {
		this.fine = fine - amount;
		return fine;

	}

	public boolean hasItem() {
		if (this.itemsBorrowed.size() > 0) {
			return true;
		}
		return false;
	}

	public void accept(LoanableItemVisitor visitor) {
		visitor.visit(this);
	}
}
