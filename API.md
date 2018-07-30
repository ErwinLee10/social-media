# Social Media API documentation


## Error handling

#### HTTP status code

Please be aware that HTTP status code is also meaningful. ([credits](http://blog.mwaysolutions.com/2014/06/05/10-best-practices-for-better-restful-api/))

 * __200 – OK__ – Everything is working
 * __201 – OK__ – New resource has been created
 * __204 – OK__ – The resource was successfully deleted
 * __304 – Not Modified__ – The client can use cached data
 * __400 – Bad Request__ – The request was invalid or cannot be served. The exact error should be explained in the error payload. E.g. The JSON is not valid
 * __401 – Unauthorized__ – The request requires an user authentication. (valid TBS-SSO assertion)
 * __403 – Forbidden__ – The server understood the request, but is refusing it or the access is not allowed.
 * __404 – Not found__ – There is no resource behind the URI.
 * __422 – Unprocessable Entity__ – Should be used if the server cannot process the entity, e.g. if an image cannot be formatted or mandatory fields are missing in the payload.
 * __500 – Internal Server Error__ – API developers should avoid this error. If an error occurs in the global catch blog, the stack trace should be logged and not returned as response.

#### Response with non-2xx status code

```javascript
{
  "errors": [
   {
    "userMessage": "Sorry, the requested resource does not exist",
    "internalMessage": "No car found in the database",
    "code": 34,
    "more info": "http://dev.mwaysolutions.com/blog/api/v1/errors/12345"
   }
  ]
} 
```

## API PATH
#### PUT /api/v1/users/friends
API to create a friend connection between two email addresses.

#### Request Body
```javascript
{
  "friends":
    [
      "andy@example.com",
      "john@example.com"
    ]
}
```

##### Response body
```javascript
{
  "success": true
}
```

#### POST /api/v1/users/friends/list
API to retrieve the friends list for an email address.

#### Request Body
```javascript
{
  "email": "andy@example.com"
}
```

##### Response body
```javascript
{
  "success": true,
  "friends" :
    [
      'john@example.com'
    ],
  "count" : 1   
}
```

#### POST /api/v1/users/friends/common
API to retrieve all email addresses that can receive updates from an email address.

#### Request Body
```javascript
{
  "friends":
    [
      "andy@example.com",
      "john@example.com"
    ]
}
```

##### Response body
````javascript
{
  "success": true,
  "friends" :
    [
      'common@example.com'
    ],
  "count" : 1   
}
````

#### PUT /api/v1/users/update/subscription
API to subscribe to updates from an email address.

#### Request Body
```javascript
{
  "requestor": "lisa@example.com",
  "target": "john@example.com"
}
```

#### PUT /api/v1/users/update/block
API to block updates from an email address

#### Request Body
```javascript
{
  "requestor": "andy@example.com",
  "target": "john@example.com"
}
```

##### Response body
````javascript
{
  "success": true
}
````

#### POST /api/v1/users/update/subscription/list
API to retrieve all email addresses that can receive updates from an email address

#### Request Body
```javascript
{
  "sender":  "john@example.com",
  "text": "Hello World! kate@example.com"
}
```

##### Response body
````javascript
{
  "success": true
  "recipients":
    [
      "lisa@example.com",
      "kate@example.com"
    ]
}
````
