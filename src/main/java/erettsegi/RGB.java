package erettsegi;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RGB {

    public static void main(String[] args) {
        int[][][] image = readImage("kep.txt");
        Scanner scanner = new Scanner(System.in);

        // 1. feladat: kép beolvasása
        System.out.println("1. feladat:");

        // 2. feladat: adott pont RGB színének kiírása
        System.out.println("2. feladat:");
        System.out.println("Kérem egy képpont adatait!");
        int row, column;
        do {
            System.out.print("Sor: ");
            row = scanner.nextInt();
            System.out.print("Oszlop: ");
            column = scanner.nextInt();
            if (row < 1 || row > 360 || column < 1 || column > 640) {
                System.out.println("Hibás adat! A sor és oszlop értékeinek 1 és 360, illetve 1 és 640 között kell lenniük.");
            }
        } while (row < 1 || row > 360 || column < 1 || column > 640);
        int[] color = image[row - 1][column - 1];
        System.out.println("A képpont színe RGB(" + color[0] + "," + color[1] + "," + color[2] + ")");

        // 3. feladat: világos képpontok számolása
        System.out.println("3. feladat:");
        int brightPoints = countBrightPoints(image);
        System.out.println("A világos képpontok száma: " + brightPoints);

        // 4. feladat: legsötétebb pontok
        System.out.println("4. feladat:");
        int darkestSum = findDarkestSum(image);
        System.out.println("A legsötétebb pont RGB összege: " + darkestSum);
        System.out.println("A legsötétebb pixelek színe:");
        printPixelsWithSum(image, darkestSum);

        // 5. és 6. feladat: felhő határvonalának meghatározása
        System.out.println("6. feladat:");
        int cloudTopRow = findCloudRow(image, 10, true);
        int cloudBottomRow = findCloudRow(image, 10, false);
        System.out.println("A felhő legfelső sora: " + cloudTopRow);
        System.out.println("A felhő legalsó sora: " + cloudBottomRow);
    }

    // 1. feladat: kép beolvasása
    private static int[][][] readImage(String filename) {
        int[][][] image = new int[360][640][3];
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            for (int i = 0; i < 360; i++) {
                for (int j = 0; j < 640; j++) {
                    for (int k = 0; k < 3; k++) {
                        image[i][j][k] = scanner.nextInt();
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return image;
    }

    // 3. feladat: világos képpontok számolása
    private static int countBrightPoints(int[][][] image) {
        int count = 0;
        for (int i = 0; i < 360; i++) {
            for (int j = 0; j < 640; j++) {
                int sum = image[i][j][0] + image[i][j][1] + image[i][j][2];
                if (sum > 600) {
                    count++;
                }
            }
        }
        return count;
    }

    // 4. feladat: legsötétebb pontok keresése
    private static int findDarkestSum(int[][][] image) {
        int minSum = Integer.MAX_VALUE;
        for (int i = 0; i < 360; i++) {
            for (int j = 0; j < 640; j++) {
                int sum = image[i][j][0] + image[i][j][1] + image[i][j][2];
                if (sum < minSum) {
                    minSum = sum;
                }
            }
        }
        return minSum;
    }

    // 4. feladat: legsötétebb pontok színének kiírása
    private static void printPixelsWithSum(int[][][] image, int sum) {
        for (int i = 0; i < 360; i++) {
            for (int j = 0; j < 640; j++) {
                if (image[i][j][0] + image[i][j][1] + image[i][j][2] == sum) {
                    System.out.println("RGB(" + image[i][j][0] + "," + image[i][j][1] + "," + image[i][j][2] + ")");
                }
            }
        }
    }

    // 5. feladat: felhő határvonalának meghatározása
    private static boolean hatar(int[][][] image, int row, int threshold) {
        for (int j = 1; j < 640; j++) {
            int diff = Math.abs(image[row][j][2] - image[row][j - 1][2]);
            if (diff > threshold) {
                return true;
            }
        }
        return false;
    }

    // 6. feladat: felhő határvonalának meghatározása a sorokban
    private static int findCloudRow(int[][][] image, int threshold, boolean top) {
        int startRow = top ? 0 : 359;
        int endRow = top ? 360 : -1;
        int step = top ? 1 : -1;

        for (int i = startRow; i != endRow; i += step) {
            if (hatar(image, i, threshold)) {
                return i;
            }
        }
        return -1;
    }
}
