
Neue Erfassungsmaske erstellen
==============================

Aufsetzend auf einer geeigneten Maske erfolgt die Erstellung einer neuen Maske am einfachsten durch Kopieren und  anschließendes Anpassen der benötigten Dateien.

Unter ``/app/models/`` befinden sich die einzelnen Modelle für die Eingabemasken. Diese erweitern die Klasse ``ZettelModel``. 
Wird eine neue Maske angelegt, so ist dafür ein neues Model entweder als Java-Klasse zu erzeugen, die die Klasse ``ZettelModel`` erbt, 
oder einfacher durch Kopieren und Anpassen einer bestehenden Klasse (z.B. ``ResearchData.java`` zu ``ResearchDataKtbl.java``).

1) In der neuen Klasse müssen nun die Variablen, die den Klassennamen enthalten entsprechend angepasst werden

2) Die neue Klasse muss anschließend in der Klasse ``ZettelRegister`` unter ``app/services/`` eingetragen werden. Das erfolgt innerhalb des Constructors der Klasse.

3) In ``app/services/`` muss zusätzlich eine Klasse erstellt werden, die dem neuen Model entspricht (z.B. ``ResearchDataKtblZettel.java``). 

Innerhalb des nun erstellten Models (``app/models/ResearchDataKtbl.java``) wird zunächst nur festgelegt, wie die Felder angezeigt werden und ob sie z.B. verpflichtend sind.
Die Einbeziehung neuer Formularfelder erfolgt erst jetzt.