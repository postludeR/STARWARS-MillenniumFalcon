public class Asteroid extends Enemy{

    public Asteroid(Postion postion,  double speed) {
        super(postion,  speed);
        setHP(3);
        if(GameEngine.rand(4)==1){
            setPostion(new Postion(0, GameEngine.rand(800)));
            setDirection(new Postion(GameEngine.rand(10), GameEngine.rand(10)-GameEngine.rand(10)));
        }else if(GameEngine.rand(4)==2){
            setPostion(new Postion(GameEngine.rand(500), 0));
            setDirection(new Postion(GameEngine.rand(10)-GameEngine.rand(10), GameEngine.rand(10)));
        }else if(GameEngine.rand(4)==3){
            setPostion(new Postion(500, GameEngine.rand(800)));
            setDirection(new Postion(-GameEngine.rand(10), GameEngine.rand(10)-GameEngine.rand(10)));
        }else if(GameEngine.rand(4)==4){
            setPostion(new Postion(GameEngine.rand(500), 800));
            setDirection(new Postion(GameEngine.rand(10)-GameEngine.rand(10), -GameEngine.rand(10)));
        }

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
    }
}
