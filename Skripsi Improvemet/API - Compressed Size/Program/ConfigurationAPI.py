
import configparser
import os
class Configuration:

    def __init__(self) :
        print("getting configuration")

        valueUsingEnv=os.getenv("skripsi-api")
        if(valueUsingEnv == None): #dev mode untuk dev mode 
            dir = os.path.dirname(__file__)
            filename = os.path.join(dir, 'Config/serverConf.ini')

            self.config = configparser.ConfigParser()
            self.config.read(filename)
            if type(self.config) is None:
                print("not found config file")
            server=self.config['API_SERVER']
            print("Configuration using Config File")
            self.valueUsingEnv=False
        else:
            print("Configuration using Env")
            self.valueUsingEnv=True
            
        

        

    def getDataBase(self):
        if(self.valueUsingEnv):
            database={
                "port":os.getenv("DBPort")  if os.getenv("DBPort") is not None else 9926  ,
                "database":os.getenv("DBName") if os.getenv("DBName") is not None else "skripsi" ,
                "iPAddress":os.getenv("DBiPAddress") if os.getenv("DBiPAddress") is not None else "localhost",
                "username":os.getenv("DBusername") if os.getenv("DBusername") is not None else "skripsiDimas" ,
                "password":os.getenv("DBpassword") if os.getenv("DBpassword") is not None else "skripsi123",
            }
        else:
            database=self.config['Database']

        return database

    def getServer(self):
        if(self.valueUsingEnv):
            server={
                "ipAddress":os.getenv("ApiPAddress") if os.getenv("ApiPAddress") is not  None else "0.0.0.0",
                "port":os.getenv("ApiPort") if os.getenv("ApiPort") is not None else 5000,
                "reload":os.getenv("ApiReload") if os.getenv("ApiReload") is not None else False,
                "worker":os.getenv("ApiWorker") if os.getenv("ApiWorker")is not None else 50,
            }
        else:
            server=self.config['API_SERVER']
        return server
    


