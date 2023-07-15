// -*- coding: utf-8 -*-
// Código JavaScript para el chatbot

const chatContainer = document.querySelector(".chat-container");
const toggleChatButton = document.getElementById("toggle-chat-button");
const minimizeButton = document.getElementById("minimize-button");
const messageInput = document.getElementById("message-input");
const sendButton = document.getElementById("send-button");
const chatBody = document.getElementById("chat-body");

// Mostrar u ocultar el chat al hacer clic en el botón flotante
toggleChatButton.addEventListener("click", () => {
  toggleChatButton.style.display = "none";
  chatContainer.style.display = "block";
  messageInput.focus(); // Enfocar automáticamente el campo de entrada de mensajes
});

// Minimizar o expandir el chat al hacer clic en el botón de minimizar
minimizeButton.addEventListener("click", () => {
  chatContainer.style.display = "none";
  toggleChatButton.style.display = "block";
});

// Enviar mensaje al hacer clic en el botón de enviar
sendButton.addEventListener("click", () => {
  sendMessage();
});

// Enviar mensaje al presionar la tecla Enter
messageInput.addEventListener("keydown", (event) => {
  if (event.key === "Enter") {
    sendMessage();
    event.preventDefault();
  }
});

// Función para enviar el mensaje
function sendMessage() {
  const message = messageInput.value.trim();
  if (message !== "") {
    const timestamp = getFormattedTimestamp();
    displayMessage(message, "user", timestamp);
    messageInput.value = "";
    scrollToBottom();
  }
}

// Mostrar mensaje en el chat
function displayMessage(message, sender, timestamp) {
  const messageElement = document.createElement("div");
  messageElement.classList.add("message");
  messageElement.classList.add(
    sender === "user" ? "user-message" : "bot-message"
  );

  const messageContent = document.createElement("div");
  messageContent.classList.add("message-content");

  const messageText = document.createElement("span");
  messageText.classList.add("message-text");
  messageText.textContent = message;

  const timestampText = document.createElement("span");
  timestampText.textContent = timestamp;
  timestampText.classList.add("timestamp");

  messageContent.appendChild(messageText);
  messageElement.appendChild(messageContent);
  messageElement.appendChild(timestampText);
  chatBody.appendChild(messageElement);

  if (sender === "user") {
    messageElement.classList.add("user-message");
    var idusersession = $('#idusersesion').val();
    console.log("idusersession = "+idusersession);
    
    //Llamado a API
    const url = 'https://chatgptsr.herokuapp.com/api'; // URL del endpoint de la API
    const data = {
      consulta: message,
      idUsuario: idusersession,
      key: 'l#Y49Ee7Fu*8#6C10Sm1RH8n7#dodP'
    }; // Datos a enviar en la solicitud

       $.ajax({
      url: url,
      type: 'POST',
      data: JSON.stringify(data),
      contentType: 'application/json',
      success: function(responseData) {

        const timestamp2 = getFormattedTimestamp();
        if(responseData.resultCode == "1"){
            displayMessage(responseData.data, "bot", timestamp2);
        }else{
            displayMessage("Tengo un error interno, intentelo más tarde.", "bot", timestamp2);
        }
        scrollToBottom();
      },
      error: function(jqXHR, textStatus, errorThrown) {
        console.error('Error en la solicitud POST:', textStatus, errorThrown);
        const timestamp2 = getFormattedTimestamp();
        displayMessage("Tengo un error interno, intentelo más tarde.", "bot", timestamp2);
        scrollToBottom();
      }
    });


  }
}

// Obtener la fecha y hora actual formateada
function getFormattedTimestamp() {
  const now = new Date();
  const options = {
    hour: "numeric",
    minute: "numeric",
    hour12: true,
  };
  return now.toLocaleString("en-US", options);
}

// Desplazarse hacia abajo para mostrar el último mensaje
function scrollToBottom() {
  chatBody.scrollTop = chatBody.scrollHeight;
}

// Función para mostrar el mensaje de bienvenida por defecto
function showDefaultMessage() {
  const defaultMessage = "Hola, ¿En qué puedo ayudarte?";
  const timestamp = getFormattedTimestamp();
  displayMessage(defaultMessage, "bot", timestamp);
  scrollToBottom();
}

// Mostrar mensaje por defecto al cargar la página
showDefaultMessage();
