#include "pc_comm.h"
#include "wifi.h"
#include "Application/app.c"
#include <util/delay.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h> 
#include <dht11.h>
#include <stdio.h>
#include "hc_sr04.h"
#include <stdlib.h>

int main() {
    //pc_comm_init(9600, NULL);

    /*sensor_init();
    wifi_init();
    // Use a private connection
    wifi_command_join_AP("Redmi 9C NFC","santi32411");
    // Connect to backend server
    wifi_command_create_TCP_connection("192.168.43.83", 23, wifi_data_callback , received_message_buffer_static_pointer);*/
    app_init();
    
    while (1) {
        //char* sensors_data = sensor_get_data();
        //String with specified data
        //Formated data
        //getTempandHum();
        //rotate(-50,10);
        wifi_command_TCP_transmit("hello", 6);
        //wifi_command_TCP_transmit(100,sensor_get_data());
        
        // Delay for 30 minutes 
        //sleep(30 * 60); // Sleep for 30 minutes
        _delay_ms(3000);
    }
    return 0;
}