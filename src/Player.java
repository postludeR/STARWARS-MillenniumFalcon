import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Player {
    int HP;
    int score;
    //被击毁 小敌机的数量
    int smallEnemyDestroyed;
    //被击毁 中敌机的数量
    int midEnemyDestroyed;
    Postion postion;
    int  coolingTime;//ms
    long lastFiringTime;
    long lastMovingTime;
    List<Bullet> bullets;
    int width,height;
    int moveSpeedX = 0;
    int moveSpeedY = 0;

    public Player(int HP,Postion postion,int coolingTime_ms) {
        this.HP = HP;
        this.postion = postion;
        this.coolingTime = 300;
        this.lastFiringTime = 0;

        this.lastMovingTime = 0;
        this.score = 0;

        this.smallEnemyDestroyed = 0;
        this.midEnemyDestroyed = 0;
        bullets = new CopyOnWriteArrayList<>();
        this.width = 30;
        this.height = 40;
    }


    public Boolean attemptToFire(int AMMO, List<Enemy> enemies){
        long currentTime = System.currentTimeMillis();
        if(currentTime-lastFiringTime>coolingTime){
            lastFiringTime =  currentTime;
            switch (AMMO){
                case 0:
//                    fire0();
//                    fire1();
//                    fire2();
//                    fire3(enemies);
//                    fire4(enemies);

                    fire0();
                    setCoolingTime(550);
                    break;
                case 1:
                    setCoolingTime(500);
                    fire1();
                    break;
                case 2:
                    setCoolingTime(500);
                    fire2();
                    break;
                case 3:
                    setCoolingTime(450);
                    fire3(enemies);
                    break;
                case 4:
                    setCoolingTime(300);
                    fire4(enemies);
                    break;
            }
            return true;
        }
        return false;
    }
    private void fire0(){
        bullets.add(new Bullet(new Postion(postion.getX()+8,postion.getY()),new Postion(0,-8), Color.BLUE));
        bullets.add(new Bullet(new Postion(postion.getX()+10, postion.getY()),new Postion(0,-8), Color.BLUE));
    }
    private void fire1(){
        bullets.add(new Bullet(new Postion(postion.getX()+0,postion.getY()),new Postion(1,-8), Color.BLUE));
        bullets.add(new Bullet(new Postion(postion.getX()+8, postion.getY()),new Postion(0,-8), Color.BLUE));
        bullets.add(new Bullet(new Postion(postion.getX()+16, postion.getY()),new Postion(-1,-8), Color.BLUE));
    }

    private void fire2(){
        bullets.add(new Bullet(new Postion(postion.getX()+0,postion.getY()),new Postion(2,-8), Color.BLUE));
        bullets.add(new Bullet(new Postion(postion.getX()+4,postion.getY()),new Postion(1,-8), Color.BLUE));
        bullets.add(new Bullet(new Postion(postion.getX()+8, postion.getY()),new Postion(0,-8), Color.BLUE));
        bullets.add(new Bullet(new Postion(postion.getX()+12, postion.getY()),new Postion(-1,-8), Color.BLUE));
        bullets.add(new Bullet(new Postion(postion.getX()+16,postion.getY()),new Postion(-2,-8), Color.BLUE));
    }

    private void fire3(List<Enemy> enemies){
        if(enemies.size()>4){
            int rand = GameEngine.rand(enemies.size()-3);
            bullets.add(new Bullet(new Postion(postion.getX()+0,postion.getY()),new Postion((enemies.get(rand).getPostion().getX()-postion.getX())/50,(enemies.get(rand).getPostion().getY()-postion.getY())/50), Color.PINK));
//            bullets.add(new Bullet(new Postion(postion.getX()+0,postion.getY()),new Postion((enemies.get(rand+1).getPostion().getX()-postion.getX())/50,(enemies.get(rand+1).getPostion().getY()-postion.getY())/50), Color.PINK));

        }


        bullets.add(new Bullet(new Postion(postion.getX()+0,postion.getY()),new Postion(2,-8), Color.BLUE));
        bullets.add(new Bullet(new Postion(postion.getX()+4,postion.getY()),new Postion(0,-8), Color.BLUE));
        bullets.add(new Bullet(new Postion(postion.getX()+8, postion.getY()),new Postion(0,-8), Color.BLUE));
        bullets.add(new Bullet(new Postion(postion.getX()+12, postion.getY()),new Postion(0,-8), Color.BLUE));
        bullets.add(new Bullet(new Postion(postion.getX()+16,postion.getY()),new Postion(-2,-8), Color.BLUE));
    }


    private void fire4(List<Enemy> enemies){
        if(enemies.size()>6){
            int rand = GameEngine.rand(enemies.size()-6);

            bullets.add(new Bullet(new Postion(postion.getX()+0,postion.getY()),new Postion((enemies.get(rand).getPostion().getX()-postion.getX())/50,(enemies.get(rand).getPostion().getY()-postion.getY())/50), Color.PINK));
            bullets.add(new Bullet(new Postion(postion.getX()+0,postion.getY()),new Postion((enemies.get(rand+1).getPostion().getX()-postion.getX())/50,(enemies.get(rand+1).getPostion().getY()-postion.getY())/50), Color.PINK));
//            bullets.add(new Bullet(new Postion(postion.getX()+0,postion.getY()),new Postion((enemies.get(rand+2).getPostion().getX()-postion.getX())/50,(enemies.get(rand+2).getPostion().getY()-postion.getY())/50), Color.PINK));
//            bullets.add(new Bullet(new Postion(postion.getX()+0,postion.getY()),new Postion((enemies.get(rand+3).getPostion().getX()-postion.getX())/50,(enemies.get(rand+3).getPostion().getY()-postion.getY())/50), Color.PINK));
//            bullets.add(new Bullet(new Postion(postion.getX()+0,postion.getY()),new Postion((enemies.get(rand+4).getPostion().getX()-postion.getX())/50,(enemies.get(rand+4).getPostion().getY()-postion.getY())/50), Color.PINK));

        }

        bullets.add(new Bullet(new Postion(postion.getX()+1,postion.getY()),new Postion(3,-8), Color.BLUE));
        bullets.add(new Bullet(new Postion(postion.getX()+3,postion.getY()),new Postion(1,-8), Color.BLUE));
        bullets.add(new Bullet(new Postion(postion.getX()+5,postion.getY()),new Postion(0,-8), Color.BLUE));
        bullets.add(new Bullet(new Postion(postion.getX()+8, postion.getY()),new Postion(0,-8), Color.BLUE));
        bullets.add(new Bullet(new Postion(postion.getX()+11, postion.getY()),new Postion(0,-8), Color.BLUE));
        bullets.add(new Bullet(new Postion(postion.getX()+14,postion.getY()),new Postion(-1,-8), Color.BLUE));
        bullets.add(new Bullet(new Postion(postion.getX()+16,postion.getY()),new Postion(-3,-8), Color.BLUE));
    }


    public void moveUp(){
//        postion.setY(postion.getY()-moveSpeed);
        setMoveSpeedY(-8);
    }
    public void moveDown(){

//        postion.setY(postion.getY()+moveSpeed);
        setMoveSpeedY(8);
    }

    public void moveLeft(){
//        postion.setX(postion.getX()-moveSpeed);
        setMoveSpeedX(-8);
    }

    public void moveRight(){
//        postion.setX(postion.getX()+moveSpeed);
        setMoveSpeedX(8);
    }

    public Postion getPostion() {
        return postion;
    }

    public void setPostion(Postion postion) {
        this.postion = postion;
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

    public int getMoveSpeedX() {
        return moveSpeedX;
    }

    public void setMoveSpeedX(int moveSpeedX) {
        this.moveSpeedX = moveSpeedX;
    }

    public int getMoveSpeedY() {
        return moveSpeedY;
    }

    public void setMoveSpeedY(int moveSpeedY) {
        this.moveSpeedY = moveSpeedY;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }
    public void addHP(int HP) {
        this.HP += HP;
    }
    public void subHP(int HP) {
        this.HP -= HP;
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }
    public void subScore(int score) {
        this.score -= score;
    }

    public int getSmallEnemyDestroyed() {
        return smallEnemyDestroyed;
    }

    public void setSmallEnemyDestroyed(int smallEnemyDestroyed) {
        this.smallEnemyDestroyed = smallEnemyDestroyed;
    }
    public void addSmallEnemyDestroyed(int smallEnemyDestroyed) {
        this.smallEnemyDestroyed += smallEnemyDestroyed;
    }

    public int getMidEnemyDestroyed() {
        return midEnemyDestroyed;
    }

    public void setMidEnemyDestroyed(int midEnemyDestroyed) {
        this.midEnemyDestroyed = midEnemyDestroyed;
    }
    public void addMidEnemyDestroyed(int midEnemyDestroyed) {
        this.midEnemyDestroyed += midEnemyDestroyed;
    }
}
