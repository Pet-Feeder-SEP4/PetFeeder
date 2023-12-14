#include "util.h"
#include <string.h>

int str_lenght(char *str) {
    char data_copy[256];
    strcpy(data_copy, str);
    char *p = data_copy;
    int length = 0;
    for (;*p; ++p) {
        length++;
    }
    return length;
}