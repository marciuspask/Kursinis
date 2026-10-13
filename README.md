# Augintinių Priežiūros Sistema

Kursinis darbas – augintinių priežiūros valdymo sistema, sukurta naudojant Java ir JavaFX.

## Funkcionalumas

- Vartotojų prisijungimas ir rolės (savininkas, priežiūrotojas, administratorius, vadovas)
- Augintinių registracija ir valdymas
- Paslaugų rezervavimas ir vykdymo sekimas
- Darbo grafikų sudarymas
- Atsiliepimai ir komentarai
- Incidentų registracija
- Nuotraukų pridėjimas

## Technologijos

- Java 21
- JavaFX 21 (FXML + CSS)
- Maven

## Paleidimas

1. Įsitikink, kad įdiegta Java 21+
2. Klonuok repozitoriją:
   ```
   git clone https://github.com/<tavo-vardas>/Kursinis.git
   ```
3. Paleisk:
   ```
   mvn clean javafx:run
   ```

## Projekto struktūra

```
src/
├── programa/       # Aplikacijos paleidimas
├── modelis/        # Duomenų modeliai (Augintinis, Vartotojas, Rezervacija...)
├── valdikliai/     # JavaFX kontroleriai
└── duomenys/       # Duomenų bazė ir sesija
resources/
└── *.fxml          # Langų išdėstymai
```
