#include "app.h"
#include "parse_info.h"
#include "mock_delay.h"

int main() {
    app_init();
    
    
    while (1) {
        
        app_start();
        //sensor_get_data();
        _delay_ms(4000);
    }
    return 0;
}