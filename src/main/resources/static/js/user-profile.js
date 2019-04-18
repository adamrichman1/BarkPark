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

            dogData.append(nameElement);
            dogData.append(breedElement);
            dogData.append(ageElement);

            listItem.appendChild(dogData);

            dogList.appendChild(listItem);
        }
    } else {
        let dogListHeader = document.getElementById('dog-list-header');
        dogListHeader.innerHTML = '<em>No dogs added yet!</em>';
    }
}

let friendManagementDiv = document.getElementById('friend-management');
let sendFriendRequestButton = document.createElement('send-friend-request-button');
sendFriendRequestButton.className="btn btn-success";
sendFriendRequestButton.innerText = "Send Friend Request";

// TODO: make a check friendship function.

sendFriendRequestButton.addEventListener('click', function () {
    const formData = {
        "sender": localStorage.getItem('username'),
        "receiver": document.getElementById('profile-username').value
    };
    $.ajax({
        url: "http://localhost:8080/sendFriendRequest",
        type: 'POST',
        data: JSON.stringify(formData),
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
});

function changeButtonToPending() {
    friendManagementDiv.removeChild(sendFriendRequestButton);
    let pendingButton = document.createElement('pending-button');
    pendingButton.className='btn btn-default';
    pendingButton.innerText='Pending Request';
    friendManagementDiv.appendChild(pendingButton);
}