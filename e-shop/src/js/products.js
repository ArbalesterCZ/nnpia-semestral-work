import {useEffect, useState} from "react";
import Product from "./product";


function Products({token}) {

    const [items, setItems] = useState([])

    const onAddProductToCart = function (product) {
        console.log(product)
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
        <div className={"products"}>
            {items.map(item => <Product key={item.id} item={item} onBuy={onAddProductToCart}/>)}
        </div>
    )
}


export default Products