package sml;


/**
 * This class extends AddInstruction
 * 
 * @author Michael Freeman
 */

public class DivInstruction extends AddInstruction {

	public DivInstruction(String label, int result, int op1, int op2) {
            super(label, "div");
            this.setSymbol('/');
            setResult(result);
            setOp1(op1);
            setOp2(op2);
	}

	@Override
	public void execute(Machine m) {
		int value1 = m.getRegisters().getRegister(this.getOp1());
		int value2 = m.getRegisters().getRegister(this.getOp2());
		m.getRegisters().setRegister(this.getResult(), value1 / value2);
	}

}