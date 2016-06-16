package de.opendata.multisnake;

import java.util.ArrayList;
import java.util.List;

import de.opendata.multisnake.tiles.Obstacle;

/**
 * Created by bambus on 16.06.16.
 */
public enum Level {

    CLASSIC_01(DefaultColor.BACKGROUND.getColor(), 100L, 1),
    DESERT_OF_DOOM_02(DefaultColor.BACKGROUND.getColor(), 80L, 10, 10,10,11,10,12,10,13,10,14,10,15,10,16,10,17,10,18,10,19,10,20,10),
    BEACH_OF_EXHAUSTING_03(DefaultColor.OBSTACLE_WOOD.getColor(), 60L, 15, 50,50,50,51,50,52,50,53,50,54,50,55,50,56,50,57);

    private int backgroundColor;
    private long frameSpeed;
    private int fruitCount;
    private List<Obstacle> obstacles;

    private Level(int backgroundColor, long frameSpeed, int fruitCount, int...obstaclePositions) {

        List<Obstacle> obstacles = new ArrayList<Obstacle>();

        for(int i = 0; i < obstaclePositions.length; i += 2) {

            int x = obstaclePositions[i];
            int y = obstaclePositions[i+1];

            obstacles.add(new Obstacle(x, y));

        }

        this.frameSpeed = frameSpeed;
        this.backgroundColor = backgroundColor;
        this.fruitCount = fruitCount;
        this.obstacles = obstacles;

    }

    private Level(int backgroundColor, int fruitCount, List<Obstacle> obstacles) {

        this.frameSpeed = frameSpeed;
        this.backgroundColor = backgroundColor;
        this.fruitCount = fruitCount;
        this.obstacles = obstacles;

    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getFruitCount() {
        return fruitCount;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public long getFrameSpeed() {
        return frameSpeed;
    }
}
