#include <stdio.h>
#include <string.h>
#include <unistd.h> 
#include <util/delay.h>
#include <unistd.h>
#include "pc_comm.h"
#include "servo.h"
#include "buttons.h"
#include "wifi.h"
#include "sensors.h"

int main() {
    pc_comm_init(9600, NULL);
    wifi_init();
    sensor_init();
    // Use a private connection
    wifi_command_join_AP("ORBI54","fearlessbox180");
    // Connect to backend server
    wifi_command_create_TCP_connection("192.168.1.7", 24, NULL, NULL);
    
    while (1) {
        sensor_get_data();
        //String with specified data
        //Formated data
        wifi_command_TCP_transmit(100, "hello");
        // Delay for 30 minutes 
        //sleep(30 * 60); // Sleep for 30 minutes
        _delay_ms(5000);
    }
    return 0;
}