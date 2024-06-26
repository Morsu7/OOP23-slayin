package slayin.model.entities.character;

import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.GameObject.Direction;
import slayin.model.entities.shots.RoundBullet;
import slayin.model.entities.shots.ShotObject;
import slayin.model.utility.Globals;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;
import slayin.model.World;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;


/**
 * Factory class to create different types of characters.
 */
public class CharacterFactory { 

    /**
     * Creates a Knight character.
     * 
     * @param w the game world
     * @return the created Knight character
     */
    public static CharacterImpl getKnight(World w){
        final double widthPlayer=w.getWidth()/23.2,heightPlayer=w.getHeight()/10.2,heightWeapon=w.getHeight()/20.5,widthWeapon=w.getWidth()/25.6;
        // la widthFromPlayer la calcolo facendo widthPlayer/2 + BoundingBoxWeapon/2
        MeleeWeapon weapon = new MeleeWeapon(10, new BoundingBoxImplRet(new P2d(0, 0), widthWeapon, heightWeapon), 0,(int)(widthPlayer/2 + widthWeapon/2),"SwordL",-1);
        Consumer<CharacterImpl> func= new Consumer<CharacterImpl>() {

            @Override
            public void accept(CharacterImpl t) {
                t.getVectorMovement().setY(Globals.FJUMP_CHARACTER);
            }
            
        };  
        Function<CharacterImpl,Optional<ShotObject>> getShot= (c)->{return Optional.empty();};
        return new CharacterImpl(new P2d(w.getWidth()/2, w.getGround()), new Vector2d(1, 0), new BoundingBoxImplRet(new P2d(0, 0), widthPlayer, heightPlayer),new Health(10, 10),w,func,getShot,"Knight",weapon);
    }


    /**
     * Creates a modified Knight character.
     * 
     * @param w the game world
     * @return the created modified Knight character
     */
    public static CharacterImpl getKnightModify(World w){
        final double widthPlayer=w.getWidth()/23.2,heightPlayer=w.getHeight()/10.2,heightWeapon=w.getHeight()/20.5,widthWeapon=w.getWidth()/25.6;
        MeleeWeapon weapon = new MeleeWeapon(10, new BoundingBoxImplRet(new P2d(0, 0), widthWeapon, heightWeapon), 0, (int)(widthPlayer/2 + widthWeapon/2),"SwordL",-1);
        Consumer<CharacterImpl> func= new Consumer<CharacterImpl>() {

            @Override
            public void accept(CharacterImpl t) {
                //t.getVectorMovement().setY(Constants.FJUMP_CHARACTER);
                t.addWeapon(new MeleeWeapon(10, new BoundingBoxImplRet(new P2d(0, 0), widthWeapon, heightWeapon), 0, -(int)(widthPlayer/2 + widthWeapon/2),"SwordR",2000));
                t.setTimeBlockedJump(1000);
                t.setTimeBlockedMove(100);
                if(t.getDir()==Direction.LEFT){
                    t.getVectorMovement().setX(-1200);
                }else{
                    t.getVectorMovement().setX(1200);
                }

            }
            
        };  
        Function<CharacterImpl,Optional<ShotObject>> getShot= (c)->Optional.empty();
        return new CharacterImpl(new P2d(w.getWidth()/2, w.getGround()), new Vector2d(1, 0), new BoundingBoxImplRet(new P2d(0, 0), widthPlayer, heightPlayer),new Health(10, 10),w,func,getShot,"Knight",weapon);
    }


    /**
     * Creates a Wizard character.
     * 
     * @param w the game world
     * @return the created Wizard character
     */
    public static CharacterImpl getWizard(World w){
        final double widthPlayer=w.getWidth()/23.2,heightPlayer=w.getHeight()/10.2,heightWeapon=w.getHeight()/1.6,widthWeapon=w.getWidth()/25.6;
        Consumer<CharacterImpl> func= new Consumer<CharacterImpl>() {

            @Override
            public void accept(CharacterImpl t) {
                //t.getVectorMovement().setY(Constants.FJUMP_CHARACTER);
                t.addWeapon(new MeleeWeapon(10, new BoundingBoxImplRet(new P2d(0, 0), widthWeapon, heightWeapon), (int)(heightWeapon/2-heightPlayer/2), 0,"Vortex",1500));
                t.setTimeBlockedJump(2300);
                t.setTimeBlockedDecLife(1500);
            }
            
        };  

        Function<CharacterImpl,Optional<ShotObject>> getShot= (c)->{return Optional.of(new RoundBullet(c,w));};

        return new CharacterImpl(new P2d(w.getWidth()/2, w.getGround()), new Vector2d(1, 0), new BoundingBoxImplRet(new P2d(0, 0), widthPlayer, heightPlayer),new Health(10, 10),w,func,getShot,"Wizard");
    }


    /**
     * Creates a Knave character.
     * 
     * @param w the game world
     * @return the created Knave character
     */
    public static CharacterImpl getKnave(World w){
        final double widthPlayer=w.getWidth()/23.2,heightPlayer=w.getHeight()/10.2,heightWeapon=w.getHeight()/20.5,widthWeapon=w.getWidth()/25.6;
        MeleeWeapon weaponLeft = new MeleeWeapon(10, new BoundingBoxImplRet(new P2d(0, 0), widthWeapon, heightWeapon), -5, (int)(widthPlayer/2 + widthWeapon/2),"SwordR",-1);
        MeleeWeapon weaponRight = new MeleeWeapon(10, new BoundingBoxImplRet(new P2d(0, 0), widthWeapon, heightWeapon), -10, -(int)(widthPlayer/2 + widthWeapon/2),"SwordL",-1);
        Consumer<CharacterImpl> func= new Consumer<CharacterImpl>() {
            @Override
            public void accept(CharacterImpl t) {
                t.getVectorMovement().setY(Globals.FJUMP_CHARACTER);
            }       
        };  
        Function<CharacterImpl,Optional<ShotObject>> getShot= (c)->Optional.empty();
        return new CharacterImpl(new P2d(w.getWidth()/2, w.getGround()), new Vector2d(1, 0), new BoundingBoxImplRet(new P2d(0, 0), widthPlayer, heightPlayer),new Health(10, 10),w,func,getShot,"Knave",weaponLeft,weaponRight);
    }
}
