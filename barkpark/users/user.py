class User:
    def __init__(self, username, name, age, email):
        self.__username = username
        self.__name = name
        self.__age = age
        self.__email = email
        self.__dogs = {}
        pass

    def add_dog(self, dog):
        self.__dogs[dog.get_name()] = dog

    def remove_dog(self, dog_name):
        self.__dogs.pop(dog_name)
