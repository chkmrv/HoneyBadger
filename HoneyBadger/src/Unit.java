import static java.lang.StrictMath.hypot;

public abstract class Unit implements MyDrawable, Located {
    private int x;
    private int y;

    public abstract void draw();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    public double getDistanceTo(double x, double y) {
        return hypot(x - this.x, y - this.y);
    }

    public double getDistanceTo(Located unit) {
        return getDistanceTo(unit.getX(), unit.getY());
    }

}
