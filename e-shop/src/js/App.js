import '../css/App.css'
import '../css/forms.css'
import '../css/navigation.css'
import {useEffect, useState} from "react"
import ProductForm from "./form/product-form";
import LoginForm from "./form/login-form";
import Products from "./products";
import CategoryForm from "./form/category-form";

import {
    BrowserRouter as Router,
    Routes,
    Route,
    Link
} from "react-router-dom";

import Cart from "./cart";
import ProductDetail from "./product-detail";
import UserForm from "./form/user-form";
import Orders from "./orders";
import Order from "./order";

function App() {

    const [token, setToken] = useState("")
    const [user, setUser] = useState("");
    const [isAdmin, setIsAdmin] = useState(false)

    const [message, setMessage] = useState("")
    const [type, setType] = useState("")
    const [lastReportMessageId, setLastReportMessageId] = useState()

    const report = {
        message,
        type
    }

    useEffect(() => {
        if (token !== "") {
            fetch('http://localhost:8080/users/logged', {
                method: 'GET',
                headers: {'Authorization': token}
            })
                .then(response => response.json())
                .then(json => {
                    setUser(json)
                    console.log(json)
                    showMessage("Login Successful")
                })
                .catch(err => showMessage(err.message, 'error'))

            fetch('http://localhost:8080/authorities', {
                method: 'GET',
                headers: {'Authorization': token},
            })
                .then(response => response.json())
                .then(json => setIsAdmin(json.length > 0 && json[0].authority === "ROLE_ADMIN")
                )
                .catch(err => showMessage(err.message))
        }
    }, [token])

    const onChangeUserInfo = function (changedUser) {
        setUser(changedUser)
        showMessage('User ' + changedUser.name + ' information changed')
    }

    const showMessage = function (message, type = 'information') {
        clearTimeout(lastReportMessageId)
        setMessage(message)
        setType(type)
        setLastReportMessageId(setTimeout(() => setMessage(""), 4000))
    }

    const onLogin = function (token) {
        if (token === "Bearer undefined")
            showMessage("Invalid Login Details", "error")
        else
            setToken(token)
    }

    const onAddProductToCart = function (productId) {
        const cart = {
            amount: 1,
            productId
        }
        fetch('http://localhost:8080/carts', {
            method: 'POST',
            headers:
                {
                    'Content-Type': 'application/json',
                    'Authorization': token
                },
            body: JSON.stringify(cart)
        })
            .then(response => response.json())
            .then(json => {
                if (json.name)
                    showMessage('Product ' + json.name + ' added to the cart.')
                else
                    showMessage(json.message, 'error')
            })
            .catch(err => showMessage(err.message, 'error'))
    }

    return (
        <Router>
            <div className="app">
                {token &&
                <>
                    <nav>
                        <ul>
                            <button className='standard' onClick={() => setToken("")}>Log Out {user.name}</button>
                            <li><Link to="/">Home</Link></li>
                            {isAdmin && <li><Link to="/product-form">Product form</Link></li>}
                            {isAdmin && <li><Link to="/category-form">Category Form</Link></li>}
                            <li><Link to="/cart">Cart</Link></li>
                            <li><Link to="/user">User Account</Link></li>
                            <li><Link to="/orders">Orders</Link></li>
                        </ul>
                    </nav>
                    <Routes>
                        <Route path="/" element={<Products isAdmin={isAdmin} token={token} showMessage={showMessage}
                                                           onAddProductToCart={onAddProductToCart}/>}/>
                        <Route path="/product-form" element={<ProductForm token={token} showMessage={showMessage}/>}/>
                        <Route path="/category-form" element={<CategoryForm token={token} showMessage={showMessage}/>}/>
                        <Route path="/cart" element={<Cart token={token}/>}/>
                        <Route path="/products/:id" element={<ProductDetail token={token} showMessage={showMessage}/>}/>
                        <Route path="/user" element={<UserForm token={token} showMessage={showMessage}
                                                               onChangeUser={onChangeUserInfo}/>}/>
                        <Route path="/product-form/:id"
                               element={<ProductForm token={token} showMessage={showMessage}/>}/>
                        <Route path="orders" element={<Orders token={token}/>}/>
                        <Route path="/orders/:id" element={<Order token={token}></Order>}/>
                    </Routes>
                </>
                }
                {!token && <LoginForm onLogin={onLogin}/>}
                {report.message && <div className={report.type}>{report.message}</div>}
            </div>
        </Router>
    );
}

export default App;
