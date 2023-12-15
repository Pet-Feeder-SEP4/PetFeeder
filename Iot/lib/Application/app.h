#pragma once
#define BUFFER_SIZE 8

extern char buffer[BUFFER_SIZE];
void tcpCallback();
void app_init();
void app_start(void);

