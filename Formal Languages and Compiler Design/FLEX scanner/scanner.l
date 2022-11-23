%option noyywrap
%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "tokens.h"

typedef struct {
	int code;
	int symbolNumber;  // -1 for symbols, separators, operators; >= 0 for identifiers and constants
} IFPelem;

typedef struct {
	char value[9];
} STelem;

int symbolsTableSize = 0;
int IFPsize = 0;
STelem symbolsTable[60];
IFPelem IFP[120];

void addToIFP(int code, int symbolNumber) {
	IFP[IFPsize].code = code;
	IFP[IFPsize].symbolNumber = symbolNumber;
	IFPsize++;
}

void addConstant(char* number) { // number as string
	int found = 0;
	int i;
	for (i = 0; i < symbolsTableSize; i++) {
		if (symbolsTable[i].value == number) {
			found = 1;
			addToIFP(CONST, i);
		}
	}
	if (found == 0) {
		strcpy(symbolsTable[symbolsTableSize].value, number);
		addToIFP(CONST, symbolsTableSize);
		++symbolsTableSize;
	}
}

void addIdentifier(char* identifier) {
	if (strlen(identifier) > 8) { // only identifiers of length <= 8 are allowed
		fprintf(stderr, "ERROR: Identifier %s is too long!\n", identifier);
		exit(1);
	}
	int found = 0;
	int i;
	for (i = 0; i < symbolsTableSize; i++) {
		if (strcmp(symbolsTable[i].value, identifier) == 0) {
			found = 1;
			addToIFP(ID, i);
		}
	}
	if (found == 0) {
		strcpy(symbolsTable[symbolsTableSize].value, identifier);
		addToIFP(ID, symbolsTableSize);
		symbolsTableSize++;
	}
}

void printsymbolsTable() {
    printf("Symbols table:\n");
	int i;
    for(i = 0 ; i < symbolsTableSize; ++i)
        printf("%d %s\n", i, symbolsTable[i].value);
    printf("\n");
}

void printIFP() {
	printf("Internal form:\n");
	int i;
	for(i = 0; i < IFPsize; ++i){
		if (IFP[i].symbolNumber == -1) // keyword, operator or separator
			printf("%d\n", IFP[i].code);
		else // identifier or constant
			printf("%d\t%d\n", IFP[i].code, IFP[i].symbolNumber);
	}
}

void printAll() {
	printsymbolsTable();
	printIFP();
}
%}

REAL_NUMBER			[+-]?(0|[1-9][0-9]*)(\.[0-9]+)?
INTEGER_NUMBER		([1-9][0-9]*)|(0[0-7]*)|(0x[0-9A-F]+)|(0b[01]+)
IDENTIFIER			[a-zA-Z][a-zA-Z0-9_]*
STRING       		\".*\"

%%

{INTEGER_NUMBER}	addConstant(yytext);
{REAL_NUMBER}		addConstant(yytext);
\#					addToIFP(HASH, -1);
include				addToIFP(INCLUDE, -1);
\<{2}				addToIFP(TWICE_LESS_THAN, -1);
\<					addToIFP(LESS_THAN, -1);
iostream			addToIFP(IOSTREAM, -1);
\>{2}				addToIFP(TWICE_MORE_THAN, -1);
\>					addToIFP(MORE_THAN, -1);
using				addToIFP(USING, -1);
namespace			addToIFP(NAMESPACE, -1);
std					addToIFP(STD, -1);
\;					addToIFP(SEMICOLON, -1);
int					addToIFP(INT, -1);
float				addToIFP(FLOAT, -1);
main				addToIFP(MAIN, -1);
\(					addToIFP(LEFT_PARANTHESIS, -1);
\)					addToIFP(RIGHT_PARANTHESIS, -1);
\{					addToIFP(LEFT_BRACE, -1);
\=					addToIFP(EQUAL, -1);
\}					addToIFP(RIGHT_BRACE, -1);
return				addToIFP(RETURN, -1);
struct				addToIFP(STRUCT, -1);
if					addToIFP(IF, -1);
while				addToIFP(WHILE, -1);
cin					addToIFP(CIN, -1);
cout				addToIFP(COUT, -1);
else				addToIFP(ELSE, -1);
\<=					addToIFP(LESS_THAN_OR_EQUAL, -1);
\>=					addToIFP(MORE_THAN_OR_EQUAL, -1);
\==					addToIFP(TWICE_EQUAL, -1);
\!=					addToIFP(DIFFERENT, -1);
\,					addToIFP(COMMA, -1);
\+					addToIFP(PLUS, -1);
\-					addToIFP(MINUS, -1);
\*					addToIFP(ASTERISK, -1);
\/					addToIFP(SLASH, -1);
\%					addToIFP(PERCENT, -1);
\.					addToIFP(DOT, -1);
{IDENTIFIER}		addIdentifier(yytext);
[ \t\n]+			;
.					{ fprintf(stderr, "ERROR: Unexpected character!\n"); exit(1); }

%%

int main(int argc, char** argv)
{
	++argv; --argc;
	if (argc > 0)
		yyin = fopen(argv[0], "r");
	else
		yyin = stdin;
	yylex();
	printAll();
}
