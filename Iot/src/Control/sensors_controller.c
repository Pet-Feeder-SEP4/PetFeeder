#include "pc_comm.h"
#include <stdio.h>
#include <string.h>
#include <unistd.h> 
#include <dht11.h>
#include <stdio.h>
#include "hc_sr04.h"
#include <stdlib.h>  // for malloc
#include <util/delay.h>

uint8_t humidity_integer, humidity_decimal, temperature_integer, temperature_decimal;
uint16_t measure;
int temperature;
int humidity;
char* waterMeasurement;
char* foodMeasurement;
int idNumber;

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
    return hc_sr04_takeMeasurement_water();
};

int getFoodMeasurement(){
    pc_comm_init(9600,NULL);
    pc_comm_send_string_blocking("food called");
    return hc_sr04_takeMeasurement_food();
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


//method to convert measurement result into string to pass it to cloud
char* intToString(int value) {
    
    char buffer[3];

    if(value<100){
        // Convert the integer to a string using sprintf with a 0 in front
    sprintf(buffer, "0%d", value);
    } else{
        sprintf(buffer, "%d", value);
    }
    
    // Allocate memory for the result string and copy the buffer
    char *resultString = malloc(strlen(buffer) + 1);
    strcpy(resultString, buffer);

    return resultString;
}