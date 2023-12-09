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
    wifi_init();
    sensor_init();
    // Use a private connection
    wifi_command_join_AP("Mamalo","Iker1234");
    // Connect to backend server
    wifi_command_create_TCP_connection("172.20.10.2", 23, wifi_data_callback , received_message_buffer_static_pointer);
    
    while (1) {
        //sensor_get_data();
        //String with specified data
        //Formated data

        //rotate(-50,10);

        wifi_command_TCP_transmit(sensor_get_data(),100);
        
        // Delay for 30 minutes 
        //sleep(30 * 60); // Sleep for 30 minutes
        _delay_ms(5000);
    }
    return 0;
}