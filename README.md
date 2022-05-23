[![Codacy Badge](https://api.codacy.com/project/badge/Grade/9808c99193c54f38a003cc5cb46e0369)](https://app.codacy.com/gh/hbz/to.science.forms?utm_source=github.com&utm_medium=referral&utm_content=hbz/to.science.formss&utm_campaign=Badge_Grade_Settings)
[![Travis Ci](https://travis-ci.org/hbz/zettel.svg?branch=master)](https://travis-ci.org/hbz/zettel)

![to.science Logo](/doc/resources/images/to.science.svg "to.science Logo")
# About
to.science-forms is a webservice to generate html forms for different media types. You can use the forms in your own website to generate valid json-ld data for your own usage [learn more](#add-zettel-to-an-existing-web-application). 

The Following media types are supported

* Article

## Screenshot
<img src="screen.png" alt="Screenshot of zettel" style="width: 800px;"/>

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

<img src="zettel-flos.png" alt="Diagram about zettel data flow" style="width: 800px;"/>

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

# Run

## Download
	cd /tmp
	git clone https://github.com/hbz/to.science.forms
	cd to.science.forms
	
## Run

	/opt/activator-1.3.2-minimal/activator run

Go to http://localhost:9000/tools/zettel

## Install 

	cd /tmp/to.science.forms
	/opt/regal/bin/activator/bin/activator dist
	cp target/universal/zettel-1.0-SNAPSHOT.zip  /tmp
	cd /tmp
	unzip zettel-1.0-SNAPSHOT.zip
	mv zettel-1.0-SNAPSHOT /opt/toscience/to.science.forms

edit /opt/toscience/to.science.forms/conf/application.conf

	contextUrl="http://localhost:9002/tools/etikett/context.json"
	etikettService="http://api.localhost:9002/tools/etikett"
	etikettUser=admin
	etikettPwd=admin
	zettel.researchData.helpText="http://localhost/node/2"

to connect to a to.science.api also edit the following vars:

	application.toscience.url="http://localhost"
  	application.toscience.url.api="http://api.localhost"

edit startscript

	sudo cp /tmp/to.science.forms/install/zettel.tmpl /etc/init.d/to.science.forms
	sudo chmod u+x /etc/init.d/to.science.forms
	sudo editor /etc/init.d/to.science.forms

set the following vars

        NAME=to.science.forms
        DESC="Zettel form provider"
        JAVA_HOME=/opt/jdk
        JAVA_OPTS="-XX:+HeapDumpOnOutOfMemoryError"
        HOME="/opt/toscience/to.science.forms"
        USER="user to run forms"
        GROUP="group of user to run forms"
        SECRET=`uuidgen` # generate a secret e.g. using uuidgen
        PORT=9003 # match with your apache.conf

include into system start and shutdown

	sudo update-rc.d to.science.forms defaults 99 20

start

	sudo service to.science.forms start

# Update

	rm -rf /tmp/to.science.forms
	cd /tmp
	git clone https://github.com/hbz/to.science.forms
	cd /tmp/to.science.forms
	/opt/activator-1.3.2-minimal/activator dist
	cp target/universal/zettel-1.0-SNAPSHOT.zip  /tmp
	cd /tmp
	unzip zettel-1.0-SNAPSHOT.zip
	cp /opt/zettel/conf/application.conf /tmp/zettel-1.0-SNAPSHOT/conf
	sudo service zettel stop
	ps -eaf | grep zettel
	# evtl kill <pid>, falls Zettel noch nicht weg ist!
	rm -rf /opt/zettel/*
	mv /tmp/zettel-1.0-SNAPSHOT/* /opt/zettel/
	sudo service zettel start

# License

GNU AFFERO GENERAL PUBLIC LICENSE
Version 3, 19 November 2007


