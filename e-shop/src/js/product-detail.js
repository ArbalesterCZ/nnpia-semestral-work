import {useParams} from "react-router-dom"
import {useEffect, useState} from "react";
import Score from "./score";

function ProductDetail({token, showMessage}) {
    const params = useParams()
    const [product, setProduct] = useState()

    useEffect(() => {
        fetch('http://localhost:8080/products/' + params.id, {
            method: 'GET',
            headers: {'Authorization': token}
        })
            .then(response => response.json())
            .then(json => setProduct(json))
            .catch(err => console.log(err))
    }, [])

    return (
        <>{product &&
        <>
            <div className='row'>
                <div className='image'>
                    <img src={'http://localhost:8080/images/' + product.image} alt='Product Image' width={300} height={300}/>
                </div>
                <div className='description'>
                    <h1>{product.name}</h1>
                    <h2>{product.price} CZK</h2>
                    <h3>{product.categoryName}</h3>
                    <div className='description'>{product.description}</div>
                </div>
            </div>
            <Score token={token} productId={product.id} showMessage={showMessage}/>
        </>}
        </>
    )
}


export default ProductDetail