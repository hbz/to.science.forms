Bisher verfügbare Formulartypen
===============================

Die verschiedenen Formulartypen sind bisher nicht dokumentiert, es wird hier versucht, eine Kurzübersicht zu geben.
Alle Formulartypen verweisen jeweils auf Templates im Verzeichnis views. In den Templates gibt es weitere Informationen z.B. zu den Parametertypen, die ich hier zunächst weglasse.  

singleSelect
------------

Template: ``views/singleSelect.scala.html``

Parameter aus dem Beispiel:

- ``myForm`` = Verweis auf die verwendete Model (zumeist ``model.ZettelModel``) ?
- ``livestock`` = Name des Forms ?
- ``services.ZettelFields.livestockZF.getLabel()`` = Methode der Klasse ZettelFields, die den Label des Feldes livestockZF beim Etikett-Service erfragt und abholt.
- ``GenericDataHelper.getFieldSelectValues("ktbl.livestock.properties", "info.ktbl.livestock")`` = Statische Methode einer Helper-Klasse, die die zur Auswahl stehenden Werte erfragt und abholt.
- ``11`` = ist die Position des neuen Formularfeldes im Erfassungsformular. Hiermit ist die Reihenfolge der Formulare steuerbar.

.. code:: html

	@singleSelect(myForm,"livestock",services.ZettelFields.livestockZF.getLabel(),"select-livestock",GenericDataHelper.getFieldSelectValues("ktbl.livestock.properties", "info.ktbl.livestock"),11)

``@singleSelect`` erzeugt ein Auswahl-Formular mit der Möglichkeit eine Option auszuwählen. Äquivalent zu <select> in html:

.. code:: html

   <select id="cars" name="cars" size="1">
       <option value="volvo">Volvo</option>
       <option value="saab">Saab</option>
       <option value="fiat">Fiat</option>
       <option value="audi">Audi</option>
     </select>

``singleSelect`` gibt wohl keine Möglichkeit eine Option als Vorausgewählt zu markieren. Ebenso habe ich bisher keine Möglichkeit entdeckt, mehr als eine Option im Auswahlfeld sichtbar zu machen.

multiselect
-----------

Template: ``views/multiselect.scala.html``

Parameter aus dem Beispiel:

- ``myForm`` =  Verweis auf das verwendete Model (zumeist model.ZettelModel) ?
- ``livestock`` =  Name des Forms ?
- ``services.ZettelFields.livestockZF.getLabel()`` = Methode der Klasse ZettelFields, die den Label des Feldes livestockZF beim Etikett-Service erfragt und abholt.
- ``GenericDataHelper.getFieldSelectValues("ktbl.livestock.properties", "info.ktbl.livestock")`` = Statische Methode einer Helper-Klasse, die die zur Auswahl stehenden Werte erfragt und abholt.
- ``11`` = ist die Position des neuen Formularfeldes im Erfassungsformular. Hiermit ist die Reihenfolge der Formulare steuerbar.

.. code:: html

	@multiselect(myForm, myMap, "livestock",services.ZettelFields.livestockZF.getLabel(),"select-livestock",GenericDataHelper.getFieldSelectValues("ktbl.livestock.properties", "info.ktbl.livestock"),11)

``@multiselect`` erzeugt zunächst ein Auswahl-Formular mit der Möglichkeit eine Option auszuwählen. Äquivalent zu <select> in html.

.. code:: html

   <select id="cars" name="cars" size="1">
       <option value="volvo">Volvo</option>
       <option value="saab">Saab</option>
       <option value="fiat">Fiat</option>
       <option value="audi">Audi</option>
     </select>
     
 
Zusätzlich erzeugt ``multiselect`` aber auch noch Buttons mit denen die Nutzenden bei Bedarf weitere dieser Felder im Formular erzeugen können oder löschen können. Damit ist eine Mehrfachauswahl zu einem Feld möglich
 
