import {useEffect, useState} from "react";
import Product from "./product";

function Products({token, onAddProductToCart}) {

    const [items, setItems] = useState([])
    const [page, setPage] = useState(0)
    const [size, setSize] = useState(4)
    const [pageCount, setPageCount] = useState(0)
    const [productCount, setProductCount] = useState(0)
    const [sortBy, setSortBy] = useState('id')

    const changePageSize = function (value) {
        if (!isNaN(value))
            setSize(value)
    }

    useEffect(() => {
        setPage(Math.min(pageCount, page))
    }, [pageCount])

    useEffect(() => {
        const head = {
            method: 'GET',
            headers: {'Authorization': token}
        }

        fetch('http://localhost:8080/products?pageNumber=' + page + '&pageSize=' + size + '&sortBy=' + sortBy, head)
            .then(response => response.json())
            .then(json => setItems(json))

        fetch('http://localhost:8080/products/count', head)
            .then(response => response.json())
            .then(count => setProductCount(count))

        setPageCount(Math.max(0, Math.ceil(productCount / size) - 1));
    }, [page, size, sortBy, productCount])


    return (
        <>
            <button onClick={() => setSortBy("price")}>PRICE</button>
            <button onClick={() => setSortBy("name")}>NAME</button>
            <label htmlFor='size-page-input'>Page Size: </label>
            <input id='size-page-input' type='number' value={size} min={1} max={50}
                   onChange={e => changePageSize(parseInt(e.target.value))}/>
            <div className={"products"}>
                {items.map(item => <Product key={item.id} product={item} onBuy={onAddProductToCart}/>)}
            </div>
            <div>
                <button onClick={() => setPage(Math.max(0, page - 1))}>Prev</button>
                <button onClick={() => setPage(Math.min(pageCount, page + 1))}>Next</button>
                <div className={"pages"}>{page} / {pageCount}</div>
            </div>
        </>
    )
}


export default Products