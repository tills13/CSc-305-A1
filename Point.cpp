// Array[Row, Col]
#include "Point.h"

using namespace std;

Point::Point() {
	//m.matrix = {{0}, {0}, {0}, {1}};
}

Point::Point(int x, int y, int z) {
	m.matrix = new Matrix();
	//m.matrix = {{x}, {y}, {z}, {1}};
}

void Point::rotate(int x, int y, int z) {

}

void Point::translate(int x, int y, int z) {

}

void Point::scale(int x, int y, int z) {

}