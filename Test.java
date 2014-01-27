import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Test {
	public static void main(String[] args) {
		int choice = 0;
		Matrix a = new Matrix();
		Matrix b = new Matrix();

		Scanner in = new Scanner(System.in);

		do {
			try {
				printMenu();
				choice = in.nextInt();
				switch (choice) {
					case 1:
						a = buildMatrix();
						break;
					case 2:
						b = buildMatrix();
						break;
					case 3:
						System.out.println(a.transpose());
						break;
					case 4:
						System.out.println(a.cofactor());
						break;
					case 5:
						System.out.println(a.determinant());
						break;
					case 6:
						System.out.println(a.multiply(b));
						break;
					case 7:
						System.out.print("multiply by: ");
						System.out.println(a.multiply(in.nextInt()));
						break;
					case 8:
						System.out.println(a.inverse());
						break;
					case 9: 
						System.out.println(a.dot(b));
						break;
					case 10:
						System.out.println(a.cross(b));
						break;
				}
			} catch (Matrix.MatrixException e) { 
				System.out.println(e.getMessage()); 
			} catch (InputMismatchException e) {
				System.out.println("... numbers only");
			}

		} while (choice != 11);
	}

	public static Matrix buildMatrix() {
		Matrix result;
		int rows, cols;
		Scanner in = new Scanner(System.in);

		System.out.print("# rows: ");
		rows = in.nextInt();
		System.out.print("# cols: ");
		cols = in.nextInt();

		result = new Matrix(rows, cols);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				for (int k = 0; k < rows; k++) {
					for (int l = 0; l < cols; l++) {
						if (k == i && l == j) System.out.print("[" + result.matrix[k][l] + "]\t");
						else System.out.print(result.matrix[k][l] + "\t");
					}

					System.out.println();
				}

				System.out.print("row " + i + ", col " + j + ": ");
				result.matrix[i][j] = in.nextInt();
			}
		}

		return result;
	}

	public static void printMenu() {
		System.out.println("1. set matrix a");
		System.out.println("2. set matrix b");
		System.out.println("3. transpose a");
		System.out.println("4. cofactor matrix of a");
		System.out.println("5. determinant of a");
		System.out.println("6. a * b");
		System.out.println("7. a * scalar");
		System.out.println("8. inverse of a");
		System.out.println("9. a dot b");
		System.out.println("10. a cross b");
		System.out.println("11. exit");
		System.out.print("> ");
	}
}