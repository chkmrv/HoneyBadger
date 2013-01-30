import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WorldGenerator {
    private final Game game;

    public WorldGenerator(Game game) {
        this.game = game;
    }

    public void generateTiles(HashMap<GridPosition, Integer> tiles) {
        // 0 1
        int[][] tileMap = new int[200][200];
        for (int x = 0; x < tileMap.length; x++) {
            for (int y = 0; y < tileMap.length; y++) {
                tileMap[x][y] = (int) (Math.random() * 5);
            }
        }

        reduceNoise(tileMap, 7, 3);
        reduceNoise(tileMap, 7, 4);
        reduceNoise(tileMap, 7, 5);
        reduceNoise(tileMap, 10, 6);
        reduceNoise(tileMap, 10, 6);

        for (int x = 0; x < tileMap.length; x++) {
            for (int y = 0; y < tileMap.length; y++) {
                tiles.put(new GridPosition((x - 100) * 32 - 1, (y - 100) * 32 - 1), tileMap[x][y]);
            }
        }
    }

    private void reduceNoise(int[][] tileMap, int max, int min) {
        for (int x = 0; x < tileMap.length; x++) {
            for (int y = 0; y < tileMap.length; y++) {
                tileMap[x][y] = getTypeNeighbors(tileMap, x, y, max, min);
            }
        }
    }

    private int getTypeNeighbors(int[][] tileMap, int x, int y, int max, int min) {
        int resultType = 4;
        int maxCount = 0;
        int grassCount = getCountNeighbors(tileMap, x, y, MyTile.GRASS);
        int grass2Count = getCountNeighbors(tileMap, x, y, MyTile.FOREST_GRASS);
        int dirtCount = getCountNeighbors(tileMap, x, y, MyTile.DIRT);
        int stoneCount = getCountNeighbors(tileMap, x, y, MyTile.STONE);
        int waterCount = getCountNeighbors(tileMap, x, y, MyTile.WATER);
        if (maxCount < grassCount) {
            maxCount = grassCount;
            resultType = MyTile.GRASS;
        }
        if (maxCount < grass2Count) {
            maxCount = grass2Count;
            resultType = MyTile.FOREST_GRASS;
        }
        if (maxCount < dirtCount) {
            maxCount = dirtCount;
            resultType = MyTile.DIRT;
        }
        if (maxCount < stoneCount) {
            maxCount = stoneCount;
            resultType = MyTile.STONE;
        }
        if (maxCount < waterCount) {
            maxCount = waterCount;
            resultType = MyTile.WATER;
        }
        if (maxCount < min) {
            return tileMap[x][y];
        } else if (maxCount >= max) {
            return (int) (Math.random() * 5);
        } else {
            return resultType;
        }

        //  return getCountNeighbors(tileMap, x, y, type);
    }

    private int getCountNeighbors(int[][] tileMap, int x, int y, int type) {
        int result = 0;

        result = result + checkTileValue(tileMap, x + 1, y + 1, type);
        result = result + checkTileValue(tileMap, x + 1, y, type);
        result = result + checkTileValue(tileMap, x + 1, y - 1, type);
        result = result + checkTileValue(tileMap, x, y + 1, type);
        result = result + checkTileValue(tileMap, x, y, type);
        result = result + checkTileValue(tileMap, x, y - 1, type);
        result = result + checkTileValue(tileMap, x - 1, y + 1, type);
        result = result + checkTileValue(tileMap, x - 1, y, type);
        result = result + checkTileValue(tileMap, x - 1, y + 1, type);
        return result;
    }

    private int checkTileValue(int[][] tileMap, int x, int y, int type) {
        try {
            if (tileMap[x][y] == type) {
                return 1;
            } else {
                return 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }

    public void generateUnits(ArrayList<Located> units, HashMap<GridPosition, Integer> tiles) {

        for (Map.Entry<GridPosition, Integer> entry : tiles.entrySet()) {
            GridPosition gridPosition = entry.getKey();
            Integer tile = entry.getValue();
            if (MyTile.GRASS == tile) {
                generateUnitOnGrass(gridPosition, units);
            }
            if (MyTile.STONE == tile) {
                generateUnitOnStone(gridPosition, units);
            }
            if (MyTile.FOREST_GRASS == tile) {
                generateUnitOnForest(gridPosition, units);
            }
            // g.drawString(gridPosition.getX() + "", gridPosition.getX(), gridPosition.getY());
            // g.drawString(gridPosition.getY() + "", gridPosition.getX(), gridPosition.getY() + 16);
        }
    }

    private void generateUnitOnForest(GridPosition gridPosition, ArrayList<Located> units) {
        int randomValue = (int) (Math.random() * 100);
        if (randomValue < 55) {
            return;
        }
        int random = (int) (Math.random() * 10);

        if (random > 2) {
            units.add(new Fir((int) (gridPosition.getX() + Math.random() * 32), (int) (gridPosition.getY() - Math.random() * 32)));
        } else {
            units.add(new Brush((int) (gridPosition.getX() + Math.random() * 32), (int) (gridPosition.getY() - Math.random() * 32)));
        }
    }

    private void generateUnitOnStone(GridPosition gridPosition, ArrayList<Located> units) {
        int randomValue = (int) (Math.random() * 100);
        if (randomValue < 85) {
            return;
        }
        int random = (int) (Math.random() * 10);

        if (random > 3) {
            units.add(new Rock((int) (gridPosition.getX() + Math.random() * 32), (int) (gridPosition.getY() - Math.random() * 32)));
        } else {
            units.add(new Fir((int) (gridPosition.getX() + Math.random() * 32), (int) (gridPosition.getY() - Math.random() * 32)));
        }
    }



    private void generateUnitOnGrass(GridPosition gridPosition, ArrayList<Located> units) {
        int randomValue = (int) (Math.random() * 100);
        if (randomValue < 95) {
            return;
        }
        int random = (int) (Math.random() * 10);
        if (random > 3) {
            units.add(new Brush((int) (gridPosition.getX() + Math.random() * 32), (int) (gridPosition.getY() - Math.random() * 32)));
        } else {
            units.add(new Fir((int) (gridPosition.getX() + Math.random() * 32), (int) (gridPosition.getY() - Math.random() * 32)));
        }
    }
}