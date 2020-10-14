Neue Formularfelder in Maske integrieren
========================================

Dieses Kapitel befasst sich ausschließlich mit der Einrichtung von neuen Formularfeldern im Modul Zettel und der Anbindung von Etikett zur Bereitstellung der benötigten Label.
Nicht beschrieben wird, wie die Auswertung der neuen Formularfelder in der Regal-Api erfolgt. Dieses Thema wird in einem separatenKapitel bzw. in der Regal-Api Dokumentation aufgegriffen.

Bisherige Umsetzung
-------------------


Liste der im Beispiel anzupassenden Dateien
***********************************************

- ``app/views/ResearchDataKtbl``
- ``services/ZettelFields``
- ``services/KtblDataHelper``
- ``conf/info.ktbl.livestock.properties``
- ``conf/labels.json``


Umsetzungsweg
**************

.. figure:: AccordionPanel.png
  :figwidth: image

  
  Beispiel eines Formularbereichs mit zwei singleFields und einem multifield

Die Einbindung neuer Formularfelder erfolgt zunächst durch Ergänzung des entsprechenden Scala-Views unter ``app/views/`` (z.B. ``researchDataKtbl.scala.html``) 
um neue Formular-Felder. Beispiel:

.. code:: html

			@accordionPanel(services.ZettelFields.ktblHeaderZF.getLabel(),"ktbl-section"){
				<br />
				@singleSelect(myForm,"livestock",services.ZettelFields.livestockZF.getLabel(),"select-livestock",KtblDataHelper.getLivestockType(),11)
				<br />
			}




1. Zunächst wird ein ``@accordionPanel`` als übergreifende Maske für die neuen Formularfelder vereinbart. Das Akkordion-Panel ermöglicht, die Formularfelder eingeklappt oder ausgeklappt anzuzeigen.  

2. Ein ``@singleSelect`` wird angelegt. Hierbei ist zu beachten, dass die hier definierten Klassen und Methodenaufrufe auch existieren. Dafür ist bisher

  a) in der Klasse ``services.ZettelFields`` eine neue Etikett-Instanz livestockZF anzulegen. *Bisher muss also für jeden in einem Formularfeld benötigten Bezeichner eine (hardcodierte) Etikett-Instanz in der Klasse ``service.ZettelFields`` deklariert werden.*
  
  b) In einer der Helper-Klassen eine neue spezifische Methode z.B. ``KtblDataHelper.getLivestockType()`` erzeugt werden. 
  
  c) Bisher wurden fast immer auch die möglichen Auswahloptionen für ``@singleSelect`` in der Klasse ``services.ZettelFields`` als HashMap untergebracht. Dadurch muss die gesamte Zettel-Applikation neu erzeugt werden, wenn sich an den Auswahloptionen etwas ändert.

3. In der Datei ``conf.labels.json`` muss für den Bezeichner des neuen Feldes ein Json-Etikett angehängt werden, der über die neu angelegte Methode (z.B. ``services.ZettelFields.livestockZF.getLabel()`` angesprochen wird.

.. code:: java

	{
		"uri": "info:regal/zettel/ktblHeader",
		"comment": "",
		"label": "Angaben für EmiMin-Daten",
		"icon": "",
		"name": "",
		"referenceType": "String",
		"container": "",
		"weight": "1",
		"type": "CONTEXT",
		"multilangLabel": {
			
		}
	},
		{
		"uri": "info:regal/zettel/livestock",
		"comment": "",
		"label": "Tierart",
		"icon": "",
		"name": "Tierart",
		"referenceType": "String",
		"container": "",
		"weight": "1",
		"type": "CONTEXT",
		"multilangLabel": {
			
		}
	}
	



Vereinfachte Umsetzung mit generischen Klassen und Methoden
------------------------------------------------------------

Am Beispiel der Anlage des multiselects-Formularfelds soll eine **neue vereinfachte** Möglichkeit vorgestellt werden, um neue Formularfelder anzulegen.

Liste der im Beispiel anzupassenden Dateien
***********************************************

- ``app/views/ResearchDataKtbl``
- ``conf/info.ktbl.livestock.properties``
- ``conf/labels.json``


Umsetzungsweg
**************

Die Einbindung neuer Formularfelder erfolgt zunächst durch Ergänzung des entsprechenden Scala-Views unter ``app/views/`` (z.B. ``researchDataKtbl.scala.html``) 
um neue Formular-Felder. Beispiel:
 
.. code:: html

			@accordionPanel(services.ZettelFields.ktblHeaderZF.getLabel(),"ktbl-section"){
				
				[...]
				
				@multiselect(myForm, jsonMap, "emissions", services.ZettelFields.getEtikettByName("emissionsZF", "info.ktbl.emission.de.emissions"),"select-emissions",GenericDataHelper.getFieldSelectValues("ktbl.livestock.properties", "info.ktbl.emission"),42)
				<br />
			}

1. Ein ``@multiselect`` wird angelegt. Hierbei ist zu beachten, dass die Klassen und Methodenaufrufe existieren. Dafür wird
  
  a) eine neue generische Methode ``services.ZettelFields.getEtikettByName("emissionsZF", "info.ktbl.emission.de.emissions")`` aufgerufen, die die benötigte Etikett-Instanz "on the fly erzeugt". 
  
  b) die generische Klasse GenericDataHelper mit der generischen Methode ``.getFieldSelectValues("Dateiname", "NamensPattern")`` [#]_  aufgerufen. 
  
  c) Eine Konfigurations-Datei ``conf/Dateiname`` angelegt [#]_. Diese enthält als Schlüssel Literale, die mit dem NamensPattern beginnen [#]_.
  

Das folgende Beispiel zeigt die Konfigurationsdatei für mehrere Formularfelder, inklusive einer ersten Vorbereitung für unterschiedliche Sprachen.   

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
	# Emissionen
	info.ktbl.emission.de.ammonia=Ammoniak (NH2)
	info.ktbl.emission.de.carbondioxide=Kohlendioxid (C02)
	info.ktbl.emission.de.diammoniumoxide=Lachgas (N2O)
	info.ktbl.emission.de.methane=Methan (CH4O)
	info.ktbl.emission.de.smells=Geruch
	info.ktbl.emission.de.particle=Partikel (Staub)
	info.ktbl.emission.de.biogene_aerosole=Bioaerosole
	info.ktbl.emission.de.others=andere

Text hier
 
 .. [#] Das NamensPattern für die Auswahl der Tierart lautet im Beispiel ``info.ktbl.livestock``. Die Sprachvariable trennt das NamensPattern von den Auswahloptionen.
 .. [#] Die Datei enthält Schlüssel-Werte-Paare, die durch ein ``=`` getrennt werden. Das Verhalten beim Einlesend er Datei orientiert sich an der ``Properties``-Klasse aus ``java.utils``   
 .. [#] Durch den NamensPattern ist es möglich, für ein Formularfeld nur bestimmte Werte aus dieser Datei zu übernehmen. Es kann aber auch für jedes Formularfeld eine eigene Datei angelegt werden.  