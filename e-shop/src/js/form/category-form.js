import {useState} from "react";

function CategoryForm({token}) {

    const [name, setName] = useState("")

    const category = {
        name
    }

    const onSubmit = event => {
        event.preventDefault()
        fetch('http://localhost:8080/category', {
            method: 'POST',
            headers:
                {
                    'Content-Type': 'application/json',
                    'Authorization': token
                },
            body: JSON.stringify(category)
        })
    }

    return (
        <form onSubmit={onSubmit}>
            <input type={"text"} required={true} placeholder={"Name"} value={name}
                   onChange={(e) => setName(e.target.value)}/>
            <input type={"submit"} value={"Add Category"}/>
        </form>)
}


export default CategoryForm