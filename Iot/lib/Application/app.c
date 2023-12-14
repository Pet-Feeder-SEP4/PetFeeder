#include "include_avr_util.h"
#include "wifi.h"
#include "app.h"
#include "pc_comm.h" 
#include "dht11.h"
#include <stdio.h>
#include "hc_sr04.h"
#include "configuration.h"

#include "parse_info.h"

char buffer[8] = "";

void tcpCallback(){
    pc_comm_send_string_blocking("tcpcallback called");
    handle_received_data(buffer);
}
void app_init(){
    pc_comm_init(9600, NULL);
    dht11_init();
    hc_sr04_init();
    wifi_init();
    wifi_command_join_AP(WIFI_NAME, WIFI_PASSWORD);
    wifi_command_create_TCP_connection(IP, PORT, tcpCallback, buffer);
    _delay_ms(3000);
    
}

void app_start(void){
    sensor_get_data();
}