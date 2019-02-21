class Dog:
    def __init__(self, name, owner_username, breed, age):
        self.__name = name
        self.__owner_username = owner_username
        self.__breed = breed
        self.__age = age
        pass

    def get_name(self):
        return self.__name
