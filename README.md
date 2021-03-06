##### This project is built using Play Framework version `2.5.10`

### Installation

Install the dependencies (`Mac os guide`)

```
$ git clone git@github.com:ErwinLee10/social-media.git
$ cd social-media
$ brew install sbt
```

to run the code, use this command:
`sbt clean compile run`
It will launch a netty server which will bind to a port `9000`

to run the testing, use this command:
`sbt test`

### Testing environment
This version of code has been deployed to AWS ec2 machine which can be accessed using this endpoint:
`http://54.169.175.25:9000`

the database has been deployed into RDS and can be accessed with this information:
```
endpoint: sp.clspvfri0oey.ap-southeast-1.rds.amazonaws.com/sp
username: sptest
password: sptestpassword
```

### API documentation 
the api documentation can be found ([here](https://github.com/ErwinLee10/social-media/blob/master/API.md))
