package fourRow_extension;

import java.util.Random;

public class TeamManager {

    private Team onTurn = Team.RED;

    public Team randomTeam() {
        int lucky_number = new Random().nextInt() % 2;
        if(lucky_number == 0)
            return Team.RED;
        return Team.BLUE;
    }


    public void swapTeam() {
        if(getTeamOnTurn() == Team.RED)
            setTeamOnTurn(Team.BLUE);
        else
            setTeamOnTurn(Team.RED);
    }


    /**
     * @return Either "red" or "blue".
     */
    public String teamToString(){
        if(onTurn == Team.BLUE)
            return "blue";
        return "red";
    }



    //------------------------------------ getter / setter -----------------------------------------

    public Team getTeamOnTurn(){
        return onTurn;
    }

    public void setTeamOnTurn(Team team){
        onTurn = team;
    }
}
