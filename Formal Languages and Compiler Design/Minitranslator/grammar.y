%{
#include <stdio.h>
#include <string.h>
#include <math.h>
%}

%token INTEGER HASH INCLUDE TWICE_LESS_THAN LESS_THAN IOSTREAM
%token TWICE_GREATER_THAN GREATER_THAN USING NAMESPACE STD
%token SEMICOLON INT MAIN LEFT_PARANTHESIS RIGHT_PARANTHESIS
%token LEFT_BRACE EQUAL RIGHT_BRACE CIN COUT COMMA PLUS
%token MINUS ASTERISK SLASH PERCENT DOT IDENTIFIER

%%

program: import namespaceStd main
        ;

import: HASH INCLUDE LESS_THAN library GREATER_THAN
        ;

library: IOSTREAM
        ;

namespaceStd: USING NAMESPACE STD SEMICOLON
        ;

main: INT MAIN LEFT_PARANTHESIS RIGHT_PARANTHESIS LEFT_BRACE statements RIGHT_BRACE
        ;

statements:
            | statements statement
        ;

statement: declaration_statement
            | assignment_statement
            | io_statement
        ;

declaration_statement: type variableList SEMICOLON
        ;

type: INT
        ;

variableList: IDENTIFIER
        | variableList COMMA IDENTIFIER
        ;

assignment_statement: IDENTIFIER EQUAL expression SEMICOLON
        ;

expression: IDENTIFIER
            | const
            | expression operator IDENTIFIER
            | expression operator INTEGER
        ;

const: INTEGER
        ;

operator: PLUS
            | MINUS
            | ASTERISK
            | SLASH
        ;

io_statement: input_statement
            | output_statement
        ;

input_statement: CIN TWICE_GREATER_THAN IDENTIFIER SEMICOLON
        ;

output_statement: COUT TWICE_LESS_THAN IDENTIFIER SEMICOLON
        ;

%%

yyerror()
{
    printf("Syntax error\n");
    exit(0);
}

main(int argc, char** argv)
{
    readFromFile(argv[1]);
    yyparse();
    printf("No error\n");
    writeToFile();
}
