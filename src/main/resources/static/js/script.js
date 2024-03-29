const canvas = document.getElementById('canvas1')
const ctx = canvas.getContext('2d')
canvas.width = 1680
canvas.height = 925

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms))
}


function onlyDigits(event) {
    if ("1234567890".indexOf(event.key) === -1)
        event.preventDefault()
}

let roomId = /id(\d+)/.exec(window.location.href)[0].substr(2)
let sockJS = new SockJS('/ws')
let stompClient = Stomp.over(sockJS)
stompClient.debug = null

let boardCount = 0
let cardNames = []

let roomSubscriber
let chairSubscriber

let winPlayer = -1

let countPalyers = 0
let chairId = -1
let raisedInFocus = false
let minBank = 1000
let maxRaise = 200

function keyUpHundler() {
    let value = document.querySelector('.range_slide > input').value
    if (value > maxRaise)
        value = maxRaise
    if (value < minBank && !raisedInFocus)
        value = minBank
    document.querySelector('.range_slide > input').value = value
    let elem = document.querySelectorAll('.range_slide > input')[1]
    elem.value = value
}


const raisedRange = document.querySelector('.range_slide > input')
raisedRange.addEventListener('focus', (event) => {
    raisedInFocus = true
    console.log(raisedInFocus)
})
raisedRange.addEventListener('blur', (event) => {
    raisedInFocus = false
    console.log(raisedInFocus)
})

const occupiedPlaces = [false, false, false, false, false, false, false, false]
const playersWalks = [true, false, false, false, false, false, false, false]
const playersHasCards = [false, false, false, false, false, false, false, false]
const playersCardArray = new Array(8)
let cardArray = []
const tmpCardPositionValues = [
    ['btn-player-1-id', [570, 80]],
    ['btn-player-2-id', [300, 260]],
    ['btn-player-3-id', [330, 480]],
    ['btn-player-4-id', [630, 650]],
    ['btn-player-5-id', [930, 650]],
    ['btn-player-6-id', [1230, 480]],
    ['btn-player-7-id', [1265, 260]],
    ['btn-player-8-id', [994, 80]],
]
const cardsPositionValues = new Map(tmpCardPositionValues);

const blindsPosition = [
    [604, 250],
    [460, 320],
    [470, 500],
    [660, 590],
    [960, 590],
    [1160, 500],
    [1175, 320],
    [1028, 250]
]

class Blind {
    constructor(index, image) {
        this.image = image
        this.width = 45
        this.height = 45
        this.index = index
        this.x = blindsPosition[index][0]
        this.y = blindsPosition[index][1]
    }

    update() {
        const dx = this.x - blindsPosition[this.index][0]
        const dy = this.y - blindsPosition[this.index][1]
        if (this.x != blindsPosition[this.index][0]) {
            this.x -= dx / 10
        }
        if (this.y != blindsPosition[this.index][1]) {
            this.y -= dy / 10
        }
    }

    draw() {
        if (countPalyers > 1)
            ctx.drawImage(this.image, this.x, this.y, this.width, this.height)
    }
}


class Card {
    constructor(id, map, image1, image2) {
        this.height = image1.height / 1.3
        this.width = image1.width / 1.3
        this.x1 = canvas.width / 2
        this.y1 = 250
        this.x2 = canvas.width / 2
        this.y2 = 250
        this.finalX1 = map.get(id)[0]
        this.finalY1 = map.get(id)[1]
        this.finalX2 = map.get(id)[0] + 30
        this.finalY2 = map.get(id)[1] + 5
        this.startHeight = 0
        this.startWidth = 0
        this.image1 = image1
        this.image2 = image2
        this.id = id
    }

    update() {
        let dx = this.x1 - this.finalX1
        let dy = this.y1 - this.finalY1
        if (this.x1 != this.finalX1) {
            this.x1 -= dx / 10
        }
        if (this.y1 != this.finalY1) {
            this.y1 -= dy / 10
        }

        dx = this.x2 - this.finalX2
        dy = this.y2 - this.finalY2
        if (this.x2 != this.finalX2) {
            this.x2 -= dx / 10
        }
        if (this.y2 != this.finalY2) {
            this.y2 -= dy / 10
        }

        const sy = this.height - this.startHeight
        const sx = this.width - this.startWidth
        if (this.height != this.startHeight) {
            this.startHeight += sy / 10
        }
        if (this.width != this.startWidth) {
            this.startWidth += sx / 10
        }
    }

