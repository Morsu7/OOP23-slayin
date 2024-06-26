package slayin.model.utility;

import java.util.Random;

import slayin.model.World;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.boss.Imp;
import slayin.model.entities.boss.Minotaur;
import slayin.model.entities.enemies.Couatl;
import slayin.model.entities.enemies.Enemy;
import slayin.model.entities.enemies.Fire;
import slayin.model.entities.enemies.Headstone;
import slayin.model.entities.enemies.Slime;
import slayin.model.events.GameEventListener;
import slayin.model.entities.Dummy;

/**
 * A class that generates the objects of the entities that need to be added to the scene.
 * In this class are described how different entities are created (starting positions, bounding boxes, etc...)
 */
public class EntityFactory {

    private final World world;
    private final Random rn;
    private final GameEventListener eventListener;

    public EntityFactory(World w, GameEventListener eventListener){
        this.world = w;
        rn = new Random();
        this.eventListener = eventListener;
    }

    public Enemy buildDummy(){
        final Vector2d DUMMY_STARTING_MOVEMENT = new Vector2d(0, 0);  // Starts with 0 speed

        final int DUMMY_HEIGHT = world.getHeight() / 20;
        final int DUMMY_LENGHT = DUMMY_HEIGHT;

        final int DUMMY_STARTING_X = (int) rn.nextInt(world.getWidth());    // Starts at a completely random X
        final int DUMMY_STARTING_Y = world.getGround() - DUMMY_HEIGHT/2;    // Starts at the ground level

        Dummy entity = new Dummy(new P2d(DUMMY_STARTING_X, DUMMY_STARTING_Y), DUMMY_STARTING_MOVEMENT, new BoundingBoxImplRet(new P2d(DUMMY_STARTING_X, DUMMY_STARTING_Y), DUMMY_LENGHT, DUMMY_HEIGHT), world);

        return entity;
    }

    /**
     * Creates and returns a new Slime enemy.
     *
     * @return a new Slime enemy.
     */

    public Enemy buildSlime(){

        final int SLIME_HEIGHT = world.getHeight() / 18;
        final int SLIME_LENGHT = SLIME_HEIGHT*2;

        final int SLIME_STARTING_X = (int) rn.nextInt(world.getWidth());    // Starts at a completely random X
        final int SLIME_STARTING_Y = world.getHeight() - SLIME_HEIGHT/2;    // Starts at the ground level

        Slime entity = new Slime(new P2d(SLIME_STARTING_X, SLIME_STARTING_Y),  new BoundingBoxImplRet(new P2d(SLIME_STARTING_X, SLIME_STARTING_Y), SLIME_LENGHT, SLIME_HEIGHT), world, this.eventListener);

        return entity;
    }

    /**
     * Creates and returns a new Fire enemy.
     *
     * @return a new Fire enemy.
     */

    public Enemy buildFire(){
        final int FIRE_HEIGHT = world.getHeight() / 14;
        final int FIRE_LENGHT = FIRE_HEIGHT;

        final int FIRE_STARTING_X = (int) rn.nextInt(world.getWidth());    // Starts at a completely random X
        final int FIRE_STARTING_Y = world.getHeight()/2 - FIRE_HEIGHT/2;    // Starts at half the world height

        Fire entity = new Fire(new P2d(FIRE_STARTING_X, FIRE_STARTING_Y),  new BoundingBoxImplRet(new P2d(FIRE_STARTING_X, FIRE_STARTING_Y), FIRE_LENGHT, FIRE_HEIGHT), world, this.eventListener);

        return entity;
    }

    public Enemy buildMinotaur(){
        BoundingBoxImplRet boundingBox= new BoundingBoxImplRet(null, world.getWidth()/12.8, world.getHeight()/4.8);
        
        return new Minotaur(null, boundingBox, this.world);
    }

    public Enemy buildImp(){
        BoundingBoxImplRet boundingBox=new BoundingBoxImplRet(null, world.getWidth()/18.29, world.getHeight()/10.29);
        
        return new Imp(null, boundingBox, this.world, this.eventListener);
    }

    /**
     * Creates and returns a new Couatl enemy.
     *
     * @return a new Couatl enemy.
     */

    public Enemy buildCouatl(){
        final int COUATL_HEIGHT = world.getHeight() / 12;
        final int COUATL_LENGHT = COUATL_HEIGHT;

        final int COUATL_STARTING_X = (int) rn.nextInt(world.getWidth());    // Starts at a completely random X
        final int COUATL_STARTING_Y = world.getHeight()/2 - COUATL_HEIGHT/2;    // Starts at half the world height

        Couatl entity = new Couatl(new P2d(COUATL_STARTING_X, COUATL_STARTING_Y),  new BoundingBoxImplRet(new P2d(COUATL_STARTING_X, COUATL_STARTING_Y), COUATL_LENGHT, COUATL_HEIGHT), world, this.eventListener);

        return entity;
    }    

    /**
     * Creates and returns a new Headstone enemy.
     *
     * @return a new Headstone enemy.
     */

    public Enemy buildHeadstone(){
        final int HEADSTONE_HEIGHT = world.getHeight() / 14;
        final int HEADSTONE_LENGHT = HEADSTONE_HEIGHT;
        int spawnx;

        if(rn.nextInt(2)==1){
            spawnx = world.getWidth()-HEADSTONE_LENGHT/2;
        }else{
            spawnx = HEADSTONE_LENGHT/2;
        }

        final int HEADSTONE_STARTING_X = spawnx;    // Starts at full left of the screen or full right
        final int HEADSTONE_STARTING_Y = world.getGround()-HEADSTONE_HEIGHT/2;    // Starts at the ground level

        Headstone entity = new Headstone(new P2d(HEADSTONE_STARTING_X, HEADSTONE_STARTING_Y),  new BoundingBoxImplRet(new P2d(HEADSTONE_STARTING_X, HEADSTONE_STARTING_Y), HEADSTONE_LENGHT, HEADSTONE_HEIGHT), world, this.eventListener);

        return entity;
    }
}
