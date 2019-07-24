# ANZ Wholesale Sample Project

This backend sample project has been solved using the following tools:
- Java
- Spring Boot
- Spring Security
- MySQL

## APIs

### View Accounts

| HTTP_REQUEST | |
| --- | --- |
| URL | http://127.0.0.1:8080/api/v1/accounts |
| METHOD | GET |
| HEADER | Authorization: Bearer 87a333253610bbaf15324c194d5582b399930ea5 |
| CURL | curl http://127.0.0.1:8080/api/v1/accounts -H "Authorization: Bearer 2a02b367-e525-40a8-b681-6c70fbc1259c" |

**Tasks:**

- Make sure that an authenticated user is invoking this API
- Reads the user id from the Principal object (Spring Security)
- Retrieve the accounts for the given user from DB, cache the DB result and return

### View Transactions

| HTTP_REQUEST | |
| --- | --- |
| URL | http://127.0.0.1:8080/api/v1/accounts/<account_id>/transactions?page=0&size=5 |
| METHOD | GET |
| HEADER | Authorization: Bearer 87a333253610bbaf15324c194d5582b399930ea5 |
| CURL | curl http://127.0.0.1:8080/api/v1/accounts/3928393/transactions?page=0&size=5 -H "Authorization: Bearer 2a02b367-e525-40a8-b681-6c70fbc1259c" |


**Tasks:**

- Make sure that an authenticated user is invoking this API
- Retrieve the account id from the URL
- Fetch the account object from DB and cache it as well
- Match the user in account object with the user from principal object to make sure that user who is logged in is trying to view transactions from his account only.
- Fetch transactions from DB with the support of pagination and return back


## Helper APIs

### Delete Cache
 
| HTTP_REQUEST | |
| --- | --- |
| URL | https://127.0.0.1:8080/api/v1/accounts/deletecache |
| METHOD | GET |
| CURL | curl https://127.0.0.1:8080/api/v1/accounts/deletecache |

**Tasks :**

- Clears the account cache
- Only meant for this implementation, not for the actual project.


## Authentication

Apart from Delete Cache API, other APIs require a valid token to be used. For authentication, Spring Security module and oAuth2 is being used.
In order to generate the token, a reference Authorization server is also built into this code. Ideally, Authorization Server would be a different implementation and a separate source code base would be maintained for it. The implementation of Authorization Server is done just to give completeness to this project. As it is not part of the current scope, its implementation is very rudimentary.

To create a valid token, following needs to be done :

1. Create app_user table

`CREATE TABLE `app_user` (
  `user_id` varchar(50) NOT NULL,
  `user_pass` varchar(100) DEFAULT NULL,
  `user_role` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
)`

2. Insert a couple of users into this table

`insert into app_user values ('user1', 'password1', 'ROLE_USER');
insert into app_user values ('user2', 'password2', 'ROLE_USER');`

3. Start the Spring Boot Application

4. Hit /oauth/token API to generate the token

`curl -u client:secret -X POST http://127.0.0.1:8080/oauth/token -d "grant_type=password&username=user1&password=password1"`

Make sure that username and password is same as the entry done at step 2.

## Dummy Data 

The SQL queries for inserting some dummy data into Account and Transaction Table

`insert into account values('3928393','SGSavings355','SAVINGS',now(),'SGD',7193.60,'user1');`

`insert into account values('93739331','AUSavings920','SAVINGS',now(),'AUD',3592.20,'user2');`

`insert into transaction values ('918393360', 11, 'SGD',now(),'CREDIT',3928393);`

`insert into transaction values ('918393361', 210, 'SGD',now(),'CREDIT',3928393);`

`insert into transaction values ('918393362', 85, 'SGD',now(),'CREDIT',3928393);`

`insert into transaction values ('918393363', 165, 'SGD',now(),'CREDIT',3928393);`

`insert into transaction values ('918393364', 95, 'SGD',now(),'CREDIT',3928393);`

`insert into transaction values ('918393365', 27, 'SGD',now(),'CREDIT',3928393);`

`insert into transaction values ('918393366', 49, 'SGD',now(),'CREDIT',3928393);`

`insert into transaction values ('918393367', 89, 'SGD',now(),'CREDIT',3928393);`

`insert into transaction values ('918393368', 52, 'SGD',now(),'CREDIT',3928393);`

`insert into transaction values ('918393369', 28, 'SGD',now(),'CREDIT',3928393);`

`insert into transaction values ('918393370', 49, 'SGD',now(),'CREDIT',3928393);`

`insert into transaction values ('918393371', 108, 'SGD',now(),'CREDIT',3928393);`

`insert into transaction values ('918393372', 99, 'SGD',now(),'CREDIT',3928393);`

## Unit Tests

Unit tests have also been included in the solution. The tests can be run as follows

`mvn test`

## Steps to be followed

- Refer to [AUTHENTICATION](#authentication) section. Create table, insert users.
- Run the application
- Generate the token
- Make sure the account and transaction table have dummy entries. Refer to [Dummy Data](#dummy-data) section
- Hit the View Accounts API. Refer to [View Accounts](#view-accounts)
- Hit the View Transactions API. Refer to [View Transactions](#view-transactions)

