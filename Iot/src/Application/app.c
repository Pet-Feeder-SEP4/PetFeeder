#include "wifi.h"
#include "app.h"
#include "pc_comm.h" 
#include "dht11.h"
#include <stdio.h>
#include "hc_sr04.h"



char buffer[128];

void tcpCallback(){
    //pc_comm_send_string_blocking(buffer);
}
void app_init(){
    //sensor_init();
    pc_comm_init(9600, NULL);  // Initialize communication at the beginning
    dht11_init();
    hc_sr04_init();
    wifi_init();
    wifi_command_join_AP("ORBI54","fearlessbox180");
    wifi_command_create_TCP_connection("192.168.1.7", 23, tcpCallback , buffer);
}

void app_start(void){

}