package com.krld.common;

import com.krld.model.*;

import java.util.ArrayList;

public class WorldGenerator {

    public static int[][] generateTiles() {
        int[][] tileMap = new int[200][200];
        for (int x = 0; x < tileMap.length; x++) {
            for (int y = 0; y < tileMap.length; y++) {
                tileMap[x][y] = (int) (Math.random() * 5);
            }
        }
        for (int i = 1; i < 5; i++) {
            tileMap = reduceNoise(tileMap, 6, 3);
        }
        for (int i = 1; i < 5; i++) {
            tileMap = reduceNoise(tileMap, 10, 3);
        }
        return tileMap;
    }

    private static int[][] reduceNoise(int[][] tileMap, int max, int min) {
        int[][] newTileMap = new int[200][200];
        System.out.println();
        System.out.println("=================");
        System.out.println("=====++++========");
        System.out.println("=================");
        System.out.println();
        for (int x = 0; x < tileMap.length; x++) {
            for (int y = 0; y < tileMap.length; y++) {
                System.out.print(tileMap[x][y] + " ");
                newTileMap[x][y] = getTypeNeighbors(tileMap, x, y, max, min);
            }
            System.out.println();
        }
        return newTileMap;
    }

    private static int getTypeNeighbors(int[][] tileMap, int x, int y, int max, int min) {
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

    private static int getCountNeighbors(int[][] tileMap, int x, int y, int type) {
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

    private static int checkTileValue(int[][] tileMap, int x, int y, int type) {
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

    public static void generateUnits(ArrayList<Located> units, int[][] tileMap, ArrayList<Moveable> moveables) {
        for (int x = 0; x < tileMap.length; x++) {
            for (int y = 0; y < tileMap.length; y++) {
                int realX = x * 32 - 1;
                int realY = y * 32 - 1;
                if (MyTile.GRASS == tileMap[x][y]) {
                    generateUnitOnGrass(realX, realY, units);
                    generateMoveablesOnGrass(realX, realY, moveables);
                }
                if (MyTile.STONE == tileMap[x][y]) {
                    generateUnitOnStone(realX, realY, units);
                }
                if (MyTile.FOREST_GRASS == tileMap[x][y]) {
                    generateUnitOnForest(realX, realY, units);
                    generateMoveablesOnForest(realX, realY, moveables);
                }
            }
        }
         /*
        for (Map.Entry<com.krld.common.GridPosition, Integer> entry : tiles.entrySet()) {
            com.krld.common.GridPosition gridPosition = entry.getKey();
            Integer tile = entry.getValue();
            if (com.krld.model.MyTile.GRASS == tile) {
                generateUnitOnGrass(gridPosition, units);
                generateMoveablesOnGrass(gridPosition, moveables);
            }
            if (com.krld.model.MyTile.STONE == tile) {
                generateUnitOnStone(gridPosition, units);
            }
            if (com.krld.model.MyTile.FOREST_GRASS == tile) {
                generateUnitOnForest(gridPosition, units);
                generateMoveablesOnForest(gridPosition, moveables);
            }
            // g.drawString(x + "", x, y);
            // g.drawString(y + "", x, y + 16);
        }
        */
    }

    private static void generateMoveablesOnForest(int x, int y, ArrayList<Moveable> moveables) {
        int randomValue = (int) (Math.random() * 200);
        if (randomValue < 199) {
            return;
        }
        int random = (int) (Math.random() * 10);
        if (random > 4) {
            moveables.add(new Boar((int) (x + Math.random() * 32), (int) (y - Math.random() * 32)));
        } else {
            moveables.add(new Wolf((int) (x + Math.random() * 32), (int) (y - Math.random() * 32)));
            moveables.add(new Wolf((int) (x + Math.random() * 32), (int) (y - Math.random() * 32)));
            moveables.add(new Wolf((int) (x + Math.random() * 32), (int) (y - Math.random() * 32)));
            moveables.add(new Wolf((int) (x + Math.random() * 32), (int) (y - Math.random() * 32)));
        }
    }

    private static void generateMoveablesOnGrass(int x, int y, ArrayList<Moveable> moveables) {
        int randomValue = (int) (Math.random() * 100);
        if (randomValue < 99) {
            return;
        }
        int random = (int) (Math.random() * 10);
        if (random > 1) {
            moveables.add(new Rabbit((int) (x + Math.random() * 32), (int) (y - Math.random() * 32)));
        } else {
            moveables.add(new Boar((int) (x + Math.random() * 32), (int) (y - Math.random() * 32)));
        }
    }

    private static void generateUnitOnForest(int x, int y, ArrayList<Located> units) {
        int randomValue = (int) (Math.random() * 100);
        if (randomValue < 55) {
            return;
        }
        int random = (int) (Math.random() * 10);

        if (random > 2) {
            units.add(new Fir((int) (x + Math.random() * 32), (int) (y - Math.random() * 32)));
        } else {
            units.add(new Brush((int) (x + Math.random() * 32), (int) (y - Math.random() * 32)));
        }
    }

    private static void generateUnitOnStone(int x, int y, ArrayList<Located> units) {
        int randomValue = (int) (Math.random() * 100);
        if (randomValue < 85) {
            return;
        }
        int random = (int) (Math.random() * 10);

        if (random > 3) {
            units.add(new Rock((int) (x + Math.random() * 32), (int) (y - Math.random() * 32)));
        } else {
            units.add(new Fir((int) (x + Math.random() * 32), (int) (y - Math.random() * 32)));
        }
    }


    private static void generateUnitOnGrass(int x, int y, ArrayList<Located> units) {
        int randomValue = (int) (Math.random() * 100);
        if (randomValue < 95) {
            return;
        }
        int random = (int) (Math.random() * 10);
        if (random > 3) {
            units.add(new Brush((int) (x + Math.random() * 32), (int) (y - Math.random() * 32)));
        } else {
            units.add(new Fir((int) (x + Math.random() * 32), (int) (y - Math.random() * 32)));
        }
    }
}