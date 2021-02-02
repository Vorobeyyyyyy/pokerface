let mainMenu = document.getElementById('main-menu-id-1')
let connectMenu = document.getElementById('connect-menu-id-1')

function hideMainMenu() {
   mainMenu.style.minWidth = '0px'
   mainMenu.style.width = '0px'
   mainMenu.style.opacity = 0
}

function showMainMenu() {
   mainMenu.style.minWidth = '1200px'
   mainMenu.style.width = '1200px'
   mainMenu.style.opacity = 1  
}

function hideConnectMenu() {
   connectMenu.style.minWidth = '0px'
   connectMenu.style.width = '0px'
   connectMenu.style.opacity = 0
}

function showConnectMenu() {
   connectMenu.style.minWidth = '400px'
   connectMenu.style.width = '400px'
   connectMenu.style.opacity = 1
}

function gotoConnectMenu() {
   hideMainMenu()
   showConnectMenu()
}

function gotoMainMenu() {
   showMainMenu()
   hideConnectMenu()
}

async function connect() {
   initListener()
   let text = 'sss'
   let response = await fetch('message', {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify({text: text})
  })
  let message = await response.json()
}

initListener = () => {
   const eventSource = new EventSource("http://localhost:8080/board/socket/1");

   eventSource.onopen = (e) => console.log("open");

   eventSource.onerror = (e) => {
      if (e.readyState === EventSource.CLOSED) {
         console.log("close");
      } else {
         console.log(e);
      }
   };

   eventSource.onmessage = function (event) {
      console.log(event.data)
   }
};