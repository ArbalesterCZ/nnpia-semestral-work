import {useEffect, useState} from "react";
import {Link} from "react-router-dom";

function Orders({token}) {

    const [orders, setOrders] = useState([])

    const removeDuplicateByKey = (arr, key) => {
        return arr.reduce((acc, val) => {
            if (acc.find(el => el[key] === val[key]))
                return [...acc];

            return [...acc, val];
        }, []);
    };

    useEffect(() => {
        fetch('http://localhost:8080/orders', {
            method: 'GET',
            headers: {'Authorization': token}
        })
            .then(response => response.json())
            .then(json => setOrders(removeDuplicateByKey(json, 'orderId')))
            .catch(err => console.log(err.message))
    }, [token])


    return (
        <table>
            <thead>
            <tr>
                <th>Order</th>
            </tr>
            </thead>
            <tbody>
            {orders.map(order =>
                <tr key={order.orderId}>
                    <td><Link to={'/orders/' + order.orderId}>{String(order.orderId).padStart(10, '0') + '#'}</Link>
                    </td>
                </tr>)}
            </tbody>
        </table>
    )
}


export default Orders