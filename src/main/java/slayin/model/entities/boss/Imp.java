package slayin.model.entities.boss;

import slayin.model.World;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.events.GameEventListener;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

import java.util.ArrayList;
import java.util.Random;

public class Imp extends Boss {

    public static enum State { START, ATTACK, WAITING, INVISIBLE, HITTED }
    private State state;
    private int numShots;
    private int shotsFired;
    private boolean posFlag;
    private ArrayList<P2d> positions = new ArrayList<>();
    private double counter;

    public Imp(P2d pos, BoundingBoxImplRet boundingBox, World world, GameEventListener eventListener) {
        super(new P2d(
                world.getWidth()/2,
                world.getHeight()/2 //first position in the centre of the screen
            ), 
            new Vector2d(0,0), //Imp teleport in 4 different position
            boundingBox, 
            world,
            eventListener);

        this.positions.add(new P2d(
            world.getWidth()-(boundingBox.getWidth()/2), //right
            world.getGround()-(boundingBox.getHeight()/2)//ground height
        ));

        this.positions.add(new P2d(
            (boundingBox.getWidth()/2), //left
            world.getGround()-(boundingBox.getHeight()/2)//ground height
        ));

        this.positions.add(new P2d(
            world.getWidth()-(boundingBox.getWidth()/2), //right
            230                                        //max height
        ));

        this.positions.add(new P2d(
            (boundingBox.getWidth()/2), //left
            230                                        //max height
        ));

        this.setHealth(10); //The Imp must receive 10 hits to be defeated
        this.setNumShots(0);//Imp attacks after first hit
        this.setShotsFired(0);

        this.getBoundingBox().updatePoint(this.getPos());//set bounding box position

        this.changeState(State.START); //initial Imp state
        this.posFlag=false;

        this.setDir(Direction.LEFT); //initial direction (only for drawing correctly image)
    }

    @Override
    public DrawComponent getDrawComponent(){
        return DrawComponentFactory.graphicsComponentImp(this);
    }

    @Override
    public void updatePos(int dt){
        switch(this.state) {
            case START:
                if(posFlag){
                    //reset flag
                    this.posFlag=false;
                    
                    //reset shots fired
                    this.setShotsFired(0);

                    //reset counter
                    this.counter=0;
                }
                //spawn in a casual position, wait 5 seconds then attacks
                if(this.secondDifference(2.0)){
                    changeState(State.ATTACK);
                }
                break;
            case ATTACK:
                if(this.secondDifference(3.0)){
                    this.changeState(State.WAITING);
                }
                //every second shoots a ball
                if(this.secondDifference(counter)){
                    if(this.numShots!=0){
                        //TODO: attacca -> spawn palla
                        if(this.getShotsFired()<this.getNumShots()){//check if it can shoot
                            this.setShotsFired(this.getShotsFired()+1);//update shotsFired+1
                        }
                    }
                    this.counter++;//update counter
                    System.out.println("Colpi lanciati: "+this.getShotsFired());
                    System.out.println("Counter: "+this.counter);
                }                
                break;
            case WAITING:
                if(this.secondDifference(2.0)){
                    this.changeState(State.INVISIBLE);
                }
                break;
            case HITTED:
                if(this.secondDifference(1.0)){
                    this.changeState(State.INVISIBLE);

                    //every three hits updates num of shots
                    if(this.getHealth() % 3==0){
                        this.setNumShots(this.getNumShots()+1);
                    }
                }
                break;
            case INVISIBLE:
                if(secondDifference(1.0)){
                    this.update(); //update position
                    this.changeState(State.START);
                }
                break;
            default:
                System.out.println("ERROR: Imp.state = "+ this.state);
        }
    }

    /**
     * if the position has not been changed, it updates it 
     */
    private void update() {

        //if is in INVISIBLE state 
        if(this.state==State.INVISIBLE){
            if(!this.posFlag){
                //he changed position, set flag to true
                this.posFlag=true;
                BoundingBoxImplRet bBox = (BoundingBoxImplRet) this.getBoundingBox();
                
                //update the position to one of the default ones
                this.setPos(
                    this.positions.get(
                        new Random().nextInt(positions.size())//get random position
                    )
                );
                if(this.getPos().getX()>(bBox.getWidth()/2)){ //if is in the right screen side
                    this.setDir(Direction.LEFT);
                }else{
                    this.setDir(Direction.RIGHT);
                }
                
                //update bounding box position
                this.getBoundingBox().updatePoint(this.getPos());
            }
        }
    }

    /**
     * if is hitted, change state and decrease health 
     */
    @Override
    public boolean onHit() {
        boolean outcome= false;
        if(this.state == State.WAITING || this.state == State.ATTACK || this.state == State.START){
            this.changeState(State.HITTED);
            this.diminishHealth(1);
            if(this.getHealth()==0){
                outcome = true;
            }
        }
        return outcome;
    }
    
    /**
     * Change Boss State and reset previousTime
     * @param state 
     */
    private void changeState(State state){
        this.state=state;
        this.setCurrentTimeToPrevious();
    }

    /**
     * @return boss State
     */
    public State getState() {
        return state;
    }

    /**
     * set how many shots imp shoots
     * @param numShots
     */
    public void setNumShots(int numShots) {
        this.numShots=numShots;
    }

    /**
     * @return how many shots imp has to shoot
     */
    public int getNumShots() {
        return this.numShots;
    }

    /**
     * set how many shots imp had shoot
     * @param num
     */
    public void setShotsFired(int num) {
        this.shotsFired=num;
    }

    /**
     * @return how many shots imp shoots
     */
    public int getShotsFired() {
        return this.shotsFired;
    }
}
