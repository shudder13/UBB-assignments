%option noyywrap
%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "grammar.tab.h"

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
			addToIFP(1, i);
		}
	}
	if (found == 0) {
		strcpy(symbolsTable[symbolsTableSize].value, number);
		addToIFP(1, symbolsTableSize);
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
			addToIFP(0, i);
		}
	}
	if (found == 0) {
		strcpy(symbolsTable[symbolsTableSize].value, identifier);
		addToIFP(0, symbolsTableSize);
		symbolsTableSize++;
	}
}

void writeSymbolsTable() {
	FILE* fp;
	fp = fopen("ST.txt", "w");
    fprintf(fp, "Symbols table:\n");
	int i;
    for(i = 0 ; i < symbolsTableSize; ++i)
        fprintf(fp, "%d %s\n", i, symbolsTable[i].value);
    fprintf(fp, "\n");
}

void writeIFP() {
	FILE* fp;
	fp = fopen("IFP.txt", "w");
	fprintf(fp, "Internal form:\n");
	int i;
	for (i = 0; i < IFPsize; ++i){
		if (IFP[i].symbolNumber == -1) // keyword, operator or separator
			fprintf(fp, "%d\n", IFP[i].code);
		else // identifier or constant
			fprintf(fp, "%d\t%d\n", IFP[i].code, IFP[i].symbolNumber);
	}
}

%}

INTEGER_NUMBER		([1-9][0-9]*)|(0[0-7]*)|(0x[0-9A-F]+)|(0b[01]+)
IDENTIFIER			[a-zA-Z][a-zA-Z0-9_]*
STRING       		\".*\"

%%

{INTEGER_NUMBER}	addConstant(yytext); strcpy(yylval.variableName, yytext); return INTEGER;
\#					addToIFP(2, -1); return HASH;
include				addToIFP(3, -1); return INCLUDE;
\<{2}				addToIFP(4, -1); return TWICE_LESS_THAN;
\<					addToIFP(5, -1); return LESS_THAN;
iostream			addToIFP(6, -1); return IOSTREAM;
\>{2}				addToIFP(7, -1); return TWICE_GREATER_THAN;
\>					addToIFP(8, -1); return GREATER_THAN;
using				addToIFP(9, -1); return USING;
namespace			addToIFP(10, -1); return NAMESPACE;
std					addToIFP(11, -1); return STD;
\;					addToIFP(12, -1); return SEMICOLON;
int					addToIFP(13, -1); return INT;
main				addToIFP(14, -1); return MAIN;
\(					addToIFP(15, -1); return LEFT_PARANTHESIS;
\)					addToIFP(16, -1); return RIGHT_PARANTHESIS;
\{					addToIFP(17, -1); return LEFT_BRACE;
\=					addToIFP(18, -1); return EQUAL;
\}					addToIFP(19, -1); return RIGHT_BRACE;
cin					addToIFP(20, -1); return CIN;
cout				addToIFP(21, -1); return COUT;
\,					addToIFP(22, -1); return COMMA;
\+					addToIFP(23, -1); return PLUS;
\-					addToIFP(24, -1); return MINUS;
\*					addToIFP(25, -1); return ASTERISK;
\/					addToIFP(26, -1); return SLASH;
{IDENTIFIER}		addIdentifier(yytext); strcpy(yylval.variableName, yytext); return IDENTIFIER;
[ \t\n]+			;
.					{ fprintf(stderr, "ERROR: Unexpected character!\n"); exit(1); }

%%

int readFromFile(char *filename){
    FILE *fp;
    fp = fopen(filename, "r");
    if (fp == NULL)
        printf("File doesn't exist!\n");
    else
        yyin = fp;
    return 0;
}

void writeToFile(){
    writeSymbolsTable();
	writeIFP();
}
