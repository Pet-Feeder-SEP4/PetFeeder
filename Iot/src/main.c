#include <stdio.h>
#include <string.h>
#include <unistd.h> 
#include <util/delay.h>
#include <unistd.h>
#include "pc_comm.h"
#include "servo.h"
#include "buttons.h"
#include "wifi.h"
#include "Sensors/sensors.h"
#include "servo360.h"
#include "HandleData/HandleData.h"

int main() {
    pc_comm_init(9600, NULL);
    sensor_init();
    wifi_init();
    // Use a private connection
    wifi_command_join_AP("Redmi 9C NFC","santi32411");
    // Connect to backend server
    wifi_command_create_TCP_connection("192.168.43.83", 23, wifi_data_callback , received_message_buffer_static_pointer);
    
    
    while (1) {
        //char* sensors_data = sensor_get_data();
        //String with specified data
        //Formated data
        //getTempandHum();
        //rotate(-50,10);
        wifi_command_TCP_transmit(100, "hello");
        //wifi_command_TCP_transmit(100,sensor_get_data());
        
        // Delay for 30 minutes 
        //sleep(30 * 60); // Sleep for 30 minutes
        _delay_ms(3000);
    }
    return 0;
}