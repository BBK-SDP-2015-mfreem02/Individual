package sml;


/**
 * This class ....
 * 
 * @author someone
 */

public class MulInstruction extends AddInstruction {

	public MulInstruction(String label, int result, int op1, int op2) {
            super(label, result, op1, op2);
	}

	@Override
	public void execute(Machine m) {
		int value1 = m.getRegisters().getRegister(this.getOp1());
		int value2 = m.getRegisters().getRegister(this.getOp2());
		m.getRegisters().setRegister(this.getResult(), value1 * value2);
	}

	@Override
	public String toString() {
		return label + ": " + opcode + " " + this.getOp1() + " * " + this.getOp2() + " to " + this.getResult();
	}
}