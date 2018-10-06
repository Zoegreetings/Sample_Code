var baseUrl = "http://ec2-35-162-112-117.us-west-2.compute.amazonaws.com:8080/hapi-fhir-jpaserver-example/baseDstu2";

var agencyUrl = 'http://ec2-35-163-169-229.us-west-2.compute.amazonaws.com:8080/hapi-fhir-jpaserver-example/baseDstu2';

var clientUrls = [// an array
	'http://ec2-35-164-207-196.us-west-2.compute.amazonaws.com:8080/hapi-fhir-jpaserver-example/baseDstu2',
	'http://ec2-35-164-48-104.us-west-2.compute.amazonaws.com:8080/hapi-fhir-jpaserver-example/baseDstu2',
	'http://ec2-35-161-74-59.us-west-2.compute.amazonaws.com:8080/hapi-fhir-jpaserver-example/baseDstu2'
];

var activeClient;
//example.js call purpleDragon.js call the id 'pageLog' in index.html
var pdInstances = {//a dictionary
	'client1': new PurpleDragon(baseUrl, clientUrls[0], agencyUrl, '#pageLog'),
	'client2': new PurpleDragon(baseUrl, clientUrls[1], agencyUrl, '#pageLog'),
	'client3': new PurpleDragon(baseUrl, clientUrls[2], agencyUrl, '#pageLog')
}
//ready function used to ensure that all the index.html is ready and loaded before the src="/scripts/exampleinterface.js" is run in index.html
$(document).ready(function() {
	$("#processButton").click(function() {
		activePd = pdInstances[$("#clientSelect").val()];//#clientSelect all #xxx are id in the index.html
		
		var observations = $("#observationList").val().split(",");
		var conditions = $("#conditionList").val().split(",");
		var medications = $("#medicationList").val().split(",");
		var medicationOrders = $("#medicationOrderList").val().split(",");

		for (var i in observations) {
			if (!isNaN(parseInt(observations[i]))) {
				activePd.processObservation(observations[i].trim());//the processObservation function in purpleDragon
			}
		}

		for (var i in conditions) {
			if (!isNaN(parseInt(conditions[i]))) {
				activePd.processCondition(conditions[i].trim());
			}
		}

		for (var i in medications) {
			if (!isNaN(parseInt(medications[i]))) {
				activePd.processMedication(medications[i].trim());
			}
		}

		for (var i in medicationOrders) {
			if (!isNaN(parseInt(medicationOrders[i]))) {
				activePd.processMedicationOrder(medicationOrders[i].trim());
			}
		}
		
	});
});