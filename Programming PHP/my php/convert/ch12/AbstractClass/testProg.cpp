#include <iostream>
#include "partTimeEmployee.h"
#include "fullTimeEmployee.h"
      
int main()
{
    fullTimeEmployee newEmp("John", "Smith", 75, 56000, 5700);
    partTimeEmployee tempEmp("Andy", "Turner", 275, 15.50, 57);

    newEmp.print();
    cout << endl;
    tempEmp.print();

    return 0; 
} 