%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "macros.h"

extern int yylex();
extern char *yytext;
FILE* out;
int tempnr = 1;
char tempBuffer[300];
char dataSegmentBuffer[500];

void addTempsToBuffer(){
    int i;
    for(i=1;i<tempnr;i++){
        sprintf(tempBuffer,"temp%d dw 0\n",i);
        strcat(dataSegmentBuffer,tempBuffer);
    }
}

void newTempName(char* s){
    sprintf(s,"[temp%d]",tempnr);
    tempnr++;
}

void addDataToBuffer(char* str){
    char data[100];
    sprintf(data,"%s dw 0\n",str);
    strcat(dataSegmentBuffer, data);
}

void printDataSegment(){
    addTempsToBuffer();
    fprintf(out, "%s", dataSegmentBuffer);
}

%}

%union {
        struct{
                char name[100];
                char code[500];
        } attributes;
        char variableName[10];
}

%token HASH INCLUDE TWICE_LESS_THAN LESS_THAN IOSTREAM
%token TWICE_GREATER_THAN GREATER_THAN USING NAMESPACE STD
%token SEMICOLON INT MAIN LEFT_PARANTHESIS RIGHT_PARANTHESIS
%token LEFT_BRACE EQUAL RIGHT_BRACE CIN COUT COMMA PLUS
%token MINUS ASTERISK SLASH
%token<variableName> INTEGER
%token<variableName> IDENTIFIER
%type<attributes> term
%type<attributes> expression


%%

program: import
        namespaceStd
                {
                        strcpy(dataSegmentBuffer, "");
                        fprintf(out, "%s", INCLUDEASM);
                        fprintf(out, "%s", GLOBALMAIN);
                        fprintf(out, "%s", TEXTSECTION);
                        fprintf(out, "%s", MAINASM);
                }
        main
                {
                        fprintf(out, "%s", RET);
                        fprintf(out, "%s", DATASECTION);
                        printDataSegment();
                }
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
                {
                        addDataToBuffer($1);
                }
        | variableList COMMA IDENTIFIER
                {
                        addDataToBuffer($3);
                }
        ;

assignment_statement: IDENTIFIER EQUAL expression SEMICOLON
                {
                        fprintf(out, "%s\n", $3.code);
                        fprintf(out, "mov ax, %s\n", $3.name);
                        fprintf(out, "mov [%s], ax\n", $1);
                }
        ;

expression: term
                {
                        strcpy($$.code, $1.code);
                        strcpy($$.name, $1.name);
                }
        | term ASTERISK expression
                {
                        newTempName($$.name);
                        sprintf($$.code, "%s\n%s\n", $3.code, $1.code);
                        sprintf(tempBuffer, MUL_ASM_FORMAT, $3.name, $1.name, $$.name);
                        strcat($$.code, tempBuffer);
                }
        | term SLASH expression
                {
                        newTempName($$.name);
                        sprintf($$.code, "%s\n%s\n", $3.code, $1.code);
                        sprintf(tempBuffer, DIV_ASM_FORMAT, $3.name, $1.name, $$.name);
                        strcat($$.code, tempBuffer);
                }
        | term PLUS expression
                {
                        newTempName($$.name);
                        sprintf($$.code, "%s\n%s\n", $3.code, $1.code);
                        sprintf(tempBuffer, ADD_ASM_FORMAT, $3.name, $1.name, $$.name);
                        strcat($$.code, tempBuffer);
                }
        | term MINUS expression
                {
                        newTempName($$.name);
                        sprintf($$.code, "%s\n%s\n", $3.code, $1.code);
                        sprintf(tempBuffer, SUB_ASM_FORMAT, $3.name, $1.name, $$.name);
                        strcat($$.code, tempBuffer);
                }
        ;

term: INTEGER
        {
                strcpy($$.code, "");
                strcpy($$.name, $1);
        }
        | IDENTIFIER
        {
                strcpy($$.code, "");
                sprintf($$.name, "[%s]", $1);
        }
        ;

io_statement: input_statement
            | output_statement
        ;

input_statement: CIN TWICE_GREATER_THAN IDENTIFIER SEMICOLON
                {
                        fprintf(out, READ_FORMAT, $3);
                }
        ;

output_statement: COUT TWICE_LESS_THAN IDENTIFIER SEMICOLON
                {
                        fprintf(out, WRITE_FORMAT, $3);
                }
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
    out = fopen("output.asm", "w");
    yyparse();
    printf("No error\n");
    writeToFile();
}
