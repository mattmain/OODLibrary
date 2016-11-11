import java.util.Calendar;

public class ReservedBook extends Book {

	private int hourlyFine = 1;

	public ReservedBook(String title, String author, String id) {
		super(title, author, id);

	}

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

	@Override
	public void placeHold(Hold hold) {
		String result = "Unable to place a hold on a reserved item.";
		System.out.println(result);
	}

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
