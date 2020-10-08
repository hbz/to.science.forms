Neue Formularfelder in Maske integrieren
========================================

Die Einbindung neuer Formularfelder erfolgt zunächst durch Ergänzung des entsprechenden Scala-Views unter app/views/ (z.B. reserachDataKtbl.scala.html) 
um neue Formular-Felder. Beispiel:

.. code:: html

			@accordionPanel(services.ZettelFields.identifiersHeaderZF.getLabel(),"ktbl-section"){
				<br />
				@multifieldText(myForm,jsonMap,"urn",services.ZettelFields.urnZF.getLabel(),"",24)
				<br />
				@multifieldText(myForm,jsonMap,"doi",services.ZettelFields.doiZF.getLabel(),"",25)
				<br />
				@multifieldText(myForm,jsonMap,"isLike",services.ZettelFields.isLikeZF.getLabel(),"",26)
			}


1. Zunächst wird ein Akkordion als übergreifende Maske für die neuen Formularfelder vereinbart. Das Akkordion ermöglicht, die Formularfelder eingeklappt oder ausgeklappt anzuzeigen.  
2. Ein MultifeldText-Formular wird festgelegt. 
Java-Klassen app/services/ZettelFields und .  
