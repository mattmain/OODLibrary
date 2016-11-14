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
/**
 * This is the standard Visitor interface for the visitor pattern.
 * 
 * @author Brahma Dathan
 * 
 */
public interface LoanableItemVisitor {
	/**
	 * This is the method for catching all LoanableItem objects that may be
	 * added in the future.
	 * 
	 * @param itemList
	 *            the item to be processed
	 */
	public void visit(ItemList itemList);

	/**
	 * This method processes Book objects
	 * 
	 * @param book
	 *            the Book to be processed
	 */
	public void visit(Book book);

	/**
	 * The method processes DVD objects
	 * 
	 * @param periodical
	 *            the DVD to be processed
	 */
	public void visit(DVD dvd);

	/**
	 * The method processes Periodical objects
	 * 
	 * @param periodical
	 *            the Periodical to be processed
	 */

	public void visit(Periodical periodical);

	/**
	 * The method processes Laptop objects
	 * 
	 * @param periodical
	 *            the Laptop to be processed
	 */
	public void visit(Laptop laptop);

	/**
	 * The method processes DigitalCamera objects
	 * 
	 * @param periodical
	 *            the DigitalCamera to be processed
	 */
	public void visit(DigitalCamera digitalCamera);

	/**
	 * The method processes Catalog objects
	 * 
	 * @param periodical
	 *            the Catalog to be processed
	 */
	public void visit(Catalog catalog);

	/**
	 * The method processes MemberList objects
	 * 
	 * @param periodical
	 *            the MemberList to be processed
	 */
	public void visit(MemberList memberlist);

	/**
	 * The method processes Member objects
	 * 
	 * @param periodical
	 *            the Member to be processed
	 */
	public void visit(Member member);

	/**
	 * The method processes Library objects
	 * 
	 * @param periodical
	 *            the library to be processed
	 */
	public void visit(Library library);

	/**
	 * The method processes LoanableItem objects
	 * 
	 * @param periodical
	 *            the LoanableItem to be processed
	 */
	public void visit(LoanableItem loanableItem);
}
