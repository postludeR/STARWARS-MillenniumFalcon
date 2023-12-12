import java.awt.*;

public class Bullet {
    Postion postion;
    Postion direction;
    Color color;
    public Bullet(Postion postion,Postion direction,Color color){
        this.postion = postion;
        this.direction = direction;
        this.color = color;
    }
    public void move(){
        postion.x += direction.x;
        postion.y += direction.y;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
