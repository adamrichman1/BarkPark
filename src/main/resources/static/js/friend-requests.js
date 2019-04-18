function populateRequestList(requests) {
    if (requests.length !== 0) {
        let requestHeader = document.getElementById('request-header');
        requestHeader.textContent = 'Pending Friend Requests';

        let requestList = document.getElementById('request-list');

        for (let i = 0; i < requests.length; i++) {
            let name = requests[i].name;
            let userData = document.createElement('div');

            let listItem = document.createElement('li');
            listItem.className = "list-group-item";

            let nameElement = document.createElement('p');
            nameElement.className = 'lead';
            nameElement.innerText = name;

            let usernameElement = document.createElement('p');
            usernameElement.className = 'small';
            usernameElement.innerText = "Username: " + requests[i].username;

            let addFriendButton = document.createElement('button');
            addFriendButton.type = 'button';
            addFriendButton.className = 'btn btn-success';
            addFriendButton.innerText = 'Add Friend';
            addFriendButton.addEventListener('click', function() {
                $.ajax({
                    url: "http://localhost:8080/acceptFriendRequest",
                    type: 'POST',
                    headers: {
                        "username": sessionStorage.getItem('username'),
                        "friendUsername": requests[i].username
                    },
                    contentType: "application/json",
                    success: function() {
                        window.location = "http://localhost:8080/friends?username=" + sessionStorage.getItem('username');
                    },
                    error: function(jqXHR, textStatus, errorThrown){
                        console.log(jqXHR.status);
                        console.log(textStatus);
                        console.log(errorThrown);
                        // TODO - ERROR HANDLE
                    }
                });
            });

            let rejectFriendButton = document.createElement('button');
            rejectFriendButton.type = 'button';
            rejectFriendButton.className = 'btn btn-danger';
            rejectFriendButton.innerText = 'Reject Friend Request';
            rejectFriendButton.addEventListener('click', function() {
                $.ajax({
                    url: "http://localhost:8080/rejectFriendRequest",
                    type: 'DELETE',
                    headers: {
                        "username": sessionStorage.getItem('username'),
                        "friendUsername": requests[i].username
                    },
                    contentType: "application/json",
                    success: function() {
                        window.location = "http://localhost:8080/friends?username=" + sessionStorage.getItem('username');
                    },
                    error: function(jqXHR, textStatus, errorThrown){
                        console.log(jqXHR.status);
                        console.log(textStatus);
                        console.log(errorThrown);
                        // TODO - ERROR HANDLE
                    }
                });
            });

            userData.append(nameElement);
            userData.append(usernameElement);
            userData.append(addFriendButton);
            userData.append(rejectFriendButton);
            listItem.appendChild(userData);
            requestList.appendChild(listItem);
        }
    } else {
        let requestHeader = document.getElementById('request-header');
        requestHeader.innerHTML = '<em>No pending friend requests</em>';
    }
}