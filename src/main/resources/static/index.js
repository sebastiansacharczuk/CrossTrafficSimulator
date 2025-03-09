// index.js

// Funkcja do aktualizacji nazwy pliku i wyświetlania bloku z plikiem
function updateFileName() {
    const file = document.getElementById('fileInput').files[0];
    const fileNameElement = document.getElementById('fileName');
    const filePreview = document.getElementById('filePreview');

    if (file) {
        fileNameElement.textContent = file.name; // Wyświetl nazwę pliku
        filePreview.style.display = 'flex'; // Pokaż blok z plikiem
    } else {
        filePreview.style.display = 'none'; // Ukryj blok z plikiem, jeśli nie ma pliku
    }
}

// Funkcja do usuwania pliku
function removeFile() {
    document.getElementById('fileInput').value = ''; // Wyczyść pole pliku
    updateFileName(); // Zaktualizuj widok
}

// Obsługa przeciągania i upuszczania plików
const dropZone = document.getElementById('dropZone');

dropZone.addEventListener('dragover', (e) => {
    e.preventDefault();
    dropZone.classList.add('dragover'); // Dodaj klasę dla wizualnego feedbacku
});

dropZone.addEventListener('dragleave', () => {
    dropZone.classList.remove('dragover'); // Usuń klasę, gdy plik opuści strefę
});

dropZone.addEventListener('drop', (e) => {
    e.preventDefault();
    dropZone.classList.remove('dragover'); // Usuń klasę po upuszczeniu pliku

    const files = e.dataTransfer.files; // Pobierz pliki
    if (files.length > 0) {
        document.getElementById('fileInput').files = files; // Przypisz plik do inputa
        updateFileName(); // Zaktualizuj widok
    }
});

// Obsługa zmiany pliku przez kliknięcie "Wybierz plik"
document.getElementById('fileInput').addEventListener('change', updateFileName);


// Zmodyfikowany fragment app.js
function showMessage(message, type = 'error') {
    const messageBox = document.getElementById('messageBox');
    messageBox.className = `message-box ${type}`;
    messageBox.innerHTML = `
        ${message}
        <button class="close-btn" onclick="this.parentElement.style.display='none'">×</button>
    `;

    // // Auto-ukrywanie po 5 sekundach
    // setTimeout(() => {
    //     messageBox.style.display = 'none';
    // }, 5000);
}

// Obsługa wysyłania danych
document.getElementById('submitBtn').addEventListener('click', () => {
    const commandsText = document.getElementById('commandsText').value; // Pobierz tekst z textarea
    const commandsFile = document.getElementById('fileInput').files[0]; // Pobierz plik

    if (commandsText && commandsFile) {
        showMessage("Tylko jedna forma przesłania komend jest możliwa w tym samym czasie. Proszę wyczyścić jedno z pól.", "warning");
        return;
    }

    if (!commandsText && !commandsFile) {
        showMessage("Proszę wypełnić pole tekstowe lub wybrać plik.", "warning");
        return;
    }

    let jsonData;

    if (commandsText) {
        try {
            jsonData = JSON.parse(commandsText); // Spróbuj sparsować tekst jako JSON
        } catch (e) {
            showMessage("Niepoprawny format JSON w polu tekstowym.", "error");
            return;
        }
    } else if (commandsFile) {
        const reader = new FileReader();
        reader.onload = function(event) {
            try {
                jsonData = JSON.parse(event.target.result); // Spróbuj sparsować plik jako JSON
                sendData(jsonData); // Wyślij dane po pomyślnym sparsowaniu
            } catch (e) {
                showMessage("Niepoprawny format JSON w pliku.", "error");
            }
        };
        reader.readAsText(commandsFile); // Odczytaj plik jako tekst
        return; // Wstrzymaj, aż plik zostanie odczytany
    }

    sendData(jsonData); // Wyślij dane, jeśli są z pola tekstowego
});

// Funkcja do wysyłania danych JSON do backendu
function sendData(jsonData) {
    fetch('/submit-commands', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(jsonData)
    })
        .then(response => response.json())
        .then(data => {
            if (data.status === "success") {
                // Zapisz result.first (data.data) do localStorage
                localStorage.setItem('stepStatuses', JSON.stringify(data.data.stepStatuses));
                console.log(data.data)
                // Zapisz result.second (data.vehiclesInfo) do localStorage
                window.location.href = '/simulation'; // Przekieruj po sukcesie
            } else {
                showMessage(`Błąd: ${data.message}`, "error");
            }
        })
        .catch(error => {
            console.error('Błąd:', error);
            showMessage("Błąd: " + error, "error");
        });
}