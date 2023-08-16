#include <iostream>
  
#include "classAssignmentOverload.h"
 
using namespace std;

void cAssignmentOprOverload::print() const
{
    if (length == 0) 
        cout << "The list is empty." << endl;
    else
    { 
        for (int i = 0; i < length; i++)
            cout << list[i] << " ";
        cout << endl;
    }
}

void cAssignmentOprOverload::insertEnd(int item)
{
    if (length == maxSize)
        cout << "List is full" << endl;
    else
        list[length++] = item;
}

void cAssignmentOprOverload::destroyList()
{
    delete [] list;
    list = nullptr;
    length = 0;
    maxSize = 0;
}

cAssignmentOprOverload::cAssignmentOprOverload(int size)
{
    maxSize = size;
    length = 0;

    if (maxSize == 0)
        list = nullptr;
    else
        list = new int[maxSize];
}

const cAssignmentOprOverload& cAssignmentOprOverload::operator=
          (const cAssignmentOprOverload& otherList) //Line 1
{                                                   //Line 2
    if (this != &otherList)  //avoid self-assignment; Line 3
    {                                               //Line 4
        delete [] list;                             //Line 5
        maxSize = otherList.maxSize;                //Line 6
        length = otherList.length;                  //Line 7

        list = new int[maxSize];                    //Line 8

        for (int i = 0; i < length; i++)            //Line 9
            list[i] = otherList.list[i];            //Line 10
    }                                               //Line 11

    return *this;                                   //Line 12
}                                                   //Line 13


