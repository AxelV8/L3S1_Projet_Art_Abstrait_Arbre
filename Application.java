import java.awt.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        // parameters that can be used in both strategies
        int widthOfImage = 10000;
        int heightOfImage = 10000;
        String filename = "nameOfFile";
        long seed = 1;
        int widthOfLine = 5;
        double sameColorProbability = 0.3;


        // For the first strategy
        System.out.println("Basic strategy:");
        int maxLeaves = 20000;
        double divisionProportion = 0.1;
        int minSizeToDivide = 5;

        long startTime = System.currentTimeMillis();

        Kd_tree first_tree = new Kd_tree(widthOfImage, heightOfImage, maxLeaves, divisionProportion, minSizeToDivide, sameColorProbability, widthOfLine, seed);
        first_tree.generateRandomTree();
        Image curr_img = first_tree.toImage(filename);

        long endTime = System.currentTimeMillis();

        System.out.println("That took " + (endTime - startTime) + " milliseconds");
        System.out.println("You can now find the art you generated in the " + filename + ".png file.");



        // For the second strategy:
        System.out.println("Our strategy:");
        int nbOfPoints = 40;
        Random random = new Random(seed);
        // false for median point, true for FIFO division
        boolean divisionApproach = false;
        // probability used in the third approach to generate points
        double clusterProbability = 0.1;

        // To create randomly distributed points across the entire zone:
        ArrayList<Point> randomPoints = Point.generateArrayOfRandomPoints(nbOfPoints, widthOfImage, heightOfImage, widthOfLine, random);
        // To create randomly distributed points on a diagonal:
        ArrayList<Point> diagonalPoints = Point.generateDiagonalOfPoints(nbOfPoints, widthOfImage, heightOfImage, widthOfLine, random);
        // Generate a cluster of points
        ArrayList<Point> clusterPoints = Point.generateCluster(nbOfPoints, widthOfImage, heightOfImage, widthOfLine, random, clusterProbability);

        long secondStart = System.currentTimeMillis();

        // changed the first attribute depending on which generation you want to use
        Pq_tree second_tree = Pq_tree.generateBetterRandomTree(diagonalPoints, widthOfImage, heightOfImage, nbOfPoints, widthOfLine, sameColorProbability, seed, divisionApproach);
        second_tree.toImage(filename);

        long secondEnd = System.currentTimeMillis();
        System.out.println("That took " + (secondEnd - secondStart) + " milliseconds");
        System.out.println("You can now find the art you generated in the " + filename + ".png file.");

    }
}
