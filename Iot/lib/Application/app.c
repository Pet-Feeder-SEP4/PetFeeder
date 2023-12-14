#include "include_avr_util.h"
#include "app.h"
#include "pc_comm.h" 
#include "dht11.h"
#include <stdio.h>
#include "hc_sr04.h"

#include "parse_info.h"
#include "util.h"
#include "servo_controller.h"
#include "wifi_controller.h"

char buffer[BUFFER_SIZE] = "";

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
    connect_wifi(tcpCallback, buffer);
    _delay_ms(3000);
}

void app_start(void){
    char* data = sensor_get_data();

    WIFI_ERROR_MESSAGE_t error = transmit_data(data);

    // if can't send data -> reconnect to wifi and server
    if (error != WIFI_OK) {
        pc_comm_send_string_blocking("Error sending data. Try to reconnect Wi-Fi and Server\n");
        close_wifi();
        connect_wifi(tcpCallback, buffer);
        app_start();
    } else {
        pc_comm_send_string_blocking(data);
    }
}

