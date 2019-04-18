// *** GLOBAL VARIABLES ***
//var colors = ['#D3684E', '#5386E4', '#4B5363', '#73CEE2', '#B7BDE8', '#B1C6C1',
//			  '#36413E', '#F7B538', '#DB7C26', '#780116', '#F48484', '#CDE2BA'];
var colors = ['#ff5a5f', '#ff5a5f', '#cc8726', '#009eb7', '#833c9e', '#209949', '#5FBB66'] //['#ff5a5f', '#3c3c3c', '#cc8726', '#009eb7', '#833c9e', '#79B4A9', '#5F4B66', '#56667A', '#8797AF', '#2C1320'];
var currYear;
var selectedState = null;
var currentLayer;
var map;
var usaLayer;
var orLayer;
var ohLayer;
var maLayer;
var orDistrictsLayer;
var ohDistrictsLayer;
var maDistrictsLayer;

// **** MAPS API FUNCTIONS ******
function initialize() {
    map = initMap();
    initSearchBox(map);
}

function initMap() {
    // initializing a map
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 37.09024, lng: -95.712891}, // approximate center of US
        zoom: 4, // 0 = most zoomed out
        styles: [
            {
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#f5f5f5"
                    }
                ]
            },
            {
                "elementType": "geometry.stroke",
                "stylers": [
                    {
                        "color": "#000000"
                    },
                    {
                        "visibility": "on"
                    }
                ]
            },
            {
                "elementType": "labels.icon",
                "stylers": [
                    {
                        "visibility": "off"
                    }
                ]
            },
            {
                "elementType": "labels.text.fill",
                "stylers": [
                    {
                        "color": "#616161"
                    }
                ]
            },
            {
                "elementType": "labels.text.stroke",
                "stylers": [
                    {
                        "color": "#f5f5f5"
                    }
                ]
            },
            {
                "featureType": "administrative",
                "elementType": "labels.text.fill",
                "stylers": [
                    {
                        "color": "#000000"
                    }
                ]
            },
            {
                "featureType": "administrative.land_parcel",
                "elementType": "labels.text.fill",
                "stylers": [
                    {
                        "color": "#bdbdbd"
                    }
                ]
            },
            {
                "featureType": "landscape.natural",
                "elementType": "geometry.fill",
                "stylers": [
                    {
                        "color": "#f3f3f5"
                    }
                ]
            },
            {
                "featureType": "landscape.natural.terrain",
                "elementType": "geometry.fill",
                "stylers": [
                    {
                        "color": "#c2c5c7"
                    }
                ]
            },
            {
                "featureType": "poi",
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#eeeeee"
                    }
                ]
            },
            {
                "featureType": "poi",
                "elementType": "labels.text",
                "stylers": [
                    {
                        "visibility": "off"
                    }
                ]
            },
            {
                "featureType": "poi",
                "elementType": "labels.text.fill",
                "stylers": [
                    {
                        "color": "#757575"
                    }
                ]
            },
            {
                "featureType": "poi.business",
                "stylers": [
                    {
                        "visibility": "off"
                    }
                ]
            },
            {
                "featureType": "poi.park",
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#e5e5e5"
                    }
                ]
            },
            {
                "featureType": "poi.park",
                "elementType": "labels.text.fill",
                "stylers": [
                    {
                        "color": "#9e9e9e"
                    }
                ]
            },
            {
                "featureType": "road",
                "stylers": [
                    {
                        "visibility": "off"
                    }
                ]
            },
            {
                "featureType": "road",
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#ffffff"
                    }
                ]
            },
            {
                "featureType": "road",
                "elementType": "labels.icon",
                "stylers": [
                    {
                        "visibility": "off"
                    }
                ]
            },
            {
                "featureType": "road.arterial",
                "elementType": "labels.text.fill",
                "stylers": [
                    {
                        "color": "#757575"
                    }
                ]
            },
            {
                "featureType": "road.highway",
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#dadada"
                    }
                ]
            },
            {
                "featureType": "road.highway",
                "elementType": "labels.text.fill",
                "stylers": [
                    {
                        "color": "#616161"
                    }
                ]
            },
            {
                "featureType": "road.local",
                "elementType": "labels.text.fill",
                "stylers": [
                    {
                        "color": "#9e9e9e"
                    }
                ]
            },
            {
                "featureType": "transit",
                "stylers": [
                    {
                        "visibility": "off"
                    }
                ]
            },
            {
                "featureType": "transit.line",
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#e5e5e5"
                    }
                ]
            },
            {
                "featureType": "transit.station",
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#eeeeee"
                    }
                ]
            },
            {
                "featureType": "water",
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#c9c9c9"
                    }
                ]
            },
            {
                "featureType": "water",
                "elementType": "geometry.fill",
                "stylers": [
                    {
                        "color": "#e2edf5"
                    }
                ]
            },
            {
                "featureType": "water",
                "elementType": "labels.text.fill",
                "stylers": [
                    {
                        "color": "#9e9e9e"
                    }
                ]
            }
        ]
    });


    // creating a layer
    usaLayer = new google.maps.Data();
    usaLayer.loadGeoJson(properties.usaLayer, {}, function (features) {
        usaLayer.forEach(function (feature) {
            if (feature.getProperty('name') == "Oregon" | feature.getProperty('name') == "Ohio"
                    | feature.getProperty('name') == "Massachusetts") {
                usaLayer.overrideStyle(feature, {fillColor: 'silver', fillOpacity: 0.4, strokeWeight: 1});
            }
        });
    });

    //globally styling map, revertStyle will revert ovverriden styles back to this.
    usaLayer.setStyle({
        strokeWeight: 0,
        fillOpacity: 0
    });

    usaLayer.setMap(map);


    // sets selected state
	usaLayer.addListener('click', function(event) {
		selectedState(event);
    // console.log(event.latLng.lat(),event.latLng.lng());

	});

    usaLayer.addListener('mouseover', function(event) {
        selected = event.feature;
        if (selected != selectedState) {
        	usaLayer.revertStyle(selected); //cmmnt
        	usaLayer.overrideStyle(selected, {fillColor: 'lightblue', fillOpacity: 0.4, strokeWeight: 1}); //cmnt
        }
    });

    usaLayer.addListener('mouseout', function(event) {
    	selected = event.feature;
    	if (selected != selectedState) {
    		usaLayer.revertStyle(selected);
				var stateName = selected.getProperty('name');
    		if(stateName == "Oregon" | stateName == "Ohio"
    			| stateName == "Massachusetts") {
                usaLayer.overrideStyle(selected, {fillColor: 'silver', fillOpacity: 0.4, strokeWeight: 1});
            }

        }
    });

    orLayer = new google.maps.Data();
    orLayer.loadGeoJson(properties.orPrecinctsLayer, {}, function (features) {
        orLayer.forEach(function (feature) {
            orLayer.overrideStyle(feature, {fillColor: 'lightblue', fillOpacity: 0.4, strokeWeight: 1});
        })
    });
    orLayer.setStyle({
        fillColor: 'lightblue',
        fillOpacity: 0.4,
        strokeWeight: 1
    });

    orLayer.setMap(map)
    orLayer.addListener('click', function (event) {
        console.log(event.feature.getId());
    });
    currentLayer = orLayer;

   ohLayer = new google.maps.Data();
   ohLayer.loadGeoJson(properties.ohPrecinctsLayer);
   ohLayer.setStyle({
   	fillColor: 'lightblue',
   	fillOpacity: 0.4,
   	strokeWeight: 1
   });

   maLayer = new google.maps.Data();
   maLayer.loadGeoJson(properties.maPrecinctsLayer);
   maLayer.setStyle({
   	fillColor: 'lightblue',
   	fillOpacity: 0.4,
   	strokeWeight: 1
   });

   // DISTRICT LAYERS

   orDistrictsLayer = new google.maps.Data();
   orDistrictsLayer.loadGeoJson(properties.orDistrictsLayer);
   orDistrictsLayer.setStyle({
       fillOpacity: 0,
       strokeWeight: 2,
       strokeColor: 'white'
   });

   ohDistrictsLayer = new google.maps.Data();
   ohDistrictsLayer.loadGeoJson(properties.ohDistrictsLayer);
   ohDistrictsLayer.setStyle({
       fillOpacity: 0,
       strokeWeight: 2,
       strokeColor: 'white'
   });

   maDistrictsLayer = new google.maps.Data();
   maDistrictsLayer.loadGeoJson(properties.maDistrictsLayer);
   maDistrictsLayer.setStyle({
       fillOpacity: 0,
       strokeWeight: 2,
       strokeColor: 'white'
   });

    return map;

}


