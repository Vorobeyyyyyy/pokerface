let mainMenu = document.getElementById('main-menu-id-1')
let connectMenu = document.getElementById('connect-menu-id-1')
let createMenu = document.getElementById('create-menu-id-1')
let descriptionMenu = document.getElementById('description-menu-id-1')

function hideMainMenu() {
	mainMenu.style.transitionDelay = '0s'
	mainMenu.style.minWidth = '0px'
	mainMenu.style.width = '0px'
	mainMenu.style.paddingRight = '0px'
	mainMenu.style.paddingLeft = '0px'
	mainMenu.style.opacity = 0
}
			
function showMainMenu() {
	mainMenu.style.transitionDelay = '0.3s'
	mainMenu.style.minWidth = '1350px'
	mainMenu.style.width = '1350px'
	mainMenu.style.paddingRight = '20px'
	mainMenu.style.paddingLeft = '20px'
	mainMenu.style.opacity = 1
}

function hideConnectMenu() {
	connectMenu.style.transitionDelay = '0s'
	connectMenu.style.minWidth = '0px'
	connectMenu.style.width = '0px'
	connectMenu.style.padding = '0px'
	connectMenu.style.opacity = 0
}

function showConnectMenu() {
	connectMenu.style.transitionDelay = '0.3s'
	connectMenu.style.minWidth = '700px'
	connectMenu.style.width = '700px'
	connectMenu.style.padding = '20px'
	connectMenu.style.opacity = 1
}

function hideDescriptionMenu() {
	descriptionMenu.style.transitionDelay = '0s'
	descriptionMenu.style.minWidth = '0px'
	descriptionMenu.style.width = '0px'
	descriptionMenu.style.padding = '0px'
	descriptionMenu.style.opacity = 0
}

function showDescriptionMenu() {
	descriptionMenu.style.transitionDelay = '0.3s'
	descriptionMenu.style.minWidth = '1350px'
	descriptionMenu.style.width = '1350px'
	descriptionMenu.style.padding = '20px'
	descriptionMenu.style.opacity = 1
}

function hideCreateMenu() {
	createMenu.style.transitionDelay = '0s'
	createMenu.style.minWidth = '0px'
	createMenu.style.width = '0px'
	createMenu.style.padding = '0px'
	createMenu.style.opacity = 0
}

function showCreateMenu() {
	createMenu.style.transitionDelay = '0.3s'
	createMenu.style.minWidth = '700px'
	createMenu.style.width = '700px'
	createMenu.style.padding = '20px'
	createMenu.style.opacity = 1
}

function gotoConnectMenu() {
	hideMainMenu()
	showConnectMenu()
}

function gotoCreateMenu() {
	hideMainMenu()
	showCreateMenu()
}

function gotoDescriptionMenu() {
	hideMainMenu()
	showDescriptionMenu()
}

function gotoMainMenu() {
	showMainMenu()
	hideConnectMenu()
	hideCreateMenu()
	hideDescriptionMenu()
}

function connect() {
	let xhr1 = new XMLHttpRequest()
	xhr1.open('POST', 'http://localhost:8080/user/login')
	xhr1.setRequestHeader('Content-Type', 'application/json')
	xhr1.onreadystatechange = function () {
		if (xhr1.readyState === 4) {
			console.log(xhr1.status)
			console.log(xhr1.responseText)
			window.location.replace('/id' +  document.querySelector('.room-id > input').value)
		}
	}
	xhr1.send('{"nickname":"' + document.querySelectorAll('.nickname > input')[0].value + '"}')
}


function createRoom() {
	let xhr1 = new XMLHttpRequest()
	xhr1.open('POST', '/user/login')
	xhr1.setRequestHeader('Content-Type', 'application/json')
	xhr1.onreadystatechange = function () {
		if (xhr1.readyState === 4) {
			console.log(xhr1.status)
			console.log(xhr1.responseText)
			let xhr2 = new XMLHttpRequest()
			xhr2.open('POST', '/room/create')
			xhr2.setRequestHeader('Content-Type', 'application/json')
			xhr2.onreadystatechange = function () {
				if (xhr2.readyState === 4) {
					console.log(xhr2.status)
					console.log(xhr2.responseText)
					window.location.replace('/id' + JSON.parse(xhr2.responseText).id)
				}
			}
			xhr2.send('{"name":"' + document.querySelector('.room-name > input').value + '"}')
		}
	}
	xhr1.send('{"nickname":"' + document.querySelectorAll('.nickname > input')[1].value + '"}')
}
