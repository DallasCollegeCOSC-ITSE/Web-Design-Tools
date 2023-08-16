  
#include <iostream>                                 //Line 1

#include "classAssignmentOverload.h"                //Line 2

using namespace std;                                //Line 3
  
int main()                                          //Line 4
{                                                   //Line 5
    cAssignmentOprOverload intList1(10);            //Line 6
    cAssignmentOprOverload intList2;                //Line 7
    cAssignmentOprOverload intList3;                //Line 8

    int i;                                          //Line 9
    int number;                                     //Line 10

    cout << "Line 11: Enter 5 integers: ";          //Line 11

    for (i = 0; i < 5; i++)                         //Line 12
    {                                               //Line 13
        cin >> number;                              //Line 14
        intList1.insertEnd(number);                 //Line 15
    }                                               //Line 16

    cout << endl;                                   //Line 17
    cout << "Line 18: intList1: ";                  //Line 18
    intList1.print();                               //Line 19

    intList3 = intList2 = intList1;                 //Line 20

    cout << "Line 21: intList2: ";                  //Line 21
    intList2.print();                               //Line 22

    intList2.destroyList();                         //Line 23

    cout << endl;                                   //Line 24
    cout << "Line 25: intList2: ";                  //Line 25
    intList2.print();                               //Line 26

    cout << "Line 27: After destroying intList2, "
         << "intList1: ";                           //Line 27
    intList1.print();                               //Line 28

    cout << "Line 29: After destroying intList2, "
         << "intList3: ";                           //Line 29
    intList3.print();                               //Line 30
    cout << endl;                                   //Line 31

    return 0;                                       //Line 32
}                                                   //Line 33

