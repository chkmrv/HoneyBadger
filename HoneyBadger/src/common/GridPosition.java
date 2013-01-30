package common;

public class GridPosition {
    private final int SIZE = 32;
    private int x;
    private int y;
    private static int count = 0;

    public GridPosition(int x, int y) {
        if (x >= 0) {
            this.x = x - x % SIZE;
        } else {
            this.x = (int) ((Math.floor(x / -SIZE) + 1) * -SIZE);
        }
        if (y >= 0) {

            this.y = y - y % SIZE;
        } else {
            this.y = (int) ((Math.floor(y / -SIZE) + 1) * -SIZE);
        }

        count = getCount() + 1;
        System.out.println("common.GridPosition count: " + count);
    }

    public static int getCount() {
        return count;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GridPosition that = (GridPosition) o;

        if (x != that.x) return false;
        if (y != that.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
