import java.awt.*;

public class TieBomber extends Enemy{
    public TieBomber(Postion postion, double speed) {
        super(postion,speed);
        setHP(3);
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
        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()+height/2),new Postion(GameEngine.rand(5)-GameEngine.rand(10),GameEngine.rand(5)-GameEngine.rand(10)), Color.GREEN));
    }
}