    draw() {
        ctx.drawImage(this.image1, this.x1, this.y1, this.startWidth, this.startHeight)
        ctx.drawImage(this.image2, this.x2, this.y2, this.startWidth, this.startHeight)
    }
}

class PlacesCard {
    constructor(index, size, image) {
        this.height = image.height / 1.3
        this.width = image.width / 1.3
        this.x = canvas.width / 2
        this.y = 250
        this.finalX = canvas.width / 2 - this.width / 2 * (size + 1) + this.width * index - (size + 2 - index * 2) * 5 + 5
        this.finalY = canvas.height / 2 - 100
        this.startHeight = 0
        this.startWidth = 0
        this.image = image
    }

    update() {
        const dx = this.x - this.finalX
        const dy = this.y - this.finalY
        if (this.x != this.finalX) {
            this.x -= dx / 10
        }
        if (this.y != this.finalY) {
            this.y -= dy / 10
        }
        const sy = this.height - this.startHeight
        const sx = this.width - this.startWidth
        if (this.height != this.startHeight) {
            this.startHeight += sy / 10
        }
        if (this.width != this.startWidth) {
            this.startWidth += sx / 10
        }
    }

    draw() {
        ctx.drawImage(this.image, this.x, this.y, this.startWidth, this.startHeight)
    }
}

function handlePlayersCards() {
    for (let i = 0; i < playersCardArray.length; i++) {
        if (playersCardArray[i]) {
            playersCardArray[i].update()
            playersCardArray[i].draw()
        }
    }
}

function handlePlacesCards() {
    for (let i = 0; i < cardArray.length; i++) {
        cardArray[i].update()
        cardArray[i].draw()
    }
}

function hundleBlinds() {
    for (let i = 0; i < blinds.length; i++) {
        if (blinds[i]) {
            blinds[i].update()
            blinds[i].draw()
        }
    }
}

async function getBoardCard(card) {
    const img = new Image()
    img.onload = () => {
        distribution(img)
    }
    img.src = card
}

async function distribution(card) {
    if (cardArray.length === 0) {
        cardArray.push(new PlacesCard(0, cardArray.length + 2, card))
    } else if (cardArray.length === 1) {
        await sleep(1000)
        cardArray.push(new PlacesCard(1, cardArray.length + 1, card))
    } else if (cardArray.length === 2) {
        await sleep(1000)
        cardArray.push(new PlacesCard(2, cardArray.length, card))
    } else if (cardArray.length === 3) {
        for (let i = 0; i < cardArray.length; i++) {
            cardArray[i].finalX -= cardArray[i].width / 2 + 5
        }
        cardArray.push(new PlacesCard(3, cardArray.length, card))
    } else if (cardArray.length === 4) {
        for (let i = 0; i < cardArray.length; i++) {
            cardArray[i].finalX -= cardArray[i].width / 2 + 5
        }
        cardArray.push(new PlacesCard(4, cardArray.length, card))
    }
}

let buttons = document.getElementsByClassName('button-player');

[...buttons].forEach(b => {
    b.addEventListener('click', function () {
        if (chairId === -1) {
            this.innerHTML = 'busy'
            sitDown(this.id[11] - 1)
        }
    })
})

function getCards(id, card1, card2) {
    const img1 = new Image()
    const img2 = new Image()
    let loaded = 0
    let func = () => {
        loaded++
        if (loaded === 2) {
            playersCardArray[id] = new Card('btn-player-' + (id + 1) + '-id', cardsPositionValues, img1, img2)
        }
    }
    img1.onload = func
    img2.onload = func
    img1.src = card1
    img2.src = card2
}

