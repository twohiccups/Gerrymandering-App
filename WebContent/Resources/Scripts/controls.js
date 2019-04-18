var paused = false;
var inProgress = false;
var interval;


var current = new Date().getFullYear();
	min = current - 20;
	select = document.getElementById('year');

for (var i = current; i >= min; i--) {
	var option = document.createElement('option');
	option.value = i;
	option.innerHTML = i;
	select.appendChild(option);
}

function expand() {
    var display = document.getElementById("content").style.display;
    if (display === "none") {
    	document.getElementById("left-panel").style = "width:20%; transition-duration: 1s;";
    	document.getElementById("expander").style = "left:20%;transition-duration:1s;"
    	setTimeout(displayContent, 1000);
    }
	else {
	  document.getElementById("left-panel").style = "width:0; transition-duration: 1s;";
	  document.getElementById("content").style = "display:none;";
	  document.getElementById("expander").style = "left:0;transition-duration: 1s;"
	}
}

function displayContent() {
	 document.getElementById("content").style = "display:inline;";
 }

function toggleCheck(checkbox) {
    if (checkbox.checked == true) {
    	if (checkbox.name === "compactness")
		document.getElementById("compactness-expanded").style.display = "inline";
		else if (checkbox.name === "populationEquality")
			document.getElementById("population-equality-expanded").style.display = "inline";
		else if (checkbox.name === "consistency")
			document.getElementById("consistency-expanded").style.display = "inline";
		else if (checkbox.name === "partisanSymmetry")
			document.getElementById("partisan-symmetry-expanded").style.display = "inline";
		else if (checkbox.name === "politicalFairness")
			document.getElementById("political-fairness-expanded").style.display = "inline";
		else if (checkbox.name === "alignment")
			document.getElementById("alignment-expanded").style.display = "inline";
	}
	else {
		if (checkbox.name === "compactness")
			document.getElementById("compactness-expanded").style.display = "none";
		else if (checkbox.name === "populationEquality")
			document.getElementById("population-equality-expanded").style.display = "none";
		else if (checkbox.name === "consistency")
			document.getElementById("consistency-expanded").style.display = "none";
		else if (checkbox.name === "partisanSymmetry")
			document.getElementById("partisan-symmetry-expanded").style.display = "none";
		else if (checkbox.name === "politicalFairness")
			document.getElementById("political-fairness-expanded").style.display = "none";
		else if (checkbox.name === "alignment")
			document.getElementById("alignment-expanded").style.display = "none";
	 }
}


$(function() {
  $('.slider').on('input change', function(){
	  $(this).next($('.slider_label')).html(this.value);
  });
  $('.slider_label').each(function(){
	  var value = $(this).prev().attr('value');
      $(this).html(value);
  });
});


function initSearchBox(map) {

var input = document.getElementById('pac-input');
var searchBox = new google.maps.places.SearchBox(input);
map.controls[google.maps.ControlPosition.TOP_RIGHT].push(input);

// Bias the SearchBox results towards current map's viewport.
map.addListener('bounds_changed', function() {
  searchBox.setBounds(map.getBounds());
});

var markers = [];
// Listen for the event fired when the user selects a prediction and retrieve
// more details for that place.
searchBox.addListener('places_changed', function() {
  var places = searchBox.getPlaces();

  if (places.length == 0) {
    return;
  }

  // Clear out the old markers.
  markers.forEach(function(marker) {
    marker.setMap(null);
  });
  markers = [];

  // For each place, get the icon, name and location.
  var bounds = new google.maps.LatLngBounds();
  places.forEach(function(place) {
    if (!place.geometry) {
      console.log("Returned place contains no geometry");
      return;
    }
    var icon = {
      url: place.icon,
      size: new google.maps.Size(71, 71),
      origin: new google.maps.Point(0, 0),
      anchor: new google.maps.Point(17, 34),
      scaledSize: new google.maps.Size(25, 25)
    };

    // Create a marker for each place.
    markers.push(new google.maps.Marker({
      map: map,
      icon: icon,
      title: place.name,
      position: place.geometry.location
    }));

    if (place.geometry.viewport) {
      // Only geocodes have viewport.
          bounds.union(place.geometry.viewport);
        } else {
          bounds.extend(place.geometry.location);
        }
      });
      map.fitBounds(bounds);
    });
  }
