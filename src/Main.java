import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    int WIDTH = 500;
    int HEIGHT = 800;
    int FPS = 24;
    GameEngine gameEngine;

    public Main(){
        gameEngine = new GameEngine() {

            private Player player=null;
            private List<Enemy> enemies;

            private final String welcomePage = "welcomePage";
            private final String gamePage = "gamePage";
            private final String rankPage = "rankPage";
            private String thisPage = welcomePage;
            private long startTime = System.currentTimeMillis();
            private long currentTime = System.currentTimeMillis();
            private long lastAddSpeedTime = System.currentTimeMillis();
            private long lastAddEnemyTime = System.currentTimeMillis();
            private long deadTime ;


            private double enemySpeed = 1;

//            private double enemySpeed = 3;

            private int generatingSpeed = 149 + 50*30;

//            private int generatingSpeed = 149  + 50;

            private Postion currentPosition=null;
            int difUpdate = 1000;

//            int difUpdate = 100;

            int NUKE = 1;
            int AMMO = 0;
            int utilitiesSpeed = 7000;

//            int utilitiesSpeed = 3000;

            List<Image> bgs = new ArrayList<>();
            Image bg = loadImage("src/images/bg.jpg");
            int bgn1 = 0;
            int bgn2 = 0;

            AudioClip IP = loadAudio("src/audio/fun.wav");
            AudioClip start = loadAudio("src/audio/start.wav");

            void drawInfo(){
                drawImage(bg, 0,-bgn1,500,1600);
                bgn1++;
                drawImage(bg, 0,-bgn2+1600,500,1600);
                bgn2++;
                if(bgn1==1600){
                    bgn1=0;
                }
                if(bgn2==1600){
                    bgn2=0;
                }

                changeColor(Color.WHITE);
                String InfoH = String.format("HP: %3d",player.getHP());
                String InfoS = String.format("Score: %3d",player.getScore());
                String InfoN = String.format("Wormhole: %3d",NUKE);
                String InfoA = String.format("Ammo Level: %3d",AMMO);
                drawText(10,20,InfoH,"serif",20);
                drawText(10,40,InfoS,"serif",20);
                drawText(10,60,InfoN,"serif",20);
                drawText(10,80,InfoA,"serif",20);
            }
            boolean isCollide(Player player,Bullet bullet){
                if(bullet.postion.x>player.postion.x && bullet.postion.x<player.postion.x+player.width){
                    if(bullet.postion.y>player.postion.y && bullet.postion.y<player.postion.y+player.height){
                        return true;
                    }
                }
                return false;
            }
            boolean isCollide(Player player,Enemy enemy){
                double e_x=enemy.postion.x;
                double e_y=enemy.postion.y;
                int e_w=enemy.width;
                int e_h=enemy.height;

                double p_x=player.postion.x;
                double p_y=player.postion.y;
                int p_w=player.width;
                int p_h=player.height;

                //rectangle detecting

                if(e_x+e_w>=p_x && e_x<=p_x+p_w && e_y+e_h>=p_y && e_y<=p_y+p_h){
                    return true;
                }

                return false;
            }
            boolean exposureToEmery(Bullet bullet,Enemy enemy){
                if(bullet.postion.x>=enemy.postion.x && bullet.postion.x<=enemy.postion.x+enemy.width){
                    if(bullet.postion.y>=enemy.postion.y && bullet.postion.y<=enemy.postion.y+enemy.height){
                        return true;
                    }
                }
                return false;
            }
            int deathEffect_max = 4;
            int deathEffect = 1;
            boolean drawDeathEffect = false;
            void drawDeath(){
                //在plyer位置绘制一个逐渐扩大的圆形
                changeColor(Color.RED);
                Image img = player_fail_images.get(deathEffect);
                drawImage(img,player.postion.x,player.postion.y,player.width,player.height);
                deathEffect+=1;
                if(deathEffect>=deathEffect_max){
                    deathEffect = 1;
                    drawDeathEffect = false;
                    gameOver();
                }

            }
            void gameOver(){

                enemySpeed = 1;
                generatingSpeed = 149 + 50*30;
                difUpdate = 100;
                NUKE = 1;
                AMMO = 0;

                thisPage = rankPage;
                int time_sec = (int) ((deadTime-startTime)/1000);
                Result result = new Result(time_sec,player.getSmallEnemyDestroyed(),
                        player.getMidEnemyDestroyed(), player.getScore()+time_sec);
                OperationalData data = new OperationalData();
                data.insertDataToFile(result);
            }



            void gamePage(){
                if(player==null)initGame();
                drawInfo();

                // Update
                currentPosition = new Postion(player.postion.x, player.postion.y);
                currentPosition.setX(currentPosition.getX()+ player.moveSpeedX);
                currentPosition.setY(currentPosition.getY()+ player.moveSpeedY);
                player.setPostion(currentPosition);
//                player.addScore(1);


                if (player.postion.x+player.width>WIDTH){
                    player.postion.x=WIDTH-player.width;
//                    player.moveSpeedX*=-1;
                }

                if (player.postion.x<0){
                    player.postion.x=0;
//                    player.moveSpeedX*=-1;
                }

                if(player.postion.y+player.height>HEIGHT){
                    player.postion.y=HEIGHT-player.height;
//                    player.moveSpeedY*=-1;
                }

                if(player.postion.y<0){
                    player.postion.y=0;
//                    player.moveSpeedY*=-1;

                }



                currentTime = System.currentTimeMillis();
                if((currentTime-startTime)/difUpdate%7==0 && (currentTime-startTime)/difUpdate>0 && lastAddSpeedTime/difUpdate!=currentTime/difUpdate){
                    if(enemySpeed<8)
                    {
                        enemySpeed+=.18;
                        lastAddSpeedTime = currentTime;
                        System.out.println("enemySpeed: "+enemySpeed);
                        
                    }
                    if(generatingSpeed>199)
                    {
                        generatingSpeed -= 50;
                        lastAddSpeedTime = currentTime;
                        System.out.println("GeneratingSpeed: "+generatingSpeed);

                    }
                    
                }
                if((currentTime-startTime)/generatingSpeed%1==0 && (currentTime-startTime)/generatingSpeed>0 && lastAddEnemyTime/generatingSpeed!=currentTime/generatingSpeed){
                    int posX = rand(WIDTH-100)+50;

                    if(rand(200)<6&&enemySpeed>4){
                        TieElite ME = new TieElite(new Postion(posX,0),enemySpeed/2);
                        ME.setHP(2);
                        enemies.add(ME);
                        lastAddEnemyTime = currentTime;
                    }
                    else if(rand(200)<10){
                        Enemy E = new Enemy(new Postion(posX,0),enemySpeed);
                        E.setHP(1);
                        enemies.add(E);
                        lastAddEnemyTime = currentTime;
                    }else if(rand(200)<2&&enemySpeed>6){

                        boolean exist = false;
                        for(Enemy e: enemies){
                            if (e instanceof Deadstar){
                                exist = true;
                            }
                        }

                        if(!exist){
                            Deadstar dd = new Deadstar(new Postion(posX,-150),enemySpeed/4, player);
                            dd.setDirection(new Postion((player.getPostion().getX()-dd.getPostion().getX())/100, (player.getPostion().getY()-dd.getPostion().getY())/100));
                            enemies.add(dd);
                            lastAddEnemyTime = currentTime;
                        }


                    }else if(rand(200)<6&&enemySpeed>1){
                        TieBomber dd = new TieBomber(new Postion(posX,0),enemySpeed/3);
                        enemies.add(dd);
                        lastAddEnemyTime = currentTime;
                    }else if(rand(200)<10&&enemySpeed>2){
                        TieReaper dd = new TieReaper(new Postion(posX,0),enemySpeed*1.5);
                        dd.setDirection(new Postion((player.getPostion().getX()-dd.getPostion().getX())/40, (player.getPostion().getY()-dd.getPostion().getY())/40));
                        enemies.add(dd);
                        lastAddEnemyTime = currentTime;
                    }else if(rand(200)<2&&enemySpeed>5){


                        boolean exist = false;
                        for(Enemy e: enemies){
                            if (e instanceof Deadstar){
                                exist = true;
                            }
                        }

                        if(!exist){
                            StarDestroyer dd = new StarDestroyer(new Postion(posX,-200),enemySpeed/9);
                            enemies.add(dd);
                            lastAddEnemyTime = currentTime;
                        }



                    }else if(rand(200)<15){
                        Asteroid dd = new Asteroid(new Postion(posX,0),enemySpeed);
                        enemies.add(dd);
                        lastAddEnemyTime = currentTime;
                    }
                }

                if((currentTime-startTime)/utilitiesSpeed%1==0 && (currentTime-startTime)/utilitiesSpeed>0 && lastAddEnemyTime/utilitiesSpeed!=currentTime/utilitiesSpeed){
                    if(rand(200)<10&&enemySpeed>2){
                        Nuke ME = new Nuke(new Postion(0,0),0);
                        enemies.add(ME);
                        lastAddEnemyTime = currentTime;
                    }
                    else if(rand(200)<10){
                        Fixing ME = new Fixing(new Postion(0,0),0);
                        enemies.add(ME);
                        lastAddEnemyTime = currentTime;
                    }else if(rand(200)<10){
                        Ammo ME = new Ammo(new Postion(0,0),0);
                        enemies.add(ME);
                        lastAddEnemyTime = currentTime;
                    }
                }
                int index = 0;
                for(int i=0;i<enemies.size();i++){

                    Enemy enemy = enemies.get(i);

                    if(enemy instanceof Deadstar){
                        enemy.setDirection(new Postion((player.getPostion().getX()-enemy.getPostion().getX())/400, (player.getPostion().getY()-50-enemy.getPostion().getY())/400));
                    }else if(enemy instanceof TieElite){
                        enemy.setDirection(new Postion((player.getPostion().getX()-enemy.getPostion().getX())/80, (player.getPostion().getY()-50-enemy.getPostion().getY())/80));
                    }

                    boolean isFire = rand(200)<5;
                    if(isFire){
                        if(enemy instanceof Deadstar){
                            ((Deadstar)enemy).attemptToFire(player);
                            playAudio(lazer, (float) .1);
                        }
//                        playAudio(lazer);
                        enemy.attemptToFire();
                    }
                    drawEnemy(enemy);
                }
                //descending
                enemies.sort((o1, o2) -> {
                    if(o1.postion.y>o2.postion.y)return 1;
                    else if(o1.postion.y<o2.postion.y)return -1;
                    else return 0;
                });
                // enemy bullets move and check collide
                for (int i=0;i<enemies.size();i++){
                    Enemy enemy = enemies.get(i);
                    enemy.move();
                    boolean isCollide = false;
                    index = 0;
                    // player and enemy bullets collide
                    while (index<enemy.bullets.size()){
                        Bullet bullet = enemy.bullets.get(index);
                        if(bullet.postion.y>HEIGHT || bullet.postion.x <0 || bullet.postion.x>WIDTH){
                            enemy.bullets.remove(index);

                            continue;
                        }
                        else if(isCollide(player,bullet)) {

                            playAudio(hit);
                            drawImage(img_BM, bullet.getPostion().getX(), bullet.getPostion().getY(), 30,30);

                            enemy.bullets.remove(index);
                            player.subHP(1);
                            if (player.getHP() <= 0) {

                                drawDeathEffect = true;
                                deadTime = System.currentTimeMillis();
                            }
                            continue;
                        }
                        bullet.move();

                        index++;
                    }
                    //player bullets collide with enemy
                    index = 0;
                    while (index<player.getBullets().size()){

                        Bullet bullet = player.getBullets().get(index);
                        if(exposureToEmery(bullet,enemy)){



                                if(enemy instanceof Fixing){

                            }else if(enemy instanceof Ammo){

                            }else if(enemy instanceof Nuke){

                            }else{
                                playAudio(hit);
                                drawImage(img_BM, bullet.getPostion().getX(), bullet.getPostion().getY(), 30,30);
                                player.getBullets().remove(index);
                            }
                            enemy.setHP(enemy.getHP()-1);


                            if(enemy.getHP()==0){
                                if(enemy instanceof Deadstar || enemy instanceof StarDestroyer){
                                    playAudio(bigBomb);
                                    drawImage(img_NK, enemies.get(i).getPostion().getX(), enemies.get(i).getPostion().getY(), enemies.get(i).getWidth(),enemies.get(i).getHeight());


                                    player.addScore(150);
                                    player.setMidEnemyDestroyed(player.getMidEnemyDestroyed()+1);
                                }
                                drawImage(img_NK, enemies.get(i).getPostion().getX(), enemies.get(i).getPostion().getY(), enemies.get(i).getWidth(),enemies.get(i).getHeight());
                                playAudio(littleBomb);
                                if(enemy instanceof TieElite || enemy instanceof TieReaper){
                                    player.addScore(30);
                                    player.setSmallEnemyDestroyed(player.getSmallEnemyDestroyed()+1);
                                }
                                else {
                                    player.addScore(10);
                                    player.setSmallEnemyDestroyed(player.getSmallEnemyDestroyed()+1);
                                }
                                System.out.println("hit enemy");
                                enemies.remove(i);
                                i--;
//                                player.getBullets().remove(index);
                                isCollide = true;
                                break;
                            }

                        }
                        index++;
                    }
                    if(isCollide)continue;

                    //enemy collide with player
                    if(isCollide(player,enemy)){
                        if(enemy instanceof Fixing){

                        }else if(enemy instanceof Nuke){
                            if(rand(2)==1){
                                if(NUKE<=3){
                                    NUKE++;
                                }
                            }else{

                            }
                            NUKE++;
                        }else if(enemy instanceof Ammo){
                            if(AMMO<4){
                                AMMO++;
                            }
                        }else {
                            drawImage(img_NK, enemies.get(i).getPostion().getX(), enemies.get(i).getPostion().getY(), enemies.get(i).getWidth(),enemies.get(i).getHeight());
                            if(enemies.get(i) instanceof Deadstar || enemies.get(i) instanceof StarDestroyer){
                                playAudio(bigBomb);
                                }else {
                                playAudio(littleBomb);
                            }
                        }

                        player.subHP(enemies.get(i).getHP());
                        enemies.remove(i);
                        i--;
                        if (player.getHP() <= 0) {
                            AMMO = 0;
                            NUKE = 0;
                            bgn1 = 0;
                            bgn2 = 0;
                            drawDeathEffect = true;
                            deadTime = System.currentTimeMillis();
                        }
                    }

                }


                if(drawDeathEffect){
                    drawDeath();
//                    return;
                }
                else{
                    drawPlayer(player);
                }

                //remove bullets out of screen

                while (index<player.getBullets().size()){
                    Bullet bullet = player.getBullets().get(index);
                    boolean isCollide = false;
//                    //bullet collide with enemy
//                    for(int i=0;i<enemies.size();i++){
//                        Enemy enemy = enemies.get(i);
//                        if(exposureToEmery(bullet,enemy)){
//
//                            enemies.remove(i);
//                            player.getBullets().remove(index);
//                            isCollide = true;
//                            break;
//                        }
//                    }
//                    if(isCollide)continue;

                    if(bullet.postion.y<0){
                        player.getBullets().remove(index);

                    }else {
                        index++;
                    }
                }

                //player bullets move
                for (int i=0;i<player.getBullets().size();i++){
                    Bullet bullet = player.getBullets().get(i);
                    bullet.move();
                }


            }
            void drawPlayer(Player player){
                changeColor(Color.BLACK);

//                drawImage(loadImage("src/images/SD1.png"),player.postion.x - player.moveSpeedX*2.5,player.postion.y - player.moveSpeedY*2.5,player.width,player.height);
//                drawImage(loadImage("src/images/SD2.png"),player.postion.x - player.moveSpeedX*2,player.postion.y - player.moveSpeedY*2,player.width,player.height);
//                drawImage(loadImage("src/images/SD3.png"),player.postion.x - player.moveSpeedX*1,player.postion.y - player.moveSpeedY*1.5,player.width,player.height);
                drawImage(loadImage("src/images/SD1.png"),player.postion.x - player.moveSpeedX/2,player.postion.y - player.moveSpeedY/2,player.width,player.height);
                drawImage(loadImage("src/images/SD2.png"),player.postion.x - player.moveSpeedX/4,player.postion.y - player.moveSpeedY/4,player.width,player.height);
                drawImage(loadImage("src/images/SD3.png"),player.postion.x - player.moveSpeedX,player.postion.y - player.moveSpeedY,player.width,player.height);
//                drawRectangle(player.postion.x,player.postion.y,20,20);
                drawImage(img_player,player.postion.x,player.postion.y,player.width,player.height);

                for (int i=0;i<player.getBullets().size();i++){
                    Bullet bullet = player.getBullets().get(i);
                    drawBullet(bullet);
                }
            }
            void drawEnemy(Enemy enemy){
                changeColor(Color.BLACK);
//                drawRectangle(enemy.postion.x,enemy.postion.y,enemy.width,enemy.height);
                if(enemy instanceof TieElite){
                    drawImage(img_mediumEnemy,enemy.postion.x,enemy.postion.y,enemy.width,enemy.height);
//                    drawRectangle(enemy.postion.x,enemy.postion.y,enemy.width,enemy.height);
                } else if(enemy instanceof Deadstar){
                    drawImage(img_deadstar,enemy.postion.x,enemy.postion.y,enemy.width,enemy.height);
//                    drawRectangle(enemy.postion.x,enemy.postion.y,enemy.width,enemy.height);
                }
                else if(enemy instanceof Fixing){
                    drawImage(img_F,enemy.postion.x,enemy.postion.y,enemy.width,enemy.height);
//                    drawRectangle(enemy.postion.x,enemy.postion.y,enemy.width,enemy.height);
                }
                else if(enemy instanceof Ammo){
                    drawImage(img_A,enemy.postion.x,enemy.postion.y,enemy.width,enemy.height);
//                    drawRectangle(enemy.postion.x,enemy.postion.y,enemy.width,enemy.height);
                }
                else if(enemy instanceof Nuke){
                    drawImage(img_N,enemy.postion.x,enemy.postion.y,enemy.width,enemy.height);
//                    drawRectangle(enemy.postion.x,enemy.postion.y,enemy.width,enemy.height);
                }
                else if(enemy instanceof TieReaper){
                    drawImage(img_TieReaper,enemy.postion.x,enemy.postion.y,enemy.width,enemy.height);
//                    drawRectangle(enemy.postion.x,enemy.postion.y,enemy.width,enemy.height);
                }else if(enemy instanceof Asteroid){

                    if(rand(2)==1){
                        drawImage(img_Asteroid,enemy.postion.x,enemy.postion.y,enemy.width,enemy.height);
                    }  else {
                        drawImage(loadImage("src/images/AS2.png"),enemy.postion.x,enemy.postion.y,enemy.width,enemy.height);
                    }


//                    drawRectangle(enemy.postion.x,enemy.postion.y,enemy.width,enemy.height);
                }
                else if(enemy instanceof TieBomber){
                    drawImage(img_TieBomber,enemy.postion.x,enemy.postion.y,enemy.width,enemy.height);
//                    drawRectangle(enemy.postion.x,enemy.postion.y,enemy.width,enemy.height);
                }else if(enemy instanceof StarDestroyer){
                    drawImage(img_TieStarDestroyer,enemy.postion.x,enemy.postion.y,enemy.width,enemy.height);
//                    drawRectangle(enemy.postion.x,enemy.postion.y,enemy.width,enemy.height);
                }



                else{
                    drawImage(img_smallEnemy,enemy.postion.x,enemy.postion.y,enemy.width,enemy.height);
                }
                for (int i=0;i<enemy.bullets.size();i++){
                    Bullet bullet = enemy.bullets.get(i);
                    drawBullet(bullet);
                }
            }
            void drawBullet(Bullet bullet){
                changeColor(bullet.color);
//                drawSolidCircle( bullet.postion.x,bullet.postion.y,2);
                if(bullet.getColor()==Color.RED){
                    drawImage(img_GB,bullet.postion.x,bullet.postion.y,6,16);
                }else if(bullet.getColor()==Color.BLUE){
                    drawImage(img_RB,bullet.postion.x,bullet.postion.y,6,16);
                }else if(bullet.getColor()==Color.PINK){
                    drawImage(img_MS,bullet.postion.x,bullet.postion.y,10,20);
                }else if(bullet.getColor()==Color.GREEN){
                    drawImage(img_BB,bullet.postion.x,bullet.postion.y,10,20);
                }else if(bullet.getColor()==Color.CYAN){
                    drawImage(img_DB,bullet.postion.x,bullet.postion.y,10,10);
                }
            }
            void rankPage(){
                drawImage(loadImage("src/images/rk.jpg"), 0, 0, 500, 800);
                mGraphics.setColor(Color.WHITE);
//                drawText((double) WIDTH /2 - 20,100,"Rank","serif",20);
                drawText((double) WIDTH /2 - 40,HEIGHT-60,"Back(ESC)","serif",20);
                OperationalData operationalData = new OperationalData();
                List<Result>results = OperationalData.loadData();
                String title = "LifeTime        Jet       Monster      Score";
                drawText((double) WIDTH /5 - 20,200,title,"serif",20);
                for(int i=0;i<Math.min(results.size(),10);i++){
                    Result result = results.get(i);
                    StringBuilder text = new StringBuilder(String.format("%5s%03d%10s", " ",result.getSurvivalTime()," "));
//                    System.out.println("text:"+text.length()+text.toString());
                    text.append(String.format("%03d%8s", result.getNumberOfSmallEnemyDestroyed()," "));
                    text.append(String.format("%03d%6s", result.getNumberOfMediumEnemyDestroyed()," "));

                    text.append(String.format("   %04d%10s", result.getScore()," "));

                    drawText((double) WIDTH /5 - 20,250+i*45, text.toString(),"Arial",20);
                }

            }
            public void clear(){
                clearBackground(WIDTH,HEIGHT);
            }

            //elements
            Image img_player;
            Image img_smallEnemy;
            Image img_mediumEnemy;
            Image img_deadstar;
            Image img_TieBomber;
            Image img_TieReaper;
            Image img_TieStarDestroyer;
            Image img_Asteroid;
            Image img_F;
            Image img_A;
            Image img_N;
            //ammo
            Image img_GB;
            Image img_RB;
            Image img_DB;
            Image img_MS;
            Image img_BB;

            //effect
            Image img_BM;
            Image img_NK;

            //Audio
            AudioClip bigBomb;
            AudioClip littleBomb;
            AudioClip lazer;
            AudioClip move;
            AudioClip hit;
            AudioClip nuke;
            AudioClip MS;


            List<Image>player_fail_images;
            public void initGame(){

                player_fail_images = new ArrayList<>();
                for(int i=1;i<=4;i++){
                    player_fail_images.add(loadImage("src/images/Player_fail_"+i+".png"));
                }
                img_player  = loadImage("src/images/MB.png");
                img_smallEnemy = loadImage("src/images/TF.png");
                img_mediumEnemy = loadImage("src/images/TE.png");
                img_deadstar = loadImage("src/images/dd.png");
                img_Asteroid = loadImage("src/images/AS.png");
                img_TieBomber = loadImage("src/images/TB.png");
                img_TieReaper = loadImage("src/images/TR.png");
                img_TieStarDestroyer = loadImage("src/images/SD.png");
                img_F = loadImage("src/images/heal.png");
                img_N = loadImage("src/images/nuke.png");
                img_A = loadImage("src/images/ammo.png");

                img_BM = loadImage("src/images/BM.png");
                img_GB = loadImage("src/images/GB.png");
                img_RB = loadImage("src/images/RB.png");
                img_NK = loadImage("src/images/NK.png");
                img_BB = loadImage("src/images/BB.png");
                img_MS = loadImage("src/images/MS.png");
                img_DB = loadImage("src/images/DB.png");


                bigBomb = loadAudio("src/audio/bomB.wav");
                littleBomb = loadAudio("src/audio/bomL.wav");
                lazer = loadAudio("src/audio/lazer.wav");
                move = loadAudio("src/audio/move.wav");
                hit = loadAudio("src/audio/hit.wav");
                nuke = loadAudio("src/audio/nuke.wav");
                MS = loadAudio("src/audio/MS.wav");

                player = new Player(25,new Postion(100,HEIGHT-150),50);
                enemies = new CopyOnWriteArrayList<>();
//                for(int i=0;i<3;i++){
//                   //enemy
//                    enemies.add(new Enemy(new Postion(0+80*i,100),500,enemySpeed));
//                }
//                enemies.add(new MediumEnemy(new Postion(400,200),500,enemySpeed));
            }

            @Override
            public void paintComponent() {
                if(thisPage.equals(welcomePage)){
                    clear();

                    welcomePage();
                }
                if(thisPage.equals(gamePage)){
                    clear();
//                    startAudioLoop(start);
                    gamePage();
                }
                if(thisPage.equals(rankPage)){
                    clear();
//                    startAudioLoop(start);
                    rankPage();
                }
                changeColor(Color.RED);
                update(5);

            }
            void welcomePage(){

                drawImage(loadImage("src/images/background.jpg"),0,0,502,800);
                startAudioLoop(IP);
                mGraphics.setColor(white);
                drawText((double) WIDTH /2 - 25 ,460,"Start(S)","serif",20);
                drawText((double) WIDTH /2 - 25, 560,"Rank(R)","serif",20);
                drawText((double) WIDTH /2 - 25,660,"Quit(Q)","serif",20);
//                startAudioLoop(start);
            }
            public void welcomePageKeyPressed(KeyEvent event){
                switch (event.getKeyCode()){
                    case KeyEvent.VK_S:

                        thisPage = gamePage;
                        initGame();
                        break;
                    case KeyEvent.VK_R:
                        thisPage = rankPage;
                        break;
                    case KeyEvent.VK_Q:
                        quit();
                        break;
                }
            }

            public void keyReleased(KeyEvent e) {
                // If player releases left key
                if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    // Stop moving horizontally
                    player.setMoveSpeedX(0);
                }
                // If player releases right key
                if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player.setMoveSpeedX(0);
                }
                // If player releases left key
                if(e.getKeyCode() == KeyEvent.VK_UP) {
                    // Stop moving horizontally
                    player.setMoveSpeedY(0);
                }
                // If player releases right key
                if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    player.setMoveSpeedY(0);
                }

            }

            @Override
            public void update(double dt) {

            }

            public void gamePageKeyPressed(KeyEvent event){
                switch (event.getKeyCode()){
                    case KeyEvent.VK_ESCAPE:
                        enemySpeed = 1;
                        generatingSpeed = 149 + 50*30;
                        difUpdate = 100;
                        NUKE = 1;
                        AMMO = 0;

                        thisPage = welcomePage;
                        break;
                    //arrow keys
                    case KeyEvent.VK_UP:
                        player.moveUp();
                        if(currentTime-player.lastMovingTime>player.coolingTime*3){
                            player.lastMovingTime = currentTime;
//                            playAudio(move);
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        player.moveDown();
                        if(currentTime-player.lastMovingTime>player.coolingTime*3){
                            player.lastMovingTime = currentTime;
//                            playAudio(move);
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        player.moveLeft();
                        if(currentTime-player.lastMovingTime>player.coolingTime*3){
                            player.lastMovingTime = currentTime;
//                            playAudio(move);
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        player.moveRight();
                        if(currentTime-player.lastMovingTime>player.coolingTime*3){
                            player.lastMovingTime = currentTime;
//                            playAudio(move);
                        }
                        break;
                    case KeyEvent.VK_E:
                        if(NUKE>0){
                            bgn1 = 0;
                            bgn2 = 0;
                            playAudio(move);
                            enemies.clear();
                            NUKE--;
                        }

                        break;

                    //space  Attempt to fire
                    case KeyEvent.VK_SPACE:
                        if(player.attemptToFire(AMMO, enemies)){
                            if(AMMO<=2){
                                playAudio(lazer, (float).1);
                            }else {
                                playAudio(lazer, (float) .1);
                                playAudio(MS);
                            }
                        };
                        break;
                }

            }

            public void rankPageKeyPressed(KeyEvent event){
                switch (event.getKeyCode()){
                    case KeyEvent.VK_ESCAPE:
                        thisPage = welcomePage;
                        break;
                }
            }
            @Override
            public void keyPressed(KeyEvent event) {
                if(thisPage.equals(welcomePage)){
                    welcomePageKeyPressed(event);
                }
                if(thisPage.equals(gamePage)){
                    gamePageKeyPressed(event);
                }
                if(thisPage.equals(rankPage)){
                    rankPageKeyPressed(event);
                }

            }
            void quit(){
                System.exit(0);
            }
        };
        gameEngine.setupWindow(WIDTH,HEIGHT);
        gameEngine.setWindowSize(WIDTH,HEIGHT);
        gameEngine.createGame(gameEngine,FPS);


    }
    public static void main(String[] args) {
        new Main();

    }


}