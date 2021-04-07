package TAB2MXL;

public class Beam {

	public int number;
	public String state; // "begin" or "continue" or "end"

	public Beam(int number, String state) {
		this.number = number;
		this.state = state;
	}
}