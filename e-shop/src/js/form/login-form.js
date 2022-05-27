import '../../css/forms.css'
import {useState} from "react";

function LoginForm({onLogin}) {

    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")

    const user = {
        username,
        password
    }

    const onSubmit = event => {
        event.preventDefault()
        fetch('http://localhost:8080/authenticate', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(user)
        })
            .then(response => response.json())
            .then(json => json.token)
            .then(token => onLogin("Bearer " + token))
    }

    return (
        <div className="form">
            <form onSubmit={onSubmit}>
                <input type={"email"} required={true} placeholder={"Email"} value={username}
                       onChange={(e) => setUsername(e.target.value)}/>
                <input type={"password"} required={true} placeholder={"Password"} value={password}
                       onChange={(e) => setPassword(e.target.value)}/>
                <input type={"submit"} value={"Login"}/>
            </form>
        </div>
    )
}

export default LoginForm