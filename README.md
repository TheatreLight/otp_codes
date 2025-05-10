# otp_codes
This is a service that creates operations and protects them by generating OTP-codes, sending them to user's email, SMS orTelegram  

# realisation details
It is implemented with Java HttpServer with PostgreSQL as DB. 

# how to check and test
You can send JSON-requests for the functionality testing. By default the server is runnning on 8080 port.
## authorization
endpoint: 
`/register`

request:
`{
    "login": "new_user",
    "pwd": "qwerty1234",
    "role": "USER"
}`


