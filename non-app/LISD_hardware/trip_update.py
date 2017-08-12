from gpsdata import *
import requests
import time
import json
from datetime import datetime

def trip_cont(start_time,flag,tripid):
	comp=False
	while not comp:
		location=loc()
		dt=datetime.now()
		cur_time=int(time.mktime(dt.timetuple()))
		latitude=(location[0])
		longitude=(location[1])
		if latitude != "0.0" and longitude != "0.0":
			userdata={}
			userdata["trip_id"]=tripid
			userdata["user_id"]="109"
			userdata["vehicle_id"]="104"
			userdata["timestamp"]=cur_time
			userdata["latitude"]=latitude
			userdata["longitude"]=longitude
			userdata["is_trip_live"]=flag;
			data=json.dumps(userdata)
			headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}
			resp = requests.post('https://api.expertise97.hasura-app.io/api/update_trip', data=data,headers=headers,timeout=10)
		comp=True
	if flag:
		return cur_time-start_time;
