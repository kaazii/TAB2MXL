package TAB2MXL;

import TAB2MXL.Note;

public class NoteUtility {

	public static Note[][] guitarNote = new Note[6][25];

	public static void initialise() {

		// for the E string on guitar
		guitarNote[0][0] = new Note("E", 4, 0, 1);
		guitarNote[0][1] = new Note("F", 4, 1, 1);
		guitarNote[0][2] = new Note("F", 4, 2, 1);
		guitarNote[0][3] = new Note("G", 4, 3, 1);
		guitarNote[0][4] = new Note("G", 4, 4, 1);
		guitarNote[0][5] = new Note("A", 4, 5, 1);
		guitarNote[0][6] = new Note("A", 4, 6, 1);
		guitarNote[0][7] = new Note("B", 4, 7, 1);
		guitarNote[0][8] = new Note("C", 5, 8, 1);
		guitarNote[0][9] = new Note("C", 5, 9, 1);
		guitarNote[0][10] = new Note("D", 5, 10, 1);
		guitarNote[0][11] = new Note("D", 5, 11, 1);
		guitarNote[0][12] = new Note("E", 5, 12, 1);
		guitarNote[0][13] = new Note("F", 5, 13, 1);
		guitarNote[0][14] = new Note("F", 5, 14, 1);
		guitarNote[0][15] = new Note("G", 5, 15, 1);
		guitarNote[0][16] = new Note("G", 5, 16, 1);
		guitarNote[0][17] = new Note("A", 5, 17, 1);
		guitarNote[0][18] = new Note("B", 5, 18, 1);
		guitarNote[0][19] = new Note("B", 5, 19, 1);
		guitarNote[0][20] = new Note("C", 6, 20, 1);
		guitarNote[0][21] = new Note("C", 6, 21, 1);
		guitarNote[0][22] = new Note("D", 6, 22, 1);
		guitarNote[0][23] = new Note("D", 6, 23, 1);
		guitarNote[0][24] = new Note("E", 6, 24, 1);

		// for the B string on guitar
		guitarNote[1][0] = new Note("B", 3, 0, 2);
		guitarNote[1][1] = new Note("C", 4, 1, 2);
		guitarNote[1][2] = new Note("C", 4, 2, 2);
		guitarNote[1][3] = new Note("D", 4, 3, 2);
		guitarNote[1][4] = new Note("D", 4, 4, 2);
		guitarNote[1][5] = new Note("E", 4, 5, 2);
		guitarNote[1][6] = new Note("F", 4, 6, 2);
		guitarNote[1][7] = new Note("F", 4, 7, 2);
		guitarNote[1][8] = new Note("G", 4, 8, 2);
		guitarNote[1][9] = new Note("G", 4, 9, 2);
		guitarNote[1][10] = new Note("A", 4, 10, 2);
		guitarNote[1][11] = new Note("B", 4, 11, 2);
		guitarNote[1][12] = new Note("B", 4, 12, 2);
		guitarNote[1][13] = new Note("C", 5, 13, 2);
		guitarNote[1][14] = new Note("C", 5, 14, 2);
		guitarNote[1][15] = new Note("D", 5, 15, 2);
		guitarNote[1][16] = new Note("D", 5, 16, 2);
		guitarNote[1][17] = new Note("E", 5, 17, 2);
		guitarNote[1][18] = new Note("F", 5, 18, 2);
		guitarNote[1][19] = new Note("F", 5, 19, 2);
		guitarNote[1][20] = new Note("G", 5, 20, 2);
		guitarNote[1][21] = new Note("G", 5, 21, 2);
		guitarNote[1][22] = new Note("A", 5, 22, 2);
		guitarNote[1][23] = new Note("B", 5, 23, 2);
		guitarNote[1][24] = new Note("B", 5, 24, 2);

		// for the G string on guitar
		guitarNote[2][0] = new Note("G", 3, 0, 3);
		guitarNote[2][1] = new Note("G", 3, 1, 3);
		guitarNote[2][2] = new Note("A", 3, 2, 3);
		guitarNote[2][3] = new Note("B", 3, 3, 3);
		guitarNote[2][4] = new Note("B", 3, 4, 3);
		guitarNote[2][5] = new Note("C", 4, 5, 3);
		guitarNote[2][6] = new Note("C", 4, 6, 3);
		guitarNote[2][7] = new Note("D", 4, 7, 3);
		guitarNote[2][8] = new Note("D", 4, 8, 3);
		guitarNote[2][9] = new Note("E", 4, 9, 3);
		guitarNote[2][10] = new Note("F", 4, 10, 3);
		guitarNote[2][11] = new Note("F", 4, 11, 3);
		guitarNote[2][12] = new Note("G", 4, 12, 3);
		guitarNote[2][13] = new Note("G", 4, 13, 3);
		guitarNote[2][14] = new Note("A", 4, 14, 3);
		guitarNote[2][15] = new Note("B", 4, 15, 3);
		guitarNote[2][16] = new Note("B", 4, 16, 3);
		guitarNote[2][17] = new Note("C", 5, 17, 3);
		guitarNote[2][18] = new Note("C", 5, 18, 3);
		guitarNote[2][19] = new Note("D", 5, 19, 3);
		guitarNote[2][20] = new Note("D", 5, 20, 3);
		guitarNote[2][21] = new Note("E", 5, 21, 3);
		guitarNote[2][22] = new Note("F", 5, 22, 3);
		guitarNote[2][23] = new Note("F", 5, 23, 3);
		guitarNote[2][24] = new Note("G", 5, 24, 3);

		// for the D string on guitar

		guitarNote[3][0] = new Note("D", 3, 0, 4);
		guitarNote[3][1] = new Note("D", 3, 1, 4);
		guitarNote[3][2] = new Note("E", 3, 2, 4);
		guitarNote[3][3] = new Note("F", 3, 3, 4);
		guitarNote[3][4] = new Note("F", 3, 4, 4);
		guitarNote[3][5] = new Note("G", 3, 5, 4);
		guitarNote[3][6] = new Note("G", 3, 6, 4);
		guitarNote[3][7] = new Note("A", 3, 7, 4);
		guitarNote[3][8] = new Note("B", 3, 8, 4);
		guitarNote[3][9] = new Note("B", 3, 9, 4);
		guitarNote[3][10] = new Note("C", 4, 10, 4);
		guitarNote[3][11] = new Note("C", 4, 11, 4);
		guitarNote[3][12] = new Note("D", 4, 12, 4);
		guitarNote[3][13] = new Note("D", 4, 13, 4);
		guitarNote[3][14] = new Note("E", 4, 14, 4);
		guitarNote[3][15] = new Note("F", 4, 15, 4);
		guitarNote[3][16] = new Note("F", 4, 16, 4);
		guitarNote[3][17] = new Note("G", 4, 17, 4);
		guitarNote[3][18] = new Note("G", 4, 18, 4);
		guitarNote[3][19] = new Note("A", 4, 19, 4);
		guitarNote[3][20] = new Note("B", 4, 20, 4);
		guitarNote[3][21] = new Note("B", 4, 21, 4);
		guitarNote[3][22] = new Note("C", 5, 22, 4);
		guitarNote[3][23] = new Note("C", 5, 23, 4);
		guitarNote[3][24] = new Note("D", 5, 24, 4);

		// for the A string on guitar

		guitarNote[4][0] = new Note("A", 2, 0, 5);
		guitarNote[4][1] = new Note("B", 2, 1, 5);
		guitarNote[4][2] = new Note("B", 2, 2, 5);
		guitarNote[4][3] = new Note("C", 3, 3, 5);
		guitarNote[4][4] = new Note("C", 3, 4, 5);
		guitarNote[4][5] = new Note("D", 3, 5, 5);
		guitarNote[4][6] = new Note("D", 3, 6, 5);
		guitarNote[4][7] = new Note("E", 3, 7, 5);
		guitarNote[4][8] = new Note("F", 3, 8, 5);
		guitarNote[4][9] = new Note("F", 3, 9, 5);
		guitarNote[4][10] = new Note("G", 3, 10, 5);
		guitarNote[4][11] = new Note("G", 3, 11, 5);
		guitarNote[4][12] = new Note("A", 3, 12, 5);
		guitarNote[4][13] = new Note("B", 3, 13, 5);
		guitarNote[4][14] = new Note("B", 3, 14, 5);
		guitarNote[4][15] = new Note("C", 4, 15, 5);
		guitarNote[4][16] = new Note("C", 4, 16, 5);
		guitarNote[4][17] = new Note("D", 4, 17, 5);
		guitarNote[4][18] = new Note("D", 4, 18, 5);
		guitarNote[4][19] = new Note("E", 4, 19, 5);
		guitarNote[4][20] = new Note("F", 4, 20, 5);
		guitarNote[4][21] = new Note("F", 4, 21, 5);
		guitarNote[4][22] = new Note("G", 4, 22, 5);
		guitarNote[4][23] = new Note("G", 4, 23, 5);
		guitarNote[4][24] = new Note("A", 4, 24, 5);

		// for the E string on guitar
		guitarNote[5][0] = new Note("E", 2, 0, 6);
		guitarNote[5][1] = new Note("F", 2, 1, 6);
		guitarNote[5][2] = new Note("F", 2, 2, 6);
		guitarNote[5][3] = new Note("G", 2, 3, 6);
		guitarNote[5][4] = new Note("G", 2, 4, 6);
		guitarNote[5][5] = new Note("A", 2, 5, 6);
		guitarNote[5][6] = new Note("B", 2, 6, 6);
		guitarNote[5][7] = new Note("B", 2, 7, 6);
		guitarNote[5][8] = new Note("C", 3, 8, 6);
		guitarNote[5][9] = new Note("C", 3, 9, 6);
		guitarNote[5][10] = new Note("D", 3, 10, 6);
		guitarNote[5][11] = new Note("D", 3, 11, 6);
		guitarNote[5][12] = new Note("E", 3, 12, 6);
		guitarNote[5][13] = new Note("F", 3, 13, 6);
		guitarNote[5][14] = new Note("F", 3, 14, 6);
		guitarNote[5][15] = new Note("G", 3, 15, 6);
		guitarNote[5][16] = new Note("G", 3, 16, 6);
		guitarNote[5][17] = new Note("A", 3, 17, 6);
		guitarNote[5][18] = new Note("B", 3, 18, 6);
		guitarNote[5][19] = new Note("B", 3, 19, 6);
		guitarNote[5][20] = new Note("C", 4, 20, 6);
		guitarNote[5][21] = new Note("C", 4, 21, 6);
		guitarNote[5][22] = new Note("D", 4, 22, 6);
		guitarNote[5][23] = new Note("D", 4, 23, 6);
		guitarNote[5][24] = new Note("E", 4, 24, 6);
	}
}