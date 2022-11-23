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
