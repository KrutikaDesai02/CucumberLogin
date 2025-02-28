Feature: Login functionality

Scenario: Login with valid credentioals
Given User navigate to login page
When User enters valid email address "Admin" in email field
And User enters valid password "admin123" in password field
And User clicks on Login button
Then User should get successfully login

Scenario Outline: Login with invalid credentioals
Given User navigate to login page
When User enters invalid email address <username> in email field
And User enters invalid password <password> in password field
And User clicks on Login button
Then User should get a proper warning message about credentials mismatch
Examples: 
|username               |password |
|amotooricap1@gmail.com	|12345		|
|amotooricap2@gmail.com	|12345		|
|amotooricap3@gmail.com	|12345		|

Scenario: Login with valid email and invalid password
Given User navigate to login page
When User enters the following credentials:
| username   | password  |
| admin      | wrong123  |
| testuser   | invalid   |
And User clicks on Login button
Then User should get a proper warning message about credentials mismatch

Scenario: Login with invalid email and valid password
Given User navigate to login page
When User enters invalid email and valid password from database
Then User should see a proper warning message for incorrect email

Scenario: Login without providing any credentails
Given User navigate to login page
When User tries to login without entering any credentials
Then User should see a warning message for missing credentials