// ** PREFERENCE MANAGEMENT **
function saveWeights() {
    //if (! user logged in) {
    //  window.alert('You must be signed in to use this feature!');
    //}
}

function loadWeights() {
    //if (! user logged in) {
    //  window.alert('You must be signed in to use this feature!');
    //}

}

function viewSaved() {
    //if (! user logged in) {
    //  window.alert('You must be signed in to use this feature!');
    //}
}

// *** MAP CONTROL FUNCTIONS **


function deleteFiles(files) {
	// TODO
	files.forEach(filename => {
		if (filename == "myMap") {
			document.getElementById("1").innerHTML = "";
		} else {
			document.getElementById("2").innerHTML = "";

		}

	});
}

function selectState(event) {
	var state = event.feature;
	usaLayer.revertStyle(state);
	var stateName = state.getProperty('name');
	if (state != selectedState && stateName == "Oregon"
		| stateName == "Ohio" | stateName == "Massachusetts") {
				usaLayer.overrideStyle(selectedState, {fillColor: 'silver', fillOpacity: 0.4, strokeWeight: 1});
				var latLng = new google.maps.LatLng(state.lat, state.lon);
				map.setCenter(latLng);
				map.setZoom(6);
				setSelected(state);
				document.getElementById('state').value = stateName;
	}
}

