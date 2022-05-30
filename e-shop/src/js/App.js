import '../css/App.css'
import '../css/forms.css'
import '../css/navigation.css'
import {useState} from "react"
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

function App() {

    const [token, setToken] = useState("")

    const [message, setMessage] = useState("")
    const [type, setType] = useState("")
    const [lastReportId, setLastReportId] = useState()

    const report = {
        message,
        type
    }

    const showMessage = function (message, type = 'information') {
        clearTimeout(lastReportId)
        setMessage(message)
        setType(type)
        setLastReportId(setTimeout(() => setMessage(""), 4000))
    }

    const onLogin = function (token) {
        if (token === "Bearer undefined")
            showMessage("Invalid Login Details", "error")
        else {
            setToken(token)
            showMessage("Login Successful")
        }
    }

    const onAddProductToCart = function (product) {
        const cart = {
            amount: 1,
            productId: product.id
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
            .then(json =>
            {
                if(json.name)
                    showMessage('Product ' + json.name + ' added to the cart.')
                else
                    showMessage(json.message,'error')
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
                            <button onClick={() => setToken("")}>Log Out</button>
                            <li><Link to="/">Home</Link></li>
                            <li><Link to="/product-form">Product form</Link></li>
                            <li><Link to="/category-form">Category Form</Link></li>
                            <li><Link to="/cart">Cart</Link></li>
                        </ul>
                    </nav>
                    <Routes>
                        <Route path="/" element={<Products token={token} onAddProductToCart={onAddProductToCart}/>}/>
                        <Route path="/product-form" element={<ProductForm token={token} showMessage={showMessage}/>}/>
                        <Route path="/category-form" element={<CategoryForm token={token} showMessage={showMessage}/>}/>
                        <Route path="/cart" element={<Cart token={token}/>}/>
                        <Route path="/products/:id" element={<ProductDetail token={token} showMessage={showMessage}/>}/>
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
