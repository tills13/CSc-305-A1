#import <vector>
#import <string>

class Matrix {
	public:
		//std::vector< std::vector<double> > matrix;
		double ** matrix;
		int rows;
		int cols;

		Matrix();
		Matrix(std::vector< std::vector<double> > matrix);
		Matrix(int _rows, int _cols);
		Matrix * transpose();
		Matrix * cofactor();
		Matrix * removeRowCol(int row, int col);
		double determinant();
		Matrix * multiply(Matrix b);
		Matrix * multiply(double b);
		Matrix * inverse();
		Matrix * dot();
		Matrix * cross();
		bool equals(Matrix b);
		bool isVector();
		void clear();
		std::string toString();

		class Helper {
			public:
				Matrix * 
		};
};