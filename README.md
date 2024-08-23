# Shopping cart
## Users
To test user endpoints, use /users to access the user controller
### /all
Get all users, no body or params
### /name
Get users with request parameters firstName & lastName
### /email
Get users with request parameter email
### /save
Create new user with request body of a JSON with email, lastName, firstName, areaOfInterest
### /update
Update user based on id with request body of a JSON with id, email, lastName, firstName, areaOfInterest
### /delete/{id}
Delete entire resource with path variable of the user id


## Products
To test product endpoints, use /products to access the product controller
