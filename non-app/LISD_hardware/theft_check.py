import requests
import time
import json

def stop_command(vehicle_id):
    theft=False
    userdata={}
    userdata["vehicle_id"]=vehicle_id
    link = """https://api.expertise97.hasura-app.io/api/get_flag_by_id?vehicle_id={str1}""".format(str1=userdata["vehicle_id"])
    resp = requests.get(link,timeout=10)
    if resp.status_code==200:
        getdata=json.loads(resp.content.decode('utf-8'))
        print(getdata["immobilize"])
        if(getdata["immobilize"]):
            theft=True
    print(theft)
    return theft
