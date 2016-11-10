public class ReservedSection extends Catalog {

	private static ReservedSection reservedSection;
	private MemberList memberList;

	/*
	 * Private constructor for singleton pattern
	 */
	protected ReservedSection() {
		super();
	}

	/**
	 * Supports the singleton pattern
	 * 
	 * @return the singleton object
	 */
	public static ReservedSection instance() {
		if (reservedSection == null) {
			return (reservedSection = new ReservedSection());
		} else {
			return reservedSection;
		}
	}
}
