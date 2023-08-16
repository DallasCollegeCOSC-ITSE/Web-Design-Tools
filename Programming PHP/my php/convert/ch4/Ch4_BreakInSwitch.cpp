//Program: Effect of break statements in a switch structure

#include <iostream>                                    //Line 1

using namespace std;                                   //Line 2

int main()                                             //Line 3
{                                                      //Line 4
    int num;                                           //Line 5

    cout << "Enter an integer between 0 and 7: ";      //Line 6
    cin >> num;                                        //Line 7
    cout << endl;                                      //Line 8
	
    switch(num)                                        //Line 9
    {                                                  //Line 10
    case 0:                                            //Line 11
    case 1:                                            //Line 12
        cout << "Learning to use ";                    //Line 13
    case 2:                                            //Line 14
        cout << "C++'s ";                              //Line 15
    case 3:                                            //Line 16
        cout << "switch structure." << endl;           //Line 17
        break;                                         //Line 18
    case 4:                                            //Line 19
        break;                                         //Line 20
    case 5:                                            //Line 21
        cout << "This program shows the effect ";      //Line 22
    case 6:                                            //Line 23
    case 7:                                            //Line 24
        cout << "of the break statement." << endl;     //Line 25
        break;                                         //Line 26
    default:                                           //Line 27
        cout << "The number is out of range." << endl; //Line 28 
    }                                                  //Line 29

    cout << "Out of the switch structure." << endl;    //Line 30

    return 0;                                          //Line 31
}                                                      //Line 32
