import '../css/table.css'
import {useEffect, useState} from "react"

function Cart({token}) {

    const [products, setProducts] = useState([])

    const [quantity, setQuantity] = useState(0)
    const [totalPrice, setTotalPrice] = useState(0)

    const removeProductFromCart = function (id) {
        setProducts([...products].filter((product) => product.id !== id))
    }

    const changeProductInCartAmount = function (value, product) {
        if (isNaN(value))
            return

        const copy = [...products]
        copy.find((item) => item.id === product.id).amount = value
        setProducts(copy)

        const dto = {
            amount: value,
            productId: product.id
        }

        fetch('http://localhost:8080/carts/', {
            method: 'PUT',
            headers:
                {
                    'Content-Type': 'application/json',
                    'Authorization': token
                }, body: JSON.stringify(dto)
        })
            .then(response => response.json())
            .then(json => console.log(json))
    }

    const onClickRemoveFromCart = function (productId) {
        fetch('http://localhost:8080/carts', {
            method: 'DELETE',
            headers:
                {
                    'Content-Type': 'application/json',
                    'Authorization': token
                },
            body: JSON.stringify({productId})
        })
            .then(response => response.json())
            .then(json => removeProductFromCart(json.id))
    }

    const onClickSendOrder = function () {
        fetch('http://localhost:8080/orders', {
            method: 'POST',
            headers: {'Authorization': token}
        })
            .then(() => setProducts([]))
            .catch(err => console.log(err.message))
    }

    useEffect(() => {
        fetch('http://localhost:8080/carts', {
            method: 'GET',
            headers: {'Authorization': token}
        })
            .then(response => response.json())
            .then(json => setProducts(json))
    }, [])

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
                <th/>
            </tr>
            </thead>
            <tbody>
            {products.map(product =>
                <tr key={product.id}>
                    <td key={product.id + 'product'}>{product.name}</td>
                    <td key={product.id + 'price'}>{product.price}</td>
                    <td key={product.id + 'amount'}><input className='short'
                                                           type={"number"}
                                                           min={1}
                                                           onChange={e => changeProductInCartAmount(parseInt(e.target.value), product)}
                                                           value={product.amount}/>
                    </td>
                    <td key={product.id + 'subtotal'}>{product.amount * product.price}</td>
                    <td>
                        <button className="delete-button"
                                onClick={() => onClickRemoveFromCart(product.id)}>&#10006;</button>
                    </td>
                </tr>)}
            <tr className="total">
                <td>Total</td>
                <td/>
                <td>{quantity}</td>
                <td>{totalPrice}</td>
                <td>
                    {products.length > 0 && <button className='standard' onClick={onClickSendOrder}>Send Order</button>}
                </td>
            </tr>
            </tbody>
        </table>
    )
}

export default Cart