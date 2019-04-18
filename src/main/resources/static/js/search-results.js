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

            let visitProfileButton = document.createElement('button');
            visitProfileButton.type = 'button';
            visitProfileButton.className = 'btn btn-primary';
            visitProfileButton.innerText = 'Visit Profile';
            visitProfileButton.addEventListener('click', function() {
                window.location = "http://localhost:8080/userProfile?username=" + searchResults[i].username;
            });

            let backDiv = document.createElement('div');
            backDiv.className='main-div';

            let backToHomeButton = document.createElement('button');
            backToHomeButton.className = 'btn btn-primary';
            backToHomeButton.textContent = 'Go Back';
            backToHomeButton.addEventListener('click', function() {
                window.location.href="http://localhost:8080/home";
            });

            backDiv.appendChild(backToHomeButton);

            userData.append(nameElement);
            userData.append(usernameElement);
            userData.append(visitProfileButton);
            listItem.appendChild(userData);
            requestList.appendChild(listItem);
            requestList.appendChild(backDiv);
        }
    } else {
        let requestHeader = document.getElementById('request-header');
        requestHeader.innerHTML = '<em>No matching users were found</em>';
        let requestList = document.getElementById('request-list');

        let backDiv = document.createElement('div');
        backDiv.className='main-div';

        let backToHomeButton = document.createElement('button');
        backToHomeButton.className = 'btn btn-primary';
        backToHomeButton.textContent = 'Go Back';
        backToHomeButton.addEventListener('click', function() {
            window.location.href="http://localhost:8080/home";
        });

        backDiv.appendChild(backToHomeButton);
        requestList.appendChild(backDiv);
    }
}