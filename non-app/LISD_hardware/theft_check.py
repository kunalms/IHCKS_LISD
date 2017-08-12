import requests
import time
import json

def stop_command(vehicle_id):
    theft=False
    userdata={}
    userdata["vehicle_id"]=vehicle_id
    data=json.dumps(userdata)
    headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}
    resp = requests.post('https://api.expertise97.hasura-app.io/api/get_flag_by_id', data=data,headers=headers,timeout=10)
    if resp.status_code==200:
        getdata=json.loads(resp.content.decode('utf-8'))
        if(getdata["Immobilized"]):
            theft=True
    return theft
