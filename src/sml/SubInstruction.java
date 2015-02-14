package sml;


/**
 * This class ....
 * 
 * @author someone
 */

public class SubInstruction extends AddInstruction {

	public SubInstruction(String label, int result, int op1, int op2) {
            super(label, "sub");
            this.setSymbol('-');
            setResult(result);
            setOp1(op1);
            setOp2(op2);
	}

	@Override
	public void execute(Machine m) {
		int value1 = m.getRegisters().getRegister(this.getOp1());
		int value2 = m.getRegisters().getRegister(this.getOp2());
		m.getRegisters().setRegister(this.getResult(), value1 - value2);
	}

}