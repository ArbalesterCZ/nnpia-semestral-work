import './css/App.css'
import Product from "./product"
import {useEffect, useState} from "react"
import ProductForm from "./product-form";
import {
    BrowserRouter as Router,
    Routes,
    Route,
    Link
} from "react-router-dom";

function App() {

    const [items, setItems] = useState([])
    const [token, setToken] = useState("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdDQzMTg5QHVwY2UuY3oiLCJleHAiOjE2NTM0OTgwMjIsImlhdCI6MTY1MzQ4MDAyMn0.Gcuh-JQ2blQDa_O4caTq_vdjCT4JvenuMiYXSBzGaIbCvfx2ojYLyo59EkqzvIT365iAG4Akpa3zWwrLGYCdYQ")

    const onBuyClick = function (product) {
        console.log(product)
    }

    const onAddProduct = function (product) {
        const copy = [...items]
        copy.push(product)
        setItems(copy)
    }

    useEffect(() => {
        fetch('http://localhost:8080/products', {
            method: 'GET',
            headers: {'Authorization': token},
        })
            .then(response => response.json())
            .then(json => setItems(json))
    }, [])

    return (
        <Router>
            <div className="app">

                <nav>
                    <ul>
                        <li>
                            <Link to="/">Home</Link>
                        </li>
                        <li>
                            <Link to="/product-form">Edit product</Link>
                        </li>
                    </ul>
                </nav>

                <Routes>
                    <Route path="/" element={<div className={"products"}>
                        {items.map(item => <Product key={item.id} item={item} onBuy={onBuyClick}/>)}</div>}/>
                    <Route path="/product-form" element={<ProductForm token={token} onAdd={onAddProduct}/>}/>
                </Routes>
            </div>

        </Router>
    );
}

export default App;
