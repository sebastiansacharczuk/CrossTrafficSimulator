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

#### 4. Zbuduj obraz Docker
   ```bash
      docker build -t <app_name> .
   ```

#### 5. Uruchom obraz
operator **-p** wskazuje port dla kontera (8080 domyślny port springa)

   ```bash
      docker run -p 8080:8080 <app_name>
   ```


### Użytkowanie

Odwiedź `http://localhost:8080` i zobacz prostą wizualizacje ruchu ruchu drogowego
## Struktura kodu

- **SimulatorController**: kontroler obsługuje żądania HTTP, renderuje strony i przetwarza komendy symulacji.
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


