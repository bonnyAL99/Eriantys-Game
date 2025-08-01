package it.polimi.ingsw.Server.ServerToClient;

import it.polimi.ingsw.Client.View;
import it.polimi.ingsw.Model.Color;
import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;

/**
 * implementation of a message from server to client to set up the character cards and eventual students on them
 * @author Federica Tommasini
 */
public class SetUpCharacterCard implements ServerToClientMessage{

    private final String[] characterCards;
    private EnumMap<Color, Integer> firstCardStudents;

    private EnumMap<Color, Integer> secondCardStudents;

    private EnumMap<Color, Integer> thirdCardStudents;

    public SetUpCharacterCard(String[] characterCards,HashMap<String,Integer> mapCostCard) {
        this.characterCards = characterCards;
    }

    public void setFirstCardStudents(EnumMap<Color, Integer> firstCardStudents) {
        this.firstCardStudents = firstCardStudents;
    }

    public void setSecondCardStudents(EnumMap<Color, Integer> secondCardStudents) {
        this.secondCardStudents = secondCardStudents;
    }

    public void setThirdCardStudents(EnumMap<Color, Integer> thirdCardStudents) {
        this.thirdCardStudents = thirdCardStudents;
    }

    public String[] getCharacterCards() {
        return characterCards;
    }

    public EnumMap<Color, Integer> getFirstCardStudents() {
        return firstCardStudents;
    }

    public EnumMap<Color, Integer> getSecondCardStudents() {
        return secondCardStudents;
    }

    public EnumMap<Color, Integer> getThirdCardStudents() {
        return thirdCardStudents;
    }

    @Override
    public void handle(View view) throws IOException {
        view.setUpCharacterCard(this);
    }
}
