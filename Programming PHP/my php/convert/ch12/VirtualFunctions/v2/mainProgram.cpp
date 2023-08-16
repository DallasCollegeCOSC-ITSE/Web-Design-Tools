   
#include <iostream>
#include "petType.h"
#include "dogType.h"

using namespace std;
 
void callPrint(petType& p);

int main()                                      //Line 1
{                                               //Line 2
    petType pet("Lucky");                       //Line 3
    dogType dog("Tommy", "German Shepherd");    //Line 4

    pet.print();                                //Line 5
    cout << endl;                               //Line 6
    dog.print();                                //Line 7

    cout << "*** Calling the function callPrint  ***"
        << endl;                                //Line 8
    callPrint(pet);                             //Line 9
    cout << endl;                               //Line 10
    callPrint(dog);                             //Line 11
    cout << endl;                               //Line 12

    return 0;                                   //Line 13
}                                               //Line 14

void callPrint(petType& p)
{	
    p.print();
}
