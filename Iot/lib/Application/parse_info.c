#include "include_avr_util.h"
#include "parse_info.h"
#include "sensor_controller.h"
#include "pc_comm.h"
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdio.h>
#include "servo360.h"
#include <stdlib.h>
#include "util.h"

char str[108];
int temperature;
int humidity;
int waterMeasurement;
int foodMeasurement;
int idNumber;

char* sensor_get_data()
{
    pc_comm_init(9600, NULL);
    getTempandHum();
    temperature = getTemp();
    humidity = getHum();
    idNumber = 37;
    waterMeasurement = getWaterMeasurement();
    _delay_ms(1000);
    foodMeasurement = getFoodMeasurement();
    sprintf(str, "{\"petFeederId\":\"%d\",\"foodLevel\":\"%d\",\"foodHumidity\":\"%d\",\"waterTemperature\":\"%d\",\"waterLevel\":\"%d\"}\n",
            idNumber, foodMeasurement, humidity, temperature, waterMeasurement);
    return str;
}

int get_gramms_from_data(char *data)
{
    char data_copy[256];
    strcpy(data_copy, data);

    if (str_length(data_copy) > 7) {
        pc_comm_send_string_blocking("Error parsing data\n");
        return -1;
    }
    
    pc_comm_send_string_blocking("Received data: ");
    pc_comm_send_string_blocking(data);
    // Tokenize the received message
    char *token = strtok(data_copy, ":");
    if (token != NULL && strcmp(token, "DIS") == 0)
    {
        // Get the next token, which should be the gramms value
        token = strtok(NULL, ":");
        if (token != NULL)
        {
            int gramms = atoi(token); // Convert the token to an integer
            if (gramms > 0) {
                return gramms;
            }
        }
    }
    
    pc_comm_send_string_blocking("Error parsing data\n");
    return -1;
}
