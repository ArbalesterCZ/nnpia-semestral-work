import '../css/table.css'
import {useEffect, useState} from "react";

function Cart({token}) {

    const [products, setProducts] = useState([])

    useEffect(() => {
        const head = {
            method: 'GET',
            headers: {'Authorization': token}
        }

        fetch('http://localhost:8080/carts', head)
            .then(response => response.json())
            .then(json => setProducts(json))
    }, [])

    return (
        <table>
            <thead>
            <tr>
                <th>Product</th>
                <th>Amount</th>
            </tr>
            </thead>
            <tbody>
            {products.map(product =>
                <tr key={product.id}>
                    <td key={product.id + 'product'}>{product.productId}</td>
                    <td key={product.id + 'amount'}>{product.amount}</td>
                </tr>)}
            </tbody>
        </table>)
}


export default Cart