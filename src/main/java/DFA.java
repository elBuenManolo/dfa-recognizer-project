
/*A class that models DFAs. States are just strings while labels
 * are characters. 
 * We shall specify DFAs without the requirement that the transition 
 * function is total. In other words, we will let the programmer specify 
 * DFAs with transitions, then assume those transitions all go to a trap state. 
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DFA {

    private final Set<String> states;
    private final Set <String> finalStates;
    private String initialState;
    private final Map<String, Map<Character, String>> transitions;
    /*A constructor that builds a DFA with the set of state names 
     * given as arguments. 
     */
    public DFA(String[] states) {
        if (states == null || states.length == 0) {
            throw new IllegalArgumentException("Cal almenys un estat.");
        }

        this.states = new HashSet<>();
        this.finalStates = new HashSet<>();
        this.transitions = new HashMap<>();

        for (String s : states) {
            if (s == null || s.isBlank()) {
                throw new IllegalArgumentException("Nom d'estat invàlid.");
            }
            if (!this.states.add(s)) {
                throw new IllegalArgumentException("Estat duplicat: " + s);
            }
            this.transitions.put(s, new HashMap<>());
        }

    }

    /*Establish the initial state  */
    public void setInitialState(String state) {
        if (!states.contains(state)) {
            throw new IllegalArgumentException("L'estat inicial no existeix.");
        }
        this.initialState = state;
    }

    /*Mark a state in the DFA as final*/
    public void addFinalState(String state) {
        if (!states.contains(state)) {
            throw new IllegalArgumentException("L'estat final no existeix: " + state);
        }
        finalStates.add(state);
    }

    // Method that adds a transition. 
    public void addTransition(String state, Character input, String nextState) {
        if (!states.contains(state) || !states.contains(nextState)) {
            throw new IllegalArgumentException("Estat no vàlid en la transició.");
        }

        transitions.get(state).put(input, nextState);
    }

    /*Given an input string, this method outputs true if the DFA accepts it.
     * Otherwise it outputs false. 
     */
    public boolean accept(String input) {
        if (initialState == null) {
            throw new IllegalStateException("No s'ha definit estat inicial.");
        }

        String current = initialState;

        for (char symbol : input.toCharArray()) {
            String next = transitions.get(current).get(symbol);
            if (next==null) return false;
            current=next;
        }

        return finalStates.contains(current);
    }

}