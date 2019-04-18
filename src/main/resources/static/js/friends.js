function populateFriendsList(friends) {
    if (friends.length !== 0) {
        let requestList = document.getElementById('request-list');

        for (let i = 0; i < friends.length; i++) {
            let name = friends[i].name;
            let userData = document.createElement('div');

            let listItem = document.createElement('li');
            listItem.className = "list-group-item";

            let nameElement = document.createElement('p');
            nameElement.className = 'lead';
            nameElement.innerText = name;

            let usernameElement = document.createElement('p');
            usernameElement.className = 'small';
            usernameElement.innerText = "Username: " + friends[i].username;

            let visitProfileButton = document.createElement('button');
            visitProfileButton.type = 'button';
            visitProfileButton.className = 'btn btn-primary';
            visitProfileButton.innerText = 'Visit Profile';
            visitProfileButton.addEventListener('click', function() {
                window.location = "http://localhost:8080/userProfile?username=" + friends[i].username;
            });

            userData.append(nameElement);
            userData.append(usernameElement);
            userData.append(visitProfileButton);
            listItem.appendChild(userData);
            requestList.appendChild(listItem);
        }
    } else {
        let requestHeader = document.getElementById('request-header');
        requestHeader.innerHTML = '<em>You have no friends :(</em>';
    }
}