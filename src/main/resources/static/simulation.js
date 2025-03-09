const animationMap = {
    0: {
        'north_west': 'lane0-left',
        'north_south': 'lane0-down'
    },
    1: {
        'north_south': 'lane1-down'
    },
    2: {
        'north_east': 'lane2-right'
    },
    10: {
        'east_north': 'lane10-up',
        'east_west': 'lane10-left'
    },
    15: {
        'east_south': 'lane15-down'
    },
    29: {
        'south_east': 'lane29-right',
        'south_north': 'lane29-up'
    },
    28: {
        'south_north': 'lane28-up'
    },
    27: {
        'south_west': 'lane27-left'
    },
    19: {
        'west_east': 'lane19-right',
        'west_south': 'lane19-down'
    },
    16: {
        'west_north': 'lane16-up'
    }

};

let stepStatuses = JSON.parse(localStorage.getItem("stepStatuses"));

const laneNumbers = [0, 1, 2, 16, 19, 27, 28, 29, 15, 10];

// Obiekt przechowujący liczbę pojazdów na każdym pasie
let laneVehicleCount = {};

// Inicjalizacja licznika dla każdego pasa
laneNumbers.forEach(lane => {
    laneVehicleCount[lane] = 0;
});

let stepIndex = 0;
let isAddingStep = true;

async function processNextStep() {

    laneNumbers.forEach(setCounterRed);

    if (stepIndex >= stepStatuses.length) {
        console.log("Brak kolejnych kroków.");
        return;
    }

    let step = stepStatuses[stepIndex];
    console.log(`Przetwarzanie kroku ${stepIndex}: ${isAddingStep ? "Dodawanie" : "Odejmowanie"}`);

    if (isAddingStep) {
        // Dodawanie pojazdów na pasach

        for (let lane in step.vehiclesOnLanes) {
            laneVehicleCount[lane] += step.vehiclesOnLanes[lane];
            updateLaneCounters()
            console.log(`Dodano ${step.vehiclesOnLanes[lane]} pojazdów do pasa ${lane}`);
        }
    } else {
        // Odejmowanie pojazdów, które opuściły pasy
        for (const vehicle of step.leftVehicles) {
            setCounterGreen(vehicle.laneNumber)
        }
        for (const vehicle of step.leftVehicles) {
            laneVehicleCount[vehicle.laneNumber] -= 1;
            await sleep(900)
            startAnimation(animationMap[vehicle.laneNumber][vehicle.directions])
            console.log(vehicle.directions)
            console.log(`Pojazd ${vehicle.vehicleId} opuścił pas ${vehicle.laneNumber}`);
            updateLaneCounters()
        }
        stepIndex++; // Przechodzimy do następnego kroku dopiero po odejmowaniu
    }

    // Przełączanie trybu dodawania/odejmowania
    isAddingStep = !isAddingStep;

    // Wyświetlenie aktualnego stanu pasów
    console.log("Aktualna liczba pojazdów na pasach:");
    laneNumbers.forEach(lane => {
        console.log(`Lane ${lane}: ${laneVehicleCount[lane]} vehicles`);
    });
}

document.getElementById("nextStepButton").addEventListener("click", processNextStep);








// Funkcja tworzy nową kropkę o zadanej klasie (typie animacji)
function startAnimation(animationClass) {
    const container = document.querySelector('.container');
    const dot = document.createElement('div');
    // Dodajemy klasy określające styl kropki oraz pozycję startową
    dot.classList.add('dot', animationClass);
    container.appendChild(dot);

    // Wymuszenie reflow, aby animacja się zresetowała
    void dot.offsetWidth;

    // Uruchomienie animacji
    dot.classList.add('animate');

    // Po zakończeniu animacji kropka zostanie usunięta
    dot.addEventListener('animationend', () => {
        dot.remove();
    });

    console.log("Animacja uruchomiona: " + animationClass);
}

function updateLaneCounters() {
    laneNumbers.forEach(lane => {
        let counterElement = document.getElementById(`counter-lane${lane}`);
        if (counterElement) {
            counterElement.textContent = laneVehicleCount[lane];
        }
    });
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

function setCounterGreen(laneNumber) {
    let counterElement = document.getElementById(`counter-lane${laneNumber}`);
    if (counterElement) {
        counterElement.style.backgroundColor = "green";
        counterElement.style.color = "white";
    }
}

function setCounterRed(laneNumber) {
    let counterElement = document.getElementById(`counter-lane${laneNumber}`);
    if (counterElement) {
        counterElement.style.backgroundColor = "red";
        counterElement.style.color = "white";
    }
}