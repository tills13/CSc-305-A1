#include "Matrix.h"
#include <math.h> 

using namespace std;

Matrix::Matrix() {
	Matrix(0, 0);
}

Matrix::Matrix(std::vector< std::vector<double> > _matrix) {
	matrix = _matrix;
	rows = _matrix.size();
	cols = _matrix[0].size();
}

Matrix::Matrix(double **_matrix) {
	
}

Matrix::Matrix(int _rows, int _cols) {
	matrix.resize(_rows);
	for (int i = 0; i < _rows; i++) matrix[i].resize(_cols);
	rows = _rows;
	cols = _cols;
}

/* transpose()
 * this method will calculate the transpose of a matrix
 *
 * returns the transpose of the original matrix
 */
Matrix * Matrix::transpose() {
	Matrix * result = new Matrix(this.cols, this.rows);
	
	for (int i = 0; i < rows; i++) {
		for (int j = 0; j < cols; j++) {
			result.matrix[j][i] = matrix[i][j];
		}
	}

	return result;
}

/* cofactor()
 * this method will calculate the cofactor matrix from an original matrix
 *
 * returns the cofactor matrix of the original matrix
 */
Matrix * Matrix::cofactor() {
	Matrix * result = new Matrix(rows, cols);

	for (int i = 0; i < result.rows; i++) {
		for (int j = 0; j < result.cols; j++) {
			result.matrix[i][j] = (double)(pow(-1, i + j) * removeRowCol(i, j).determinant());
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
Matrix * Matrix::removeRowCol(int row, int col) {
	Matrix * result = new Matrix(rows - 1, cols - 1);

	int k = 0; // row
	int l = 0; // col
	for (int i = 0; i < rows; i++) {
		if (i == row) continue;
		for (int j = 0; j < cols; j++) {
			if (j == col) continue;
			result.matrix[k][l] = matrix[i][j];

			l = (l + 1) % (cols - 1);
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
double Matrix::determinant() {
	if (rows == 2 && cols == 2) return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

	double determinant = 0;
	
	for (int j = 0; j < cols; j++) {
		determinant += (double)(pow(-1, j) * (matrix[0][j])) * removeRowCol(0, j).determinant();
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
Matrix * Matrix::multiply(Matrix b) throws MatrixException {
	if (cols == b.rows || rows == b.cols){
		Matrix result = new Matrix(rows, b.cols);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < b.cols; j++) {
				int sum = 0;
				for (int k = 0; k < cols; k++) sum += matrix[i][k] * b.matrix[k][j];

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
Matrix * Matrix::multiply(double b) {
	Matrix * result = new Matrix(rows, cols);
	
	for (int i = 0; i < rows; i++) {
		for (int j = 0; j < cols; j++) {
			result.matrix[j][i] = matrix[i][j] * b;
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
Matrix * Matrix::inverse() {
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
Matrix * Matrix::dot(Matrix b) throws MatrixException {
	if (!isVector() || !b.isVector()) throw new MatrixException("cannot dot product non-vector matrices");
	return transpose().multiply(b);
}

/* cross(Matrix)
 * this method calculates the cross product of two vectors
 *
 * b - the vector to cross product by (this x b)
 * throws MatrixException on illegal operation
 * 
 * returns the result of the operation
 */
Matrix Matrix::cross(Matrix b) throws MatrixException {
	if (!isVector() || !b.isVector()) throw new MatrixException("cannot cross product non-vector matrices");
	return Helper.generateDualVectorMatrix(rows, this).multiply(b);
}

/* equals(Matrix)
 * this method checks for equality between two matrices
 *
 * b - the other matrix
 * 
 * returns whether or not the two matrices are equal
 */
bool Matrix::equals(Matrix b) {
	if (cols != b.cols || rows != b.rows) return false;
	for (int i = 0; i < rows; i++) {
		for (int j = 0; j < cols; j++) {
			if (matrix[i][j] != b.matrix[i][j]) return false;
		}
	}

	return true;
}

/* isVector()
 * this method checks if the matrix is in the form n rows by 1 col
 *
 * returns whether or not the the matrix is a vector
 */
bool Matrix::isVector() {
	return cols == 1;
}

/* clear()
 * this method clears the matrix, replacing values with 0s
 */
void clear() {
	for (int i = 0; i < rows; i++) {
		for (int j = 0; j < cols; j++) {
			matrix[i][j] = 0.0;
		}
	}
}

/* toString()
 * this method builds an easy-to-read string from the matrix
 * 
 * returns the string representation of the matrix
 */

string Matrix::toString() {
	return "balk";
}


//--------

Matrix * Matrix::generateDualVectorMatrix() {
	Matrix * result = new Matrix(rows, rows);

	result.matrix[0] = std::vector<double>({0, -vector.matrix[2][0], vector.matrix[1][0]});
	return new Matrix(size == 3 ? new double[][] {{0, -vector.matrix[2][0], vector.matrix[1][0]},
														  {vector.matrix[2][0], 0, -vector.matrix[0][0]},
													   	  {-vector.matrix[1][0], vector.matrix[0][0], 0}} :
										  new double[][] {{0, -vector.matrix[2][0], vector.matrix[1][0], 0},
														  {vector.matrix[2][0], 0, -vector.matrix[0][0], 0},
														  {-vector.matrix[1][0], vector.matrix[0][0], 0, 0},
														  {0, 0, 0, 1}});
}

