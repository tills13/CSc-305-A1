//ARRAY[ROW][COL]

public class Matrix {
	public double [][] matrix;
	public int cols = 3;
	public int rows = 3;

	public Matrix() {
		
	}

	/* Matrix(Double[][])
	 * constructor given a matrix
	 */
	public Matrix(double[][] matrix) {
		this.matrix = matrix;
		this.rows = matrix.length;
		this.cols = matrix[0].length;
	}

	/* Matrix(Int, Int)
	 * constructor given the size (non-square matrix)
	 */
	public Matrix(int rows, int cols) {
		this.matrix = new double[rows][cols];
		this.rows = rows;
		this.cols = cols;

		clear();
	}

	/* Matrix(Int)
	 * constructor given the size (square matrix)
	 */
	public Matrix(int size) {
		this.matrix = new double[size][size];
		this.rows = rows;
		this.cols = cols;

		clear();
	}

	/* Matrix(Int, Boolean)
	 * constructor given a size and if it's an identity matrix
	 */
	public Matrix(int size, boolean isIdentity) {
		this(size);

		if (isIdentity) {
			for (int i = 0; i < this.rows; i++) {
				for (int j = 0; j < this.cols; j++) {
					this.matrix[i][j] = (i == j) ? 1 : 0;
				}
			}
		}
	}

