//Program: Largest of three numbers
 
#include <iostream>                                 //Line 1
 
using namespace std;                                //Line 2

//Function prototype
double larger(double x, double y);                  //Line 3 
double compareThree(double x, double y, double z);  //Line 4       

int main()                                          //Line 5
{                                                   //Line 6
    double one, two;                                //Line 7

    cout << "Line 8: The larger of 5 and 10 is "
         << larger(5, 10) << endl;                  //Line 8

    cout << "Line 9: Enter two numbers: ";          //Line 9
    cin >> one >> two;                              //Line 10
    cout << endl;                                   //Line 11

    cout << "Line 12: The larger of " << one 
         << " and " << two << " is " 
         << larger(one, two) << endl;               //Line 12

    cout << "Line 13: The largest of 43.48, 34.00, " 
         << "and 12.65 is " 
         << compareThree(43.48, 34.00, 12.65)
         << endl;                                   //Line 13

   return 0;                                        //Line 14
}                                                   //Line 15

double larger(double x, double y)
{
    double max;

    if (x >= y)
        max = x;
    else
        max = y;

    return max;
}

double compareThree (double x, double y, double z)
{
    return larger(x, larger(y, z));
}
