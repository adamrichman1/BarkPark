from barkpark.db_manager import DBManager


class UserDBManager(DBManager):
    def __init__(self):
        super(UserDBManager, self).__init__()

    def insert_user_to_db(self, user):
        """
        Used to add a user to the DB

        :param user:
        :return:
        """
        pass

    def get_user_profile(self, username):
        """
        Used to query for a user's profile based on the user's username

        :param username:
        :return:
        """
        pass

    def add_friend_request(self, username, friend_username):
        """
        Used to add a pending friend request between two users in the DB

        :param username:
        :param friend_username:
        :return:
        """
        pass

    def remove_friend_request(self, username, friend_username):
        """
        Used to remove a pending friend request between two users in the DB

        :param username:
        :param friend_username:
        :return:
        """
        pass

    def add_friend(self, username, friend_username):
        """
        Used to add a friendship between two users in the DB

        :param username:
        :param friend_username:
        :return:
        """

    def remove_friend(self, username, friend_username):
        """
        Used to remove a friendship between two users in the DB

        :param username:
        :param friend_username:
        :return:
        """