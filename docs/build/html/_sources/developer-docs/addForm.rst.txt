Neue Formularfelder in Maske integrieren
========================================

Die Einbindung neuer Formularfelder erfolgt zunächst durch Ergänzung des entsprechenden Scala-Views unter app/views/ (z.B. ``researchDataKtbl.scala.html``) 
um neue Formular-Felder. Beispiel:

.. code:: html

			@accordionPanel(services.ZettelFields.ktblHeaderZF.getLabel(),"ktbl-section"){
				<br />
				@singleSelect(myForm,"livestock",services.ZettelFields.livestockZF.getLabel(),"select-livestock",KtblDataHelper.getLivestockType(),11)
				<br />
			}




1. Zunächst wird ein Akkordion als übergreifende Maske für die neuen Formularfelder vereinbart. Das Akkordion ermöglicht, die Formularfelder eingeklappt oder ausgeklappt anzuzeigen.  

2. Ein ``@singleSelect`` wird angelegt. Hierbei ist zu beachten die, dass die Klassen und Methodenaufrufe existieren. Dafür muss

  a) in der Klasse ``services.ZettelFields`` eine neue Etikett-Instanz livestockZF vereinbart werden. Bisher existiert für jeden für ein Formularfeld benötigten Bezeichner eine explizit in der Klasse ZettelFields vereinbarte (hardcodierte) Etikett-Instanz.
  
  b) In einer der Helper-Klassen eine neue spezifische Methode z.B. ``KtblDataHelper.getLivestockType()`` erzeugt werden. 
  
  c) Bisher wurden fast immer auch die Auswahloptionen in der Klasse als HashMap untergebracht. Dadurch muss die gesamte Zettel-Applikation neu erzeugt werden, wenn sich an den Auswahloptionen etwas ändert.

Am Beispiel der Anlage des multiselects-Formularfelds soll eine *neue vereinfachte* Möglichkeit gezeigt werden, neue Formularfelder anzulegen.

Beispiel: 
 
.. code:: html

			@accordionPanel(services.ZettelFields.ktblHeaderZF.getLabel(),"ktbl-section"){
				<br />
				@singleSelect(myForm,"livestock",services.ZettelFields.livestockZF.getLabel(),"select-livestock",KtblDataHelper.getLivestockType(),11)
				<br />
				@multiselect(myForm,jsonMap,"dataOrigin",services.ZettelFields.dataOriginZF.getLabel(),"select-dataOrigin",GenericDataHelper.getFieldSelectValues("ktbl.livestock.properties", "info.ktbl.livestock"),112)
				<br />
			}

1. Ein ``@multiselect`` wird angelegt. Hierbei ist zu beachten, dass die Klassen und Methodenaufrufe existieren. Dafür muss
  
  a) in der Klasse ``services.ZettelFields`` eine neue Etikett-Instanz livestockZF vereinbart werden. **Es soll im nächsten Schritt versucht werden, auch hier generischen Code einzuführen, der auf hardcodierte Etikett-Instanzen verzichtet.**
  
  b) Die generische Klasse GenericDataHelper mit der generischen Methode ``.getFieldSelectValues("Dateiname", "NamensPattern")`` [#]_  erzeugt werden. 
  
  c) Eine Konfigurations-Datei mit Dateiname entsprechend der Vorgaben der Properties-Klasse im Verzeichnis conf angelegt werden. Durch den NamensPattern ist es möglich, für ein Formularfeld nur bestimmte Werte aus dieser Datei zu übernehmen. Es kann aber auch für jedes Formularfeld eine eigene Datei angelegt werden. 
  

Das folgende Beispiel zeigt die Konfigurationsdatei für mehrere Formularfelder, sowie eine erste (nicht funktionale) Implementierung für unterschiedliche Sprachen.   

.. code:: text

	# Tierart
  	info.ktbl.livestock.de.cattle=Rind
	info.ktbl.livestock.de.swine=Schwein
 	info.ktbl.livestock.de.hens=Huhn
  	info.ktbl.livestock.de.turkey=Pute
  	info.ktbl.livestock.de.duck=Ente
  	info.ktbl.livestock.en.cattle=Cattle
	info.ktbl.livestock.fr.cattle=Bovin
	# Produktionsrichtung
	info.ktbl.livestock.cattle.de.diary_farming=Milchviehhaltung
	info.ktbl.livestock.cattle.de.calf_fattening=Kälbermast
	# Lüftung
	info.ktbl.ventilation.de.enforced_ventilation=zwangsgelüftet
	info.ktbl.ventilation.de.self_ventilation=freigelüftet
	info.ktbl.ventilation.de.combined_ventilation=kombinierte Lüftung
	# Emmissionen
	info.ktbl.emmision.de.ammonium=Ammoniak (NH<sub>2</sub>)
	info.ktbl.emission.de.carbondioxide=Kohlendioxid (C0<sub>2</sub>)
	info.ktbl.emission.de.diammoniumoxide=Lachgas (N<sup>2</sup>O)
	info.ktbl.emission.de.methane=Methan (CH<sub>4</sub>O)
	info.ktbl.emission.de.smells=Geruch
	info.ktbl.emission.de.particle=Partikel (Staub)
	info.ktbl.emission.de.biogene_aerosole=Bioaerosole
	info.ktbl.emission.de.others=andere

Text hier
 
 .. [#] Das NamensPattern für die Auswahl der Tierart lautet im Beispiel ``info.ktbl.livestock``. Die Sprachvariable trennt das NamensPattern von den Auswahloptionen.
  