import {useState} from "react";

function ScoreForm({token, productId, showMessage, onUpdateCommentary}) {

    const MIN_SCORE = 0
    const MAX_SCORE = 10

    const [value, setValue] = useState(MAX_SCORE)
    const [comment, setComment] = useState("")

    const score = {
        value,
        comment,
        productId,
        timestamp: new Date().toISOString().substring(0, 19),
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
            .then(json => onUpdateCommentary(json))
            .catch(err => showMessage(err.message, 'error'))
        setValue(MAX_SCORE)
        setComment("")
    }

    return (
        <div className="form">
            <form onSubmit={onSubmit}>
                <textarea type='textarea' placeholder='Commentary' maxLength={255} value={comment}
                          onChange={e => setComment(e.target.value)}/>
                <div>
                    <input type='number' placeholder={'Score [' + MIN_SCORE + '-' + MAX_SCORE + ']'} required={true}
                           min={MIN_SCORE} max={MAX_SCORE} value={value}
                           onChange={e => setValue(parseInt(e.target.value))}/>
                    <input type='submit' value='Add Score'/>
                </div>
            </form>
        </div>)
}

export default ScoreForm