function getThreeBoardCards(cards) {
    const img1 = new Image()
    const img2 = new Image()
    const img3 = new Image()
    let loaded = 0
    let func = () => {
        loaded++
        if (loaded === 3) {
            cardArray.push(new PlacesCard(0, cardArray.length + 2, img1))
            cardArray.push(new PlacesCard(1, cardArray.length + 1, img2))
            cardArray.push(new PlacesCard(2, cardArray.length, img3))
        }
    }
    img1.onload = func
    img2.onload = func
    img3.onload = func
    img1.src = cards[0]
    img2.src = cards[1]
    img3.src = cards[2]
}

function getOneBoardCards(card) {
    const img1 = new Image()
    img1.onload = () => {
        for (let i = 0; i < cardArray.length; i++) {
            cardArray[i].finalX -= cardArray[i].width / 2 + 5
        }
        cardArray.push(new PlacesCard(cardArray.length, cardArray.length, img1))
    }
    img1.src = card
}

function rangeSlide(value) {
    let elem = document.querySelector('.range_slide > input')
    elem.value = value
}

function rangeInputValue(value) {
    let elem = document.querySelectorAll('.range_slide > input')[1]
    elem.value = value
}

function pickUpCards(id) {
    countPalyers--
    console.log(countPalyers)
    playersHasCards[id] = false
    console.log(playersHasCards)
    playersCardArray[id].height = 0
    playersCardArray[id].width = 0
    playersCardArray[id].finalX1 = canvas.width / 2
    playersCardArray[id].finalX2 = canvas.width / 2
    playersCardArray[id].finalY1 = 250
    playersCardArray[id].finalY2 = 250
}

function nextBlind() {
    if (countPalyers > 1) {
        for (let i = blinds[0].index == 7 ? 0 : blinds[0].index + 1; i < playersHasCards.length; i++) {
            if (playersHasCards[i]) {
                blinds[0].index = i
                break
            }
            if (i == 7)
                i = 0
        }
        for (let i = blinds[1].index == 7 ? 0 : blinds[1].index + 1; i < playersHasCards.length; i++) {
            if (playersHasCards[i]) {
                blinds[1].index = i
                break
            }
            if (i == 7)
                i = 0
        }
    }
}

const blinds = new Array(2)

function setBlinds() {
    let bigInd
    for (let i = 0; i < playersHasCards.length; i++) {
        if (playersHasCards[i]) {
            blinds[0] = new Blind(i, smallImg)
            if (i == 7)
                bigInd = 0
            else bigInd = i + 1
            break
        }
    }
    for (let i = bigInd; i < playersHasCards.length; i++) {
        if (playersHasCards[i]) {
            blinds[1] = new Blind(i, bigImg)
        }
    }
}


function nextMove() {
    for (let i = 0; i < playersWalks.length; i++) {
        let btnElem = document.querySelector('.player-' + (i + 1) + ' > button')
        let label1 = document.querySelectorAll('.player-' + (i + 1) + ' > span')[0]
        let label2 = document.querySelectorAll('.player-' + (i + 1) + ' > span')[1]
        if (playersWalks[i]) {
            btnElem.style.border = '4px solid #70ffffaf'
            btnElem.style.boxShadow = 'inset 0 0 25px #70ffff59'
            label1.style.border = '4px solid #70ffffaf'
            label1.style.boxShadow = 'inset 0 0 19px #70ffff59'
            label2.style.border = '2px solid #70ffffaf'
            label2.style.boxShadow = 'inset 0 0 19px #70ffff59'
        } else {
            btnElem.style.border = '4px solid #333'
            btnElem.style.boxShadow = 'none'
            label1.style.border = '4px solid #333'
            label1.style.boxShadow = 'none'
            label2.style.border = '2px solid #333'
            label2.style.boxShadow = 'none'
        }
    }
}


function checkRange() {
    let elem = document.querySelector('.range_slide > input')
    if (elem.value === '' && !raisedInFocus)
        elem.value = 0
}

function animate() {
    ctx.clearRect(0, 0, canvas.width, canvas.height)
    handlePlacesCards()
    handlePlayersCards()
    hundleBlinds()
    checkRange()
    requestAnimationFrame(animate)
}

webSocketConnect()
enterRoomById()
animate()

