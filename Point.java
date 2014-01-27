public class Point {
	private Matrix point = new Matrix(4, 1);

	public Point(int x, int y, int z) {
		this.point = new Matrix(new double[][]{{x},{y},{z},{1}});
	}

	/* rotate(Int, Int, Int)
	 * this method will rotate the point in space by x, y, z degrees
	 *
	 * x - rotation about the x axis
	 * y - rotation about the y axis
	 * z - rotation about the z axis
	 * 
	 * throws MatrixException (because .multiply does), but should never throw it itself
	 */
	public void rotate(int x, int y, int z) throws Matrix.MatrixException {
		this.point = Matrix.Helper.generateRotationMatrixX(x).multiply(point);
		this.point = Matrix.Helper.generateRotationMatrixY(y).multiply(point);
		this.point = Matrix.Helper.generateRotationMatrixZ(z).multiply(point);
	}

	/* translate(Int, Int, Int)
	 * this method will translate the point in space by x, y, z
	 *
	 * x - x translation
	 * y - y translation
	 * z - z translation
	 * 
	 * throws MatrixException (because .multiply does), but should never throw it itself
	 */
	public void translate(int x, int y, int z) throws Matrix.MatrixException {
		this.point = Matrix.Helper.generateTranslationMatrix(x, y, z).multiply(point);
	}

	/* scale(Int, Int, Int)
	 * this method will scale the point in space by x, y, z
	 *
	 * x - x scale
	 * y - y scale
	 * z - z scale
	 * 
	 * throws MatrixException (because .multiply does), but should never throw it itself
	 */
	public void scale(int x, int y, int z) throws Matrix.MatrixException {
		this.point =  Matrix.Helper.generateScaleMatrix(x, y, z).multiply(point);
	}

	/* toString()
	 * this method builds an easy-to-read string from the point
	 * 
	 * returns the string representation of the point
	 */
	public String toString() {
		return this.point.matrix[0][0] + "\n" + this.point.matrix[1][0] + "\n" + this.point.matrix[2][0] + "\n";
	}
}