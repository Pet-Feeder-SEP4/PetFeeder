#include <stdio.h>
#include <string.h>
#include <unistd.h> 
#include <util/delay.h>
#include "pc_comm.h"
#include "servo.h"
#include "buttons.h"
#include "wifi.h"
#include "Sensors/sensors.h"
#include "servo360.h"
#include "HandleData/HandleData.h"

char buffer[128];
void tcpCallback(){
    pc_comm_send_string_blocking(buffer);
}
int main() {
    pc_comm_init(9600, NULL);
    //sensor_init();
    wifi_init();
    //Connect to backend server
    wifi_command_join_AP("Mamalo","Iker1234");
    wifi_command_create_TCP_connection("172.20.10.2", 23, tcpCallback , buffer);
    //wifi_command_TCP_transmit("hello\n",5);
    //wifi_command_close_TCP_connection();
    //pc_comm_send_string_blocking("hello sent\n");
    
    while (1) {
        //rotate(-50,2);
        //wifi_command_create_TCP_connection("192.168.1.7", 23, wifi_data_callback , received_message_buffer_static_pointer);
        //_delay_ms(5000);
        
        wifi_command_TCP_transmit(sensor_get_data(),128);
        //sensor_get_data();
        //wifi_command_TCP_transmit(getTemp(),8);
        //_delay_ms(3000);
        //wifi_command_TCP_transmit(getHum(),8);
        //wifi_command_TCP_transmit("hellooss",8);
        //wifi_command_close_TCP_connection();
        //sensor_get_data();
        //pc_comm_send_string_blocking("hello sent 2\n\n");
        _delay_ms(3000);
    }
    return 0;
}