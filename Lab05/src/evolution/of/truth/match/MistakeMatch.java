package evolution.of.truth.match;

import evolution.of.truth.agent.Agent;

public class MistakeMatch extends Match {
    public MistakeMatch (Agent agentA, Agent agentB){
        super(agentA, agentB);
    }

    @Override
    public void playGame() {
        int choiceA = agentA.choice(previousChoiceB);
        int choiceB = agentB.choice(previousChoiceA);

        if(Math.random() < 0.05)
            choiceA = 1 - choiceA;

        if(Math.random() < 0.05)
            choiceB = 1 - choiceB;

        agentA.setScore(agentA.getScore() + ruleMatrix[choiceA][choiceB][0]);
        agentB.setScore(agentB.getScore() + ruleMatrix[choiceA][choiceB][1]);
        previousChoiceA = choiceA;
        previousChoiceB = choiceB;
    }
}
