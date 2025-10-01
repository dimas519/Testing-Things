
import mysql.connector

class mysqlDB:
    def __init__(self,ip,port,database,username,password) :
        self.ip=ip
        self.port=port
        self.database=database
        self.username=username
        self.password=password
        
    def connect(self):
        self.mydb = mysql.connector.connect(
        
        host=self.ip,
        port=self.port,
        database=self.database,

        user=self.username,
        password=self.password
        )

    def closeConnect(self):
        self.mydb.close()

    def executeSelectQuery(self,query,dictionary=True):
        self.connect()
        mycursor = self.mydb.cursor( dictionary=dictionary)
        try:
            mycursor.execute(query)
            self.mydb.commit()  
            data=mycursor.fetchall() 
            return data
        except mysql.connector.IntegrityError as err:
            return False
        finally:
            mycursor.close()
            self.closeConnect()
        
    
    def executeNonSelectQuery(self ,query):
        self.connect()
        try:
            mycursor = self.mydb.cursor()
            mycursor.execute(query)
            self.mydb.commit()
            if mycursor.lastrowid == 0:
                return 1
            return mycursor.lastrowid
        except mysql.connector.IntegrityError as err:
            print(err)
            return None
        finally:
            self.closeConnect()