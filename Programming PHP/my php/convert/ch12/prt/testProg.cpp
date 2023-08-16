
#include <iostream>                                 //Line 1
#include "ptrMemberVarType.h"                       //Line 2
 
using namespace std;                                //Line 3
   
void testCopyConst(ptrMemberVarType temp);          //Line 4

int main()                                          //Line 5
{                                                   //Line 6
    ptrMemberVarType listOne;                       //Line 7

    int num;                                        //Line 8
    int index;                                      //Line 9

    cout << "Line 10: Enter 5 integers." << endl;   //Line 10

    for (index = 0; index < 5; index++)             //Line 11
    {
        cin >> num;                                 //Line 12
        listOne.insertAt(index, num);               //Line 13
    }

    cout << "Line 14: listOne: ";                   //Line 14
    listOne.print();                                //Line 15
    cout << endl;                                   //Line 16

       //Declare listTwo and initialize it using listOne
    ptrMemberVarType listTwo(listOne);              //Line 17

    cout << "Line 18: listTwo: ";                   //Line 18
    listTwo.print();                                //Line 19
    cout << endl;                                   //Line 20

    listTwo.insertAt(5, 34);                        //Line 21
    listTwo.insertAt(2, -76);                       //Line 22

    cout << "Line 23: After modifying listTwo: ";   //Line 23
    listTwo.print();                                //Line 24
    cout << endl;                                   //Line 25

    cout << "Line 26: After modifying listTwo, " 
         << "listOne: ";                            //Line 26
    listOne.print();                                //Line 27
    cout << endl;                                   //Line 28
   
    cout << "Line 29: Calling the function testCopyConst"
         << endl;                                   //Line 29

        //Call function testCopyConst
    testCopyConst(listOne);                         //Line 30

    cout << "Line 31: After a call to the function "
         << "testCopyConst, " << endl
         << "         listOne is: ";                //Line 31

    listOne.print();                                //Line 32
    cout << endl;                                   //Line 33

    return 0;                                       //Line 34
}                                                   //Line 35

void testCopyConst(ptrMemberVarType temp)           //Line 36
{                                                   //Line 37
    cout << "Line 38: *** Inside the function "
         << "testCopyConst ***" << endl;            //Line 38

    cout << "Line 39: Object temp data: ";          //Line 39
    temp.print();                                   //Line 40
    cout << endl;                                   //Line 41

    temp.insertAt(3, -100);                         //Line 42
    cout << "Line 43: After changing temp: ";       //Line 43
    temp.print();                                   //Line 44
    cout << endl;                                   //Line 45

    cout << "Line 46: *** Exiting the function "
         << "testCopyConst ***" << endl;           //Line 46
}                                                  //Line 47
