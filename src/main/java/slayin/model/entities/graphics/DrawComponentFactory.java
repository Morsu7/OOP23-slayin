package slayin.model.entities.graphics;

import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.net.URISyntaxException;

import slayin.model.bounding.BoundingBox;
import slayin.model.bounding.BoundingBoxImplCirc;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.character.Character;
import slayin.model.entities.character.Character.Direction;

public class DrawComponentFactory {

    private final static String FORMAT_SPRITE=".png";
    
    public static DrawComponent graphicsComponentCharacter(Character character){
        return new DrawComponent() {

            @Override
            public void draw(Graphics g) {
                URL pathKnight;
                Image img;
                // cerco la x più piccola per disegnare correttaemente il personaggio
                double minX;
                if(character.getDir()==Direction.LEFT){
                    pathKnight = this.getClass().getResource("/assets/character/"+character.getClass().getSimpleName()+"Left"+FORMAT_SPRITE);
                }else{
                    pathKnight = this.getClass().getResource("/assets/character/"+character.getClass().getSimpleName()+"Right"+FORMAT_SPRITE);
                }
                try {
                    img = ImageIO.read(new File(pathKnight.toURI()));
                    BoundingBoxImplRet boxCharacter = (BoundingBoxImplRet)character.getBoundingBox();
                    minX=boxCharacter.getX();
                    //confronto la x della boxCharacter con quella delle armi se quella delle armi e minore cambio valore di minX
                    for(var t : character.getWeapons()){
                        if(t.getBoxWeapon() instanceof BoundingBoxImplRet){
                            BoundingBoxImplRet bBox =(BoundingBoxImplRet) t.getBoxWeapon();
                            if(bBox.getX()<minX) minX=bBox.getX();
                        }else if(t.getBoxWeapon() instanceof BoundingBoxImplCirc){
                            BoundingBoxImplCirc bBox =(BoundingBoxImplCirc) t.getBoxWeapon();
                            //TODO: cambiare getx() del BoundinBoxImplCirc
                            if(bBox.getX()-bBox.getRadius()<minX) minX=bBox.getX();
                        }
                    }
                    g.drawImage(img,(int)minX,(int)boxCharacter.getY(), null);
                }catch (URISyntaxException | IOException e) {
                    System.out.println("impossibile caricare l'immagine del personaggio");
                    e.printStackTrace();
                }
            }
            
        };

    }


    public static DrawComponent graphicsComponentBoundigBox(BoundingBox bBox){
        return new DrawComponent() {

            @Override
            public void draw(Graphics g) {
                if(bBox instanceof BoundingBoxImplRet){
                    BoundingBoxImplRet newBBox= (BoundingBoxImplRet)bBox;
                    g.drawRect((int)newBBox.getX(),(int)newBBox.getY(), (int)newBBox.getWidth(),(int) newBBox.getHeight());
                }else if(bBox instanceof BoundingBoxImplCirc){
                    //TODO: da testare che sia giusto
                    BoundingBoxImplCirc newBBox= (BoundingBoxImplCirc) bBox;
                    g.drawOval((int)newBBox.getPoint().getX(),(int)newBBox.getPoint().getY(), (int)newBBox.getRadius(),(int) newBBox.getRadius());
                }
            }
            
        };

    }


}