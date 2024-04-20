package slayin.model.entities.character;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import slayin.model.World;
import slayin.model.World.Edge;
import slayin.model.bounding.BoundingBox;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.GameObject;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.movement.MovementController;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;
import slayin.model.utility.Constants;

/**
 *  Base class for all characters.
 *  by default the character only moves left and right
 */
public class Character extends GameObject{
    private Vector2d gravity;
    private Health life;
    private List<MeleeWeapon> weapons;

    /**
     * The constructor of the Character class
     * @param pos -initial position of the character
     * @param vectorMouvement - displacement vector
     * @param boundingBox - boundinf box of the character
     * @param life - initial value of the character's life
     * @param world - reference world used the character
     * @param weapons - melee weapons belonging to the character
     */
    public Character(P2d pos, Vector2d vectorMouvement, BoundingBox boundingBox,Health life,World world, MeleeWeapon ... weapons) {
        super(pos, vectorMouvement, boundingBox,world);
        this.life=life;
        this.weapons= new ArrayList<>(Arrays.asList(weapons));
        gravity= new Vector2d(0, Constants.GRAVITY_CHARACTER);  
    }

    /**
     * A getter for the weapons attribute
     * @return list of melee weapons
     */
    public List<MeleeWeapon> getWeapons(){
        return this.weapons;
    }

    /**
     * A method that returns true if the life value is greater than zero
     * @return true if the life value is greater than zero and false otherwise
     */
    public boolean isAlive(){
        return life.getHealth()>0;
    }

    /**
     * A method that returns the value of the life attribute
     * 
     * @return the value of the life attribute
     */
    public Health getLife() {
        return this.life;
    }

    /**
     * A method decreases life by a defined value
     * @param damage - value that will decrease life, must be greater than zero otherwise it will not decrease life
     */
    public void decLife(int damage){
        this.life.decLife(damage);
    }

    @Override
    public DrawComponent getDrawComponent(){
        return DrawComponentFactory.graphicsComponentCharacter(this);
    }


    
    public void updateVel(MovementController input) {
        if(input.isJumping() && this.getWorld().isTouchingGround(this)){
            this.getVectorMovement().setY(Constants.FJUMP_CHARACTER);
            input.setJumping(false);
        }else if(input.isMovingLeft()){
            this.getVectorMovement().setX(Constants.FLEFT_CHARACTER);
            this.setDir(Direction.LEFT);
        }else if(input.isMovingRight()){
            this.getVectorMovement().setX(Constants.FRIGHT_CHARACTER);
            this.setDir(Direction.RIGHT);
        }
    }

    private void controlCollision(){
        List<Edge> collision= this.getWorld().collidingWith(this);
        BoundingBoxImplRet bBox = (BoundingBoxImplRet) this.getBoundingBox();
        for(var col : collision){

            if(col == Edge.LEFT_BORDER && this.getDir()==Direction.LEFT){
                this.getVectorMovement().setX(0);
                this.getPos().setX(bBox.getWidth()/2);
            }

            if(col == Edge.RIGHT_BORDER && this.getDir()==Direction.RIGHT){
                this.getVectorMovement().setX(0);
                this.getPos().setX(this.getWorld().getWidth()-bBox.getWidth()/2);
            }
            if(col == Edge.BOTTOM_BORDER ){
                this.getVectorMovement().setY(0);
                this.setPos(new P2d(this.getPos().getX(),this.getWorld().getGround()-(bBox.getHeight()/2)));
            }
        }
    }

    private void updateBoundingBox(){
        this.getBoundingBox().updatePoint(this.getPos());
        if(this.getDir()==Direction.LEFT){
            this.getWeapons().stream().forEach(t->{
                if(t.getBoxWeapon() instanceof BoundingBoxImplRet){
                    BoundingBoxImplRet bBoxWeapon = (BoundingBoxImplRet) t.getBoxWeapon();
                    t.updateBoxWeapon(new P2d(this.getPos().getX()-(t.getWidthFromPlayer()/2)-(bBoxWeapon.getWidth()/2),this.getPos().getY()+t.getHeightFromPlayer()));
                }//volendo se si hanno armi circolari si può aggiungere il controllo anche per quelle
            });
        }else{
            this.getWeapons().stream().forEach(t->{
                if(t.getBoxWeapon() instanceof BoundingBoxImplRet){
                    BoundingBoxImplRet bBoxWeapon = (BoundingBoxImplRet) t.getBoxWeapon();
                    t.updateBoxWeapon(new P2d(this.getPos().getX()+(t.getWidthFromPlayer()/2)+(bBoxWeapon.getWidth()/2),this.getPos().getY()+t.getHeightFromPlayer()));
                }//volendo se si hanno armi circolari si può aggiungere il controllo anche per quelle
            });
        }
    }

    @Override
    public void updatePos(int dt) {
        //add the gravity 
        this.setVectorMovement(this.getVectorMovement().sum(gravity.mul(0.001*dt)));
        //actual movement
        this.setPos(this.getPos().sum(this.getVectorMovement().mul(0.001*dt)));
        //update bounding box player
        this.getBoundingBox().updatePoint(this.getPos());
        //controll collision
        this.controlCollision();
        //update all bounding box
        this.updateBoundingBox();
    }
}
