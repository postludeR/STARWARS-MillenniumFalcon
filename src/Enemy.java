import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Enemy {
    Postion postion;
    Postion direction;
    int  coolingTime;//ms
    long lastFiringTime;
    List<Bullet> bullets;
    int width,height;
    int HP;

    private void init(Postion postion){
        this.postion = postion;
        this.coolingTime = coolingTime;
        this.lastFiringTime = System.currentTimeMillis();
        bullets = new CopyOnWriteArrayList<>();
        direction = new Postion(0,2);
        width = 40;
        height = 35;
        coolingTime = 500;
        HP = 2;
    }
    public  Enemy(Postion postion,  double speed){
        init(postion);
        direction = new Postion(0,speed);
    }
    public Enemy(Postion postion, int coolingTime) {
        init(postion);
    }
    public void move(){
        postion.x += direction.x;
        postion.y += direction.y;
    }
    public void attemptToFire(){
        long currentTime = System.currentTimeMillis();
        if(currentTime-lastFiringTime>coolingTime){
            lastFiringTime =  currentTime;
            fire();
        }
    }
    private void fire(){
        bullets.add(new Bullet(new Postion(postion.getX()+width/2.0,postion.getY()+height),new Postion(0,8+direction.getY()), Color.RED));
    }
    public Postion getPostion() {
        return postion;
    }

    public void setPostion(Postion postion) {
        this.postion = postion;
    }

    public Postion getDirection() {
        return direction;
    }

    public void setDirection(Postion direction) {
        this.direction = direction;
    }

    public int getCoolingTime() {
        return coolingTime;
    }

    public void setCoolingTime(int coolingTime) {
        this.coolingTime = coolingTime;
    }

    public long getLastFiringTime() {
        return lastFiringTime;
    }

    public void setLastFiringTime(long lastFiringTime) {
        this.lastFiringTime = lastFiringTime;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(List<Bullet> bullets) {
        this.bullets = bullets;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }
}
