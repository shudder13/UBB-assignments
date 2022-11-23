# Mini-programming language specification (C++ based)
> Backus Naur Form

**\<program>** ::= #include \<iostream>  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; using namespace std; int main() {<statement_list> <return_statement>}  
**<statement_list>** ::= \<statement> | \<statement> <statement_list>  
**<return_statement>** ::= return \<number>;  
**\<statement>** ::= <declaration_statement> | <assignment_statement> | <io_statement> | <control_flow_statement>  
**<declaration_statement>** ::= \<type> <id_list>;  
**<assignment_statement>** ::= \<id> = \<expression>;  
**<io_statement>** ::= <input_statement> | <output_statement>  
**<control_flow_statement>** ::= <conditional_statement> | <while_statement>  
**\<type>** ::= int | float | <user_structure>  
**<id_list>** ::= \<id> | \<id> <id_list>  
**\<id>** ::= \<letter> | \<letter> \<string>  
**\<letter>** ::= a | b | ... | z | A | B | .. | Z  
**\<digit>** ::= 0 | <positive_digit>  
**<positive_digit>** = 1 | 2 | ... | 9  
**<digits_list>** ::= \<digit> | \<digit> <digits_list>  
**\<number>** ::= \<digit> | <positive_digit> <digits_list>  
**\<char>** ::= \<letter> | \<digit> | _  
**\<string>** ::= \<char> | \<char> \<string>  
**\<expression>** ::= \<id> | \<constant> | \<expression> \<operator> \<expression>  
**\<operator>** ::= + | - | * | / | %  
**<real_number>** ::= \<number>.\<number>  
**\<constant>** ::= \<number> | <real_number>  
**<input_statement>** ::= cin >> \<id>;  
**<output_statement>** ::= cout << \<expression>;  
**<conditional_statement>** ::= if (<boolean_expression>) {<statement_list>} | if (<boolean_expression>) {<statement_list>} else {<statement_list>}  
**<boolean_expression>** ::= \<expression> <relation_operator> \<expression>  
**<relation_operator>** ::= > | < | == | >= | <= | !=  
**<while_statement>** ::= while (<boolean_expression>) {<statement_list>}  
**<user_structure>** ::= struct \<id> {<declaration_statement_list>};  
**<declaration_statement_list>** ::= <declaration_statement> | <declaration_statement> <declaration_statement_list>  


## The source codes of 3 mini-programs
```cpp
// Calculate the perimeter and area of the circle of a given radius
#include <iostream>
using namespace std;

int main() {
	float radius, perimeter, area, PI;
	PI = 3.14;
	
	cout << "Enter the radius value: ";
	cin >> radius;
	
	perimeter = 2 * PI * radius;
	area = PI * radius * radius;
	
	cout << "The perimeter of the circle is equal to ";
	cout << perimeter;
	cout << " and the area is equal to ";
	cout << area;
	cout << ".";
	
	return 0;
}
```

```cpp
// Greatest common divisor of 2 natural numbers
#include <iostream>
using namespace std;

int main() {
	int x, y;
	cout << "Enter the first number: ";
	cin >> x;
	cout << "Enter the second number: ";
	cin >> y;
	
	while (x != y) {
		if (x > y) {
			x = x - y;
		}
		else {
			y = y - x;
		}
	}
	cout << "The greatest common divisor of the two numbers is equal to ";
	cout << x;
	cout << ".";
	return 0;
}
```

```cpp
// Calculate the sum of the numbers read from the keyboard
#include <iostream>
using namespace std;

int main() {
	int n, x, sum, i;
	i = 0;
	sum = 0;
	
	cin >> n;
	while (i < n) {
		cin >> x;
		sum = sum + x;
		i = i + 1;
	}
	cout << "The sum of the numbers is equal to ";
	cout << sum;
	cout << ".";
	return 0;
}
```


## The source codes of 2 programs containing errors
```cpp
// Contains 2 errors that are at the same time errors in the original language (C++)
#include <iostream>
using namespace std;

int main() {
	cout >> "Hello World!";
	return "Hello, World!";
}
```

```cpp
// Contains 2 errors that are not errors in the original language
#include <iostream>
using namespace std;

int main() {
	int i = 0;
	i += 3;
	return 0;
}
```
