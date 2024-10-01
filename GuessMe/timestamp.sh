#!/bin/bash

# Guess Random Number
# Author: Parad0x_0xff
# Date: 2024-08-03

logTime=$(adb logcat -d | grep "keyCode=KEYCODE_ENTER" | tail -n 1 |awk '{print $2}')
init_date=$(gdate -d "$logTime" +"%s%3N")
echo $init_date
