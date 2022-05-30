import {useState} from "react";

function CategoryForm({token, showMessage}) {

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
            .then(response => response.json())
            .then(json => {
                if (json.name)
                    showMessage('Category ' + json.name + ' added.')
                else
                    showMessage(json.message, 'error')
            })
            .catch(err => showMessage(err.message, 'error'))
    }

    return (
        <div className="form">
            <form onSubmit={onSubmit}>
                <input type={"text"} required={true} placeholder={"Name"} value={name}
                       onChange={e => setName(e.target.value)}/>
                <input type={"submit"} value={"Add Category"}/>
            </form>
        </div>)
}


export default CategoryForm