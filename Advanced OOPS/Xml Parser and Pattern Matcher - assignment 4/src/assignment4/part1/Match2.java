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
public class Match2 {
    PatternMatchChain matchChain;
    CreateObjects co = new CreateObjects();

    Match2(String pattern)
    {
        PatternMatchChain matchChain = co.chainOfObjects(pattern,0);
        this.matchChain = matchChain;

        if(pattern.length() > 0)
        {
            for (int i = 1; i < pattern.length(); i++)
            {
            PatternMatchChain next = co.chainOfObjects(pattern, i);
            matchChain.setNextChain(next);
            matchChain = next;
            }
        }
        else
        System.out.print("Please Enter Some Pattern to search");
    }

    int findFirstIn(String literal) {
        for(int i = 0; i < literal.length(); i++) {
            if (matchChain.validateCharacter(literal, i)){
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) throws Exception {
        Match match = new Match("u*t");
        System.out.print(match.findFirstIn("cacacat"));
    }
}
