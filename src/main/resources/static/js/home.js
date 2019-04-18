// This example requires the Places library. Include the libraries=places
// parameter when you first load the API. For example:
let map;

function initMap() {
    // Create the map.
    let schenleyPark = {lat: 40.4348, lng: -79.9425};
    map = new google.maps.Map(document.getElementById('map'), {
        center: schenleyPark,
        zoom: 13
    });

    // Create the places service.
    let service = new google.maps.places.PlacesService(map);
    let getNextPage = null;
    let moreButton = document.getElementById('more');
    moreButton.onclick = function() {
        moreButton.disabled = true;
        if (getNextPage) getNextPage();
    };

    // Perform a nearby search.
    service.nearbySearch(
        {location: schenleyPark, radius: 500, type: ['park']},
        function(results, status, pagination) {
            if (status !== 'OK') return;

            createMarkers(results);
            moreButton.disabled = !pagination.hasNextPage;
            getNextPage = pagination.hasNextPage && function() {
                pagination.nextPage();
            };
        });
}

function createMarkers(places) {
    let bounds = new google.maps.LatLngBounds();
    let placesList = document.getElementById('places');

    for (let i = 0, place; place = places[i]; i++) {
        let image = {
            url: place.icon,
            size: new google.maps.Size(71, 71),
            origin: new google.maps.Point(0, 0),
            anchor: new google.maps.Point(17, 34),
            scaledSize: new google.maps.Size(25, 25)
        };

        let marker = new google.maps.Marker({
            map: map,
            icon: image,
            title: place.name,
            position: place.geometry.location
        });

        let li = document.createElement('li');
        li.textContent = place.name;
        placesList.appendChild(li);

        bounds.extend(place.geometry.location);
    }
    map.fitBounds(bounds);
}

function searchFriendsByName(name) {
    $.ajax({
        url: "http://localhost:8080/sendFriendRequest",
        type: 'POST',
        headers: {
            "name": name
        },
        contentType: "application/json",
        success: function() {
            changeButtonToPending();
        },
        error: function(jqXHR, textStatus, errorThrown){
            console.log(jqXHR.status);
            console.log(textStatus);
            console.log(errorThrown);
            // TODO - ERROR HANDLE
        }
    });
}
