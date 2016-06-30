# About
Zettel is a webservice to generate html forms for different media types. You can use the forms in your own website to generate valid json-ld data for your own usage [learn more](#add-zettel-to-an-existing-web-application). 

The Following media types are supported

* Research Data


# Get zettel running

## Download Activator

	cd /tmp
	wget http://downloads.typesafe.com/typesafe-activator/1.3.2/typesafe-activator-1.3.2-minimal.zip
	unzip typesafe-activator-1.3.2-minimal.zip
	sudo mv activator-1.3.2-minimal /opt

## Git clone    
    
    cd /tmp
    git clone https://github.com/hbz/zettel
    cd zettel

## Run
	
	/opt/activator-1.3.2-minimal/activator run
	
Go to http://localhost:9000/tools/zettel


# Add zettel to an existing web application

[Find a complete example](https://github.com/hbz/zettel/blob/master/app/views/client.scala.html) on how to embedd a zettel form to your application, using javascript.

You can include a zettel form using iframes

	<iframe src="http://localhost:9000/tools/zettel/forms?id=katalog:data" width="100%"
		style="height: 100vh; border: none;" id="myIframe-1">
		<p>iframes are not supported by your browser.</p>
	</iframe>


A zettel form sends json data to a parent window using events.

	function postJsonData() {
			var target = parent.postMessage ? parent
					: (parent.document.postMessage ? parent.document
							: undefined);
			if (typeof target != "undefined") {
				var data = $('#embeddedJson').text();
				if (data.length) {
					target.postMessage(data, "*");
				}
			}
		}
A client can catch the data using an event listener

    function getMessage(e){
    	 var obj = JSON.parse(e.data);
    	 console.log(JSON.stringify(obj.message));
    	 console.log(JSON.stringify(obj.code));
    	 console.log(JSON.stringify(obj.action));
    }
	window.addEventListener("message", getMessage, false);

The json data has the form "message", "code", "action". If the form contains errors a code of 400 is replied together with a reasonable message. If zettel was able to produce rdf from the form it sends a code of 200 and the actual data in the message field.

    {
    	"message":{...more json data or URI-encoded rdf-xml...},
    	"code": <"200"|"400">
    }
To edit an existing resource you must send your data to the zettelform this can be done by posting form-data to the /forms route. You can also post rdf-xml data. Zettel will try to fill the form with your rdf data. You can achieve this by sending a message to the zettel iframe. Zettel listens to javascript events. Please use the following pattern:

	target.postMessage({
					'queryParam' : 'id=katalog:data&format=xml&topicId='
							+ topicId + '&documentId=' + documentId,
					'message' : rdf,
					'action'  : 'postDataToZettel'
				}, "*");


# List forms

GET /forms

Responds with text/html to provide al list of supported forms. e.g.

	<ul>
	<li><a href="/forms?id=katalog:data">katalog:data</li>
	</ul>

# Get a specific form
GET /form?id=any:supportedType

Responds a text/html form for the type

# Convert form data to json-ld

Accept: application/json
POST /form?id=any:supportedType

The request is processed as application/x-www-form-urlencoded. Incoming data is validated. A Json-Ld representation of the posted data is replied.

# Add new forms to zettel
Comming soon...

#License

GNU AFFERO GENERAL PUBLIC LICENSE
Version 3, 19 November 2007


