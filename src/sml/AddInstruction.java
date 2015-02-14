package sml;

import lombok.Data;

/**
 * This class extends instruction and becomes a base class for my other
 * mathematical instruction class
 * @author Michael Freeman
 */
@Data
public class AddInstruction extends Instruction {

	private int result;
	private int op1;
	private int op2;
        private char symbol;

	public AddInstruction(String label, String op) {
		super(label, op);
	}

	public AddInstruction(String label, int result, int op1, int op2) {
		this(label, "add");
                this.symbol = '+';
		this.result = result;
		this.op1 = op1;
		this.op2 = op2;
	}

	@Override
	public void execute(Machine m) {
		int value1 = m.getRegisters().getRegister(op1);
		int value2 = m.getRegisters().getRegister(op2);
		m.getRegisters().setRegister(result, value1 + value2);
	}

	@Override
	public String toString() {
		return label + ": " + opcode + " register " + op1 + " " + this.getSymbol() + " register " + op2 + " stored in register " + result;
	}
}