function setSelected(state) {
    selectedState = state; // setting global variable
    setCurrentLayer(selectedState);
}

function setCurrentLayer(selectedState) {
  if (selectedState.getProperty('name') == "Oregon") {
	  currentLayer = orLayer;
	  maLayer.setMap(null);
	  ohLayer.setMap(null);
	  orLayer.setMap(map);
 }
 else if (selectedState.getProperty('name') == "Ohio") {
	  currentLayer = ohLayer;
	  maLayer.setMap(null);
	  orLayer.setMap(null);
	  ohLayer.setMap(map);
 }
 else if (selectedState.getProperty('name') == "Massachusetts") {
	  currentLayer = maLayer;
	  ohLayer.setMap(null);
	  orLayer.setMap(null);
	  maLayer.setMap(map);
 }

    console.log("current layer set to: " + selectedState.getProperty('name'));

  // info popup upon clicking a precinct
  var contentString = '<div id="content">'+
  '<div id="siteNotice">'+
  '</div>'+
  '<h1 id="firstHeading" class="firstHeading">2018</h1>'+
  '<div id="bodyContent">'+
  '<p><b>Democrat:</b> 47% <i class="fas fa-democrat"></i></p>'+
  '<p><b>Republican:</b> 33% <i class="fas fa-republican"></i></p>'+
  '<hr></hr>'+
  '<p><b>Black:</b> 14% <span class="black box"></span></p>'+
  '<p><b>White:</b> 80% <span class="white box"></span></p>'+
  '<p><b>Other:</b> 6% <span class="other box"></span></p>'+
  '</div>'+
  '</div>';

    var infowindow = new google.maps.InfoWindow({
        content: contentString,
        maxWidth: 200
    });


//

}

