import java.util.Scanner;

public class MatrixFlip {
    public static void printFlippedMatrix(char[][] matrix) {
        int n=matrix.length, m=matrix[0].length;

        for(int i=n-1; i>=0; i--) {
            for(int j=m-1; j>=0; j--)
                System.out.print(matrix[i][j]);
            System.out.println();
        }
    }
}