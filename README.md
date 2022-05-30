# nnpia-semestral-work

## Data Model
![data-model](rsc/images/data-model.png)

## BACKEND

### PRODUCTS
| operation | Path | Parameters | Body | Description
| --- | --- | --- | --- | --- |
| `POST`   | /products | | price, name, description, image | Create new product |
| `GET`    | /products | pageNumber, pageSize, sortBy| | Get all products |
| `GET`    | /products/***{id}*** | | | Get specific product |
| `PUT`    | /products/***{id}*** | | price, name, description, image | Update specific product |
| `DELETE` | /products/***{id}*** | | | Delete specific product |


### CATEGORY
| operation | Path | Parameters | Body | Description
| --- | --- | --- | --- | --- |
| `POST`   | /category | | name | Create new category |
| `GET`    | /category | | | Get all categories |
| `GET`    | /category/***{id}*** | | | Get specific category |
| `PUT`    | /category/***{id}*** | | name | Update specific category |
| `DELETE` | /category/***{id}*** | | | Delete specific category |


### USERS
| operation | Path | Parameters | Body | Description
| --- | --- | --- | --- | --- |
| `POST`   | /users | | name, email, password | Create new user |
| `GET`    | /users | | | Get all users |
| `GET`    | /users/***{id}*** | | | Get specific user |
| `PUT`    | /users/***{id}*** | | name, email, password | Update specific user |
| `DELETE` | /users/***{id}*** | | | Delete specific user |


### SCORE
| operation | Path | Parameters | Body | Description
| --- | --- | --- | --- | --- |
| `POST`   | /score | | value, productId | Create new score |
| `GET`    | /score/***{productId}*** | | | Get all scores of specific product |
| `GET`    | /score/***{userId}***/***{productId}*** | | | Get specific score |
| `PUT`    | /score | | value, productId | Update specific score |
| `DELETE` | /score/***{userId}***/***{productId}*** | | | Delete specific score |


### CART
| operation | Path | Parameters | Body | Description
| --- | --- | --- | --- | --- |
| `POST`   | /cart | | amount, comment, productId | Create new product in cart |
| `GET`    | /cart | | | Get all products in cart of specific user |
| `GET`    | /cart/***{userId}***/***{productId}*** | | | Get specific product in score |
| `PUT`    | /cart | | amount, comment, productId | Update specific product in score |
| `DELETE` | /cart | | | Delete specific product in cart |

## FRONTEND