function enterRoomById() {
    let xhr1 = new XMLHttpRequest()
    xhr1.open('POST', '/room/enter')
    xhr1.setRequestHeader('Content-Type', 'application/json')
    xhr1.onreadystatechange = function () {
        if (xhr1.readyState === 4) {
            console.log(xhr1.status)
            console.log(xhr1.responseText)
            let json = JSON.parse(xhr1.responseText)

            let gameStarted = json.gameStarted
            console.log(gameStarted)

            document.getElementsByClassName('room-pot')[0].innerHTML = json.room.pot

            let players = json.room.sitedPlayers
            for (let i = 0; i < players.length; i++) {
                if (players[i] != null) {
                    let elem = document.querySelector('.player-' + (i + 1) + ' > span')
                    elem.innerHTML = players[i].nickname
                    occupiedPlaces[i] = true
                    elem = document.querySelector('.player-' + (i + 1) + ' > button')
                    elem.innerHTML = 'busy'
                    elem = document.querySelector('.player-' + (i + 1) + ' .player-rate')
                    elem.innerHTML = players[i].bank + '/' + players[i].bet

                    let isFolded = players[i].folded
                    console.log(isFolded)

                    if (!isFolded && gameStarted) {
                        getCards(i, '../images/cards/back.png', '../images/cards/back.png')
                        document.querySelector('.player-' + (i + 1) + ' > button').style.opacity = '0'
                    }
                }
            }

            if (json.chair >= 0) {
                chairId = json.chair
            }

            if (gameStarted) {
                let currentCards = []

                let cards = json.room.cards
                for (let i = 0; i < cards.length; i++) {
                    if (cards[i] != null) {
                        console.log(cards[i])
                        currentCards.push('../images/cards/' + cards[i].suit + '_' + cards[i].value + '.png')
                    }
                }

                console.log(currentCards)

                if (currentCards.length === 3) {
                    boardCount = 3
                    getThreeBoardCards(currentCards)
                }
                if (currentCards.length === 4) {
                    boardCount = 4
                    getThreeBoardCards(currentCards)
                    getOneBoardCards(currentCards[3])
                }
                if (currentCards.length === 5) {
                    boardCount = 5
                    getThreeBoardCards(currentCards)
                    getOneBoardCards(currentCards[3])
                    getOneBoardCards(currentCards[4])
                }

                if (chairId >= 0 && json.cards != null) {
                    let card1 = '../images/cards/' + json.cards[0].suit + '_' + json.cards[0].value + '.png'
                    let card2 = '../images/cards/' + json.cards[1].suit + '_' + json.cards[1].value + '.png'
                    getCards(chairId, card1, card2)
                    document.querySelector('.player-' + (chairId + 1) + ' > button').style.opacity = '0'
                }

                if (boardCount === 5) {
                    boardCount = 0
                    cardNames = []
                }
            }
        }
    }
    let data = '{"roomId":"' + roomId + '"}'
    console.log(data)
    xhr1.send(data)
}


