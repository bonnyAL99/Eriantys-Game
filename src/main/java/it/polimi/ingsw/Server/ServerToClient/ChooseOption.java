package it.polimi.ingsw.Server.ServerToClient;

import it.polimi.ingsw.Client.View;

/**
 * implementation of a message from server to client to ask the client to choose the next action to make
 * @author Federica Tommasini, Giuseppe Bonanno
 */
public class ChooseOption implements ServerToClientMessage{

    private final boolean    expertMode;
    private String     msg;
    private final OptionType type;

    public ChooseOption(OptionType type,boolean expertMode){
        this.type=type;
        this.expertMode=expertMode;
        switch (type){
            case MOVESTUDENTS:
                    msg="Move student \n";
                    break;
            case MOVENATURE:
                    msg="Move mother nature \n";
                break;
            case CHOOSECLOUD:
                msg="Choose the number of the cloud to take the students: \n";
                break;

        }
    }

    public String getMsg() {
        return msg;
    }

    public OptionType getType() {
        return type;
    }

    public void handle(View view){
        view.chooseOption(this);
    }

    public boolean isExpertMode() {return expertMode;}

}
