//The user program that uses the class clockType
   
#include <iostream>
#include "clockType.h"
  
using namespace std; 

int main()
{
    clockType myClock;

    myClock.setTime(5, 4, 30);  

    cout << "Line 2: myClock: "; 
    myClock.printTime();  
    cout << endl; 

    cout << "The hours of myClock: " << myClock.getHours() << endl;

    return 0;
}//end main



