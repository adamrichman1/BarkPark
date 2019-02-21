class DBManager(object):
    def __init__(self):
        pass

    def _execute_query(self, sql, *query_params):
        """
        Used to execute a SQL query

        :param sql: the query string
        :param query_params: the parameters to prepare the query with
        :return: the data returned from the query
        """
        pass

    def _execute_update(self, sql, *query_params):
        """
        Used to execute an update query

        :param sql: the query string
        :param query_params: the parameters to prepare the query with
        :return: void
        """
        pass

    def __connect_to_db(self, db_url):
        """
        Used to connect to a DB before executing a query

        :param db_url: the URL of the DB to connect to
        :return: a connection object to the DB
        """
        pass

    def __close_connection(self, connection):
        """
        Used to close a connection to a DB after executing a query

        :param connection: the connection object to the DB to close
        :return: void
        """
        pass
