import java.awt.*;

public class TieElite extends Enemy{
    public TieElite(Postion postion, double speed) {
        super(postion, speed);
        setHP(5);
        setWidth(23);
        setHeight(60);
    }
    //fire
    public void attemptToFire(){
        long currentTime = System.currentTimeMillis();
        if(currentTime-lastFiringTime>coolingTime){
            lastFiringTime =  currentTime;
            fire();
        }
    }
    public void fire() {
        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()+height),new Postion(3,5+direction.getY()), Color.RED));
        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()+height),new Postion(0,7+direction.getY()), Color.RED));
        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()+height),new Postion(-3,5+direction.getY()), Color.RED));
    }
}
