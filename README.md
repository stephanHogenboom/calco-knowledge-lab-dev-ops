# calco knowledge lab  

## dev-ops  


### part one  
This is a very simple JavaFX application that stores data about master classers and is used for two assignements. The first assigment is a operator simulation, attendees of the copurse have figure out why a thread in the background can not  upload master classer data  through a CSV interface after a while. Afterwards they have to discuss why it was easy or hard to solve the assignment and what can be done to easier find why a problem like this occurs.

### part two  
In the second assignenet attendees have to team up again and write new code for the application. At the same time a production problem is simulated which the teams have to solve. They have to think how to divide their rescources between the production problem and the writing of new features.


Each new feature assignment should have corresponding (J)unit tests. Futurempore it should be documented better than the existing code./

The unit test can be found under the /src/test map in the code, and is accompanied with previously inserted   
//TODO comments.




### Lijst van opdrachten


Indien onderstaande tabel het niet goed doet. Paste alles in de editor op :
https://dillinger.io/

| nummer | opdracht | code geschreven + test  | unit test| comments|
|:--|:---------:|-----------------------:|---------:|--------:|
|1  |Kleur van tabs veranderen  | 10|nvt|3|
|2.0|Toevoegen master classer        |||
|2.1|verifiëer dat email adress een @ bevat | 5 | 3| 2| 
|2.2|verifiëer dat email adress een . bevat  | 5 | 3| 2| 
|2.3|verifiëer dat email adress eerst tekst bevat, daarna een @, vervolgens tekst, een punt en tot slot 2 tot 5 letters | 10 | 6| 4|
|2.4|verifiëer dat een telefoon nummer precies 10 cijfers heeft | 5| 3|2|
|2.5|verifiëer dat een telefoon nummer precies elf tekens heeft mits het één streepje bevat| 5 | 3| 2|
|2.6|verifiëer dat de postcode in het juioste formaat wordt opgegeven| 10| 3| 2|
|3|het kunnen toevoegen van een job-type | 15| nvt | 10|
|4|het toevoegen van een company (inclusief database layer)| 45 | nvt| 15|
|5|In het rapport een kolom toevoegen met het address van de master classer | 20| 7| 3|
|6|Velden toevoegen aan master classer scherm:  |   | |
|6.1|field manager| 10| nvt | 2|
|6.2|specialisaties| 10| nvt | 2|
|7|Prio één indien niet opgelosd| -75 |nvt | 5 |



### uitleg van opdrachten

1. Pas de html of liever de css van de betreffende elementen aan.  

2. - Gebruik de MasterClasserHelper class om de email addressen te valideren en roep deze class vervolgens weer aan vanaf AddMasterClasseScreen regel 133. Gebruik de MasterClasserHelperTest class om je code te unit testen.  
   - Doe hetzelfde voor de telefoonnummers 
   - gebruik voor de postcode de addresHelper class en test class
3. De master classer dao (Data Access Object) heeft al een methode om een jobtype toe te voegen aan de databaseJe hoeft deze methode alleen nog aan te roepen vanuit het JobTypesScreen
4. Om een Company toe te voegen moet je eerst een methode schrijven in de master classer dao (Data Access Object) die een company toevoegt. Hint: kijk naar andere methodes die iets soortgelijks doen. Roep je zelf gemaakte methode vervolgens aan vanuit het CompanyOverviewScreen.
5. Zorg ervoor dat er een colum met het address van de master classer in het rapport komt: in het formaat: adress huisnummer toevoeging, postcode stad. Bijvoorbeeld voorbeeld Spinozalaan 25a, 2273RN Voorburg. 
6. Zorg ervoor dat de info die in de lijst van master classers zit in labels terecht komt in het MasterClasserOverViewScreen





 




