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

            userData.append(nameElement);
            userData.append(usernameElement);

            listItem.appendChild(userData);

            requestList.appendChild(listItem);
        }
    } else {
        let requestHeader = document.getElementById('request-header');
        requestHeader.innerHTML = '<em>No pending friend requests</em>';
    }
}