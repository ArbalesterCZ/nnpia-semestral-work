import {useEffect, useState} from "react";
import Product from "./product";
import {useNavigate} from "react-router-dom";

function Products({token, onAddProductToCart}) {

    const MIN_PAGE_SIZE = 1
    const MAX_PAGE_SIZE = 50

    const [items, setItems] = useState([])
    const [page, setPage] = useState(0)
    const [size, setSize] = useState(4)
    const [pageCount, setPageCount] = useState(0)
    const [productCount, setProductCount] = useState(0)
    const [sortBy, setSortBy] = useState('id')

    const changePageSize = function (value) {
        (!isNaN(value) && value >= MIN_PAGE_SIZE && value <= MAX_PAGE_SIZE) && setSize(value)
    }

    const navigate = useNavigate();
    const navigateToSpecificProduct = function (id) {
        navigate('/products/' + id, {replace: true})
    }

    const navigateToSpecificProductForm = function (id) {
        navigate('/product-form/' + id, {replace: true})
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
            <button className='standard' onClick={() => setSortBy("price")}>PRICE</button>
            <button className='standard' onClick={() => setSortBy("name")}>NAME</button>
            <label htmlFor='size-page-input'>Page Size: </label>
            <input className='ultra-short' id='size-page-input' type='number' value={size} min={MIN_PAGE_SIZE}
                   max={MAX_PAGE_SIZE} onChange={e => changePageSize(parseInt(e.target.value))}/>
            <div className={"products"}>
                {items.map(item => <Product
                    key={item.id}
                    id={item.id}
                    url='/products/'
                    title={item.name}
                    subtitle={item.price + ' CZK'}
                    description={item.description}
                    image={item.image}
                    buttonThreeName='Edit Product'
                    onClickThree={navigateToSpecificProductForm}
                    buttonTwoName='Product Details'
                    onClickTwo={navigateToSpecificProduct}
                    buttonOneName='Add To the Cart'
                    onClickOne={onAddProductToCart}
                />)}
            </div>
            <div>
                <button className='standard' onClick={() => setPage(Math.max(0, page - 1))}>Prev</button>
                <button className='standard' onClick={() => setPage(Math.min(pageCount, page + 1))}>Next</button>
                <div className={"pages"}>{page} / {pageCount}</div>
            </div>
        </>
    )
}


export default Products