let profileUsername;

function setProfileUsername(username) {
    profileUsername = username;
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

function getFriends(username) {
    $.ajax({
        url: "http://localhost:8080/getFriends",
        type: 'GET',
        contentType: "application/json",
        headers: {
            "username": username
        },
        success: function(data) {
            console.log(data);
            establishFriendStatus(data);
        },
        error: function(jqXHR, textStatus, errorThrown){
            console.log(jqXHR.status);
            console.log(textStatus);
            console.log(errorThrown);
            // TODO - ERROR HANDLE
        }
    });
}

function checkIfUsersAreFriends(userFriends) {
    console.log(userFriends);

    if(userFriends.length !== 0) {
        userFriends.forEach(function(friend) {
            if(friend.username === localStorage.getItem('username')) {
                return true;
            }
        });
    }
    return false;
}

function establishFriendStatus(userFriends) {
    let sendFriendRequestButton = document.getElementById('friend-button');
    let usersAreFriends = checkIfUsersAreFriends(userFriends);

    if (!usersAreFriends) {
        sendFriendRequestButton.innerText = "Send Friend Request";
        sendFriendRequestButton.className="btn btn-default";
        sendFriendRequestButton.addEventListener('click', friendRequestListener);
        sendFriendRequestButton.hidden=false;
    } else {
        sendFriendRequestButton.innerText = "Friends";
        sendFriendRequestButton.className="btn btn-success";
        sendFriendRequestButton.removeEventListener('click', friendRequestListener);
        sendFriendRequestButton.hidden=false;
    }
}

function friendRequestListener() {
    $.ajax({
        url: "http://localhost:8080/sendFriendRequest",
        type: 'POST',
        headers: {
            "username": localStorage.getItem('username'),
            "friendUsername": profileUsername
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

function changeButtonToPending() {
    let pendingButton = document.getElementById('friend-button');
    pendingButton.className='btn btn-default';
    pendingButton.innerText='Pending Request';
}