# About
Katalog is a webservice to generate html forms for different media types. Katalog can also be used to convert form data into Json-Ld.

The Following media types are supported

* Proceedings
* Articles
* Scientific Data

The Following operations are supported

## List forms

GET /form

Responds with text/html to provide al list of supported forms. e.g.

	<ul>
	<li><a href="?type=bibo:proceeding">Proceeding</a></li>
	<li><a href="?type=bibo:article">Article</a></li>
	<li><a href="?type=katalog:data">Scientific Data</li>
	</ul>

## Get a specific form
GET /form?type=any:supportedType

Responds a text/html form for the type

## Convert form data to json-ld

POST /form?type=any:supportedType

The request is processed as application/x-www-form-urlencoded. Incoming data is validated. A Json-Ld representation of the posted data is replied.




