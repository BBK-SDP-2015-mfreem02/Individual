package sml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import java.lang.Class;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 */
public class Translator {

	// word + line is the part of the current line that's not yet processed
	// word has no whitespace
	// If word and line are not empty, line begins with whitespace
	private String line = "";
	private Labels labels; // The labels of the program being translated
	private ArrayList<Instruction> program; // The program to be created
	private String fileName; // source file of SML code

	private static final String SRC = "src";

	public Translator(String fileName) {
		this.fileName = SRC + "/" + fileName;
	}

	// translate the small program in the file into lab (the labels) and
	// prog (the program)
	// return "no errors were detected"
	public boolean readAndTranslate(Labels lab, ArrayList<Instruction> prog) {

		try (Scanner sc = new Scanner(new File(fileName))) {
			// Scanner attached to the file chosen by the user
			labels = lab;
			labels.reset();
			program = prog;
			program.clear();

			try {
				line = sc.nextLine();
			} catch (NoSuchElementException ioE) {
				return false;
			}

			// Each iteration processes line and reads the next line into line
			while (line != null) {
				// Store the label in label
				String label = scan();

				if (label.length() > 0) {
					Instruction ins = getInstruction(label);
					if (ins != null) {
						labels.addLabel(label);
						program.add(ins);
					}
				}

				try {
					line = sc.nextLine();
				} catch (NoSuchElementException ioE) {
					return false;
				}
			}
		} catch (IOException ioE) {
			System.out.println("File: IO error " + ioE.getMessage());
			return false;
		}
		return true;
	}

	// line should consist of an MML instruction, with its label already
	// removed. Translate line into an instruction with label label
	// and return the instruction
	public Instruction getInstruction(String label) {
		int s1; // Possible operands of the instruction
		int s2;
		int r;

                String destLabel;

		if (line.equals(""))
			return null;

		String ins = scan();
                
                //classes used to get the constructors
                Class cI = int.class;
                Class cS = String.class;
                Class c;
                
                //Building the parameters used for the getclass forname method
                String className = "sml." + ins.toUpperCase().charAt(0) + ins.substring(1) + "Instruction";

                try{
                    c = Class.forName(className);
                    if(ins.equals("bnz")) {
                        r = scanInt();
                        destLabel = scan();
                        //return null if either of these values are not populated
                        if(r == Integer.MAX_VALUE || destLabel.equals("")) {
                            return null;
                        } else {
                            Constructor con = c.getConstructor(cS, cI, cS);
                            return (Instruction)con.newInstance(label, r, destLabel);
                        }
                    } else {
                        r = scanInt();
                        s1 = scanInt();
                        s2 = scanInt();
                        
                        //If all parameters are populated - build the Constructor the instruction for ADD/MULT/DIV/SUB 
                        if (r != Integer.MAX_VALUE && s1 != Integer.MAX_VALUE && s2 != Integer.MAX_VALUE) {
                            
                            Constructor con = c.getConstructor(cS, cI, cI, cI);
                            return (Instruction)con.newInstance(label, r, s1, s2);
                            
                        //if register and value operands are populated - Build the Constructor and return the LIN Instruction
                        } else if (r != Integer.MAX_VALUE && s1 != Integer.MAX_VALUE) {
                            
                            Constructor con = c.getConstructor(cS, cI, cI);
                            return (Instruction)con.newInstance(label, r, s1);
                            
                        //if only the register is populated - Build the Constructor and the return the OUT Instruction  
                        } else if (r != Integer.MAX_VALUE) {
                            
                            Constructor con = c.getConstructor(cS, cI);
                            return (Instruction)con.newInstance(label, r);
                            
                        } else {
                            return null;
                        }
                    }
                }catch (NoSuchMethodException | InstantiationException| IllegalAccessException| InvocationTargetException | ClassNotFoundException ex) {
                    System.out.println("Exception caught : " + ex.getMessage());
                    return null;
                }
}

	/*
	 * Return the first word of line and remove it from line. If there is no
	 * word, return ""
	 */
	private String scan() {
		line = line.trim();
		if (line.length() == 0)
			return "";

		int i = 0;
		while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
			i = i + 1;
		}
		String word = line.substring(0, i);
		line = line.substring(i);
		return word;
	}

	// Return the first word of line as an integer. If there is
	// any error, return the maximum int
	private int scanInt() {
		String word = scan();
		if (word.length() == 0) {
			return Integer.MAX_VALUE;
		}

		try {
			return Integer.parseInt(word);
		} catch (NumberFormatException e) {
			return Integer.MAX_VALUE;
		}
	}
}