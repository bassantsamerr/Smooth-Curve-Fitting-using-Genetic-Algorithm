package smoothcurvega;
/**
 * @author Reem, Bassant, Mahmoud
 */
public class point {
    private double x, y;

    public point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    @Override
    public String toString() {
        return "point{" + "x=" + x + ", y=" + y + '}';
    }
    
}
