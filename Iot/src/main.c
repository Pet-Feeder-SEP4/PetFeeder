#include "Application/app.h"
#include <util/delay.h>

int main() {
    app_init();
    
    while (1) {
        sensor_get_data();
        _delay_ms(4000);
    }
    return 0;
}