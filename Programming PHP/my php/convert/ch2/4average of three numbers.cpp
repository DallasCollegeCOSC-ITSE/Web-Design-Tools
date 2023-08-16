//set 3 numbers, compute the average then print out all the numbers and the final average
#include <iostream>

using namespace std;

int main() 
{
	int num1;
    int num2; 
    int num3;
    int average;

    num1 = 125;
	num2 = 28;
	num3 = -25;
    average = (num1 + num2 + num3) / 3;

    cout << "num1 = " << num1 << endl;
    cout << "num2 = " << num2 << endl;
    cout << "num3 = " << num3 << endl;
    cout << "average = " << average << endl;

	return 0;
}