function getInfo(precinct) {
    var contentString = '<div id="content">' +
            '<div id="siteNotice">' +
            '</div>' +
            '<h1 id="firstHeading" class="firstHeading"> ' + currYear + '</h1>' +
            '<div id="bodyContent">' +
            '<p><b>Democrat:</b>' + feature.VotingData1['0'] + '%<span class="democrat box"></span></p>' +
            '<p><b>Republican:</b>' + feature.VotingData1['1'] + '%<span class="republican box"></span></p>' +
            '<hr></hr>' +
            '<p><b>Black:</b>' + feature.DemographicData.black + '%<span class="black box"></span></p>' +
            '<p><b>White:</b>' + feature.DemographicData.white + '%<span class="white box"></span></p>' +
            '<p><b>White:</b>' + feature.DemographicData.asian + '%<span class="asian box"></span></p>' +
            '<p><b>Other:</b>' + feature.DemographicData.other + '%<span class="other box"></span></p>' +
            '</div>' +
            '</div>';

    return contentString;
}

//*** ALGORITHM UPDATE FUNCTIONS ***
function startAlgorithm() {
        inProgress = true;
        currentLayer.revertStyle(); // clear old layer when user starts new algorithm
        document.getElementById("updatemsg").innerHTML = "Algorithm has started.";

        // initialize algorithm
        var state = document.getElementById("state").value;
//        var numDistricts = document.getElementById("numDistrict").value;
var numDistricts = 10;
        var electionYear = document.getElementById("year").value;
        var seedStrategy = document.getElementById("seedStrategy").value;
        var compactness = document.getElementById("compactness").value;
        var polsby = document.getElementById("polsby").value;
        var schwartzberg = document.getElementById("schwartzberg").value;
        var reock = document.getElementById("reock").value;
        var populationEquality = document.getElementById("populationEquality").value;
        var efficiencyGap = document.getElementById("partisanSymmetry").value;
        console.log('compactness' + compactness + '  ' +  polsby );
        console.log('compactness');
        console.log('compactness');
        updateMapManager();

    function request() {
        $.ajax({
            url: 'calculate',
            type: 'POST',
            dataType:'text',
            data: {
                state: state,
                numDistricts: numDistricts,
                electionYear: electionYear,
                seedStrategy: seedStrategy,
                 compactness: compactness,
                      polsby: polsby,
                schwartzberg: schwartzberg,
                       reock:reock,
          populationEquality: populationEquality,
               efficiencyGap: efficiencyGap},
            success: function (response) {
                console.log(response);
            },
            error: function (error) {
                console.log(error);
                console.log("Could not initialize algorithm.");
            }
        }
);
    };
    request();
    console.log('response happened bbbbb');
}


function updateMapManager() {

    function request() {
    	console.log("entered 2nd request");
    	$.ajax({
        url: 'update',
        type: 'GET',
        dataType: 'json',
        success: function (response) {
            console.log(response);
        	if (!response.isDone) {
        		if (response.movesReady) {
        			displayMoves(response.moves);
        		}
        	} else {
						clearInterval(interval);
						inProgress = false;
					}
        },
        error: function (error) {
            console.log("request failed in update map manager");
        }
    });
}
    interval = setInterval(request, 1000);
}

function displayMoves(moves) {
    if (moves != -1 | moves != null) {
        moves.forEach(move => {
            showMovePrecinct(move);
        });
    }
}


function showMovePrecinct(move) {
    console.log("moving precint");
    var destID = move.destinationDistrictID;

    var feature = currentLayer.getFeatureById(move.precinctID);

//  var newColor = 'purple';
    var newColor = colors[destID]
//  if (destID <= colors.length) {
//	  newColor = colors[destID];
//  } else {
//	  newColor = colors[destID - (destID-colors.length)];
//  }
    currentLayer.overrideStyle(feature, {fillColor: newColor, fillOpacity: 0.4});
}

function toggleOn() {
  if (currentLayer != null) {
    var stateName = currentLayer.getProperty('name');
    if (stateName == "Oregon") orDistrictsLayer.setMap(map);
    else if (stateName == "Ohio") ohDistrictsLayer.setMap(map);
    else if (stateName == "Massachusetts") maDistrictsLayer.setMap(map);
  }
}

function toggleOff() {
  if (currentLayer != null) {
    var stateName = currentLayer.getProperty('name');
    if (stateName == "Oregon") orDistrictsLayer.setMap(null);
    else if (stateName == "Ohio") ohDistrictsLayer.setMap(null);
    else if (stateName == "Massachusetts") maDistrictsLayer.setMap(null);
  }
}
