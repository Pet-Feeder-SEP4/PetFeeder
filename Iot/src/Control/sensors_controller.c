#include "sensor_controller.h"
#include "pc_comm.h"
#include <stdio.h>
#include <string.h>
#include <unistd.h> 
#include <dht11.h>
#include <stdio.h>
#include "hc_sr04.h"
#include <util/delay.h>

uint8_t humidity_integer, humidity_decimal, temperature_integer, temperature_decimal;
uint16_t measure;
char str[64];
int temperature;
int humidity;
int waterMeasurement;
int foodMeasurement;
int idNumber;

// Function prototypes
void sensor_init();
//char* sensor_get_data();
int getHum();
int getTemp();
int getWaterMeasurement();
int getFoodMeasurement();


int getTemp(){
    //pc_comm_send_string_blocking("water data called\n");
    uint8_t humidity_integer, humidity_decimal, temperature_integer, temperature_decimal;
    if (dht11_get(&humidity_integer, &humidity_decimal, &temperature_integer, &temperature_decimal) == DHT11_OK) {
        return temperature_integer;
     }
}

int getHum(){
    uint8_t humidity_integer, humidity_decimal, temperature_integer, temperature_decimal;
    if (dht11_get(&humidity_integer, &humidity_decimal, &temperature_integer, &temperature_decimal) == DHT11_OK) {
        return humidity_integer;
     }
}

int getWaterMeasurement(){
    uint16_t temporaryMeasure;
    temporaryMeasure = hc_sr04_takeMeasurement_water();
   if (temporaryMeasure != 0)
        {
           return temporaryMeasure;
        }
        else
        {
            // Print an error message if the read operation fails
            pc_comm_send_string_blocking("Invalid water measurement\n");
        }
};

int getFoodMeasurement(){
    uint16_t temporaryMeasure;
    temporaryMeasure = hc_sr04_takeMeasurement_food();
   if (temporaryMeasure != 0)
        {
           return temporaryMeasure;
        }
        else
        {
            // Print an error message if the read operation fails
            pc_comm_send_string_blocking("Invalid food measurement\n");
        }
};

int waterMeasurementPercentage(){
    //put height of the cup
    int capacity=1000;
    int temporaryMeasure=getWaterMeasurement();
    int result= (temporaryMeasure*100)/ capacity;
    return result;
}

int foodMeasurementPercentage(){
    //put height of the cup
    int capacity=1000;
    int temporaryMeasure=getFoodMeasurement();
    int result= (temporaryMeasure*100)/ capacity;
    return result;
}
