#include "app.h"
#include "parse_info.h"
#include <util/delay.h>

int main() {
    app_init();
    wifi_command_TCP_transmit("37\n", 2);

    while (1) {
        app_start();
        _delay_ms(4000);
    }
    
    return 0;
}