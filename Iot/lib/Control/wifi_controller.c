#include "wifi_controller.h"
#include "../Application/configuration.h"
#include "../Application/util.h"

#include "pc_comm.h"
#include <stdio.h>
#include "../Application/include_avr_util.h"

void connect_wifi(WIFI_TCP_Callback_t tcpCallback, char *buffer) {
    wifi_init();
    
    WIFI_ERROR_MESSAGE_t error_AP = WIFI_FAIL;
    WIFI_ERROR_MESSAGE_t error_TCP = WIFI_FAIL;

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
            _delay_ms(5000);
        }
    }
}

void close_wifi() {
    wifi_command_close_TCP_connection();
    wifi_command_quit_AP();
}

WIFI_ERROR_MESSAGE_t transmit_data(char *data) {
    return wifi_command_TCP_transmit((uint8_t *)data, str_length(data));
}