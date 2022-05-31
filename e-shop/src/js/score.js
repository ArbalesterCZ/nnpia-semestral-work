import {useEffect, useState} from "react";
import ScoreForm from "./form/score-form";

function Score({token, productId, showMessage}) {

    const [score, setScore] = useState([])

    const [totalScoreValue, setTotalScoreValue] = useState(0)
    const [totalScoreCount, setTotalScoreLength] = useState(0)

    const updateCommentary = function (json) {
        const copy = [...score]
        const find = copy.find(item => item.userId === json.userId)
        if (find) {
            find.value = json.value
            find.comment = json.comment
        } else
            copy.push(json)

        showMessage('Score with value [' + json.value + '] added to evaluation.')
        setScore(copy)
    }

    useEffect(() => {
        fetch('http://localhost:8080/score?productId=' + productId, {
            method: 'GET',
            headers: {'Authorization': token}
        })
            .then(response => response.json())
            .then(json => setScore(json))
            .catch(err => console.log(err))
    }, [])

    useEffect(() => {
        let value = 0
        score.map(item => value += item.value)
        setTotalScoreLength(score.length)
        if (score.length > 0)
            setTotalScoreValue(value / score.length)
    }, [score])

    return (
        <div>
            <ScoreForm token={token} productId={productId} showMessage={showMessage}
                       onUpdateCommentary={updateCommentary}/>
            <h1>Scores</h1>
            {totalScoreCount !== 0 &&
            <h2>Total Score: <mark>{totalScoreValue}</mark> with <mark>{totalScoreCount}</mark> reviews</h2>}
            {totalScoreCount === 0 && <h2>Not Rated Yet</h2>}
            {score.map(item =>
                <div key={item.userId + 'score-div'} className='commentary'>
                    <h2 key={item.userId + 'score-value'}>{item.value}/10</h2>
                    <h3 key={item.userId + 'score-owner'}>{item.userName}</h3>
                    <p key={item.userId + 'score-comment'}>{item.comment}</p>
                </div>
            )}
        </div>
    )
}


export default Score