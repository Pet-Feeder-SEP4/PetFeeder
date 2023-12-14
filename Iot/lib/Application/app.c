#include "include_avr_util.h"
#include "wifi.h"
#include "app.h"
#include "pc_comm.h" 
#include "dht11.h"
#include <stdio.h>
#include "hc_sr04.h"
#include "configuration.h"

#include "parse_info.h"
#include "util.h"
#include "servo_controller.h"

char buffer[BUFFER_SIZE] = "";

void connect_wifi(void);

void tcpCallback(){
    pc_comm_send_string_blocking("Reciving data...\n");
    
    int gramms = get_gramms_from_data(buffer);

    if (gramms > -1) {
        dispense(gramms);
    }
}

void app_init(){
    pc_comm_init(9600, NULL);
    dht11_init();
    hc_sr04_init();
    connect_wifi();
    _delay_ms(3000);
}

void app_start(void){
    char* data = sensor_get_data();

    WIFI_ERROR_MESSAGE_t error = wifi_command_TCP_transmit((uint8_t *)data, str_length(data));

    // if can't send data -> reconnect to wifi and server
    if (error != WIFI_OK) {
        pc_comm_send_string_blocking("Error sending data. Try to reconnect Wi-Fi and Server\n");
        wifi_command_close_TCP_connection();
        wifi_command_quit_AP();
        connect_wifi();
        app_start();
    } else {
        pc_comm_send_string_blocking(data);
    }
}

void connect_wifi() {
    wifi_init();
    
    WIFI_ERROR_MESSAGE_t error_AP, error_TCP;
    
    while(error_AP != WIFI_OK) {
        error_AP = wifi_command_join_AP(WIFI_NAME, WIFI_PASSWORD);
        if (error_AP != WIFI_OK) {
            char str[128];
            sprintf(str, "Error connecting to the wifi %s\n", WIFI_NAME);
            pc_comm_send_string_blocking(str);
            _delay_ms(5000);
        }
    }

    while(error_TCP != WIFI_OK) {
        error_TCP = wifi_command_create_TCP_connection(IP, PORT, tcpCallback, buffer);
        if (error_TCP != WIFI_OK) {
            char str[128];
            sprintf(str, "Error connecting to the server %s:%d\n", IP, PORT);
            pc_comm_send_string_blocking(str);
        }
        _delay_ms(5000);
    }
}