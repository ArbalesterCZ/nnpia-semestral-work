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

function App() {

    const [token, setToken] = useState("")
    const [message, setMessage] = useState("")
    const [type, setType] = useState("")

    const sign = {
        message,
        type
    }

    const onLogin = function (token) {
        if (token === "Bearer undefined") {
            setMessage("Invalid Login Details")
            setType("error")
        } else {
            setMessage("Login successful")
            setType("information")
        }
        const sentText = message
        setTimeout(() => {
            if (sentText === message)
                setMessage("")
        }, 4000)
        setToken(token)
    }

    return (
        <Router>
            <div className="app">
                {sign.message && <div className={sign.type}>{sign.message}</div>}
                <nav>
                    <ul>
                        <li><Link to="/">Login</Link></li>
                        <li><Link to="/products">Home</Link></li>
                        <li><Link to="/product-form">Product form</Link></li>
                        <li><Link to="/category-form">Category Form</Link></li>
                        <li><Link to="/score-form">Score Form</Link></li>
                    </ul>
                </nav>
                <Routes>
                    <Route path="/" element={<LoginForm onLogin={onLogin}/>}/>
                    <Route path="/products/" element={<Products token={token}/>}/>
                    <Route path="/product-form" element={<ProductForm token={token}/>}/>
                    <Route path="/category-form" element={<CategoryForm token={token}/>}/>
                    <Route path="/score-form" element={<ScoreForm token={token}/>}/>
                </Routes>
            </div>
        </Router>
    );
}

export default App;
