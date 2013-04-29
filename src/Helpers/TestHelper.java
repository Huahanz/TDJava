package Helpers;

import balls.Ball;

public class TestHelper {

	public static void print(String message){
		System.out.println(message);
	}
	
	public static void dumpBall(Ball ball){
		System.out.println("BALL :: " + ball.getClass().getName() + " (" + ball.getX() +" , " + ball.getY() + ") " );
	}
	public static void printTwoDArray(int[][] array, int n, int m) {
		for (int a = 0; a < n; a++) {
			for (int b = 0; b < m; b++) {
				System.out.print(array[a][b] + ", ");
			}
			System.out.println();
		}
		System.out.println();

	}
	public static void printTwoDArray(float[][] array, int n, int m) {
		for (int a = 0; a < n; a++) {
			for (int b = 0; b < m; b++) {
				System.out.print(array[a][b] + ", ");
			}
			System.out.println();
		}
		System.out.println();
		
	}

	public static void printTwoDArray(byte[][] array, int n, int m) {
		for (int a = 0; a < n; a++) {
			for (int b = 0; b < m; b++) {
				System.out.print(array[a][b] + ", ");
			}
			System.out.println();
		}
		System.out.println();

	}

	public static void printTwoDArray(boolean[][] array, int n, int m) {
		for (int a = 0; a < n; a++) {
			for (int b = 0; b < m; b++) {
				System.out.print(array[a][b] + ", ");
			}
			System.out.println();
		}
		System.out.println();

	}

	public static void printFourDArray(int[][][][] array, int n, int m) {
		for (int a = 0; a < n; a++) {
			for (int b = 0; b < m; b++) {
				for (int c = 0; c < n; c++) {
					for (int d = 0; d < m; d++) {
						System.out.print(array[a][b][c][d] + ", ");
					}
					System.out.println();
				}
				System.out.println();
				System.out.println();
			}
			System.out.println();
			System.out.println();
			System.out.println();
		}
		System.out.println();

	}

	public static void printFourDArray(byte[][][][] array, int n, int m) {
		for (int a = 0; a < n; a++) {
			for (int b = 0; b < m; b++) {
				for (int c = 0; c < n; c++) {
					for (int d = 0; d < m; d++) {
						System.out.print(array[a][b][c][d] + ", ");
					}
					System.out.println();
				}
				System.out.println();
				System.out.println();
			}
			System.out.println();
			System.out.println();
			System.out.println();
		}
		System.out.println();

	}

	public static void printFourDArray(boolean[][][][] array, int n, int m) {
		for (int a = 0; a < n; a++) {
			for (int b = 0; b < m; b++) {
				for (int c = 0; c < n; c++) {
					for (int d = 0; d < m; d++) {
						System.out.print(array[a][b][c][d] + ", ");
					}
					System.out.println();
				}
				System.out.println();
				System.out.println();
			}
			System.out.println();
			System.out.println();
			System.out.println();
		}
		System.out.println();

	}

}