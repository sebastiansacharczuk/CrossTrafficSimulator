# CrossTrafficSimulator

**CrossTrafficSimulator** to symulator ruchu drogowego na skrzyżowaniu. Projekt modeluje zachowanie pojazdów na skrzyżowaniu z wieloma pasami ruchu, sterując ich przepływem za pomocą algorytmu zarządzającego światłami. Symulator odczytuje komendy z pliku JSON (np. dodawanie pojazdów lub kroki symulacji) i zapisuje wyniki do pliku wyjściowego.

## Funkcjonalność
- **Dodawanie pojazdów**: Pojazdy są przypisywane do odpowiednich pasów na podstawie ich kierunku (np. `"south_west"`).
- **Symulacja kroku**: W każdym kroku wybierana jest optymalna konfiguracja świateł, która minimalizuje kolizje ścieżek pojazdów, a następnie pojazdy przepuszczane są przez skrzyżowanie.
- **Obliczanie czasu zielonego światła**: Czas trwania zielonego światła zależy od liczby pojazdów na pasach.
- **Zarządzanie ruchem**: Symulator usuwa pojazdy z pasów po ich przejeździe i zapisuje dane do pliku wyjściowego.

## Wymagania
- **JDK**: Zalecana wersja 17
- **Maven**: Zalecana wersja 3.6.0

## Instalacja / Przygotowanie
#### 1. Sklonuj repozytorium:
   ```bash 
      git clone https://github.com/sebastiansacharczuk/CrossTrafficSimulator.git
   ```
#### 2. Przejdź do katalogu projektu:
   ```bash
      cd CrossTrafficSimulator
   ```
    
#### 3. Zbuduj projekt przy użyciu Maven:
   To polecenie pobierze wszystkie zależności, skompiluje kod, uruchomi testy jednostkowe (jeśli istnieją) i wygeneruje plik JAR.

   ```bash
      mvn clean install
   ```
## Użytkowanie

#### 1. Przygotuj plik wejściowy JSON (np. input.json) z komendami:
   ```json
  {
    "commands": [
      {"type": "addVehicle", "vehicleId": "vehicle1", "startRoad": "south", "endRoad": "west"},
      {"type": "step"}
   ]
  }
   ```

#### 2. Przejdź do katalogu projektu, który zawiera plik pom.xml, a następnie uruchom komendę:

   ```bash
      mvn clean install
   ```
   To polecenie pobierze wszystkie zależności, skompiluje kod, uruchomi testy jednostkowe (jeśli istnieją) i wygeneruje plik JAR.
#### 3. Po skompilowaniu projektu i wygenerowaniu pliku JAR, możesz uruchomić aplikację za pomocą polecenia:
   ```bash
      java -jar target/<nazwa_pliku>.jar <plik_wejściowy.json> <plik_wyjściowy.json>
      java -jar target/CrossTrafficSimulator-1.0-SNAPSHOT.jar input.json output.json
   ```
#### 4. Sprawdź plik wyjściowy output.json, który zawiera informacje o pojazdach, które opuściły skrzyżowanie.

## Struktura kodu

- **CrossTrafficSimulatorApplication**: Główna klasa aplikacji, przetwarza argumenty i uruchamia symulację.
- **CommandProcessor**: Odpowiada za przetwarzanie komend z pliku JSON i zarządzanie symulacją.
- **IntersectionController**: Singleton zarządzający pasami, pojazdami i konfiguracjami świateł.
- **IndependentPathsFinder**: Algorytm znajdujący maksymalne niezależne zbiory ścieżek, używane do konfiguracji świateł.
- **Lane**: Klasa reprezentująca pas ruchu z listą pojazdów.
- **Vehicle**: Klasa reprezentująca pojazd z identyfikatorem, kierunkiem i czasem oczekiwania.
- **ManualSettings**: Zawiera stałe konfiguracyjne, takie jak ścieżki pojazdów i mapowanie kierunków.

## Testy

Aby uruchomić testy napisane w **JUNIT 5**:
   ```
   mvn test
   ```
   

**Autor**
Sebastian Sacharczuk - [GitHub](https://github.com/sebastiansacharczuk)


