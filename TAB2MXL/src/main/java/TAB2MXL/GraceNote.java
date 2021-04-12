package TAB2MXL;

import java.util.ArrayList;

public class GraceNote {

	public ArrayList <Note> noteList;
	public String [] complexTypeList; //g, h, p, s

	public GraceNote (ArrayList<Note> noteList, String [] complexTypeList) {
		this.noteList = noteList;
		this.complexTypeList = complexTypeList;
	}
	
	public GraceNote() {
		this.noteList = new ArrayList<Note>();
		this.complexTypeList = null;
	}
}