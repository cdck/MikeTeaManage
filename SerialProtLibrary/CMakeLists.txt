# 指定 cmake 的最小版本
cmake_minimum_required(VERSION 3.4.1)

add_library(
            serial_port
            SHARED
            src/main/jni/SerialPort.c)

find_library(
            log-lib
            log)

target_link_libraries(
                      # serial_port 是上面 add_library 指定的变量名
                      serial_port
                      # log-lib 是上面 find_library 指定的变量名
                      ${log-lib})

# set(CMAKE_C_FLAGS_RELEASE "${CMAKE_C_FLAGS_RELEASE} -s")
# set(CMAKE_CXX_FLAGS_RELEASE "${CMAKE_CXX_FLAGS_RELEASE} -s")