function sockEvent(event) {
    console.log(event)
    let json = JSON.parse(event)
    switch (json.event) {
        case 'PlayerSitDown': {
            let id = json.chair
            let elem = document.querySelector('.player-' + (id + 1) + ' > span')
            elem.innerHTML = json.nickname
            occupiedPlaces[id] = true
            elem = document.querySelector('.player-' + (id + 1) + ' > button')
            elem.innerHTML = 'busy'
            elem = document.querySelector('.player-' + (id + 1) + ' .player-rate')
            elem.innerHTML = '1000/0'
            break
        }
        case 'PlayerCombination' : {
            let elem = document.querySelector('.player-' + (json.chairId + 1) + ' .player-rate')
            elem.innerHTML = json.combination.type
            break
        }
        case 'SetCards': {
            let id = json.chairId
            playersHasCards[id] = true

            let card1 = '../images/cards/' + json.firstCard.suit + '_' + json.firstCard.value + '.png'
            let card2 = '../images/cards/' + json.secondCard.suit + '_' + json.secondCard.value + '.png'

            for (let i = 0; i < occupiedPlaces.length; i++) {
                if (playersHasCards[i]) {
                    getCards(i, card1, card2)
                    document.querySelector('.player-' + (i + 1) + ' > button').style.opacity = '0'
                } else if (occupiedPlaces[i]) {
                    getCards(i, '../images/cards/back.png', '../images/cards/back.png')
                    document.querySelector('.player-' + (i + 1) + ' > button').style.opacity = '0'
                }
            }
            break
        }
        case 'GameStarted' : {
            break
        }
        case 'TurnTime' : {
            let elem = document.querySelector('.room-timer')
            let sec = json.time
            elem.innerHTML = sec
            if (sec <= 10)
                elem.style.color = 'rgb(255,74,74)'
            else
                elem.style.color = '#70ffffaf'

            let chair = json.chair
            if (chair > 0) {
                for (let i = 0; i < playersWalks.length; i++)
                    playersWalks[i] = false
                playersWalks[chair] = true
                nextMove()
            }
            break
        }
        case 'RoomPot' : {
            let value = json.value
            console.log(value)
            document.getElementsByClassName('room-pot')[0].innerHTML = value
            break
        }
        case 'AddBoardCard' : {
            boardCount++
            let card = '../images/cards/' + json.card.suit + '_' + json.card.value + '.png'
            if (boardCount < 4) {
                cardNames.push(card)
                if (cardNames.length === 3) {
                    getThreeBoardCards(cardNames)
                }
            } else if (boardCount > 3) {
                getOneBoardCards(card)
            }
            if (boardCount === 5) {
                boardCount = 0
                cardNames = []
            }
            break
        }
        case 'ClearBoardCards' : {
            boardCount = 0
            cardArray = []
            cardNames = []
            if (winPlayer >= 0) {
                let btnElem = document.querySelector('.player-' + (winPlayer + 1) + ' > button')
                let label1 = document.querySelectorAll('.player-' + (winPlayer + 1) + ' > span')[0]
                let label2 = document.querySelectorAll('.player-' + (winPlayer + 1) + ' > span')[1]
                btnElem.style.border = '4px solid #333'
                btnElem.style.boxShadow = 'none'
                label1.style.border = '4px solid #333'
                label1.style.boxShadow = 'none'
                label2.style.border = '2px solid #333'
                label2.style.boxShadow = 'none'
                winPlayer = -1
            }
            break
        }
        case 'PlayerWin' : {
            winPlayer = json.chairId
            let btnElem = document.querySelector('.player-' + (winPlayer + 1) + ' > button')
            let label1 = document.querySelectorAll('.player-' + (winPlayer + 1) + ' > span')[0]
            let label2 = document.querySelectorAll('.player-' + (winPlayer + 1) + ' > span')[1]
            btnElem.style.border = '4px solid rgb(248, 236, 73)'
            btnElem.style.boxShadow = 'inset 0 0 25px rgb(248, 236, 73)'
            label1.style.border = '4px solid rgb(248, 236, 73)'
            label1.style.boxShadow = 'inset 0 0 19px rgb(248, 236, 73)'
            label2.style.border = '2px solid rgb(248, 236, 73)'
            label2.style.boxShadow = 'inset 0 0 19px rgb(248, 236, 73)'
            break
        }
        case 'MaxRaise' : {
            minBank = json.minRaise
            maxRaise = json.maxRaise
            let elem = document.querySelectorAll('.range_slide > input')[1]
            elem.min = minBank
            elem.max = maxRaise
            break
        }
        case 'PlayerRaised' : {
            let elem = document.querySelector('.player-' + (json.chairId + 1) + ' .player-rate')
            elem.innerHTML = json.currentBank + '/' + json.bet
            break
        }
        case 'PlayerFolded' : {
            let player = json.chairId
            playersCardArray[player].height = 0
            playersCardArray[player].width = 0
            playersCardArray[player].finalX1 = canvas.width / 2
            playersCardArray[player].finalX2 = canvas.width / 2
            playersCardArray[player].finalY1 = 250
            playersCardArray[player].finalY2 = 250
            let elem = document.querySelector('.player-' + (player + 1) + ' .player-rate')
            elem.innerHTML = json.currentBank + '/0'
            document.querySelector('.player-' + (player + 1) + ' > button').style.opacity = '0'
            break
        }
        case 'PlayerCalled' : {
            let elem = document.querySelector('.player-' + (json.chairId + 1) + ' .player-rate')
            elem.innerHTML = json.currentBank + '/' + json.bet
            break
        }
        case 'PlayerChecked' : {
            let elem = document.querySelector('.player-' + (json.chairId + 1) + ' .player-rate')
            elem.innerHTML = json.currentBank + '/' + json.bet
            break
        }
        case 'RevealCards' : {
            let id = json.chairId

            let card1 = '../images/cards/' + json.firstCard.suit + '_' + json.firstCard.value + '.png'
            let card2 = '../images/cards/' + json.secondCard.suit + '_' + json.secondCard.value + '.png'

            getCards(id, card1, card2)
            break
        }
        case 'PlayerGetUp' : {
            let id = json.chairId
            occupiedPlaces[id] = false
            playersWalks[id] = false
            playersHasCards[id] = false
            playersCardArray[id] = null
            document.querySelector('.player-' + (id + 1) + ' > button').style.opacity = '0.8'
            let elem = document.querySelector('.player-' + (id + 1) + ' > span')
            elem.innerHTML = 'free'
            elem = document.querySelector('.player-' + (id + 1) + ' > button')
            elem.innerHTML = '+'
            elem = document.querySelector('.player-' + (id + 1) + ' .player-rate')
            elem.innerHTML = '0'
        }
    }
}

