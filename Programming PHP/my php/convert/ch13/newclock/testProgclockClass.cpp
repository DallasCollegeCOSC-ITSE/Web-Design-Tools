//**********************************************************
// Author: D.S. Malik
//
// This program shows how to use the class clockType.
//**********************************************************
  
#include <iostream>                                  //Line 1
#include "newClock.h"                                //Line 2

using namespace std;                                 //Line 3

int main()                                           //Line 4
{                                                    //Line 5
    clockType myClock(7, 24, 32);                    //Line 6
    clockType yourClock;                             //Line 7

    cout << "Line 8: myClock = " << myClock 
         << endl;                                    //Line 8
    cout << "Line 9: yourClock = " << yourClock
         << endl;                                    //Line 9

    cout << "Line 10: Enter the time in the form "
         << "hr:min:sec ";                           //Line 10
    cin >> myClock;                                  //Line 11
    cout << endl;                                    //Line 12

    cout << "Line 13: The new time of myClock = "
         << myClock << endl;                         //Line 13

    ++myClock;                                       //Line 14

    cout << "Line 15: After incrementing the time, " 
         << "myClock = " << myClock << endl;         //Line 15

    yourClock.setTime(13, 35, 38);                   //Line 16
    cout << "Line 17: After setting the time, "
         << "yourClock = " << yourClock << endl;     //Line 17

    if (myClock == yourClock)                        //Line 18
        cout << "Line 19: The times of myClock and "
             << "yourClock are equal." << endl;      //Line 19
    else                                             //Line 20
        cout << "Line 21: The times of myClock and "
             << "yourClock are not equal." << endl;  //Line 21

    if (myClock <= yourClock)                        //Line 22
        cout << "Line 23: The time of myClock is "
             << "less than or equal to " << endl
             << "the time of yourClock." << endl;    //Line 23
    else                                             //Line 24
        cout << "Line 25: The time of myClock is "
             << "greater than the time of " 
             << "yourClock." << endl;                //Line 25

    return 0;                                        //Line 26
}                                                    //Line 27

