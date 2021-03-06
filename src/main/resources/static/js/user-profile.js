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
    let friends = null;
    $.ajax({
        async: false,
        url: "http://localhost:8080/getFriends",
        type: 'GET',
        contentType: "application/json",
        headers: {
            "username": username
        },
        success: function(data) {
            console.log(data);
            friends = data;
        },
        error: function(jqXHR, textStatus, errorThrown){
            console.log(jqXHR.status);
            console.log(textStatus);
            console.log(errorThrown);
            // TODO - ERROR HANDLE
        }
    });

    let friendRequests = null;
    $.ajax({
        async: false,
        url: "http://localhost:8080/getSentFriendRequests",
        type: 'GET',
        contentType: "application/json",
        headers: {
            "username": localStorage.getItem('username')
        },
        success: function(data) {
            console.log(data);
            friendRequests = data;
        },
        error: function(jqXHR, textStatus, errorThrown){
            console.log(jqXHR.status);
            console.log(textStatus);
            console.log(errorThrown);
            // TODO - ERROR HANDLE
        }
    });

    establishFriendStatus(friends, friendRequests, username);
}

function usersAreFriends(userFriends) {
    console.log(userFriends);
    return userFriends.some(function(friend) {
        return friend.username === sessionStorage.getItem("username");
    });
}

function pendingRequest(pendingRequests, otherUser) {
    return pendingRequests.some(function(friend) {
        return friend.username === otherUser;
    });
}

function establishFriendStatus(userFriends, pendingRequests, otherUser) {
    let unfriendButton = document.getElementById('unfriend-button');
    let sendFriendRequestButton = document.getElementById('friend-button');

    if (otherUser === sessionStorage.getItem("username")) {
        sendFriendRequestButton.hidden=true;
        unfriendButton.hidden=true;
    }
    else if(!usersAreFriends(userFriends) && !pendingRequest(pendingRequests, otherUser)) {
        sendFriendRequestButton.innerText = "Send Friend Request";
        sendFriendRequestButton.className="btn btn-default";
        sendFriendRequestButton.addEventListener('click', friendRequestListener);
        unfriendButton.setAttribute('hidden', 'true');
        sendFriendRequestButton.hidden=false;
    }
    else if (pendingRequest(pendingRequests, otherUser)) {
        changeButtonToPending();
    }
    else {
        sendFriendRequestButton.innerText = "Friends";
        sendFriendRequestButton.className="btn btn-success";
        sendFriendRequestButton.hidden=false;

        unfriendButton.innerText="Unfriend";
        unfriendButton.className="btn btn-danger";
        unfriendButton.addEventListener('click', unfriendListener);
        unfriendButton.hidden=false;
    }
}

function friendRequestListener() {
    $.ajax({
        url: "http://localhost:8080/sendFriendRequest",
        type: 'POST',
        headers: {
            "username": sessionStorage.getItem('username'),
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

function unfriendListener() {
    $.ajax({
        url: "http://localhost:8080/removeFriend",
        type: 'DELETE',
        headers: {
            "username": sessionStorage.getItem('username'),
            "friendUsername": profileUsername
        },
        contentType: "application/json",
        success: function() {
            window.location.reload();
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
    pendingButton.removeEventListener('click', friendRequestListener);
    pendingButton.className='btn btn-default';
    pendingButton.innerText='Pending Request';
}