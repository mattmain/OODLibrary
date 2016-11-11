public class LoanableItemFactory {
	private static final int BOOK = 1;
	private static final int PERIODICAL = 2;
	public static final int DVD = 3;
	public static final int CAMERA = 4;
	public static final int LAPTOP = 5;
	private static LoanableItemFactory singleton = null;

	private LoanableItemFactory() {
	}

	public static LoanableItemFactory instance() {
		if (singleton == null) {
			singleton = new LoanableItemFactory();
		}
		return singleton;
	}

	// code for Singleton
	public LoanableItem createLoanableItem(int type, String title,
			String author, String id, String brand) {
		switch (type) {
		case BOOK:
			return new Book(title, author, id);
		case PERIODICAL:
			return new Periodical(title, id);
		case DVD:
			return new DVD(title, id);
		case CAMERA:
			return new DigitalCamera(id, brand);
		case LAPTOP:
			return new Laptop(id, brand);
		default:
			return null;
		}

	}

}