	/* transpose()
	 * this method will calculate the transpose of a matrix
	 *
	 * returns the transpose of the original matrix
	 */
	public Matrix transpose() {
		Matrix result = new Matrix(this.cols, this.rows);
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				result.matrix[j][i] = this.matrix[i][j];
			}
		}

		return result;
	}

	/* cofactor()
	 * this method will calculate the cofactor matrix from an original matrix
	 *
	 * returns the cofactor matrix of the original matrix
	 */
	public Matrix cofactor() {
		Matrix result = new Matrix(this.rows, this.cols);
		for (int i = 0; i < result.rows; i++) {
			for (int j = 0; j < result.cols; j++) {
				result.matrix[i][j] = (double)(Math.pow(-1, i + j) * removeRowCol(i, j).determinant());
			}
		}

		return result;
	}

	/* removeRowCol(Int, Int)
	 * this method will remove a row and a column from a matrix
	 *
	 * row - the row to be removed
	 * col - the col to be removed
	 * returns the original matrix less row row and column col
	 */
	public Matrix removeRowCol(int row, int col) {
		Matrix result = new Matrix(this.rows - 1, this.cols - 1);

		int k = 0; // row
		int l = 0; // col
		for (int i = 0; i < this.rows; i++) {
			if (i == row) continue;
			for (int j = 0; j < this.cols; j++) {
				if (j == col) continue;
				result.matrix[k][l] = this.matrix[i][j];

				l = (l + 1) % (this.cols - 1);
				if (l == 0) k++;
			}
		}

		return result;
	}

	/* determinant()
	 * this method will calculate the determinant of a matrix
	 *
	 * returns the determinant (double) of the original matrix
	 */
	public double determinant() {
		if (this.rows == 2 && this.cols == 2) return this.matrix[0][0] * this.matrix[1][1] - this.matrix[0][1] * this.matrix[1][0];

		int determinant = 0;
		
		for (int j = 0; j < this.cols; j++) {
			determinant += (double)(Math.pow(-1, j) * (this.matrix[0][j])) * removeRowCol(0, j).determinant();
		}

		return determinant;
	}

	/* multiply(Matrix)
	 * this method will multiply two matrices together
	 *
	 * b - the matrix to multiply by (this * b)
	 * throws MatrixException on illegal operation
	 * 
	 * returns the result of the operation
	 */
	public Matrix multiply(Matrix b) throws MatrixException {
		if (this.cols == b.rows || this.rows == b.cols){
			Matrix result = new Matrix(this.rows, b.cols);

			for (int i = 0; i < this.rows; i++) {
				for (int j = 0; j < b.cols; j++) {
					int sum = 0;
					for (int k = 0; k < this.cols; k++) sum += this.matrix[i][k] * b.matrix[k][j];

					result.matrix[i][j] = sum;
				} 
			}

			return result;
		} else throw new MatrixException("this.cols must match b.rows");
	}

	/* multiply(Matrix)
	 * this method will multiply this matrix by a scalar
	 *
	 * b - the scalar to multiply by (this * b)
	 * 
	 * returns the result of the operation
	 */
	public Matrix multiply(double b) {
		Matrix result = new Matrix(this.rows, this.cols);
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				result.matrix[j][i] = this.matrix[i][j] * b;
			}
		}

		return result;
	}

	/* inverse()
	 * this method determine the inverse of a matrix
	 *
	 * throws MatrixException on illegal operation
	 * 
	 * returns the inverse of the original matrix
	 */
	public Matrix inverse() {
		return this.cofactor().transpose().multiply(1 / this.determinant());
	}

	/* dot(Matrix)
	 * this method calculates the dot product of two vectors
	 *
	 * b - the vector to dot product by (this dot b)
	 * throws MatrixException on illegal operation
	 * 
	 * returns the result of the operation
	 */
	public Matrix dot(Matrix b) throws MatrixException {
		if (!this.isVector() || !b.isVector()) throw new MatrixException("cannot dot product non-vector matrices");
		return this.transpose().multiply(b);
	}

	/* cross(Matrix)
	 * this method calculates the cross product of two vectors
	 *
	 * b - the vector to cross product by (this x b)
	 * throws MatrixException on illegal operation
	 * 
	 * returns the result of the operation
	 */
	public Matrix cross(Matrix b) throws MatrixException {
		if (!this.isVector() || !b.isVector()) throw new MatrixException("cannot cross product non-vector matrices");
		return Helper.generateDualVectorMatrix(this.rows, this).multiply(b);
	}

	/* equals(Matrix)
	 * this method checks for equality between two matrices
	 *
	 * b - the other matrix
	 * 
	 * returns whether or not the two matrices are equal
	 */
	public boolean equals(Matrix b) {
		if (this.cols != b.cols || this.rows != b.rows) return false;
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				if (this.matrix[i][j] != b.matrix[i][j]) return false;
			}
		}

		return true;
	}

	/* isVector()
	 * this method checks if the matrix is in the form n rows by 1 col
	 *
	 * returns whether or not the the matrix is a vector
	 */
	public boolean isVector() {
		return this.cols == 1;
	}

	/* clear()
	 * this method clears the matrix, replacing values with 0s
	 */
	public void clear() {
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				this.matrix[i][j] = 0.0;
			}
		}
	}

	/* toString()
	 * this method builds an easy-to-read string from the matrix
	 * 
	 * returns the string representation of the matrix
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) sb.append(matrix[i][j] + "\t");
			sb.append("\n");
		}

		return sb.toString();
	}

	class MatrixException extends Exception {
		public MatrixException(String message) {
			super(message);
		}
	}

	static class Helper {
		public static Matrix generateRotationMatrixX(int degrees) {
			return new Matrix(new double[][] {{1, 0, 0, 0}, 
											  {0, Math.cos(degrees), -Math.sin(degrees), 0}, 
											  {0, Math.sin(degrees), Math.cos(degrees), 0}, 
											  {0, 0, 0, 1}});
		}

		public static Matrix generateRotationMatrixY(int degrees) {
			return new Matrix(new double[][] {{Math.cos(degrees), 0, Math.sin(degrees), 0}, 
											  {0, 1, 0, 0}, 
											  {-Math.sin(degrees), 0, Math.cos(degrees), 0}, 
											  {0, 0, 0, 1}});
		}

		public static Matrix generateRotationMatrixZ(int degrees) {
			return new Matrix(new double[][] {{Math.cos(degrees), -Math.sin(degrees), 0, 0}, 
											  {Math.sin(degrees), Math.cos(degrees), 0, 0}, 
											  {0, 0, 1, 0}, 
											  {0, 0, 0, 1}});
		}

		public static Matrix generateTranslationMatrix(double x, double y, double z) {
			return new Matrix(new double[][] {{1, 0, 0, x}, 
											  {0, 1, 0, y}, 
											  {0, 0, 1, z}, 
											  {0, 0, 0, 1}});
		}

		public static Matrix generateScaleMatrix(double x, double y, double z) {
			return new Matrix(new double[][] {{x, 0, 0, 0}, 
											  {0, y, 0, 0}, 
											  {0, 0, z, 0}, 
											  {0, 0, 0, 1}});
		}

		public static Matrix generateDualVectorMatrix(int size, Matrix vector) {
			return new Matrix(size == 3 ? new double[][] {{0, -vector.matrix[2][0], vector.matrix[1][0]},
														  {vector.matrix[2][0], 0, -vector.matrix[0][0]},
													   	  {-vector.matrix[1][0], vector.matrix[0][0], 0}} :
										  new double[][] {{0, -vector.matrix[2][0], vector.matrix[1][0], 0},
														  {vector.matrix[2][0], 0, -vector.matrix[0][0], 0},
														  {-vector.matrix[1][0], vector.matrix[0][0], 0, 0},
														  {0, 0, 0, 1}});
		}
	}
}