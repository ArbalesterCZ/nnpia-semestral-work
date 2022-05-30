import '../css/product.css'
import {useNavigate} from "react-router-dom";
import {useCallback} from "react";

function Product({product, onBuy}) {

    const navigate = useNavigate();
    const LinkToDetailHandler = useCallback(() => navigate('/products/' + product.id, {replace: true}), [navigate]);

    return (
        <div className='column' style={{backgroundImage: 'url(http://localhost:8080/images/' + product.image + ')'}}>
            <h2><span>{product.name}</span></h2>
            <h3><span>{product.price} CZK</span></h3>
            <div><span>{product.description.substring(0, 32) + '...'}</span></div>
            <button className='details' onClick={LinkToDetailHandler}>View Detail</button>
            <button className='buy' onClick={() => onBuy(product)}>To the Cart</button>
        </div>
    )
}

export default Product