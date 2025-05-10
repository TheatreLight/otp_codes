# otp_codes
This is a service that creates operations and protects them by generating OTP-codes, sending them to user's email, SMS orTelegram  

# realisation details
It is implemented with Java HttpServer with PostgreSQL as DB. 

# how to check and test
You can send JSON-requests for the functionality testing. By default the server is runnning on 8080 port.
Create the .env file in the root of working directory. 
```
DB_URL=jdbc:postgresql://<YOUR_URL>
DB_UNAME=
DB_PWD=
SECRET=
EXP_TIME=
EMAIL_UNAME=
EMAIL_PWD=
MAIL_FROM=
MAIL_TO=
SMTP_HOST=
SMTP_PORT=
SMPP_DEST=
BOT_TOKEN=
CHAT_ID=
TG_UNAME=
FILENAME=<FILENAME_FOR_LOCAL_STORAGE>
```

## authorization
endpoint: 
`/register`

request:
```
{
    "login": "new_user",
    "pwd": "qwerty1234",
    "role": "USER"
}
```

response:
```
{"role":"USER","message":"Successfully registered!","login":"new_user"}
```

## authentication
endpoint:
`/login`

request:
```
{
    "login": "new_user",
    "pwd": "qwerty1234"
}
```

response:
```
{"role":"USER","login":"new_user","message":"Successfully logged in.","token":"<YOUR_TOKEN>"}
```

## mock operation
Don't forget to add received token to your headers!

endpoint:
`/operation/new`

request:
```
{
    "operationType": "PAYMENT",
    "operationStatus": "NEW",
    "senders": [
        "EMAIL",
        "LOCAL"
    ]
}
```

response:
```
{"otp":"877231","message":"Successfully created operation"}
```




