#include "wifi.h"
#include "app.h"



char buffer[128];

void tcpCallback(){
    //pc_comm_send_string_blocking(buffer);
}
void app_init(){
    //sensor_init();
    wifi_init();
    wifi_command_join_AP("Orbi54","fearlessbox180");
    wifi_command_create_TCP_connection("192.168.1.7", 23, tcpCallback , buffer);
}

void app_start(void){

}