/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment4.part1;

/**
 *
 * @author ayishwarya
 */
public interface PatternMatchChain {
    
    void setNextChain(PatternMatchChain nextChain);
    boolean validateCharacter(String targetString, int index);
    
    
}
