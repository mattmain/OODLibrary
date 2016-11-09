public class ReservedBook extends Book {

	private int hourlyFine = 1;

	public ReservedBook(String title, String author, String id) {
		super(title, author, id);

	}

	@Override
	public int calculateFine() {
		int hours = (int) (System.currentTimeMillis() / 3600000);
		int hourDue = (int) ((this.getDueDate().getTimeInMillis()) / 3600000);
		int difference = hours - hourDue;
		if (difference > 0) {
			return difference * hourlyFine;
		}
		return 0;
	}

	@Override
	public void placeHold(Hold hold) {
		String result = "Unable to place a hold on a reserved item.";
		System.out.println(result);
	}

}
