#include <stdio.h>
#include <string.h>
#include <unistd.h> 
#include <util/delay.h>
#include <unistd.h>
#include "pc_comm.h"
#include "servo.h"
#include "buttons.h"
#include "wifi.h"


int main() {
    pc_comm_init(9600, NULL);
    wifi_init();

    // Use a private connection
    wifi_command_join_AP("ORBI54","fearlessbox180");

    // Connect to backend server
    wifi_command_create_TCP_connection("192.168.1.7", 24, NULL, NULL);

    while (1) {
        
        //Data format (Adjust later so it sends sensors' data)
        int water_level = 5;
        int food_level = 2;
        int food_humidity = 7;
        int water_temperature = 4;
        int id_number = 150;

        //String with specified data
        char dataToSend[100]; //73 constant part (strings) and 5*3 data place holders
        snprintf(dataToSend, sizeof(dataToSend), "water level=%03d food level=%03d food humidity=%03d dwater temperature=%03d id Number=%03d\n", water_level, food_level, food_humidity, water_temperature, id_number);
        
        //Formated data
        wifi_command_TCP_transmit(dataToSend, strlen(dataToSend));
        
        // Delay for 30 minutes 
        //sleep(30 * 60); // Sleep for 30 minutes
        _delay_ms(5000);
    }

    return 0;
}
