window.addEventListener("load",initialize,true);


function initialize(){
    //initial call to gather all of user's dogs
    sendGetDogsRequest();

}

function sendGetDogsRequest() {
    $.ajax({
        url: "http://localhost:8080/getDogs",
        type: 'GET',
        contentType: "application/json",
        headers: {
            'username': localStorage.getItem("username")
        },
        success: function(data) {
            console.log(data);
            populateDogList(data);
        },
        error: function(jqXHR, textStatus, errorThrown){
            console.log(jqXHR.status);
            console.log(textStatus);
            console.log(errorThrown);
            // TODO - ERROR HANDLE
        }
    });
}

function populateDogList(dogs) {
    if (dogs.length !== 0) {
        let dogListHeader = document.getElementById('dog-list-header');
        dogListHeader.textContent = 'All Dogs';

        let dogList = document.getElementById('dog-list');

        for (let i = 0; i < dogs.length; i++) {
            let dogName = dogs[i].name;
            let dogData = document.createElement('div');

            let listItem = document.createElement('li');
            listItem.className = "list-group-item";

            let nameElement = document.createElement('p');
            nameElement.className = 'lead';
            nameElement.innerText = dogName;

            let breedElement = document.createElement('p');
            breedElement.className = 'small';
            breedElement.innerText = "Breed: " + dogs[i].breed;

            let ageElement = document.createElement('p');
            ageElement.className = 'small';
            ageElement.innerText = "Age: " + dogs[i].age;

            let deleteButton = document.createElement('button');
            deleteButton.id = "remove-dog-button";
            deleteButton.class = "btn btn-danger";
            deleteButton.textContent = 'Remove Dog';
            deleteButton.addEventListener('click', function() {
                const formData = {
                    "dogName": dogName
                };
                removeDog(formData);
            });

            dogData.append(nameElement);
            dogData.append(breedElement);
            dogData.append(ageElement);
            dogData.append(deleteButton);

            listItem.appendChild(dogData);

            dogList.appendChild(listItem);
        }
    } else {
        let dogListHeader = document.getElementById('dog-list-header');
        dogListHeader.innerHTML = '<em>No dogs added yet!</em>';
    }
}

function removeDog(data) {
    $.ajax({
        url: "http://localhost:8080/removeDog",
        type: 'DELETE',
        contentType: "application/json",
        headers: {
            'username': localStorage.getItem("username")
        },
        data: JSON.stringify(data),
        success: function() {
            sendGetDogsRequest();
        },
        error: function(jqXHR, textStatus, errorThrown){
            console.log(jqXHR.status);
            console.log(textStatus);
            console.log(errorThrown);
            // TODO - ERROR HANDLE
        }
    });
}