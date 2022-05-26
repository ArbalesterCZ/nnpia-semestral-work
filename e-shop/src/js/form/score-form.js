import {useState} from "react";

function ScoreForm({token}) {

    const MIN_SCORE = 0
    const MAX_SCORE = 10

    const [value, setValue] = useState(MAX_SCORE)
    const [comment, setComment] = useState("")
    const [timestamp, setTimestamp] = useState()

    const score = {
        value,
        comment,
        timestamp,
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

        setValue(MAX_SCORE)
        setComment("")
    }

    return (
        <form onSubmit={onSubmit}>
            <input type={"number"} required={true} min={MIN_SCORE} max={MAX_SCORE} value={value}
                   onChange={(e) => setValue(parseInt(e.target.value))}/>
            <input type={"text"} placeholder={"Comment"} value={comment}
                   onChange={(e) => setComment(e.target.value)}/>
            <input type={"datetime-local"} required={true} value={timestamp}
                   onChange={(e) => setTimestamp(e.target.value)}/>
            <input type={"submit"} value={"Add Score"}/>
        </form>)
}

export default ScoreForm