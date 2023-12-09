#include "parse_info.h"
#include "sensor_controller.h"
#include "pc_comm.h"
#include <stdio.h>
#include <string.h>
#include <unistd.h> 
#include "dht11.h"
#include <stdio.h>
#include "hc_sr04.h"
#include <util/delay.h>
#include "wifi.h"

char str[108];
int temperature;
int humidity;
int waterMeasurement;
int foodMeasurement;
int idNumber;

void sensor_get_data(){
    pc_comm_init(9600, NULL);
    //pc_comm_send_string_blocking("sensor class called\n");
    temperature=getTemp();
    humidity=getHum();
    idNumber=555;
    waterMeasurement=getWaterMeasurement();
    _delay_ms(1000);
    foodMeasurement=getFoodMeasurement();
    sprintf(str, "{\"petFeederId\":\"%d\",\"foodLevel\":\"%d\",\"foodHumidity\":\"%d\",\"waterTemperature\":\"%d\",\"waterLevel\":\"%d\"}\n",
            idNumber, foodMeasurement, humidity, temperature, waterMeasurement);
    /*sprintf(str, "water= %d food= %d   hum=%d temp=%d id=%d \n\n",
                waterMeasurement,foodMeasurement,humidity,temperature,idNumber);*/
    wifi_command_TCP_transmit(str,108);
    pc_comm_send_string_blocking(str);
}

//Add method for handling data.
void handle_received_data(char *data) {
    pc_comm_send_string_blocking("Received data: ");
    pc_comm_send_string_blocking(data);
    

    // Tokenize the received message
    char *token = strtok(data, ":");
    if (token != NULL && strcmp(token, "DIS") == 0) {
        // Get the next token, which should be the gramms value
        token = strtok(NULL, ":");
        if (token != NULL) {
            int gramms = atoi(token);  // Convert the token to an integer

            pc_comm_send_string_blocking(" Dispensing ");
            pc_comm_send_int_blocking(gramms);
            pc_comm_send_string_blocking(" gramms of food\n");

            // Calculate the amount of turns based on the gramms value
            int amountOfTurns = gramms / 10; // Assuming 10 gramms per second

            // Call the rotate function from servo360.h
            rotate(50, amountOfTurns);  // Adjust the speed as needed
        }
    } else {
        pc_comm_send_string_blocking("Error parsing data\n");
    }
}