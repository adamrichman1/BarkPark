from barkpark.db_manager import DBManager


class DogDBManager(DBManager):
    def __init__(self):
        super(DogDBManager, self).__init__()

    def insert_dog_to_db(self, dog):
        """
        Used to add a dog to the DB

        :param dog: the Dog object containing the dog's profile
        :return: void
        """
        pass

    def remove_dog_from_db(self, dog_name, owner_username):
        """
        Used to remove a dog from the dog DB table

        :param dog_name: the name of the dog to remove
        :param owner_username: the username of the owner to query for
        :return: void
        """
        pass

    def get_dog_profile(self, dog_name, owner_username):
        """
        Used to query for a dog's profile based on the dog's name

        :param dog_name: the name of the dog to query for
        :param owner_username: the username of the owner to query for
        :return: a Dog object containing the dog's profile
        """
        pass

    def update_dog_profile(self, dog):
        """
        Used to update a dog's profile

        :param dog: the new dog profile to add to the dog DB
        :return: void
        """

    def create_dog_table(self):
        """
        Used to create the dog DB table

        :return: void
        """
        pass
