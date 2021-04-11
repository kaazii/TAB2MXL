package TAB2MXL;

import TAB2MXL.Note;

public class BassNoteUtility {

	public Note[][] BassNote = new Note[4][13];
	public Note[] BassRepo = new Note[12];

	public int counter1 = 3;
	public int octave1 = 2;

	public int counter2 = 7;
	public int octave2 = 2;

	public int counter3 = 5;
	public int octave3 = 1;

	public int counter4 = 0;
	public int octave4 = 1;

	// functions to add to Bass NoteUtility
	public void initialise() {
		BassRepo[0] = new Note("E", 0);
		BassRepo[1] = new Note("F", 0);
		BassRepo[2] = new Note("F", 1);
		BassRepo[3] = new Note("G", 0);
		BassRepo[4] = new Note("G", 1);
		BassRepo[5] = new Note("A", 0);
		BassRepo[6] = new Note("A", 1);
		BassRepo[7] = new Note("B", 0);
		BassRepo[8] = new Note("C", 0);
		BassRepo[9] = new Note("C", 1);
		BassRepo[10] = new Note("D", 0);
		BassRepo[11] = new Note("D", 1);

		// creation of the array of notes for Bass
		makeArray(counter4, octave4, 3, 4, BassRepo, BassNote); // line 4
		makeArray(counter3, octave3, 2, 3, BassRepo, BassNote); // line 3
		makeArray(counter2, octave2, 1, 2, BassRepo, BassNote); // line 2
		makeArray(counter1, octave1, 0, 1, BassRepo, BassNote); // line 1

	}

	public void makeArray(int counter, int octave, int arline, int line, Note[] BassRepo, Note[][] BassNote) {
		int repoCont;
		for (int i = 0; i < 25; i++) {
			repoCont = counter % 11;
			if (repoCont == 8) {
				octave++;
				BassNote[arline][i] = new Note(BassRepo[repoCont].getStep(), octave, i, line,
						BassRepo[repoCont].getAlter());
			} else {
				BassNote[arline][i] = new Note(BassRepo[repoCont].getStep(), octave, i, line,
						BassRepo[repoCont].getAlter());
			}
			counter++;
		}
	}

	public int getCounter1() {
		return counter1;
	}

	public void setCounter1(int counter1) {
		this.counter1 = counter1;
	}

	public int getOctave1() {
		return octave1;
	}

	public void setOctave1(int octave1) {
		this.octave1 = octave1;
	}

	public int getCounter2() {
		return counter2;
	}

	public void setCounter2(int counter2) {
		this.counter2 = counter2;
	}

	public int getOctave2() {
		return octave2;
	}

	public void setOctave2(int octave2) {
		this.octave2 = octave2;
	}

	public int getCounter3() {
		return counter3;
	}

	public void setCounter3(int counter3) {
		this.counter3 = counter3;
	}

	public int getOctave3() {
		return octave3;
	}

	public void setOctave3(int octave3) {
		this.octave3 = octave3;
	}

	public int getCounter4() {
		return counter4;
	}

	public void setCounter4(int counter4) {
		this.counter4 = counter4;
	}

	public int getOctave4() {
		return octave4;
	}

	public void setOctave4(int octave4) {
		this.octave4 = octave4;
	}

//	public void initialise() {
//		// for the G string on Bass
//		BassNote[0][0] = new Note("G", 2, 0, 1);
//		BassNote[0][1] = new Note("G", 2, 1, 1);
//		BassNote[0][2] = new Note("A", 2, 2, 1);
//		BassNote[0][3] = new Note("A", 2, 3, 1);
//		BassNote[0][4] = new Note("B", 2, 4, 1);
//		BassNote[0][5] = new Note("C", 2, 5, 1);
//		BassNote[0][6] = new Note("C", 3, 6, 1);
//		BassNote[0][7] = new Note("D", 3, 7, 1);
//		BassNote[0][8] = new Note("D", 3, 8, 1);
//		BassNote[0][9] = new Note("E", 3, 9, 1);
//		BassNote[0][10] = new Note("F", 3, 10, 1);
//		BassNote[0][11] = new Note("F", 3, 11, 1);
//		BassNote[0][12] = new Note("G", 3, 12, 1);
//
//		// for the D string on Bass
//
//		BassNote[1][0] = new Note("B", 2, 0, 2);
//		BassNote[1][1] = new Note("C", 2, 1, 2);
//		BassNote[1][2] = new Note("C", 2, 2, 2);
//		BassNote[1][3] = new Note("D", 2, 3, 2);
//		BassNote[1][4] = new Note("D", 2, 4, 2);
//		BassNote[1][5] = new Note("E", 2, 5, 2);
//		BassNote[1][6] = new Note("F", 2, 6, 2);
//		BassNote[1][7] = new Note("F", 2, 7, 2);
//		BassNote[1][8] = new Note("G", 2, 8, 2);
//		BassNote[1][9] = new Note("G", 2, 9, 2);
//		BassNote[1][10] = new Note("A", 3, 10, 2);
//		BassNote[1][11] = new Note("B", 3, 11, 2);
//		BassNote[1][12] = new Note("B", 3, 12, 2);
//
//		// for the A string on Bass
//
//		BassNote[2][0] = new Note("A", 1, 0, 3);
//		BassNote[2][1] = new Note("A", 1, 1, 3);
//		BassNote[2][2] = new Note("B", 1, 2, 3);
//		BassNote[2][3] = new Note("C", 2, 3, 3);
//		BassNote[2][4] = new Note("C", 2, 4, 3);
//		BassNote[2][5] = new Note("D", 2, 5, 3);
//		BassNote[2][6] = new Note("D", 2, 6, 3);
//		BassNote[2][7] = new Note("E", 2, 7, 3);
//		BassNote[2][8] = new Note("F", 2, 8, 3);
//		BassNote[2][9] = new Note("F", 2, 9, 3);
//		BassNote[2][10] = new Note("G", 2, 10, 3);
//		BassNote[2][11] = new Note("G", 2, 11, 3);
//		BassNote[2][12] = new Note("A", 2, 12, 3);
//
//		// for the E string on Bass
//
//		BassNote[3][0] = new Note("E", 1, 0, 4);
//		BassNote[3][1] = new Note("F", 1, 1, 4);
//		BassNote[3][2] = new Note("F", 1, 2, 4);
//		BassNote[3][3] = new Note("G", 1, 3, 4);
//		BassNote[3][4] = new Note("G", 1, 4, 4);
//		BassNote[3][5] = new Note("A", 1, 5, 4);
//		BassNote[3][6] = new Note("A", 1, 6, 4);
//		BassNote[3][7] = new Note("B", 1, 7, 4);
//		BassNote[3][8] = new Note("C", 2, 8, 4);
//		BassNote[3][9] = new Note("C", 2, 9, 4);
//		BassNote[3][10] = new Note("D", 2, 10, 4);
//		BassNote[3][11] = new Note("D", 2, 11, 4);
//		BassNote[3][12] = new Note("E", 2, 12, 4);
//	}
}
