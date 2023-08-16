   
#include <iostream>

#include "petType.h"
 
using namespace std;
 
void petType::print() const
{
    cout << "Name: " << name;
}

petType::petType(string n)
{
    name = n;
}
