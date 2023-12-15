#pragma once
#include <stdint.h>
#include "wifi.h"

void connect_wifi(WIFI_TCP_Callback_t tcpCallback, char *buffer);
void close_wifi();
WIFI_ERROR_MESSAGE_t transmit_data(char *);