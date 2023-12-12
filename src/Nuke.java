public class Nuke extends Enemy{
    public Nuke(Postion postion, double speed) {
        super(postion, speed);
        setPostion(new Postion(100+GameEngine.rand(300), 200+GameEngine.rand(400)));
        setDirection(new Postion(GameEngine.rand(6)-GameEngine.rand(4), GameEngine.rand(6) - GameEngine.rand(4)));
        setHeight(30);
        setWidth(30);
        setHP(0);
    }
    public void attemptToFire(){
        long currentTime = System.currentTimeMillis();
        if(currentTime-lastFiringTime>coolingTime){
            lastFiringTime =  currentTime;
            fire();
        }
    }
    public void fire() {
    }
}
