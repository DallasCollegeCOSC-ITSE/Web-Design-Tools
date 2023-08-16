
//Chapter 4: Example 4-14 
//Program to determine if additional charges are applicable on
//a suitcase accompanying a passenger on an economy flight. 

#include <iostream>                                        //Line 1
#include <iomanip>                                         //Line 2

using namespace std;                                       //Line 3
  
int main()                                                 //Line 4
{                                                          //Line 5
    double suitcaseDimension;                              //Line 6
    double suitcaseWeight;                                 //Line 7
    double additionalCharges = 0.0;                        //Line 8
    
    cout << fixed << showpoint << setprecision(2);         //Line 9
    cout << "Line 10: Enter suitcase dimensions "  
         << "(length + width + depth) in inches: ";        //Line 10
    cin >> suitcaseDimension;                              //Line 11
    cout << endl;                                          //Line 12

    cout << "Line 13: Enter suitcase weight in pounds: ";  //Line 13
    cin >> suitcaseWeight;                                 //Line 14
    cout << endl;                                          //Line 15

    if (suitcaseDimension > 108 || suitcaseWeight > 50)    //Line 16
        additionalCharges = 50.00;                         //Line 17

    cout <<"Line 18: Additional suitcase charges: $"    
         << additionalCharges << endl;                     //Line 18
    
    return 0;                                              //Line 19
}                                                          //Line 20