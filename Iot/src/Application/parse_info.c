#include "parse_info.h"
#include "Control/sensor_controller.h"
#include "pc_comm.h"
#include <stdio.h>
#include <string.h>
#include <unistd.h> 
#include <dht11.h>
#include <stdio.h>
#include "hc_sr04.h"
#include <util/delay.h>
#include "wifi.h"

char str[64];
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
    wifi_command_TCP_transmit(str,104);
    pc_comm_send_string_blocking(str);
}