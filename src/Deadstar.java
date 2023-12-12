import java.awt.*;

public class Deadstar extends Enemy{

    public Deadstar(Postion postion,  double speed, Player player) {
        super(postion, speed);

        setHeight(300);
        setWidth(300);
        setHP(100);
        setCoolingTime(1);
//        setPostion(new Postion(GameEngine.rand(500), GameEngine.rand(800)));
//        setDirection(new Postion(GameEngine.rand(6)-GameEngine.rand(4), GameEngine.rand(6) - GameEngine.rand(4)));
    }
    //fire
    public void attemptToFire(Player player){
        long currentTime = System.currentTimeMillis();
        if(currentTime-lastFiringTime>coolingTime){
            lastFiringTime =  currentTime;
            fire(player);

        }
    }

    public void fire(Player player) {
        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()+height/2),new Postion((player.getPostion().getX()-postion.getX())/50,(player.getPostion().getY()-postion.getY())/50), Color.GREEN));
        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()+height/2),new Postion((player.getPostion().getX()-postion.getX())/50,(player.getPostion().getY()-postion.getY())/50), Color.GREEN));
        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()+height/2),new Postion((player.getPostion().getX()-postion.getX())/50,(player.getPostion().getY()-postion.getY())/50), Color.GREEN));
        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()+height/2),new Postion((player.getPostion().getX()-postion.getX())/50,(player.getPostion().getY()-postion.getY())/50), Color.GREEN));
        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()+height/2),new Postion(GameEngine.rand(5)-GameEngine.rand(5),GameEngine.rand(5)-GameEngine.rand(5)), Color.CYAN));
        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()+height/2),new Postion(GameEngine.rand(5)-GameEngine.rand(5),GameEngine.rand(5)-GameEngine.rand(5)), Color.CYAN));
        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()+height/2),new Postion(GameEngine.rand(5)-GameEngine.rand(5),GameEngine.rand(5)-GameEngine.rand(5)), Color.CYAN));
        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()+height/2),new Postion(GameEngine.rand(5)-GameEngine.rand(5),GameEngine.rand(5)-GameEngine.rand(5)), Color.CYAN));
        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()+height/2),new Postion(GameEngine.rand(5)-GameEngine.rand(5),GameEngine.rand(5)-GameEngine.rand(5)), Color.CYAN));
        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()+height/2),new Postion(GameEngine.rand(5)-GameEngine.rand(5),GameEngine.rand(5)-GameEngine.rand(5)), Color.CYAN));

//        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()+height),new Postion(3,5+direction.getY()), Color.RED));
//        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()+height),new Postion(0,7+direction.getY()), Color.RED));
//        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()+height),new Postion(-3,5+direction.getY()), Color.RED));
//
//        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()),new Postion(3,-5-direction.getY()), Color.RED));
//        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()),new Postion(-0,-7-direction.getY()), Color.RED));
//        bullets.add(new Bullet(new Postion(postion.getX()+width/2,postion.getY()),new Postion(-3,-5-direction.getY()), Color.RED));
    }
}
