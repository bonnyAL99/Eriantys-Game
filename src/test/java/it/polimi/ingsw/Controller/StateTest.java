package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Controller.State.*;
import it.polimi.ingsw.Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class StateTest {

    private GameControllerState state;
    private GameController gc;
    private Action a=new Action();


    public void MoveMotherNatureTurnAction(){
        gc.setState(new MoveMotherNatureState());
        int oldMotherNaturePosition=gc.getModel().getMotherNaturePosition();
        /*Random rand = new Random();
        int cardNumber1 = rand.nextInt(gc.getModel().getPlayerByID(1).getAssistantCards().size());
        int cardNumber2 = rand.nextInt(gc.getModel().getPlayerByID(2).getAssistantCards().size());

        AssistantCard card1=gc.getModel().getPlayerByID(1).getAssistantCards().get(cardNumber1);
        AssistantCard card2=gc.getModel().getPlayerByID(2).getAssistantCards().get(cardNumber2);
        gc.addCurrentAssistantCard(1,card1);
        gc.addCurrentAssistantCard(2,card2);
*/
        Action action=new Action();
        gc.getModel().setCurrentPlayer(1);
        action.setMotherNatureSteps(gc.getCurrentCardPlayers().get(1).getMothernaturesteps());
        gc.getState().turnAction(gc,action);
        assertEquals(gc.getModel().getMotherNaturePosition(),(oldMotherNaturePosition+gc.getCurrentCardPlayers().get(1).getMothernaturesteps())
                %gc.getModel().getIslandSize());
        oldMotherNaturePosition=gc.getModel().getMotherNaturePosition();
        gc.getModel().setCurrentPlayer(2);
        gc.getModel().setTwoAdditionalSteps(true);
        action.setMotherNatureSteps(gc.getCurrentCardPlayers().get(2).getMothernaturesteps()+2);
        gc.getState().turnAction(gc,action);
        assertEquals(gc.getModel().getMotherNaturePosition(),
                (oldMotherNaturePosition+gc.getCurrentCardPlayers().get(2).getMothernaturesteps()+2)%gc.getModel().getIslandSize());
    }

    @BeforeEach
    public void setUp(){
        gc=new GameController(true,3);
        gc.getModel().addPlayer(1,"player1");
        gc.getModel().addPlayer(2,"player2");
        gc.getModel().addPlayer(3,"player3");
        this.gc.getModel().setCurrentPlayer(1);
    }

    @Test
    public void PlayCharactherCardTurnAction(){
        List<CharacterCard> cards=gc.getModel().getCharacterCards();
        Action action=new Action();
        EnumMap<Color,Integer> entrance=new EnumMap<Color, Integer>(Color.class);
        EnumMap<Color,Integer> choosen=new EnumMap<Color, Integer>(Color.class);
        EnumMap<Color,Integer> cardStudents=new EnumMap<Color, Integer>(Color.class);
        Integer posisland;
        Color color;
        state=new PlayCharacterCardState();
        cards.remove(2);
        CharacterCard card= new CharacterCard("thief.jpg");
        cards.add(card);
        gc.getModel().getCharactersPositions().put("thief.jpg",2);
        for(Player p:this.gc.getModel().getPlayers()){
            for(int i=0;i<15;i++){
                p.addMoney();
            }
        }

        action.setChosenColor(Color.RED);
        action.setAsset("thief.jpg");
        int [] studentsRed=new int[3];
        int num=0;
        for(Player p: gc.getModel().getPlayers())
        {
            studentsRed[num]=p.getStudentsOf(Color.RED);
            num++;
        }
        state.turnAction(gc,action);
        num=0;
        for(Player p: gc.getModel().getPlayers())
        {
            if(studentsRed[num]>3)
                assertEquals(p.getStudentsOf(Color.RED),studentsRed[num]-3);
            else
                assertEquals(p.getStudentsOf(Color.RED),0);
            num++;
        }

        cardStudents.put(Color.RED,4);
        choosen.put(Color.RED,1);
        card = new CharacterCard("innkeeper.jpg",cardStudents);
        cards.remove(2);
        cards.add(card);
        gc.getModel().getCharactersPositions().put("innkeeper.jpg",2);
        action=new Action();
        action.setAsset("innkeeper.jpg");
        action.setChosenStudents(choosen);
        action.setPosIsland(1);
        EnumMap<Color,Integer> islandStudents=new EnumMap<Color, Integer>(Color.class);
        islandStudents.put(Color.GREEN,1);
        gc.getModel().getIslandByPosition(1).setStudents(islandStudents);
        state.turnAction(gc,action);
        assertEquals(gc.getModel().getIslandByPosition(1).getStudentsOf(Color.RED),1);


    }
    @Test
    public void DecideFirstPlayerTurnAction() {
        boolean assertBoolean=false;

        gc.addCurrentAssistantCard(1,new AssistantCard(1,1,"Turtle","turtle.png"));
        gc.addCurrentAssistantCard(2,new AssistantCard(8,4,"Cat","cat.png"));
        gc.addCurrentAssistantCard(3,new AssistantCard(8,4,"Cat","cat.png"));

        state=new DecideFirstPlayerState();
        gc.setFirstPlayer(0);
        try{
            state.turnAction(gc,new Action());
        }catch (Exception e){
            if(e instanceof IllegalArgumentException)
                assertBoolean=true;
            else
                assertBoolean=false;
        }
        assertTrue(assertBoolean);

        gc.addCurrentAssistantCard(3,new AssistantCard(5,3,"Lizard","lizard.png"));
        state=new DecideFirstPlayerState();
        gc.setFirstPlayer(0);
        state.turnAction(gc,new Action());
        Integer [] turnOrder=gc.getTurnOrder();
        assertTrue(turnOrder[0]==1);
        assertTrue(turnOrder[1]==3);
        assertTrue(turnOrder[2]==2);

        MoveMotherNatureTurnAction();
    }


    @Test
    public void MoveStudentTurnAction(){
        this.gc.getModel().getIslandByPosition(1).setTower(this.gc.getModel().getPlayerTower(1));
        this.gc.getModel().getIslandByPosition(2).setTower(this.gc.getModel().getPlayerTower(1));
        EnumMap<Color,Integer> entry=new EnumMap<Color, Integer>(Color.class);
        entry.put(Color.BLUE,2);
        entry.put(Color.PINK,3);
        entry.put(Color.YELLOW,2);
        entry.put(Color.RED,1);
        entry.put(Color.GREEN,1);
        this.gc.getModel().getPlayerByID(1).setEntryStudents(entry);
        state=new MoveStudentsState();
        a.setMove(MoveTo.ISLAND);
        a.setPosIsland(1);
        a.setColorStudent(Color.PINK);
        state.turnAction(gc,a);

        a.setMove(MoveTo.ISLAND);
        a.setPosIsland(2);
        a.setColorStudent(Color.PINK);
        state.turnAction(gc,a);

        a.setMove(MoveTo.SCHOOL);
        a.setColorStudent(Color.BLUE);
        state.turnAction(gc,a);
        a.setMove(MoveTo.SCHOOL);
        a.setColorStudent(Color.PINK);
        state.turnAction(gc,a);

        System.out.println("Students "+this.gc.getModel().getPlayerByID(1).getEntryStudents());
        System.out.println(gc.getModel().getPlayerByID(1).getStudents());

        TakeStudentTurnAction();
    }


    public void TakeStudentTurnAction() {
        state=new TakeStudentsState();
        this.gc.setState(state);
        a.setChooseCloud(1);
        this.gc.getModel().getStudentsFromBag();
        state.turnAction(gc,a);
        assertFalse(gc.getModel().areStudentsOnCloud(1));

    }


}
