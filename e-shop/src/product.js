import './css/product.css'

function Product({item, onBuy}) {
    return (
        <div className={'column'}>
            <h2>{item.name}</h2>
            <h3>{item.price} CZK</h3>
            <div>{item.description}</div>
            <button onClick={() => onBuy({item})}>To the Cart</button>
        </div>)
}

export default Product