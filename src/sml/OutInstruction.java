/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sml;

/**
 *
 * @author mike
 */
public class OutInstruction extends Instruction {
    private int result;
    
    
    public OutInstruction(String l, String op){
        super(l, op);
    }
    
    public OutInstruction(String label, int reg, int s1, int s2){
        this(label, "out");
        this.result = reg;
    }
    
    @Override
    public void execute(Machine m){
        System.out.println(m.getRegisters().getRegister(result));
    }
    
    @Override
    public String toString(){
        return super.toString() + " " + result;
    }
    
}
