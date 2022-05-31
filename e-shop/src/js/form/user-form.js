import {useEffect, useState} from "react";

function UserForm({token, onChangeUser, showMessage}) {

    const [name, setName] = useState("")
    const [email, setEmail] = useState("")
    const [oldPassword, setOldPassword] = useState("")
    const [password, setPassword] = useState("")
    const [password2, setPassword2] = useState("")

    const userJson = {
        name,
        email,
        password,
        oldPassword
    }

    useEffect(() => {
        fetch('http://localhost:8080/users/logged', {
            method: 'GET',
            headers: {'Authorization': token}
        })
            .then(response => response.json())
            .then(json => {
                setName(json.name)
                setEmail(json.email)
            })
            .catch(err => showMessage(err.message, 'error'))
    }, [token])

    const onEditUser = event => {
        event.preventDefault()
        if (password === password2)
            fetch('http://localhost:8080/users/logged', {
                method: 'PUT',
                headers: {
                    'Authorization': token,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(userJson)
            })
                .then(response => response.json())
                .then(json => onChangeUser(json))
                .catch(err => showMessage(err.message, 'error'))
        else
        {
            setPassword('')
            setPassword2('')
            showMessage('Passwords must match.', 'error')
        }
    }

    return (
        <div className='form'>
            <form onSubmit={onEditUser}>
                <input type={"text"} required={true} placeholder={"Username"} value={name}
                       onChange={e => setName(e.target.value)}/>
                <input className='disabled' type={"email"} readOnly={true} required={true} placeholder={"Email Address"}
                       value={email}/>
                <input type={"password"} minLength='5' placeholder={"Old Password"} value={oldPassword}
                       onChange={e => setOldPassword(e.target.value)}/>
                <input type={"password"} minLength='5' placeholder={"New Password"} value={password}
                       onChange={e => setPassword(e.target.value)}/>
                <input type={"password"} minLength='5' placeholder={"New Password Check"} value={password2}
                       onChange={e => setPassword2(e.target.value)}/>
                <input type={"submit"} value={"Edit User Information"}/>
            </form>
        </div>
    )
}


export default UserForm