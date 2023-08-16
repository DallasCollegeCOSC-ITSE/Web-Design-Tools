 
#include <iostream>                                  //Line 1
#include "clockType.h"                               //Line 2
 
using namespace std;                                 //Line 3  

int main()                                           //Line 4
{                                                    //Line 5
    clockType myClock(8, 23, 50);                    //Line 6
    clockType yourClock(8, 23, 50);                  //Line 7
    clockType tempClock(9, 16, 25);                  //Line 8

    cout << "Line 9: myClock: ";                     //Line 9
    myClock.printTime();                             //Line 10
    cout << endl;                                    //Line 11

    cout << "Line 12: yourClock: ";                  //Line 12
    yourClock.printTime();                           //Line 13
    cout << endl;                                    //Line 14

    cout << "Line 15: tempClock: ";                  //Line 15
    tempClock.printTime();                           //Line 16
    cout << endl;                                    //Line 17

        //Compare myClock and yourClock
    if (myClock == yourClock)                        //Line 18
        cout << "Line 19: The time of myClock and "
             << "yourClock are equal." << endl;      //Line 19
    else                                             //Line 20
        cout << "Line 21: The time of myClock and "
             << "yourClock are not equal." << endl;  //Line 21

        //Compare myClock and tempClock
    if (myClock == tempClock)                        //Line 22
        cout << "Line 23: The time of myClock and "
             << "tempClock are equal." << endl;      //Line 23
    else                                             //Line 24
        cout << "Line 25: The time of myClock and "
             << "tempClock are not equal." << endl;  //Line 25

    return 0;                                        //Line 26
}//end main                                          //Line 27



