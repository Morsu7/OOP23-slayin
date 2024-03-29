package slayin.model;

public abstract class GameObject {
    protected P2d pos;
    protected Vector2d vectorMouvement;


    public GameObject(P2d pos,Vector2d vectorMouvement){
        this.pos=pos;
        this.vectorMouvement=vectorMouvement;
    }

    public P2d getPos(){
        return this.pos;
    }

    public void setPos(P2d pos) {
        this.pos = pos;
    }

    public Vector2d getVectorMouvement() {
        return vectorMouvement;
    }

    public void setVectorMouvement(Vector2d vectorMouvement) {
        this.vectorMouvement = vectorMouvement;
    }


    public abstract void updateVel(InputController input);   
    
    public abstract void updatePos();


}
