import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";

function Order({token}) {

    const params = useParams()

    const [products, setProducts] = useState([])

    const [quantity, setQuantity] = useState(0)
    const [totalPrice, setTotalPrice] = useState(0)

    useEffect(() => {
        fetch('http://localhost:8080/orders/' + params.id, {
            method: 'GET',
            headers: {'Authorization': token}
        })
            .then(response => response.json())
            .then(json => setProducts(json))
            .catch(err => console.log(err.message))
    }, [token])

    useEffect(() => {
        let quantityCounter = 0
        products.map(product => quantityCounter += product.amount)
        setQuantity(quantityCounter)

        let totalPriceCounter = 0
        products.map(product => totalPriceCounter += product.amount * product.price)
        setTotalPrice(totalPriceCounter)
    }, [products])

    return (
        <table>
            <thead>
            <tr>
                <th>Product</th>
                <th>Price</th>
                <th>Amount</th>
                <th>Subtotal Price</th>
            </tr>
            </thead>
            <tbody>
            {products.map(item =>
                <tr key={item.id}>
                    <td>{item.name}</td>
                    <td>{item.price}</td>
                    <td>{item.amount}</td>
                    <td>{item.amount * item.price}</td>
                </tr>)
            }
            <tr>
                <td/>
                <td/>
                <td>{quantity}</td>
                <td>{totalPrice}</td>
            </tr>
            </tbody>
        </table>
    )
}


export default Order