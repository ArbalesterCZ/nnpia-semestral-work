import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";

function ProductForm({token, showMessage}) {

    const params = useParams()

    const MIN_PRICE = 1;

    const [price, setPrice] = useState(MIN_PRICE)
    const [name, setName] = useState("")
    const [description, setDescription] = useState("")
    const [categoryId, setCategoryId] = useState(1)
    const [image, setImage] = useState("")

    const [categories, setCategories] = useState([])

    useEffect(() => {
        fetch('http://localhost:8080/category', {
            method: 'GET',
            headers: {'Authorization': token},
        })
            .then(response => response.json())
            .then(json => setCategories(json))
            .catch(err => showMessage(err))

        if (params.id)
            fetch('http://localhost:8080/products/' + params.id, {
                method: 'GET',
                headers: {'Authorization': token},
            })
                .then(response => response.json())
                .then(json => {
                    setPrice(json.price)
                    setName(json.name)
                    setDescription(json.description)
                    setImage(json.image)
                    setCategoryId(json.categoryId)
                })
                .catch(error => showMessage(error.message, 'error'))
    }, [])

    const product = {
        price,
        name,
        description,
        image,
        categoryId
    }

    const onSubmit = event => {
        event.preventDefault()

        const head = {
            'Content-Type': 'application/json',
            'Authorization': token
        }

        if (params.id)
            fetch('http://localhost:8080/products/' + params.id, {
                method: 'PUT',
                headers: head,
                body: JSON.stringify(product)
            })
                .then(response => response.json())
                .then(json => showMessage('Product ' + json.name + ' updated'))
                .catch(error => showMessage(error.message, 'error'))
        else {
            fetch('http://localhost:8080/products/', {
                method: 'POST',
                headers: head,
                body: JSON.stringify(product)
            })
                .then(response => response.json())
                .then(json => showMessage('Product ' + json.name + ' created'))
                .catch(error => showMessage(error.message, 'error'))

            setPrice(MIN_PRICE)
            setName("")
            setDescription("")
            setCategoryId(1)
        }
    }

    return (
        <div className="form">
            <form onSubmit={onSubmit}>
                <div>
                    <input type='number' placeholder='price' required={true} min={MIN_PRICE} value={price}
                           onChange={e => {
                               const tryParse = parseInt(e.target.value)
                               !isNaN(tryParse) && setPrice(tryParse)
                           }}/>
                    <input type='text' required={true} placeholder='Name' value={name}
                           onChange={e => setName(e.target.value)}/>
                </div>
                <textarea placeholder={"Description"} value={description} maxLength={255}
                          onChange={e => setDescription(e.target.value)}/>
                <div>
                    <input className='long' required={!params.id} type='file' placeholder='Image' accept='image/*'
                           onChange={e => setImage(e.target.value.substring(e.target.value.lastIndexOf("\\") + 1))}/>
                </div>
                <div>
                    <select name='category' value={categoryId} onChange={e => setCategoryId(parseInt(e.target.value))}>
                        {categories.map(item => <option key={item.id} value={item.id}>{item.name}</option>)}
                    </select>
                    <input type='submit' value={params.id ? 'Update Product' : 'Create Product'}/>
                </div>
            </form>
        </div>)
}


export default ProductForm