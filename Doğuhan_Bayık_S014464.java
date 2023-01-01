import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.io.IOException;
import java.util.Arrays;

public class Doğuhan_Bayık_S014464 {

    private List<String> stackAlphabet;
    private String startingSymbol;
    private List<String> inputAlphabet;
    private int numStates;
    private List<String> states;
    private String startState;
    private String acceptState;
    private String rejectState;
    private List<TransitionRule> transitionRules;
    private String string;
    private List<String> tape;
    private int headPos;
    private String currentState;
    private List<String> visitedStates;

    public static void main(String[] args) throws IOException {

        Doğuhan_Bayık_S014464 machine = parseInputFile("C:\\Users\\MONSTER\\Desktop\\bitirme\\demo\\src\\Input_Doğuhan_Bayık_S014464.txt");
        machine.run();

    }

    public Doğuhan_Bayık_S014464(List<String> stackAlphabet, String startingSymbol, List<String> inputAlphabet, int numStates,
                                 List<String> states, String startState, String acceptState, String rejectState,
                                 List<TransitionRule> transitionRules, String string) {
        this.stackAlphabet = stackAlphabet;
        this.startingSymbol = startingSymbol;
        this.inputAlphabet = inputAlphabet;
        this.numStates = numStates;
        this.states = states;
        this.startState = startState;
        this.acceptState = acceptState;
        this.rejectState = rejectState;
        this.transitionRules = transitionRules;
        this.string = string;
        this.tape = new ArrayList<>(string.length());
        for (int i = 0; i < string.length(); i++) {
            this.tape.add(string.substring(i, i + 1));
        }
        this.headPos = 0;
        this.currentState = startState;
        this.visitedStates = new ArrayList<>();
    }

    public static Doğuhan_Bayık_S014464 parseInputFile(String inputFilePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));

        // Parse the input file and extract the necessary information
        int numStackAlphabet = Integer.parseInt(reader.readLine().trim());
        List<String> stackAlphabet = Arrays.asList(reader.readLine().trim().split(" "));
        int numInputAlphabet = Integer.parseInt(reader.readLine().trim());
        List<String> inputAlphabet = Arrays.asList(reader.readLine().trim().split(" "));
        String startingSymbol = reader.readLine().trim();
        int numStates = Integer.parseInt(reader.readLine().trim());
        List<String> states = Arrays.asList(reader.readLine().trim().split(" "));
        String startState = reader.readLine().trim();
        String acceptState = reader.readLine().trim();
        String rejectState = reader.readLine().trim();
        List<TransitionRule> transitionRules = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            String[] line = reader.readLine().trim().split(" ");
            transitionRules.add(new TransitionRule(line[0], line[1], line[2],
                    line[3].equals("L") ? Direction.LEFT : Direction.RIGHT, line[4]));
        }
        String string = reader.readLine().trim();

        reader.close();

        return new Doğuhan_Bayık_S014464(stackAlphabet, startingSymbol, inputAlphabet, numStates, states, startState,
                acceptState, rejectState, transitionRules, string);
    }


    public void run() {
        while (true) {
            visitedStates.add(currentState);

            TransitionRule matchingRule = null;
            for (TransitionRule rule : transitionRules) {
                if (rule.getCurrentState().equals(currentState) && rule.getCurrentSymbol().equals(tape.get(headPos))) {
                    matchingRule = rule;
                    break;
                }
            }

            if (matchingRule == null) {
                System.out.println("LOOPED: " + visitedStates);
                return;
            }

            currentState = matchingRule.getNextState();
            tape.set(headPos, matchingRule.getNextSymbol());
            if (matchingRule.getDirection() == Direction.LEFT) {
                headPos--;
                if (headPos < 0) {
                    System.out.println("REJECTED: " + visitedStates);
                    return;
                }
            } else {
                headPos++;
                if (headPos >= tape.size()) {
                    tape.add(startingSymbol);
                }
            }

            if (currentState.equals(acceptState)) {
                System.out.println("ACCEPTED: " + visitedStates);
                return;
            } else if (currentState.equals(rejectState)) {
                System.out.println("REJECTED: " + visitedStates);
                return;
            }
        }
    }
}

enum Direction {
    LEFT, RIGHT
}

class TransitionRule {
    private String currentState;
    private String currentSymbol;
    private String nextSymbol;
    private Direction direction;
    private String nextState;

    public TransitionRule(String currentState, String currentSymbol, String nextSymbol, Direction direction,
                          String nextState) {
        this.currentState = currentState;
        this.currentSymbol = currentSymbol;
        this.nextSymbol = nextSymbol;
        this.direction = direction;
        this.nextState = nextState;
    }

    public String getCurrentState() {
        return currentState;
    }

    public String getCurrentSymbol() {
        return currentSymbol;
    }

    public String getNextSymbol() {
        return nextSymbol;
    }

    public Direction getDirection() {
        return direction;
    }

    public String getNextState() {
        return nextState;
    }
}
