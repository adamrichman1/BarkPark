let addDogButton = document.getElementById('add-dog-button');
let ownerUsername = sessionStorage.getItem('username');
let allDogs = null;

function setUserDogs(dogs) {
    allDogs = dogs;
}

console.log(ownerUsername);
addDogButton.addEventListener('click', function () {
    const formData = {
        "name": document.getElementById('new-dog-name').value,
        "ownerUsername": ownerUsername,
        "breed": document.getElementById('new-dog-breed').value,
        "age": document.getElementById('new-dog-age').value
    };
    console.log(formData);
    document.getElementById('add-dog-form').reset();
    $.ajax({
        url: "http://localhost:8080/addDog",
        type: 'POST',
        data: JSON.stringify(formData),
        contentType: "application/json",
        success: function() {
            allDogs.push(formData);
            console.log(allDogs);
            populateDogList(allDogs);
        },
        error: function(jqXHR, textStatus, errorThrown){
            console.log(jqXHR.status);
            console.log(textStatus);
            console.log(errorThrown);
            // TODO - ERROR HANDLE
        }
    });
});

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
            deleteButton.className = "btn btn-danger";
            deleteButton.textContent = 'Remove Dog';
            deleteButton.addEventListener('click', function() {
                const formData = {
                    "ownerUsername": sessionStorage.getItem('username'),
                    "name": dogName
                };
                removeDog(formData);
            });

            let rollButton = document.createElement('button');
            rollButton.id = "roll-dog-button";
            rollButton.className = "btn btn-warning";
            rollButton.textContent = 'Roll Over';
            rollButton.addEventListener('click', function() {
                window.open("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
            });

            dogData.append(nameElement);
            dogData.append(breedElement);
            dogData.append(ageElement);
            dogData.append(deleteButton);
            dogData.append(rollButton);

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
            'username': sessionStorage.getItem("username")
        },
        data: JSON.stringify(data),
        success: function() {
            allDogs.pop(data);
            console.log(allDogs);
            resetDogList();
            populateDogList(allDogs);
        },
        error: function(jqXHR, textStatus, errorThrown){
            console.log(jqXHR.status);
            console.log(textStatus);
            console.log(errorThrown);
            // TODO - ERROR HANDLE
        }
    });
}

function resetDogList() {
    let dogList = document.getElementById('dog-list');
    while(dogList.firstChild) {
        dogList.removeChild(dogList.firstChild);
    }
}