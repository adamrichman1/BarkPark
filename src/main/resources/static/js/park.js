function populatePeopleInPark(peopleInPark, parkName) {
    document.getElementById("park-label").innerText="Users in " + parkName;
    if (peopleInPark.length !== 0) {
        let requestList = document.getElementById('request-list');

        for (let i = 0; i < peopleInPark.length; i++) {
            let name = peopleInPark[i].name;
            let userData = document.createElement('div');

            let listItem = document.createElement('li');
            listItem.className = "list-group-item";

            let nameElement = document.createElement('p');
            nameElement.className = 'lead';
            nameElement.innerText = name;

            let usernameElement = document.createElement('p');
            usernameElement.className = 'small';
            usernameElement.innerText = "Username: " + peopleInPark[i].username;

            let visitProfileButton = document.createElement('button');
            visitProfileButton.type = 'button';
            visitProfileButton.className = 'btn btn-primary';
            visitProfileButton.innerText = 'Visit Profile';
            visitProfileButton.addEventListener('click', function () {
                window.location = "http://localhost:8080/userProfile?username=" + peopleInPark[i].username;
            });

            userData.append(nameElement);
            userData.append(usernameElement);
            userData.append(visitProfileButton);
            listItem.appendChild(userData);
            requestList.appendChild(listItem);
        }
    }
    else {
        let requestHeader = document.getElementById('request-header');
        requestHeader.innerHTML = '<em>No BarkPark users are currently in ' + parkName +'\n</em>';
    }
    if (!alreadyInPark(peopleInPark)) {
        let goToParkButton = document.createElement('button');
        goToParkButton.type = 'button';
        goToParkButton.className = 'btn btn-success';
        goToParkButton.innerText = 'Join Park';
        goToParkButton.addEventListener('click', function () {
            window.location = "http://localhost:8080/joinPark?parkName=" + parkName + "&username=" + sessionStorage.getItem("username");
        });
        document.getElementById('request-header').appendChild(goToParkButton);
    }
    else {
        let goToParkButton = document.createElement('button');
        goToParkButton.type = 'button';
        goToParkButton.className = 'btn btn-danger';
        goToParkButton.innerText = 'Leave Park';
        goToParkButton.addEventListener('click', function () {
            window.location = "http://localhost:8080/leavePark?parkName=" + parkName + "&username=" + sessionStorage.getItem("username");
        });
        document.getElementById('request-header').appendChild(goToParkButton);
    }
}

function alreadyInPark(users) {
    return users.some(function(user) {
        return user.username === sessionStorage.getItem("username");
    });
}