from flask import Flask
from flask import request

app = Flask(__name__)


@app.route('/')
def hello_world():
    """
    Used to say Hello World!

    :return:
    """
    return 'Hello World!'


@app.route('/signup', methods=['POST'])
def sign_up():
    """
    Endpoint hit when a user attempts to sign up

    :return:
    """
    data = request.data


@app.route('/login', methods=['POST'])
def login():
    """
    Endpoint hit when a user attempts to log in

    :return:
    """
    data = request.data


@app.route('/logout', methods=['DELETE'])
def logout():
    """
    Endpoint hit when a user attempts to log out

    :return:
    """
    data = request.data


@app.route('/update_user_profile', methods=['UPDATE'])
def update_user_profile():
    """
    Endpoint hit when a user attempts to update their profile

    :return:
    """
    data = request.data


@app.route('/add_dog', methods=['POST'])
def add_dog():
    """
    Endpoint hit when a user attempts to add a dog to their profile

    :return:
    """
    data = request.data


@app.route('/update_dog_profile', methods=['UPDATE'])
def update_dog_profile():
    """
    Endpoint hit when a user attempts to update a dog's profile

    :return:
    """
    data = request.data


@app.route('/remove_dog', methods=['DELETE'])
def remove_dog():
    """
    Endpoint hit when a user attempts to remove a dog from their profile

    :return:
    """
    data = request.data


@app.route('/goto_park', methods=['POST'])
def goto_park():
    """
    Endpoint hit when a user attempts to inform the system they are in the park

    :return:
    """
    data = request.data


@app.route('/leave_park', methods=['POST'])
def leave_park():
    """
    Endpoint hit when a user attempts to tell the system they left the park

    :return:
    """
    data = request.data


@app.route('/start_dog_party', methods=['POST'])
def start_dog_party():
    """
    Endpoint hit when a user attempts to start a dog party

    :return:
    """
    data = request.data


@app.route('/join_dog_party', methods=['POST'])
def join_dog_party():
    """
    Endpoint hit when a user attempts to join a dog party

    :return:
    """
    data = request.data


@app.route('/end_dog_party', methods=['DELETE'])
def end_dog_party():
    """
    Endpoint hit when a user attempts to end a dog party

    :return:
    """
    data = request.data


@app.route('/send_friend_request', methods=['POST'])
def send_friend_request():
    """
    Endpoint hit when a user sends a friend request

    :return:
    """
    data = request.data


@app.route('/remove_friend', methods=['DELETE'])
def remove_friend():
    """
    Endpoint hit when a user removes a friend

    :return:
    """
    data = request.data


@app.route('/accept_friend_request', methods=['POST'])
def accept_friend_request():
    """
    Endpoint hit when a user accepts a friend request

    :return:
    """
    data = request.data


@app.route('/reject_friend_request', methods=['DELETE'])
def reject_friend_request():
    """
    Endpoint hit when a user rejects a friend request

    :return:
    """
    data = request.data


if __name__ == '__main__':
    app.run()
