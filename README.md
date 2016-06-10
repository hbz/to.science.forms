# About
Zettel is a webservice to generate html forms for different media types. Zettel can also be used to convert form data into Json-Ld.

The Following media types are supported

* Proceedings
* Articles
* Research Data

The Following operations are supported

## List forms

GET /forms

Responds with text/html to provide al list of supported forms. e.g.

	<ul>
	<li><a href="/forms?id=bibo:proceedings">Proceedings</a></li>
	<li><a href="/forms?id=bibo:article">Article</a></li>
	<li><a href="/forms?id=katalog:data">katalog:data</li>
	</ul>

## Get a specific form
GET /form?id=any:supportedType

Responds a text/html form for the type

## Convert form data to json-ld

POST /form?id=any:supportedType

The request is processed as application/x-www-form-urlencoded. Incoming data is validated. A Json-Ld representation of the posted data is replied.




