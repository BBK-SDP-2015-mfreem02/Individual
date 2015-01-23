/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sml;
import java.util.ArrayList;

/**
 *
 * @author mike
 */
public class BNZInstruction extends Instruction{
    private int register;
    private String destLabel;
    public BNZInstruction(String l, String op){
        super(l, op);
    }
    
    public BNZInstruction(String l, int reg, String dlab){
        this(l, "bnz");
        register = reg;
        destLabel = dlab;
    }
    
    @Override
    public void execute(Machine m){
        //counter used to keep track of loop
        int programCounter = 0;
        
        //array list to iterate through
        ArrayList<Instruction>  program = m.getProg();
        
        if(m.getRegisters().getRegister(register) != 0){
            for(Instruction ins : program){
                if(ins.getLabel().equals(destLabel)){
                    m.setPc(programCounter);
                    break;
                }
                programCounter++;
            }
        }
    }
    
    @Override
    public String toString(){
        return super.toString() + " " + register + "repeat instruction from " + destLabel;
    }
    
}