function sitDown(id) {
    let xhr = new XMLHttpRequest()
    xhr.open('POST', '/room/sit')
    xhr.setRequestHeader('Content-Type', 'application/json')
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            console.log(xhr.status);
            console.log(xhr.responseText);
            if (xhr.status === 200) {
                chairId = id
                occupiedPlaces[id] = true
                chairSubscriber = stompClient.subscribe(
                    '/user/' + roomId + '_' + chairId + '/events', e => {
                        sockEvent(e.body)
                    }
                )
            }
        }
    }
    xhr.send('{"chairId": "' + id + '"}')
}

function webSocketConnect() {
    stompClient.connect({}, () => {
        console.log('connected')
        roomSubscriber = stompClient.subscribe(
            '/room/' + roomId + "/events", e => {
                sockEvent(e.body)
            }
        )
    }, () => {
        console.log('not connected')
    })
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function fold() {
    let xhr = new XMLHttpRequest()
    xhr.open('POST', '/game/fold')
    xhr.setRequestHeader('Content-Type', 'application/json')
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            console.log(xhr.status)
            console.log(xhr.responseText)
        }
    }
    xhr.send();
}

function call() {
    let xhr = new XMLHttpRequest()
    xhr.open('POST', '/game/call')
    xhr.setRequestHeader('Content-Type', 'application/json')
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            console.log(xhr.status)
            console.log(xhr.responseText)
        }
    }
    xhr.send()
}

function raise() {
    let xhr = new XMLHttpRequest()
    xhr.open('POST', '/game/raise')
    xhr.setRequestHeader('Content-Type', 'application/json')
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            console.log(xhr.status)
            console.log(xhr.responseText)
        }
    };
    let data = '{"value":"' + document.querySelector('.range_slide > input').value + '"}'
    xhr.send(data);
}

function check() {
    let xhr = new XMLHttpRequest()
    xhr.open('POST', '/game/check')
    xhr.setRequestHeader('Content-Type', 'application/json')
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            console.log(xhr.status)
            console.log(xhr.responseText)
        }
    }
    xhr.send()
}

function escapeRoom() {
    roomSubscriber.unsubscribe()
    window.location.replace('/main')
}

function getUp() {
    let xhr = new XMLHttpRequest()
    xhr.open('POST', '/room/getup')
    xhr.setRequestHeader('Content-Type', 'application/json')
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            console.log(xhr.status)
            console.log(xhr.responseText)
            chairSubscriber.unsubscribe()
            chairId = -1
        }
    }
    xhr.send()
}
