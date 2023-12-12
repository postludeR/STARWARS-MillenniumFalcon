import java.awt.*;

public class StarDestroyer extends Enemy{
    public StarDestroyer(Postion postion, double speed) {
        super(postion, speed);
        setHP(40);
        setHeight(200);
        setWidth(100);
        setCoolingTime(200);
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
        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()+height/1.3),new Postion(1,5+direction.getY()), Color.RED));
        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()+height/1.3),new Postion(2,5+direction.getY()), Color.RED));
        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()+height/1.3),new Postion(3,5+direction.getY()), Color.RED));
        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()+height/1.3),new Postion(0,7+direction.getY()), Color.RED));
        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()+height/1.3),new Postion(-3,5+direction.getY()), Color.RED));
        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()+height/1.3),new Postion(-2,5+direction.getY()), Color.RED));
        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()+height/1.3),new Postion(-1,5+direction.getY()), Color.RED));
    }
}
