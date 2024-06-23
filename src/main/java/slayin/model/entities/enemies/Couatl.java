package slayin.model.entities.enemies;

import java.util.Random;

import slayin.model.World;
import slayin.model.World.Edge;
import slayin.model.bounding.BoundingBox;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.events.GameEventListener;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class Couatl extends Enemy{

    private static final int scorePerKill = 3;
    private static final int damageOnHit = 2;
    private int oldDt = 0;
    private Boolean pause = false,down = false,up = false;
    private Random random;
    private int SPEEDX = this.getWorld().getWidth()/13;
    private int SLOWSPEEDY = this.getWorld().getWidth()/4;
    private int FASTSPEEDY = this.getWorld().getHeight()/2;
    private double startingY;

    public Couatl(P2d pos, BoundingBox boundingBox, World world, GameEventListener eventListener) {
        super(pos, new Vector2d(0, 0), boundingBox, world, eventListener);
        random = new Random();
        startingY = this.getPos().getY();
    }

    @Override
    public void updatePos(int dt){
        oldDt+=dt;
        if(oldDt>2000){
            if(!down && !up){
                pause = !pause;
            }
            oldDt = 0;
        } 
        this.updateDir();
        
        this.setPos(this.getPos().sum(this.getVectorMovement().mul(0.001*dt)));
        // aggiorno di nuovo la BoundinBox
        this.getBoundingBox().updatePoint(this.getPos());
    }
    
    @Override
    public void updateDir(){
        if(this.getVectorMovement().equals(new Vector2d(0, 0)) && !pause && this.getPos().getY()==startingY){
            if(random.nextInt(2)==1){
                setDir(Direction.LEFT);
                this.setVectorMovement(new Vector2d(-SPEEDX, 0)); 
            }else{
                setDir(Direction.RIGHT);
                this.setVectorMovement(new Vector2d(SPEEDX, 0));
            }
        }else{
            if(pause && !down && !up){
                this.setVectorMovement(new Vector2d(0, 0));
            }else if(up){
                this.setVectorMovement(new Vector2d(0, SLOWSPEEDY));
                if(this.getPos().getY()<=startingY){
                    up = false;
                }
            }else if(down){
                this.setVectorMovement(new Vector2d(0, FASTSPEEDY));
                if(this.getWorld().isTouchingGround(this)){
                    down = false;
                    pause = true;
                }
            }
        }
        
        
        /////
        var collision = this.getWorld().collidingWith(this);
        for(var col : collision){

            if(col == Edge.LEFT_BORDER && this.getDir()==Direction.LEFT){
                this.setVectorMovement(new Vector2d(SPEEDX,0));
                setDir(Direction.RIGHT);
            }

            if(col == Edge.RIGHT_BORDER && this.getDir()==Direction.RIGHT){
                this.setVectorMovement(new Vector2d(-SPEEDX,0));
                setDir(Direction.LEFT);
            }
        }
    }

    @Override
    public int getScorePerKill(){
        return scorePerKill;
    }

    @Override
    public DrawComponent getDrawComponent(){
        return DrawComponentFactory.graphicsComponentEnemy((Enemy)this);
    }

    @Override
    public int getDamageOnHit() {
        return Couatl.damageOnHit;
    }
}
