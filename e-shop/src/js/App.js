import '../css/App.css'
import '../css/forms.css'
import '../css/navigation.css'
import {useState} from "react"
import ProductForm from "./form/product-form";
import LoginForm from "./form/login-form";
import Products from "./products";
import CategoryForm from "./form/category-form";
import ScoreForm from "./form/score-form";

import {
    BrowserRouter as Router,
    Routes,
    Route,
    Link
} from "react-router-dom";
import ProductDetails from "./productDetails";
import Cart from "./cart";

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
            showMessage("Login successful")
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
            .then(json => showMessage('Product ' + json.productId + ' added to the cart.'))
            .catch(error => showMessage(error.message, 'error'))
    }

    return (
        <Router>
            <div className="app">
                <nav>
                    <ul>
                        <li><Link to="/">Login</Link></li>
                        <li><Link to="/products">Home</Link></li>
                        <li><Link to="/product-form">Product form</Link></li>
                        <li><Link to="/category-form">Category Form</Link></li>
                        <li><Link to="/score-form">Score Form</Link></li>
                        <li><Link to="/product-details">Product Details</Link></li>
                        <li><Link to="/cart">Cart</Link></li>
                    </ul>
                </nav>
                <Routes>
                    <Route path="/" element={<LoginForm onLogin={onLogin}/>}/>
                    <Route path="/products/"
                           element={<Products token={token} onAddProductToCart={onAddProductToCart}/>}/>
                    <Route path="/product-form" element={<ProductForm token={token} showMessage={showMessage}/>}/>
                    <Route path="/category-form" element={<CategoryForm token={token}/>}/>
                    <Route path="/score-form" element={<ScoreForm token={token}/>}/>
                    <Route path="/product-details"
                           element={<ProductDetails token={token} onBuy={onAddProductToCart}/>}/>
                    <Route path="/cart" element={<Cart token={token}/>}/>
                </Routes>
                {report.message && <div className={report.type}>{report.message}</div>}
            </div>
        </Router>
    );
}

export default App;
