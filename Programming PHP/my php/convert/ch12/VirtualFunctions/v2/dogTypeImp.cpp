   
#include <iostream>

#include "dogType.h"
 
using namespace std;

void dogType::print() const 
{
    petType::print();
    cout << ", Breed: " << breed << endl;
} 

dogType:: dogType(string n, string b)
               : petType(n)
{
    breed = b;
}
