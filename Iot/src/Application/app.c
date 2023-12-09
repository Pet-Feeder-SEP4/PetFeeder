#include "wifi.h"
#include "app.h"
#include "pc_comm.h" 
#include "dht11.h"
#include <stdio.h>
#include <util/delay.h>
#include "hc_sr04.h"
#include "parse_info.h"
#include "sensor_controller.h"



char buffer[8];

void tcpCallback(){
    pc_comm_send_string_blocking("tcpcallback called");
    handle_received_data(buffer);
}
void app_init(){
    //sensor_init();
    pc_comm_init(9600, NULL);  // Initialize communication at the beginning
    dht11_init();
    hc_sr04_init();
    wifi_init();
    wifi_command_join_AP("Redmi 9C NFC","santi32411");
    wifi_command_create_TCP_connection("192.168.43.83", 23, tcpCallback, buffer);
}

void app_start(void){
    sensor_get_data();
    //Delay to send data to cloud.
    //_delay_ms(5000);
    
}