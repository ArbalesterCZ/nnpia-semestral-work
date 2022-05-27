import '../css/product.css'

function Product({item, onDetails, onBuy}) {
    return (
        <div className={'column'}>
            <h2>{item.name}</h2>
            <h3>{item.price} CZK</h3>
            <div>{item.description}</div>
            {onDetails && <button className={'details'} onClick={() => onDetails(item.id)}>View Details</button>}
            <button className={'buy'} onClick={() => onBuy(item)}>To the Cart</button>
        </div>)
}

export default Product