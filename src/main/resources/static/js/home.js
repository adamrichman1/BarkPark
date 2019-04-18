// This example requires the Places library. Include the libraries=places
// parameter when you first load the API. For example:
let map;
let service = null;

function initMap() {
    // Create the map.
    let schenleyPark = {lat: 40.4348, lng: -79.9425};
    map = new google.maps.Map(document.getElementById('map'), {
        center: schenleyPark,
        zoom: 13
    });

    // Create the places service.
    service = new google.maps.places.PlacesService(map);
    let getNextPage = null;
    let moreButton = document.getElementById('more');
    moreButton.onclick = function() {
        searchCur();
        if (getNextPage) getNextPage();
    };

    search(schenleyPark);
}

function searchCur() {
    console.log(map.getCenter());
    search(map.getCenter());
}

function search(coordinates) {
    console.log(coordinates);
    // Perform a nearby search.
    service.nearbySearch(
        {location: coordinates, radius: 500, type: ['park']},
        function(results, status, pagination) {
            if (status !== 'OK') return;

            createMarkers(results);
        });
}

function createMarkers(places) {
    let bounds = new google.maps.LatLngBounds();
    let placesList = document.getElementById('places');
    while(placesList.firstChild) {
        placesList.removeChild(placesList.firstChild);
    }

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
        let placeLink = document.createElement('a');
        placeLink.setAttribute("href", "http://localhost:8080/park?parkName=" + place.name + "&username=" + sessionStorage.getItem("username"));
        placeLink.appendChild(li);
        placesList.appendChild(placeLink);

        bounds.extend(place.geometry.location);
    }
    map.fitBounds(bounds);
}

function findFriendsByName(name) {
    window.location = "http://localhost:8080/findFriendsByName?name=" + name;
}
