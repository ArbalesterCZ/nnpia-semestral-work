import {useEffect, useState} from "react";

function ProductForm({token}) {

    const MIN_PRICE = 1;

    const [price, setPrice] = useState(MIN_PRICE)
    const [name, setName] = useState("")
    const [description, setDescription] = useState("")
    const [category, setCategory] = useState(1)

    const [categories, setCategories] = useState([])

    useEffect(() => {
        fetch('http://localhost:8080/category', {
            method: 'GET',
            headers: {'Authorization': token},
        })
            .then(response => response.json())
            .then(json => setCategories(json))
    }, [])

    const product = {
        price,
        name,
        description,
        categoryId: category
    }

    const onSubmit = event => {
        event.preventDefault()

        fetch('http://localhost:8080/products', {
            method: 'POST',
            headers:
                {
                    'Content-Type': 'application/json',
                    'Authorization': token
                },
            body: JSON.stringify(product)
        })

        setPrice(MIN_PRICE)
        setName("")
        setDescription("")
        setCategory(1)
    }

    return (
        <form onSubmit={onSubmit}>
            <input type={"number"} required={true} min={MIN_PRICE} value={price}
                   onChange={(e) => setPrice(parseInt(e.target.value))}/>
            <input type={"text"} required={true} placeholder={"Name"} value={name}
                   onChange={(e) => setName(e.target.value)}/>
            <input type={"text"} placeholder={"Description"} value={description}
                   onChange={(e) => setDescription(e.target.value)}/>
            <select name="category" onChange={(e) => setCategory(parseInt(e.target.value))}>
                {categories.map(item => <option key={item.id} value={item.id}>{item.name}</option>)}
            </select>
            <input type={"submit"} value={"Add Product"}/>
        </form>)
}


export default ProductForm