package TAB2MXL;

import java.util.ArrayList;

public class GraceNote {

	public ArrayList <Note> noteList;
	public String [] complexTypeList; //g, h, p, s
	
	public int relativeIndex;

	public GraceNote (ArrayList<Note> noteList, String [] complexTypeList, int relativeIndex) {
		this.noteList = noteList;
		this.complexTypeList = complexTypeList;
		this.relativeIndex = relativeIndex;
	}
}