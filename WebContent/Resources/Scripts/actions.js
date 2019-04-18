var toggled = false;

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
function pause() {
    if (inProgress) {
        if (paused) {
            document.getElementById("updatemsg").innerHTML = "Algorithm is resumed.";
            document.getElementById("pauseicon").innerHTML = "<i class='far fa-pause-circle'>";
            updateMapManager();
        } else {
            document.getElementById("updatemsg").innerHTML = "Algorithm is paused.";
            document.getElementById("pauseicon").innerHTML = "<i class='far fa-play-circle'>";
            clearInterval(interval);
        }
        paused = !paused;
    }
}

function stop() {
    inProgress = false;
    paused = false;
    clearInterval(interval);
    document.getElementById("updatemsg").innerHTML = "Algorithm was cancelled.";
}

function save() {
    if (paused | !inProgress) {
        var fname = prompt("Enter a name for this map", "myMap");
        if (fname != null) { // user did not cancel prompt
            function request() {
                $.ajax({
                    url: 'save',
                    type: 'POST',
                    dataType: 'text',
                    data: {name: fname},
                    success: function (response) {
                        console.log("Map was saved.");
                    },
                    error: function (error) {
                        console.log("Map failed saving.");
                    }
                });
            }

            request();

        }
    }
}

function load(filename) {
    if (!inProgress) {
        if (filename != null) { // user did not cancel prompt
            function request() {
                $.ajax({
                    url: 'load',
                    type: 'POST',
                    dataType: 'json',
                    data:{filename: filename},
                    success: function (response) {
                        displayMoves(response);
                    },
                    error: function (error) {
                        console.log("Map failed loading.");
                    }
                });
            }
            request();
        }
    }
}

function deleteFile(filename) {
      if (filename != null) { // user did not cancel prompt
          function request() {
              $.ajax({
                  url: 'delete',
                  type: 'POST',
                  dataType: 'json',
                  data:{filename: filename},
                  success: function (response) {
                  },
                  error: function (error) {
                      console.log("failed deleting");
                  }
              });
          }
          request();
      }

}
