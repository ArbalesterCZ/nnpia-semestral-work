import Product from "./product";
import ScoreForm from "./form/score-form";

function ProductDetails({product, onBuy, token}) {


    return (
        <>
            <Product item={product} onBuy={onBuy} onDetails={null}/>
            <ScoreForm token={token}/>
        </>
    )
}


export default ProductDetails