import {useState} from "react";

function ScoreForm({token}) {

    const MIN_SCORE = 0
    const MAX_SCORE = 10

    const [value, setValue] = useState(MAX_SCORE)
    const [comment, setComment] = useState("")

    const score = {
        value,
        comment,
        timestamp: new Date().toISOString().substring(0, 19),
        userId: 1,
        productId: 1
    }

    const onSubmit = event => {
        event.preventDefault()
        console.log(score)
        fetch('http://localhost:8080/score', {
            method: 'POST',
            headers:
                {
                    'Content-Type': 'application/json',
                    'Authorization': token
                },
            body: JSON.stringify(score)
        })
            .then(response => response.json())
            .then(json => console.log(json))
        setValue(MAX_SCORE)
        setComment("")
    }

    return (
        <div className="form">
            <form onSubmit={onSubmit}>
                <input type={"number"} required={true} min={MIN_SCORE} max={MAX_SCORE} value={value}
                       onChange={(e) => setValue(parseInt(e.target.value))}/>
                <input type={"text"} placeholder={"Comment"} value={comment}
                       onChange={(e) => setComment(e.target.value)}/>
                <input type={"submit"} value={"Add Score"}/>
            </form>
        </div>)
}

export default ScoreForm