#include "Matrix.h"

class Point {
	Matrix m;

	Point();
	Point(int x, int y, int z);
	void rotate(int x, int y, int z);
	void translate(int x, int y, int z);
	void scale(int x, int y, int z);
};