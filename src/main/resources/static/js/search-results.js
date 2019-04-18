function populateSearchResults(searchResults) {
    if (searchResults.length !== 0) {
        let requestHeader = document.getElementById('request-header');
        requestHeader.textContent = 'Search Results';

        let requestList = document.getElementById('request-list');
        for (let i = 0; i < searchResults.length; i++) {
            let name = searchResults[i].name;
            let userData = document.createElement('div');

            let listItem = document.createElement('li');
            listItem.className = "list-group-item";

            let nameElement = document.createElement('p');
            nameElement.className = 'lead';
            nameElement.innerText = name;

            let usernameElement = document.createElement('p');
            usernameElement.className = 'small';
            usernameElement.innerText = "Username: " + searchResults[i].username;

            let addFriendButton = document.createElement('button');
            addFriendButton.type = 'button';
            addFriendButton.className = 'btn btn-success';
            addFriendButton.innerText = 'Visit Profile';
            addFriendButton.addEventListener('click', function() {
                window.location = "http://localhost:8080/userProfile?username=" + searchResults[i].username;
            });

            userData.append(nameElement);
            userData.append(usernameElement);
            userData.append(addFriendButton);
            listItem.appendChild(userData);
            requestList.appendChild(listItem);
        }
    } else {
        let requestHeader = document.getElementById('request-header');
        requestHeader.innerHTML = '<em>No pending friend requests</em>';
    }
}