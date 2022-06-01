import '../css/product.css'

function Product({
                     id,
                     title,
                     subtitle,
                     description,
                     image,
                     onClickOne,
                     onClickTwo,
                     onClickThree,
                     onClickFour,
                     buttonOneName,
                     buttonTwoName,
                     buttonThreeName,
                     buttonFourName
                 }) {
    return (
        <div className='column' style={{backgroundImage: 'url(http://localhost:8080/images/' + image + ')'}}>
            {title && <h2><span>{title}</span></h2>}
            {subtitle && <h3><span>{subtitle}</span></h3>}
            {description && <div><span>{description.substring(0, 32) + '...'}</span></div>}
            {onClickFour && <button className='four' onClick={() => onClickFour(id)}>{buttonFourName}</button>}
            {onClickThree && <button className='three' onClick={() => onClickThree(id)}>{buttonThreeName}</button>}
            {onClickTwo && <button className='two' onClick={() => onClickTwo(id)}>{buttonTwoName}</button>}
            {onClickOne && <button className='one' onClick={() => onClickOne(id)}>{buttonOneName}</button>}
        </div>
    )
}

export default Product