Neue Formularfelder in Maske integrieren
========================================

Die Einbindung neuer Formularfelder erfolgt zunächst durch Ergänzung des entsprechenden Scala-Views unter app/views/ (z.B. reserachDataKtbl.scala.html) 
um neue Formular-Felder. Beispiel:

.. code:: html

			@accordionPanel(services.ZettelFields.ktblHeaderZF.getLabel(),"ktbl-section"){
				<br />
				@singleSelect(myForm,"livestock",services.ZettelFields.livestockZF.getLabel(),"select-livestock",GenericDataHelper.getFieldSelectValues("ktbl.livestock.properties", "info.ktbl.livestock"),11)
				<br />
				@multiselect(myForm,jsonMap,"dataOrigin",services.ZettelFields.dataOriginZF.getLabel(),"select-dataOrigin",GenericDataHelper.getFieldSelectValues("ktbl.livestock.properties", "info.ktbl.livestock"),112)
				<br />
			}




1. Zunächst wird ein Akkordion als übergreifende Maske für die neuen Formularfelder vereinbart. Das Akkordion ermöglicht, die Formularfelder eingeklappt oder ausgeklappt anzuzeigen.  
2. Ein @singleSelect
