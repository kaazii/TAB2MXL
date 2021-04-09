package TAB2MXL;

import TAB2MXL.Note;

public class NoteUtility {

	public  Note[][] guitarNote = new Note[6][25];
	public  Note[] guitarRepo  = new Note[12];
	
	public int counter1=0;
	public int octave1=4;
	
	public int counter2=7;
	public int octave2=3;
	
	public int counter3=3;
	public int octave3=3;
	
	public int counter4=10;
	public int octave4=3;
	
	public int counter5=5;
	public int octave5=2;
	
	public int counter6=0;
	public int octave6=2;

	public void initialise() {
		guitarRepo [0] = new Note("E", 0);
		guitarRepo [1] = new Note("F", 0);
		guitarRepo [2] = new Note("F", 1);
		guitarRepo [3] = new Note("G", 0);
		guitarRepo [4] = new Note("G", 1);
		guitarRepo [5] = new Note("A", 0);
		guitarRepo [6] = new Note("A", 1);
		guitarRepo [7] = new Note("B", 0);
		guitarRepo [8] = new Note("C", 0);
		guitarRepo [9] = new Note("C", 1);
		guitarRepo [10] = new Note("D", 0);
		guitarRepo [11] = new Note("D", 1);
		
		//creation of the array of notes for guitar 
		makeArray(counter6,octave6,5,6,guitarRepo,guitarNote); //line 6
		makeArray(counter5,octave5,4,5,guitarRepo,guitarNote); //line 5
		makeArray(counter4,octave4,3,4,guitarRepo,guitarNote); //line 4
		makeArray(counter3,octave3,2,3,guitarRepo,guitarNote); //line 3
		makeArray(counter2,octave2,1,2,guitarRepo,guitarNote); //line 2
		makeArray(counter1,octave1,0,1,guitarRepo,guitarNote); //line 1
		
		
		
	}
	
	public void makeArray(int counter,int octave,int arline,int line,Note[] guitarRepo,Note[][] guitarNote) {
		int repoCont;
		for(int i=0;i<25;i++) {
		repoCont=counter%11;
		if(repoCont==8) {
			octave++;
			guitarNote[arline][i] = new Note(guitarRepo[repoCont].getStep(), octave, i, line,guitarRepo[repoCont].getAlter());
		}
		else {
			guitarNote[arline][i] = new Note(guitarRepo[repoCont].getStep(), octave, i, line,guitarRepo[repoCont].getAlter());
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
	

	public int getCounter5() {
		return counter5;
	}
	

	public void setCounter5(int counter5) {
		this.counter5 = counter5;
	}
	

	public int getOctave5() {
		return octave5;
	}
	

	public void setOctave5(int octave5) {
		this.octave5 = octave5;
	}
	

	public int getCounter6() {
		return counter6;
	}
	

	public void setCounter6(int counter6) {
		this.counter6 = counter6;
	}
	

	public int getOctave6() {
		return octave6;
	}
	

	public void setOctave6(int octave6) {
		this.octave6 = octave6;
	}
}