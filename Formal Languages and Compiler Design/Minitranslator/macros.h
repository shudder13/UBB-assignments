#ifndef CODEASM_H
#define CODEASM_H

#define INCLUDEASM "%include 'io.inc' \n\n"

#define GLOBALMAIN "global main \n\n"

#define TEXTSECTION "section .text\n"

#define MAINASM "main:\n"

#define DATASECTION "\nsection .data\n"

#define RET "\nret\n"

#define ADD_ASM_FORMAT "\
mov ax, %s \n\
add ax, %s \n\
mov %s, ax \n\
"

#define SUB_ASM_FORMAT "\
mov ax, %s \n\
sub ax, %s \n\
mov %s, ax \n\
"

#define MUL_ASM_FORMAT "\
mov ax, %s \n\
mov bx, %s \n\
mul bx \n\
mov %s, ax \n\
"

#define DIV_ASM_FORMAT "\
mov ax, %s \n\
mov dx, 0\n\
mov bx, %s \n\
div bx \n\
mov %s, ax \n\
"

#define READ_FORMAT "\
call io_readint\n\
mov [%s], AX\n\
"

#define WRITE_FORMAT "\
mov AX, [%s]\n\
call io_writeint\n\
call io_writeln\n\
"

#endif
